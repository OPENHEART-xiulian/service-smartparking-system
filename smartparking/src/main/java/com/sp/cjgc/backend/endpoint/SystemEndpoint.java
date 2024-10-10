package com.sp.cjgc.backend.endpoint;

import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.domain.SystemChargeRules;
import com.sp.cjgc.backend.domain.SystemParking;
import com.sp.cjgc.backend.domain.SystemParkingDetail;
import com.sp.cjgc.backend.domain.SystemRole;
import com.sp.cjgc.backend.domain.SystemUsers;
import com.sp.cjgc.backend.domain.SystemWeChatJsapiPay;
import com.sp.cjgc.backend.domain.query.SystemCameraDeviceQuery;
import com.sp.cjgc.backend.domain.query.SystemParkingDetailQuery;
import com.sp.cjgc.backend.domain.query.SystemRoleQuery;
import com.sp.cjgc.backend.domain.query.SystemUsersQuery;
import com.sp.cjgc.backend.endpoint.converter.SystemCameraDeviceConverter;
import com.sp.cjgc.backend.endpoint.converter.SystemChargeRulesConverter;
import com.sp.cjgc.backend.endpoint.converter.SystemParkingConverter;
import com.sp.cjgc.backend.endpoint.converter.SystemParkingDetailConverter;
import com.sp.cjgc.backend.endpoint.converter.SystemRoleConverter;
import com.sp.cjgc.backend.endpoint.converter.SystemUsersConverter;
import com.sp.cjgc.backend.endpoint.converter.SystemWeChatJsapiPayConverter;
import com.sp.cjgc.backend.endpoint.request.SystemCameraDeviceReq;
import com.sp.cjgc.backend.endpoint.request.SystemChargeRulesReq;
import com.sp.cjgc.backend.endpoint.request.SystemParkingDetailReq;
import com.sp.cjgc.backend.endpoint.request.SystemParkingReq;
import com.sp.cjgc.backend.endpoint.request.SystemRoleReq;
import com.sp.cjgc.backend.endpoint.request.SystemUsersReq;
import com.sp.cjgc.backend.endpoint.request.SystemWeChatJsapiPayReq;
import com.sp.cjgc.backend.endpoint.response.SystemCameraDeviceResp;
import com.sp.cjgc.backend.endpoint.response.SystemChargeRulesResp;
import com.sp.cjgc.backend.endpoint.response.SystemParkingDetailResp;
import com.sp.cjgc.backend.endpoint.response.SystemParkingResp;
import com.sp.cjgc.backend.endpoint.response.SystemRoleResp;
import com.sp.cjgc.backend.endpoint.response.SystemUsersResp;
import com.sp.cjgc.backend.endpoint.response.SystemWeChatJsapiPayResp;
import com.sp.cjgc.backend.service.SystemCameraDeviceService;
import com.sp.cjgc.backend.service.SystemChargeRulesService;
import com.sp.cjgc.backend.service.SystemParkingDetailService;
import com.sp.cjgc.backend.service.SystemParkingService;
import com.sp.cjgc.backend.service.SystemRoleService;
import com.sp.cjgc.backend.service.SystemUsersService;
import com.sp.cjgc.backend.service.SystemWeChatJsapiPayService;
import com.sp.cjgc.common.annotation.PassToken;
import com.sp.cjgc.common.exception.RequestObject;
import com.sp.cjgc.common.exception.ResponseObject;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.utils.AuthorityType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统管理-用户表(SystemUsers)表控制层
 *
 * @author zoey
 * @since 2024-08-14 09:10:41
 */
@RestController
@Api(tags = "系统管理")
@RequestMapping("systemManagement")
public class SystemEndpoint {

    @Autowired
    private SystemRoleService systemRoleService;
    @Autowired
    private SystemUsersService systemUsersService;
    @Autowired
    private SystemParkingService systemParkingService;
    @Autowired
    private SystemChargeRulesService systemChargeRulesService;
    @Autowired
    private SystemCameraDeviceService systemCameraDeviceService;
    @Autowired
    private SystemParkingDetailService systemParkingDetailService;
    @Autowired
    private SystemWeChatJsapiPayService systemWeChatJsapiPayService;

    @PassToken(required = false)
    @ApiOperation(value = "用户-分页查询列表")
    @PostMapping(value = "/pageUserList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<SystemUsersResp.ListDTO> pageUserList(@RequestBody RequestObject<SystemUsersReq.QueryDTO> ro) {
        // 请求数据转换
        SystemUsersQuery query = SystemUsersConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = systemUsersService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(SystemUsersConverter.INSTANCE.toPage(resp));
    }

    @ApiOperation(value = "用户-更改密码")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/updatePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<SystemUsersResp.SystemUsersDTO> updatePassword(@RequestBody RequestObject<SystemUsersReq.UpdatePasswordDTO> ro) {
        // 请求数据转换
        SystemUsersQuery query = SystemUsersConverter.INSTANCE.toQuery(ro.getBody());
        // 用户登录
        return ResponseObject.success(SystemUsersConverter.INSTANCE.toDTO(systemUsersService.updatePassword(query)));
    }

    @ApiOperation(value = "用户-新增或编辑")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/userCreateOrUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<SystemUsersResp.SystemUsersDTO> userCreateOrUpdate(@RequestBody RequestObject<SystemUsersReq.CreateOrUpdateDTO> ro) {
        // 请求数据转换
        SystemUsers newEntity = SystemUsersConverter.INSTANCE.toEntity(ro.getBody());
        // 用户登录
        return ResponseObject.success(SystemUsersConverter.INSTANCE.toDTO(systemUsersService.createOrUpdateUser(newEntity)));
    }

    @ApiOperation(value = "用户-删除")
    @PassToken(required = false, authority = AuthorityType.DELETE)
    @PostMapping(value = "/deleteUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> deleteUser(@RequestBody RequestObject<SystemUsersReq.DeleteDTO> ro) {
        // 删除
        systemUsersService.removeUser(ro.getBody().getIds());
        // 响应数据转换
        return ResponseObject.success();
    }

    @ApiOperation(value = "用户-重置密码")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/resettingPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> resettingPassword(@RequestBody RequestObject<SystemUsersReq.ResettingPasswordDTO> ro) {
        // 删除
        systemUsersService.resettingPassword(ro.getBody().getIds(), ro.getBody().getPassword());
        // 响应数据转换
        return ResponseObject.success();
    }

    @ApiOperation(value = "用户-编辑(只编辑头像和用户名称)")
    @PassToken(required = false, verifyPermissions = false)
    @PostMapping(value = "/userUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<SystemUsersResp.SystemUsersDTO> userUpdate(@RequestBody RequestObject<SystemUsersReq.UpdateDTO> ro) {
        // 请求数据转换
        SystemUsers newEntity = SystemUsersConverter.INSTANCE.toSpUsers(ro.getBody());
        // 用户编辑
        return ResponseObject.success(SystemUsersConverter.INSTANCE.toDTO(systemUsersService.updateUser(newEntity)));
    }

    @ApiOperation(value = "用户-更新状态")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/updateUserStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> updateUserStatus(@RequestBody RequestObject<SystemUsersReq.UpdateStatusDTO> ro) {
        // 更新用户状态
        systemUsersService.updateStatus(ro.getBody().getIds(), ro.getBody().getStatus());
        return ResponseObject.success();
    }

    @PassToken(required = false)
    @ApiOperation(value = "角色-分页查询列表")
    @PostMapping(value = "/pageRoleList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<SystemRoleResp.ListDTO> pageRoleList(@RequestBody RequestObject<SystemRoleReq.QueryDTO> ro) {
        // 请求数据转换
        SystemRoleQuery query = SystemRoleConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = systemRoleService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(SystemRoleConverter.INSTANCE.toPage(resp));
    }

    @ApiOperation(value = "角色-新增或修改")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/createOrUpdateRole", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> createOrUpdateRole(@RequestBody RequestObject<SystemRoleReq.CreateOrUpdateDTO> ro) {
        // 请求数据转换
        SystemRole entity = SystemRoleConverter.INSTANCE.toEntity(ro.getBody());
        // 调用服务
        systemRoleService.saveOrUpdate(entity);
        // 响应数据转换
        return ResponseObject.success();
    }

    @PassToken(required = false)
    @ApiOperation(value = "摄像头设备-查询列表")
    @PostMapping(value = "/getCameraList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<List<SystemCameraDeviceResp.SystemCameraDeviceDTO>> getCameraList(
            @RequestBody RequestObject<SystemCameraDeviceReq.QueryDTO> ro
    ) {
        // 请求数据转换
        SystemCameraDeviceQuery query = SystemCameraDeviceConverter.INSTANCE.toQuery(ro.getBody());
        // 响应数据转换
        return ResponseObject.success(SystemCameraDeviceConverter.INSTANCE.toListDTO(systemCameraDeviceService.getList(query)));
    }

    @ApiOperation(value = "摄像头设备-新增或修改")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/newOrUpdatedCamera", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> newOrUpdatedCamera(@RequestBody RequestObject<SystemCameraDeviceReq.CreateOrUpdateDTO> ro) {
        // 请求数据转换
        SystemCameraDevice entity = SystemCameraDeviceConverter.INSTANCE.toEntity(ro.getBody());
        // 新增或更新
        systemCameraDeviceService.createOrUpdate(entity);
        // 响应数据转换
        return ResponseObject.success();
    }

    @ApiOperation(value = "摄像头设备-删除")
    @PassToken(required = false, authority = AuthorityType.DELETE)
    @PostMapping(value = "/deleteCamera", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> deleteCamera(@RequestBody RequestObject<SystemCameraDeviceReq.DeleteDTO> ro) {
        // 删除
        systemCameraDeviceService.removeBatchByIds(ro.getBody().getIds());
        // 响应数据转换
        return ResponseObject.success();
    }

    @PassToken(required = false)
    @ApiOperation(value = "收费规则-查询")
    @PostMapping(value = "/getChargeRules")
    public ResponseObject<SystemChargeRulesResp.SystemChargeRulesDTO> getChargeRules() {
        // 响应数据转换
        return ResponseObject.success(SystemChargeRulesConverter.INSTANCE.toDTO(systemChargeRulesService.getEntity()));
    }

    @ApiOperation(value = "收费规则-更新")
    @PostMapping(value = "/updateChargeRules")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    public ResponseObject<SystemChargeRulesResp.SystemChargeRulesDTO> updateChargeRules(
            @RequestBody RequestObject<SystemChargeRulesReq.UpdateDTO> ro
    ) {
        // 请求数据转换
        SystemChargeRules entity = SystemChargeRulesConverter.INSTANCE.toEntity(ro.getBody());
        // 响应数据转换
        return ResponseObject.success(SystemChargeRulesConverter.INSTANCE.toDTO(systemChargeRulesService.updateEntity(entity)));
    }

    @PassToken(required = false)
    @ApiOperation(value = "车位配置-查询")
    @PostMapping(value = "/getParking")
    public ResponseObject<SystemParkingResp.SystemParkingDTO> getParking() {
        // 响应数据转换
        return ResponseObject.success(SystemParkingConverter.INSTANCE.toDTO(systemParkingService.getEntity()));
    }

    @ApiOperation(value = "车位配置-更新")
    @PostMapping(value = "/updateParking")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    public ResponseObject<SystemParkingResp.SystemParkingDTO> updateParking(@RequestBody RequestObject<SystemParkingReq.UpdateDTO> ro) {
        // 请求数据转换
        SystemParking entity = SystemParkingConverter.INSTANCE.toEntity(ro.getBody());
        // 响应数据转换
        return ResponseObject.success(SystemParkingConverter.INSTANCE.toDTO(systemParkingService.updateEntity(entity)));
    }

    @PassToken(required = false)
    @ApiOperation(value = "租赁车位-分页查询列表")
    @PostMapping(value = "/pageParkingDetailList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<SystemParkingDetailResp.ListDTO> pageParkingDetailList(@RequestBody RequestObject<SystemParkingDetailReq.QueryDTO> ro) {
        // 请求数据转换
        SystemParkingDetailQuery query = SystemParkingDetailConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = systemParkingDetailService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(SystemParkingDetailConverter.INSTANCE.toPage(resp));
    }

    @ApiOperation(value = "租赁车位-新增或编辑")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/parkingDetailCreateOrUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<SystemParkingDetailResp.SystemParkingDetailDTO> parkingDetailCreateOrUpdate(
            @RequestBody RequestObject<SystemParkingDetailReq.CreateOrUpdateDTO> ro
    ) {
        // 请求数据转换
        SystemParkingDetail entity = SystemParkingDetailConverter.INSTANCE.toEntity(ro.getBody());
        // 响应数据转换
        return ResponseObject.success(SystemParkingDetailConverter.INSTANCE.toDTO(systemParkingDetailService.createOrUpdate(entity)));
    }

    @PassToken(required = false)
    @ApiOperation(value = "租赁车位-统计")
    @PostMapping(value = "/parkingDetailSum")
    public ResponseObject<Integer> parkingDetailSum() {
        // 响应数据转换
        return ResponseObject.success(systemParkingDetailService.sumAssignedNumber());
    }

    @ApiOperation(value = "租赁车位-删除")
    @PassToken(required = false, authority = AuthorityType.DELETE)
    @PostMapping(value = "/parkingDetailDelete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> parkingDetailDelete(@RequestBody RequestObject<SystemParkingDetailReq.DeleteDTO> ro) {
        systemParkingDetailService.removeBatchByIds(ro.getBody().getIds());
        return ResponseObject.success();
    }

    @PassToken(required = false)
    @ApiOperation(value = "微信JSAPI支付配置-查询")
    @PostMapping(value = "/getWeChatJsapiPay")
    public ResponseObject<SystemWeChatJsapiPayResp.SystemWeChatJsapiPayDTO> getWeChatJsapiPay() {
        // 响应数据转换
        return ResponseObject.success(SystemWeChatJsapiPayConverter.INSTANCE.toDTO(systemWeChatJsapiPayService.getWeChatJsapiPay()));
    }

    @ApiOperation(value = "微信JSAPI支付配置-新增或更新")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/weChatJsapiPayCreateOrUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<SystemWeChatJsapiPayResp.SystemWeChatJsapiPayDTO> weChatJsapiPayCreateOrUpdate(
            @RequestBody RequestObject<SystemWeChatJsapiPayReq.CreateOrUpdateDTO> ro
    ) {
        // 请求数据转换
        SystemWeChatJsapiPay entity = SystemWeChatJsapiPayConverter.INSTANCE.toEntity(ro.getBody());
        // 响应数据转换
        return ResponseObject.success(SystemWeChatJsapiPayConverter.INSTANCE.toDTO(systemWeChatJsapiPayService.createOrUpdate(entity)));
    }

    @PostMapping(value = "/uploadPrivateKey")
    @ApiOperation(value = "微信JSAPI支付配置-私钥文件上传")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @ApiImplicitParam(name = "files", value = "文件上传", required = true, dataType = "MultipartFile", allowMultiple = true, paramType = "query")
    public ResponseObject<String> uploadPrivateKey(@RequestParam("files") MultipartFile[] files) {
        // 响应数据转换
        return ResponseObject.success(systemWeChatJsapiPayService.uploadPrivateKey(files[0]));
    }

    /**
     * 专门用于给前端使用
     *
     * @return
     */
    @PostMapping(value = "/getAppid")
    @ApiOperation(value = "微信JSAPI支付配置-公众号APPID查询")
    public ResponseObject<String> getAppid() {
        systemWeChatJsapiPayService.getWeChatJsapiPay();
        return ResponseObject.success(systemWeChatJsapiPayService.getAppId());
    }
}
