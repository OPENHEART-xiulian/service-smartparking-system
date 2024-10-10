package com.sp.cjgc.backend.endpoint.converter;

import com.sp.cjgc.backend.domain.OrderPaidCatOutbound;
import com.sp.cjgc.backend.domain.query.OrderPaidCatOutboundQuery;
import com.sp.cjgc.backend.endpoint.request.OrderPaidCatOutboundReq;
import com.sp.cjgc.backend.endpoint.response.OrderPaidCatOutboundResp;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 订单-车辆出库已支付订单记录(OrderPaidCatOutbound) 转换工具类
 *
 * @author zoey
 * @since 2024-08-20 13:50:20
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface OrderPaidCatOutboundConverter {

    OrderPaidCatOutboundConverter INSTANCE = Mappers.getMapper(OrderPaidCatOutboundConverter.class);

    /**
     * 订单-车辆出库已支付订单记录 查询条件入参转换成 OrderPaidCatOutboundQuery 对象
     *
     * @param queryDTO
     * @return
     */
    OrderPaidCatOutboundQuery toQuery(OrderPaidCatOutboundReq.QueryDTO queryDTO);

    /**
     * 订单-车辆出库已支付订单记录 对象响应数据转换
     *
     * @param entity
     * @return
     */
    OrderPaidCatOutboundResp.OrderPaidCatOutboundDTO toDTO(OrderPaidCatOutbound entity);

    /**
     * 订单-车辆出库已支付订单记录 列表响应数据转换
     *
     * @param list
     * @return
     */
    List<OrderPaidCatOutboundResp.OrderPaidCatOutboundDTO> toListDTO(List<OrderPaidCatOutbound> list);

    /**
     * 分页查询 订单-车辆出库已支付订单记录 列表响应数据转换
     *
     * @param query
     * @return
     */
    OrderPaidCatOutboundResp.ListDTO toPage(PageInfoRespQuery query);
}
