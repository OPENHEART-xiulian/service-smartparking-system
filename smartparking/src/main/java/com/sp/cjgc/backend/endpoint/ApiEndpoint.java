package com.sp.cjgc.backend.endpoint;

import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.OrderPaidCatOutbound;
import com.sp.cjgc.backend.domain.ParkCollectCoupons;
import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.endpoint.converter.OrderPaidCatOutboundConverter;
import com.sp.cjgc.backend.endpoint.converter.ParkCollectCouponsConverter;
import com.sp.cjgc.backend.endpoint.request.ApiReq;
import com.sp.cjgc.backend.endpoint.request.OrderPaidCatOutboundReq;
import com.sp.cjgc.backend.endpoint.response.OrderPaidCatOutboundResp;
import com.sp.cjgc.backend.endpoint.response.ParkCollectCouponsResp;
import com.sp.cjgc.backend.order.EnterParkingUtil;
import com.sp.cjgc.backend.service.OrderPaidCatOutboundService;
import com.sp.cjgc.backend.service.ParkCollectCouponsService;
import com.sp.cjgc.backend.service.SystemCameraDeviceService;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.common.exception.RequestObject;
import com.sp.cjgc.common.exception.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 16:11:10
 * @Description:
 */
@Slf4j
@RestController
@Api(tags = "移动端")
@RequestMapping("api")
public class ApiEndpoint {

    /**
     * 服务对象
     */
    @Autowired
    private OrderPaidCatOutboundService orderPaidCatOutboundService;
    @Autowired
    private ParkCollectCouponsService parkCollectCouponsService;
    @Autowired
    private SystemCameraDeviceService systemCameraDeviceService;

    @ApiOperation(value = "车辆订单查询-出口支付-提前支付")
    @PostMapping(value = "/queryOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<OrderPaidCatOutboundResp.OrderPaidCatOutboundDTO> queryOrder(
            @RequestBody RequestObject<OrderPaidCatOutboundReq.QueryOrderDTO> ro
    ) {
        // 响应数据转换
        return ResponseObject.success(
                OrderPaidCatOutboundConverter.INSTANCE.toDTO(
                        orderPaidCatOutboundService.getEntity(
                                ro.getBody().getPlates(),
                                ro.getBody().getDeviceIp())
                )
        );
    }

    @ApiOperation(value = "统计剩余抵用券")
    @PostMapping(value = "/assignedNumber", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<ParkCollectCouponsResp.ParkCollectDTO> assignedNumber(@RequestBody RequestObject<ApiReq.QueryDTO> ro
    ) {
        LocalDateTime now = LocalDateTime.now();
        long timestamp = now.toInstant(ZoneOffset.UTC).toEpochMilli();
        if (null != ro.getBody().getTimestamp() && ro.getBody().getTimestamp() > 0) {
            // 判断时间戳是否有值
            if (ro.getBody().getTimestamp() < timestamp) throw new BizException(RespEnum.OVERDUE_ERROR);
        }
        // 调用服务
        Map<String, Object> params = parkCollectCouponsService.assignedNumber(ro.getBody().getUserId());
        // 响应数据转换
        return ResponseObject.success(ParkCollectCouponsConverter.INSTANCE.toDTO(params));
    }

    @ApiOperation(value = "停车领劵")
    @PostMapping(value = "/createParkCollect", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<ParkCollectCouponsResp.ParkCollectCouponsDTO> createParkCollect(
            @RequestBody RequestObject<ApiReq.CreateDTO> ro
    ) {
        // 调用服务
        ParkCollectCoupons entity = parkCollectCouponsService.userCollectCoupons(
                ro.getBody().getUserId()
                , ro.getBody().getPlates()
                , ro.getBody().getTimestamp()
        );
        // 响应数据转换
        return ResponseObject.success(ParkCollectCouponsConverter.INSTANCE.toDTO(entity));
    }

    @ApiOperation(value = "无牌车扫码进场")
    @PostMapping(value = "/qrCodeCarEnter")
    public ResponseObject<?> qrCodeCarEnter(@RequestBody RequestObject<OrderPaidCatOutboundReq.Query1DTO> ro) {
        if (StringUtils.isBlank(ro.getBody().getPhoneNumber())) return ResponseObject.error(RespEnum.INPUT_ERROR_1);
        if (StringUtils.isBlank(ro.getBody().getDeviceIp())) return ResponseObject.error(RespEnum.INPUT_ERROR_2);
        if (StringUtils.isBlank(ro.getBody().getDevicePort())) return ResponseObject.error(RespEnum.INPUT_ERROR_3);
        // 根据设备的IP和端口号，查询该设备信息
        SystemCameraDevice cameraDevice = systemCameraDeviceService.getEntity(
                ro.getBody().getDeviceIp(),
                ro.getBody().getDevicePort()
        );
        // 创建车辆进场记录
        EnterParkingUtil.createRecordCarEnterByPhoneNumber(ro.getBody().getPhoneNumber(), cameraDevice);
        // 响应数据转换
        return ResponseObject.success();
    }

    @ApiOperation(value = "无牌车扫码出场")
    @PostMapping(value = "/qrCodeCarOut")
    public ResponseObject<OrderPaidCatOutboundResp.OrderPaidCatOutboundDTO> qrCodeCarOut(
            @RequestBody RequestObject<OrderPaidCatOutboundReq.Query1DTO> ro
    ) {
        if (StringUtils.isBlank(ro.getBody().getPhoneNumber())) return ResponseObject.error(RespEnum.INPUT_ERROR_1);
        if (StringUtils.isBlank(ro.getBody().getDeviceIp())) return ResponseObject.error(RespEnum.INPUT_ERROR_2);
        if (StringUtils.isBlank(ro.getBody().getDevicePort())) return ResponseObject.error(RespEnum.INPUT_ERROR_3);
        // 响应数据转换
        OrderPaidCatOutbound order = orderPaidCatOutboundService.createOrder(
                ro.getBody().getPhoneNumber(),
                ro.getBody().getDeviceIp()
        );
        return ResponseObject.success(OrderPaidCatOutboundConverter.INSTANCE.toDTO(order));
    }
}
