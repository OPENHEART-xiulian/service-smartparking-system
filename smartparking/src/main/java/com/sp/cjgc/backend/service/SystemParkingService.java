package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.SystemParking;

/**
 * 系统管理-车位数量表(SystemParking)表服务接口
 *
 * @author zoey
 * @since 2024-08-15 16:24:51
 */
public interface SystemParkingService extends IService<SystemParking> {

    /**
     * 系统管理-车位数量表-查询对象
     *
     * @return
     */
    SystemParking getEntity();

    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    SystemParking updateEntity(SystemParking entity);
}
