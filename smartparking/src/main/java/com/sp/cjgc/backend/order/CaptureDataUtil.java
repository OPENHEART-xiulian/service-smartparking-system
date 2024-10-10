package com.sp.cjgc.backend.order;

import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.service.SystemCameraDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Author: Zoey
 * @Since: 2024-08-16 10:15:21
 * @Description:
 */
@Slf4j
@Component
public class CaptureDataUtil {


    @Autowired
    private SystemCameraDeviceService systemCameraDeviceService;

    private static SystemCameraDeviceService staticSystemCameraDeviceService;

    @PostConstruct
    public void init() {
        staticSystemCameraDeviceService = this.systemCameraDeviceService;
    }

    /**
     * 处理摄像头一体机抓拍到的数据生成车辆进出记录
     *
     * @param entity
     */
    public static void createCaptureData(String license, String deviceIP, short devicePort) {
        // 根据设备的IP和端口号，查询该设备信息
        SystemCameraDevice cameraDevice = staticSystemCameraDeviceService.getEntity(deviceIP, String.valueOf(devicePort));
        // 判断是否存在该设备信息
        if (Objects.nonNull(cameraDevice)) {
            // 判断是出口还是进口 设备作用，1｜进；0｜出
            if (cameraDevice.getDeviceRole() == 1) {
                // 进口
                EnterParkingUtil.createRecordCarEnter(license, cameraDevice);
            } else {
                // 出口
                ExitParkingUtil.createRecordsCarOut(license, cameraDevice);
            }
        } else {
            log.info("IP为：【{}】的摄像头一体机设备信息不存在", deviceIP);
        }
    }
}
