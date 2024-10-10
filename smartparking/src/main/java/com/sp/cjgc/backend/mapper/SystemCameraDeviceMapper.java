package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.domain.query.SystemCameraDeviceQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统管理-摄像头设备管理表(SystemCameraDevice)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-14 10:25:42
 */
@Repository
public interface SystemCameraDeviceMapper extends BaseMapper<SystemCameraDevice> {

    /**
     * 查询 系统管理-摄像头设备管理表 列表
     *
     * @param query
     * @return
     */
    List<SystemCameraDevice> getList(@Param("query") SystemCameraDeviceQuery query);

    /**
     * 查询 系统管理-摄像头设备管理表
     *
     * @param deviceIp
     * @param devicePort
     * @return
     */
    SystemCameraDevice getEntity(@Param("deviceIp") String deviceIp, @Param("devicePort") String devicePort);
}
