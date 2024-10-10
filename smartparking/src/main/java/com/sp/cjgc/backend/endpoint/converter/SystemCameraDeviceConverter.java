package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.domain.query.SystemCameraDeviceQuery;
import com.sp.cjgc.backend.endpoint.request.SystemCameraDeviceReq;
import com.sp.cjgc.backend.endpoint.response.SystemCameraDeviceResp;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统管理-摄像头设备管理表(SystemCameraDevice) 转换工具类
 *
 * @author zoey
 * @since 2024-08-14 10:25:42
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SystemCameraDeviceConverter {

    SystemCameraDeviceConverter INSTANCE = Mappers.getMapper(SystemCameraDeviceConverter.class);

    /**
     * 系统管理-摄像头设备管理表 数据新增或更新入参转换成 SpCameraDevice 对象
     *
     * @param dto
     * @return
     */
    SystemCameraDevice toEntity(SystemCameraDeviceReq.CreateOrUpdateDTO dto);

    /**
     * 系统管理-摄像头设备管理表 查询条件入参转换成 SpCameraDeviceQuery 对象
     *
     * @param queryDTO
     * @return
     */
    SystemCameraDeviceQuery toQuery(SystemCameraDeviceReq.QueryDTO queryDTO);

    /**
     * 系统管理-摄像头设备管理表 对象响应数据转换
     *
     * @param entity
     * @return
     */
    SystemCameraDeviceResp.SystemCameraDeviceDTO toDTO(SystemCameraDevice entity);

    /**
     * 系统管理-摄像头设备管理表 列表响应数据转换
     *
     * @param list
     * @return
     */
    List<SystemCameraDeviceResp.SystemCameraDeviceDTO> toListDTO(List<SystemCameraDevice> list);
}
