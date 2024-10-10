package com.sp.cjgc.backend.endpoint;

import com.sp.cjgc.backend.domain.CarVisitor;
import com.sp.cjgc.backend.domain.CarVpiMonthlyRent;
import com.sp.cjgc.backend.domain.query.CarVisitorQuery;
import com.sp.cjgc.backend.domain.query.CarVpiMonthlyRentQuery;
import com.sp.cjgc.backend.domain.query.RecordCarEnterQuery;
import com.sp.cjgc.backend.domain.query.RecordsCarOutboundQuery;
import com.sp.cjgc.backend.endpoint.converter.CarVisitorConverter;
import com.sp.cjgc.backend.endpoint.converter.CarVpiMonthlyRentConverter;
import com.sp.cjgc.backend.endpoint.converter.RecordCarEnterConverter;
import com.sp.cjgc.backend.endpoint.converter.RecordsCarOutboundConverter;
import com.sp.cjgc.backend.endpoint.request.CarVisitorReq;
import com.sp.cjgc.backend.endpoint.request.CarVpiMonthlyRentReq;
import com.sp.cjgc.backend.endpoint.request.RecordCarEnterReq;
import com.sp.cjgc.backend.endpoint.request.RecordsCarOutboundReq;
import com.sp.cjgc.backend.endpoint.response.CarVisitorResp;
import com.sp.cjgc.backend.endpoint.response.CarVpiMonthlyRentResp;
import com.sp.cjgc.backend.endpoint.response.RecordCarEnterResp;
import com.sp.cjgc.backend.endpoint.response.RecordsCarOutboundResp;
import com.sp.cjgc.backend.service.CarVisitorService;
import com.sp.cjgc.backend.service.CarVpiMonthlyRentService;
import com.sp.cjgc.backend.service.RecordCarEnterService;
import com.sp.cjgc.backend.service.RecordsCarOutboundService;
import com.sp.cjgc.common.annotation.PassToken;
import com.sp.cjgc.common.exception.RequestObject;
import com.sp.cjgc.common.exception.ResponseObject;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.utils.AuthorityType;
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
 * @Since: 2024-08-14 13:26:32
 * @Description:
 */
@RestController
@Api(tags = "车辆管理")
@RequestMapping("vehicleManagement")
public class VehicleEndpoint {

    @Autowired
    private CarVisitorService carVisitorService;
    @Autowired
    private RecordCarEnterService recordCarEnterService;
    @Autowired
    private CarVpiMonthlyRentService carVpiMonthlyRentService;
    @Autowired
    private RecordsCarOutboundService recordsCarOutboundService;

    @PassToken(required = false)
    @ApiOperation(value = "访客车登记-分页查询管列表")
    @PostMapping(value = "/visitorVansPageList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<CarVisitorResp.ListDTO> visitorVansPageList(@RequestBody RequestObject<CarVisitorReq.QueryDTO> ro) {
        // 请求数据转换
        CarVisitorQuery query = CarVisitorConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = carVisitorService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(CarVisitorConverter.INSTANCE.toPage(resp));
    }

    @ApiOperation(value = "访客车登记-新增或修改")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/newOrUpdatedVisitorVans", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> newOrUpdatedVisitorVans(@RequestBody RequestObject<CarVisitorReq.CreateOrUpdateDTO> ro) {
        // 请求数据转换
        CarVisitor entity = CarVisitorConverter.INSTANCE.toEntity(ro.getBody());
        // 新增或更新
        carVisitorService.createOrUpdate(entity);
        // 响应数据转换
        return ResponseObject.success();
    }

    @ApiOperation(value = "访客车登记-删除")
    @PassToken(required = false, authority = AuthorityType.DELETE)
    @PostMapping(value = "/deleteVisitorVans", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> deleteVisitorVans(@RequestBody RequestObject<CarVisitorReq.DeleteDTO> ro) {
        // 删除
        carVisitorService.removeBatchByIds(ro.getBody().getIds());
        // 响应数据转换
        return ResponseObject.success();
    }

    @PassToken(required = false)
    @ApiOperation(value = "vip月租车登记-分页查询列表")
    @PostMapping(value = "/monthlyCarPageList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<CarVpiMonthlyRentResp.ListDTO> monthlyCarPageList(@RequestBody RequestObject<CarVpiMonthlyRentReq.QueryDTO> ro) {
        // 请求数据转换
        CarVpiMonthlyRentQuery query = CarVpiMonthlyRentConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = carVpiMonthlyRentService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(CarVpiMonthlyRentConverter.INSTANCE.toPage(resp));
    }

    @ApiOperation(value = "vip月租车登记-新增或修改")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/newOrUpdatedMonthlyCar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> newOrUpdatedMonthlyCar(@RequestBody RequestObject<CarVpiMonthlyRentReq.CreateOrUpdateDTO> ro) {
        // 请求数据转换
        CarVpiMonthlyRent entity = CarVpiMonthlyRentConverter.INSTANCE.toEntity(ro.getBody());
        // 新增或更新
        carVpiMonthlyRentService.createOrUpdate(entity);
        // 响应数据转换
        return ResponseObject.success();
    }

    @ApiOperation(value = "vip月租车登记-删除")
    @PassToken(required = false, authority = AuthorityType.DELETE)
    @PostMapping(value = "/deleteMonthlyCar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> deleteMonthlyCar(@RequestBody RequestObject<CarVpiMonthlyRentReq.DeleteDTO> ro) {
        // 删除
        carVpiMonthlyRentService.removeBatchByIds(ro.getBody().getIds());
        // 响应数据转换
        return ResponseObject.success();
    }

    @PassToken(required = false)
    @ApiOperation(value = "车辆进场记录-分页查询列表")
    @PostMapping(value = "/getInventoryPageList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<RecordCarEnterResp.ListDTO> getInventoryPageList(@RequestBody RequestObject<RecordCarEnterReq.QueryDTO> ro) {
        // 请求数据转换
        RecordCarEnterQuery query = RecordCarEnterConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = recordCarEnterService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(RecordCarEnterConverter.INSTANCE.toPage(resp));
    }

    @PassToken(required = false)
    @ApiOperation(value = "车辆出场记录-分页查询列表")
    @PostMapping(value = "/getOutboundPageList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<RecordsCarOutboundResp.ListDTO> getOutboundPageList(@RequestBody RequestObject<RecordsCarOutboundReq.QueryDTO> ro) {
        // 请求数据转换
        RecordsCarOutboundQuery query = RecordsCarOutboundConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = recordsCarOutboundService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(RecordsCarOutboundConverter.INSTANCE.toPage(resp));
    }
}
