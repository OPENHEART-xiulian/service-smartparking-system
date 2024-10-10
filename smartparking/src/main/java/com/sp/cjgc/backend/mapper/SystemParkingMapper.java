package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.SystemParking;
import org.springframework.stereotype.Repository;

/**
 * 系统管理-车位数量表(SystemParking)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-15 16:24:50
 */
@Repository
public interface SystemParkingMapper extends BaseMapper<SystemParking> {

    /**
     * 查询 系统管理-车位数量表 对象
     *
     * @return
     */
    SystemParking getEntity();
}
