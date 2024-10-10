package com.sp.cjgc.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.domain.query.SystemCameraDeviceQuery;
import com.sp.cjgc.backend.mapper.SystemCameraDeviceMapper;
import com.sp.cjgc.backend.service.SystemCameraDeviceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统管理-摄像头设备管理表(SystemCameraDevice)表服务实现类
 *
 * @author zoey
 * @since 2024-08-14 10:25:42
 */
@Service
public class SystemCameraDeviceServiceImpl extends ServiceImpl<SystemCameraDeviceMapper, SystemCameraDevice> implements SystemCameraDeviceService {

    /**
     * 系统管理-摄像头设备管理表-查询列表
     *
     * @param query
     * @return
     */
    @Override
    public List<SystemCameraDevice> getList(SystemCameraDeviceQuery query) {
        // 查询列表
        return this.baseMapper.getList(query);
    }

    /**
     * 系统管理-摄像头设备管理表-新增或修改
     *
     * @param entity
     */
    @Override
    public void createOrUpdate(SystemCameraDevice entity) {
        // 判断是否存在ID
        if (StringUtils.isBlank(entity.getId())) {
            entity.setCreateTime(LocalDateTime.now());
        } else {
            entity.setUpdateTime(LocalDateTime.now());
        }
        this.saveOrUpdate(entity);
    }

    /**
     * 系统管理-摄像头设备管理表-根据IP和端口查询
     *
     * @param deviceIp
     * @param devicePort
     * @return
     */
    @Override
    public SystemCameraDevice getEntity(String deviceIp, String devicePort) {
        return this.baseMapper.getEntity(deviceIp, devicePort);
    }
}
