package com.sp.cjgc.hik.utils;

import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.hik.sdk.HCNetSDK;
import com.sun.jna.Pointer;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: Zoey
 * @Since: 2024-08-15 13:05:33
 * @Description: 海康闸机控制
 */
@Slf4j
public class GateUtil {

    static HCNetSDK hcNetSDK = HCNetSDK.INSTANCE;

    /**
     * 闸机控制
     *
     * @param controlGate 是否开启控制闸 true|开启  false｜关闭
     * @param controlType 开关状态 0｜关； 1｜开
     * @param entity      设备信息
     * @return
     */
    public static void controlGate(Boolean controlGate, int controlType, SystemCameraDevice entity) {
        log.info("【{}】闸机控制", controlGate ? "开启" : "未开启");
        if (controlGate) {
            // 设备登陆
            loginDevice(entity);
            // 创建 道闸控制 对象
            HCNetSDK.NET_DVR_BARRIERGATE_CFG cfg = new HCNetSDK.NET_DVR_BARRIERGATE_CFG();
            // 通道号
            cfg.dwChannel = 1;
            // 结构体大小
            cfg.dwSize = cfg.size();
            // 道闸号：0-表示无效值(设备需要做有效值判断)，1-道闸1
            cfg.byLaneNo = 1;
            // 控制参数  0-关闭道闸，1-开启道闸，2-停止道闸，3-锁定道闸
            cfg.byBarrierGateCtrl = (byte) controlType;
            // 保留，置为0
            cfg.byRes[0] = 0;
            // 控制闸
            Pointer name = cfg.getPointer();
            cfg.write();

            boolean isControl = hcNetSDK.NET_DVR_RemoteControl(entity.getUserId(), 3128, name, cfg.size());
            String message = controlType == 1 ? "开启道闸" : "关闭道闸";
            if (!isControl) {
                int error = hcNetSDK.NET_DVR_GetLastError();
                log.info("【{}】失败，异常码：【{}】", message, error);
                hcNetSDK.NET_DVR_Logout(entity.getUserId());
            }
        }
    }

    /**
     * 闸机设备登陆
     *
     * @param entity 设备信息
     */
    private static void loginDevice(SystemCameraDevice entity) {
        // 判断设备是否登陆成功
        if (null == entity.getUserId() || entity.getUserId() == -1) {
            // 初始化设备
            hcNetSDK.NET_DVR_Init();
            // 设备登陆
            int lUserID = DeviceLoginUtil.loginDevice(hcNetSDK, -1,
                    entity.getDeviceIp(), entity.getDevicePort(),
                    entity.getDeviceUserName(), entity.getDevicePassword());
            entity.setUserId(lUserID);
        }
    }
}
