package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.ParkCollectCoupons;
import com.sp.cjgc.backend.endpoint.response.ParkCollectCouponsResp;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

/**
 * 商户-停车领劵(ParkCollectCoupons) 转换工具类
 *
 * @author zoey
 * @since 2024-08-22 09:56:37
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface ParkCollectCouponsConverter {

    ParkCollectCouponsConverter INSTANCE = Mappers.getMapper(ParkCollectCouponsConverter.class);

    /**
     * 商户-停车领劵 对象响应数据转换
     *
     * @param entity
     * @return
     */
    ParkCollectCouponsResp.ParkCollectCouponsDTO toDTO(ParkCollectCoupons entity);

    /**
     * 剩余抵用券数量响应数据转换
     *
     * @param params
     * @return
     */
    default ParkCollectCouponsResp.ParkCollectDTO toDTO(Map<String, Object> params) {
        if (params == null) {
            return null;
        } else {
            ParkCollectCouponsResp.ParkCollectDTO parkCollectDTO = new ParkCollectCouponsResp.ParkCollectDTO();
            parkCollectDTO.setAssignedNumber(params.get("assignedNumber") == null ? "0" : params.get("assignedNumber").toString());
            parkCollectDTO.setAssignedStatus(params.get("assignedStatus") == null ? "0" : params.get("assignedStatus").toString());
            parkCollectDTO.setUserName(params.get("userName") == null ? "" : params.get("userName").toString());
            return parkCollectDTO;
        }
    }
}
