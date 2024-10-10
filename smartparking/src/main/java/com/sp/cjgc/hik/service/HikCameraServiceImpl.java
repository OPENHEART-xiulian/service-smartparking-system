package com.sp.cjgc.hik.service;//package com.sp.cjgc.hik.service;

import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.service.SystemCameraDeviceService;
import com.sp.cjgc.hik.sdk.HCNetSDK;
import com.sp.cjgc.hik.utils.DeviceLoginUtil;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Zoey
 * @Since: 2024-08-15 09:20:40
 * @Description: 海康摄像头布防
 *     备注：如果有设备信息，可以放开 implements CommandLineRunner
 */
@Slf4j
@Component
public class HikCameraServiceImpl
//        implements CommandLineRunner
{

    @Autowired
    private SystemCameraDeviceService spCameraDeviceService;

    @Autowired
    private FMSGCallBack_V31 fmsgCallBack_v31;

    static HCNetSDK hcNetSDK = HCNetSDK.INSTANCE;

    private ExecutorService executor;

    // 报警布防句柄
    static int lAlarmHandle = -1;

    static boolean keepRunning = true;

    public void run(String... args) {
        log.info("开始启动海康摄像头事件监听...");
        List<SystemCameraDevice> cameraDevices = spCameraDeviceService.list();
        //官方的说法：linux系统建议调用以下接口加载组件库
        DeviceLoginUtil.isLinux(hcNetSDK);
        // 设备初始化
        boolean initSuccess = hcNetSDK.NET_DVR_Init();

        // 设置线程池数量，考虑是否需要根据实际情况调整线程池大小
        this.executor = Executors.newFixedThreadPool(cameraDevices.size());
        try {
            for (SystemCameraDevice cameraDevice : cameraDevices) {
                executor.execute(() -> {
                    try {
                        // 第一步：初始化SDK
                        if (initSuccess) {
                            String UserData = "UserData";
                            HCNetSDK.BYTE_ARRAY UserDataByte = new HCNetSDK.BYTE_ARRAY(25);
                            UserDataByte.read();
                            UserDataByte.byValue = UserData.getBytes();
                            UserDataByte.write();
                            Pointer pUserDataByte = UserDataByte.getPointer();

                            // 第二步：设置报警回调函数
                            if (!hcNetSDK.NET_DVR_SetDVRMessageCallBack_V31(fmsgCallBack_v31, pUserDataByte)) {
                                log.info("设备IP【{}】设置回调函数失败,失败原因：{}", cameraDevice.getDeviceIp(), hcNetSDK.NET_DVR_GetLastError());
                                return;
                            } else {
                                log.info("设备IP【{}】设置回调函数成功", cameraDevice.getDeviceIp());
                            }

                            // 第三步：登录设备
                            int lUserID = DeviceLoginUtil.loginDevice(hcNetSDK, -1,
                                    cameraDevice.getDeviceIp(), cameraDevice.getDevicePort(),
                                    cameraDevice.getDeviceUserName(), cameraDevice.getDevicePassword());

                            // 判断设备是否登陆成功
                            if (lUserID > -1) {
                                // 更新摄像头设备ID
                                cameraDevice.setUserId(lUserID);
                                spCameraDeviceService.updateById(cameraDevice);

                                log.info("设备IP：【{}】 设备所在位置：【{}】登录成功", cameraDevice.getDeviceIp(), cameraDevice.getDeviceLocation());
                                // 第四步：报警布防
                                keepRunning = DeviceLoginUtil.setAlarm(hcNetSDK, lUserID, lAlarmHandle, keepRunning, cameraDevice.getDeviceIp());

                                // 第五步：保持 长连接
                                while (true) {
                                    if (!keepRunning) break;
                                }

                                // 第六步：撤防、注销、释放SDK资源
                                DeviceLoginUtil.cleanup(hcNetSDK, lUserID, lAlarmHandle, cameraDevice.getDeviceIp());
                            } else {
                                log.info("设备IP：【{}】 登录失败", cameraDevice.getDeviceIp());
                            }
                        } else {
                            log.info("设备初始化失败");
                        }
                    } catch (Exception e) {
                        log.error("监控异常: 相机IP={}", cameraDevice.getDeviceIp(), e);
                    }
                });
            }
            log.info("海康摄像头事件监听服务已启动，共启动{}个监听任务。", cameraDevices.size());
        } finally {
            // 注册一个shutdown hook来确保应用退出时能够关闭线程池
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("应用正在关闭，关闭线程池...");
                executor.shutdown();
                try {
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                        log.info("线程池未在规定时间内关闭，已强制关闭");
                    }
                } catch (InterruptedException ie) {
                    executor.shutdownNow();
                    Thread.currentThread().interrupt();
                    log.error("线程池关闭时出现中断异常", ie);
                }
            }));
        }
    }
}
