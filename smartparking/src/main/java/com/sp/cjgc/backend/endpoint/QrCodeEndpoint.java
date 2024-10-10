package com.sp.cjgc.backend.endpoint;

import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.SystemCameraDevice;
import com.sp.cjgc.backend.service.SystemCameraDeviceService;
import com.sp.cjgc.backend.utils.QRCodeUtil;
import com.sp.cjgc.common.annotation.PassToken;
import com.sp.cjgc.common.exception.ResponseObject;
import com.sp.cjgc.common.utils.AuthorityType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: Zoey
 * @Since: 2024-09-09 14:44:44
 * @Description:
 */
@RestController
@Api(tags = "二维码生成")
@RequestMapping("qrcodeEndpoint")
public class QrCodeEndpoint {

    @Value("${file.qrCode}")
    private String qrCodeUrl;

    @Value("${file.thirdParty}")
    private Boolean thirdParty;

    @Autowired
    private SystemCameraDeviceService systemCameraDeviceService;

    @GetMapping(value = "/enterQrCode")
    @ApiOperation(value = "生成无牌车进出场二维码")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    public ResponseObject<String> enterQrCode(
            @ApiParam(value = "进出库设备IP") @RequestParam String deviceIp,
            @ApiParam(value = "进出库设备端口号") @RequestParam String devicePort,
            @ApiParam(value = "跳转地址,前端提供,地址结尾不需要拼接?") @RequestParam String url
    ) {
        // 根据IP和端口查询数据
        SystemCameraDevice entity = systemCameraDeviceService.getEntity(deviceIp, devicePort);
        // 判断数据是否存在
        if (Objects.isNull(entity)) return ResponseObject.error(RespEnum.FAILURE.getCode(), "设备不存在");
        // 判断是否在该设备处收费
        if (0 == entity.getIsToll())
            return ResponseObject.error(RespEnum.FAILURE.getCode(), "未配置在" + entity.getDeviceLocation() + "处收费");
        // 二维码地址内容
        String content = getUrl(url, "?") + "?devicePort=" + devicePort + "&deviceIp=" + deviceIp;
        // 默认 定义为无牌车进场二维码名称
        String qrCodeName = deviceIp + devicePort;
        // 去掉.
        qrCodeName = qrCodeName.replaceAll("\\.", "");
        // 响应数据转换
        return ResponseObject.success(QRCodeUtil.generateQRCode(qrCodeName, qrCodeUrl, thirdParty, content));
    }


    @GetMapping(value = "/playQRCode")
    @ApiOperation(value = "生成有牌车支付二维码")
    @PassToken(required = false, authority = AuthorityType.CREATE)
    public ResponseObject<String> playQRCode(
            @ApiParam(value = "跳转地址,地址结尾不需要拼接?") @RequestParam String url,
            @ApiParam(value = "出库设备IP(有IP就是在出口支付二维码，没有就是提前支付二维码)") @RequestParam(required = false) String deviceIp,
            @ApiParam(value = "出库设备端口号(有端口号就是在出口支付二维码，没有就是提前支付二维码)") @RequestParam(required = false) String devicePort
    ) {
        // 定义名称
        String qrCodeName = "tiqianzhifuerweima";
        String content = getUrl(url, "?");
        if (Objects.nonNull(deviceIp) && Objects.nonNull(devicePort)) {
            content += "?devicePort=" + devicePort + "&deviceIp=" + deviceIp;
            // 定义名称为提前支付二维码
            qrCodeName = "zhifu" + deviceIp + devicePort;
            // 去掉.
            qrCodeName = qrCodeName.replaceAll("\\.", "");
        }
        // 响应数据转换
        return ResponseObject.success(QRCodeUtil.generateQRCode(qrCodeName, qrCodeUrl, thirdParty, content));
    }

    /**
     * 去掉跳转地址最后一个字符
     *
     * @param url
     * @param type
     * @return
     */
    private static String getUrl(String url, String type) {
        // 移除 url 的最后一个字符
        if (url.endsWith(type)) url = url.substring(0, url.length() - 1);
        return url;
    }
}
