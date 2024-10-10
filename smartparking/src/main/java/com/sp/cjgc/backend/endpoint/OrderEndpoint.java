package com.sp.cjgc.backend.endpoint;

import com.sp.cjgc.backend.domain.query.OrderPaidCatOutboundQuery;
import com.sp.cjgc.backend.endpoint.converter.OrderPaidCatOutboundConverter;
import com.sp.cjgc.backend.endpoint.request.OrderPaidCatOutboundReq;
import com.sp.cjgc.backend.endpoint.response.OrderPaidCatOutboundResp;
import com.sp.cjgc.backend.service.OrderPaidCatOutboundService;
import com.sp.cjgc.common.annotation.PassToken;
import com.sp.cjgc.common.exception.RequestObject;
import com.sp.cjgc.common.exception.ResponseObject;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Zoey
 * @Since: 2024-08-20 14:07:03
 * @Description:
 */
@RestController
@Api(tags = "订单管理")
@RequestMapping("orderEndpoint")
public class OrderEndpoint {

    /**
     * 服务对象
     */
    @Autowired
    private OrderPaidCatOutboundService orderPaidCatOutboundService;

    @PassToken(required = false)
    @ApiOperation(value = "车辆出库订单记录-分页查询列表")
    @PostMapping(value = "/pageList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<OrderPaidCatOutboundResp.ListDTO> pageList(@RequestBody RequestObject<OrderPaidCatOutboundReq.QueryDTO> ro) {
        // 请求数据转换
        OrderPaidCatOutboundQuery query = OrderPaidCatOutboundConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = orderPaidCatOutboundService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(OrderPaidCatOutboundConverter.INSTANCE.toPage(resp));
    }
}
