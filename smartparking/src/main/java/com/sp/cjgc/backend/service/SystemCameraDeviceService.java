package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.domain.query.SystemCameraDeviceQuery;

import java.util.List;

/**
 * 系统管理-摄像头设备管理表(SystemCameraDevice)表服务接口
 *
 * @author zoey
 * @since 2024-08-14 10:25:42
 */
public interface SystemCameraDeviceService extends IService<SystemCameraDevice> {

    List<SystemCameraDevice> getList(SystemCameraDeviceQuery query);

    void createOrUpdate(SystemCameraDevice entity);

    SystemCameraDevice getEntity(String deviceIp, String devicePort);
}
