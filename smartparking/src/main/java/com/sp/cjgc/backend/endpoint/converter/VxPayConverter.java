package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.WxChatPayDto;
import com.sp.cjgc.backend.endpoint.request.WxPayReq;
import com.sp.cjgc.backend.domain.WxPayReqParam;
import com.sp.cjgc.backend.endpoint.response.WxPayResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Zoey
 * @Since: 2024-09-05 14:09:09
 * @Description:
 */
@Mapper
public interface VxPayConverter {

    VxPayConverter INSTANCE = Mappers.getMapper(VxPayConverter.class);

    WxPayReqParam toEntity(WxPayReq.QueryDTO dto);

    WxPayResp.RespDTO toDto(WxChatPayDto dto);
}
