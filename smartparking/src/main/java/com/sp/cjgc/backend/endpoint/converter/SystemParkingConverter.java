package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.SystemParking;
import com.sp.cjgc.backend.endpoint.request.SystemParkingReq;
import com.sp.cjgc.backend.endpoint.response.SystemParkingResp;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统管理-车位数量表(SystemParking) 转换工具类
 *
 * @author zoey
 * @since 2024-08-15 16:24:52
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SystemParkingConverter {

    SystemParkingConverter INSTANCE = Mappers.getMapper(SystemParkingConverter.class);

    /**
     * 系统管理-车位数量表 数据更新入参转换成 SystemParking 对象
     *
     * @param dto
     * @return
     */
    SystemParking toEntity(SystemParkingReq.UpdateDTO dto);

    /**
     * 系统管理-车位数量表 对象响应数据转换
     *
     * @param entity
     * @return
     */
    SystemParkingResp.SystemParkingDTO toDTO(SystemParking entity);
}
