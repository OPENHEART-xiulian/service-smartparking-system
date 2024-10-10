package com.sp.cjgc.backend.endpoint;

import com.sp.cjgc.MyConstants;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.query.MerchantReconciliationQuery;
import com.sp.cjgc.backend.endpoint.converter.MerchantReconciliationConverter;
import com.sp.cjgc.backend.endpoint.converter.ParkCollectCouponsConverter;
import com.sp.cjgc.backend.endpoint.request.MerchantReconciliationReq;
import com.sp.cjgc.backend.endpoint.request.ParkCollectCouponsReq;
import com.sp.cjgc.backend.endpoint.response.MerchantReconciliationResp;
import com.sp.cjgc.backend.endpoint.response.ParkCollectCouponsResp;
import com.sp.cjgc.backend.service.MerchantReconciliationService;
import com.sp.cjgc.backend.service.ParkCollectCouponsService;
import com.sp.cjgc.backend.utils.QRCodeUtil;
import com.sp.cjgc.common.annotation.PassToken;
import com.sp.cjgc.common.exception.RequestObject;
import com.sp.cjgc.common.exception.ResponseObject;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;
import com.sp.cjgc.common.utils.AuthorityType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * 商户-对账记录(MerchantReconciliation)表控制层
 *
 * @author zoey
 * @since 2024-08-20 13:13:44
 */
@RestController
@Api(tags = "商户")
@RequestMapping("merchantReconciliation")
public class MerchantEndpoint {

    @Value("${file.qrCode}")
    private String qrCodeUrl;

    @Value("${file.thirdParty}")
    private Boolean thirdParty;

    /**
     * 服务对象
     */
    @Autowired
    private ParkCollectCouponsService parkCollectCouponsService;
    @Autowired
    private MerchantReconciliationService merchantReconciliationService;

    @PassToken(required = false)
    @ApiOperation(value = "对账记录-分页查询列表")
    @PostMapping(value = "/pageList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<MerchantReconciliationResp.ListDTO> pageList(@RequestBody RequestObject<MerchantReconciliationReq.QueryDTO> ro) {
        // 请求数据转换
        MerchantReconciliationQuery query = MerchantReconciliationConverter.INSTANCE.toQuery(ro.getBody());
        // 调用服务
        PageInfoRespQuery resp = merchantReconciliationService.getPageList(query);
        // 响应数据转换
        return ResponseObject.success(MerchantReconciliationConverter.INSTANCE.toPage(resp));
    }

    @ApiOperation(value = "对账记录-确认发放")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    @PostMapping(value = "/updateStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<?> updateStatus(@RequestBody RequestObject<MerchantReconciliationReq.UpdateStatusDTO> ro) {
        // 调用服务
        merchantReconciliationService.updateStatus(ro.getBody().getId());
        // 响应数据转换
        return ResponseObject.success();
    }

    @ApiOperation(value = "统计剩余抵用券")
    @PassToken(required = false, verifyPermissions = false)
    @PostMapping(value = "/assignedNumber", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject<ParkCollectCouponsResp.ParkCollectDTO> assignedNumber(
            @RequestBody RequestObject<ParkCollectCouponsReq.CountDTO> ro
    ) {
        // 调用服务
        Map<String, Object> params = parkCollectCouponsService.assignedNumber(ro.getBody().getUserId());
        // 响应数据转换
        return ResponseObject.success(ParkCollectCouponsConverter.INSTANCE.toDTO(params));
    }

    @ApiOperation(value = "扫码领劵")
    @GetMapping(value = "/generateQRCode")
    @PassToken(required = false, verifyPermissions = false)
    public ResponseObject<String> generateQRCode(
            @ApiParam(value = "商户ID", required = true) @RequestParam String userId,
            @ApiParam(value = "跳转地址，地址结尾不需要拼接/", required = true) @RequestParam String url) {
        if (userId == null || userId.isEmpty())
            return ResponseObject.error(RespEnum.FAILURE.getCode(), "请输入商户ID");
        if (url == null || url.isEmpty())
            return ResponseObject.error(RespEnum.FAILURE.getCode(), "请输入跳转地址");
        // 移除 url 的最后一个字符
        if (url.endsWith("/")) url = url.substring(0, url.length() - 1);
        // 获取当前时间并加上 30 分钟
        LocalDateTime thirtyMinutesLater = LocalDateTime.now().plusMinutes(MyConstants.QR_TIME);
        // 将 LocalDateTime 转换为 Unix 时间戳
        long timestamp = thirtyMinutesLater.toInstant(ZoneOffset.UTC).toEpochMilli();
        // 二维码地址内容
        String content = url + "?userId=" + userId + "&timestamp=" + timestamp;
        // 响应数据转换
        return ResponseObject.success(QRCodeUtil.generateQRCode(userId, qrCodeUrl, thirdParty, content));
    }
}
