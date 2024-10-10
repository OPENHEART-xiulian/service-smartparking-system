package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.RecordsCarOutbound;
import com.sp.cjgc.backend.domain.query.RecordsCarOutboundQuery;
import com.sp.cjgc.backend.endpoint.request.RecordsCarOutboundReq;
import com.sp.cjgc.backend.endpoint.response.RecordsCarOutboundResp;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 记录-车辆出场记录表(RecordsCarOutbound) 转换工具类
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface RecordsCarOutboundConverter {

    RecordsCarOutboundConverter INSTANCE = Mappers.getMapper(RecordsCarOutboundConverter.class);

    /**
     * 记录-车辆出场记录表 查询条件入参转换成 RecordsCarOutboundQuery 对象
     *
     * @param queryDTO
     * @return
     */
    RecordsCarOutboundQuery toQuery(RecordsCarOutboundReq.QueryDTO queryDTO);

    /**
     * 记录-车辆出场记录表 对象响应数据转换
     *
     * @param entity
     * @return
     */
    RecordsCarOutboundResp.RecordsCarOutboundDTO toDTO(RecordsCarOutbound entity);

    /**
     * 记录-车辆出场记录表 列表响应数据转换
     *
     * @param list
     * @return
     */
    List<RecordsCarOutboundResp.RecordsCarOutboundDTO> toListDTO(List<RecordsCarOutbound> list);

    /**
     * 分页查询 记录-车辆出场记录表 列表响应数据转换
     *
     * @param query
     * @return
     */
    RecordsCarOutboundResp.ListDTO toPage(PageInfoRespQuery query);
}
