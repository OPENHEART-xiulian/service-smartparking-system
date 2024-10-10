package com.sp.cjgc.backend.endpoint.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Zoey
 * @Since: 2024-09-05 14:04:08
 * @Description:
 */
public class WxPayResp implements Serializable {
    private static final long serialVersionUID = -24599605854870981L;


    @Getter
    @Setter
    @ApiModel(value = "WxPayResp.RespDTO", description = "微信支付输出DTO")
    public static class RespDTO implements Serializable {
        private static final long serialVersionUID = -43459889318881245L;
        //@formatter:off
        @ApiModelProperty(value = "需要支付的小程序id(公众号ID)", position = 1)
        private String appId;
        @ApiModelProperty(value = "时间戳（当前的时间）", position = 1)
        private String timeStamp;
        @ApiModelProperty(value = "随机字符串，不长于32位。", position = 1)
        private String nonceStr;
        @ApiModelProperty(value = "小程序下单接口返回的prepay_id参数值，提交格式如：prepay_id=***", position = 1)
        private String prepayId;
        @ApiModelProperty(value = "签名类型，默认为RSA，仅支持RSA。", position = 1)
        private String signType;
        @ApiModelProperty(value = "签名，使用字段appId、timeStamp、nonceStr、package计算得出的签名值", position = 1)
        private String paySign;
        //@formatter:on
    }
}
