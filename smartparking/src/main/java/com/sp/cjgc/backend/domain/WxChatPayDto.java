package com.sp.cjgc.backend.domain;

import lombok.Data;

/**
 * @Author: Zoey
 * @Since: 2024-09-05 13:40:23
 * @Description:
 */
@Data
public class WxChatPayDto {

    /**
     * 公众号ID
     */
    private String appId;

    /**
     * 时间戳（当前的时间）
     */
    private String timeStamp;

    /**
     * 随机字符串，不长于32位。
     */
    private String nonceStr;

    /**
     * 小程序下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
     */
    private String prepayId;

    /**
     * 签名类型，默认为RSA，仅支持RSA。
     */
    private String signType;

    /**
     * 签名，使用字段appId、timeStamp、nonceStr、package计算得出的签名值
     */
    private String paySign;

}
