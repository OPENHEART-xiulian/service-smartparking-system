package com.sp.cjgc.backend.utils;

import com.sp.cjgc.backend.service.SystemWeChatJsapiPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: Zoey
 * @Since: 2024-09-06 14:51:05
 * @Description: 获取微信JSAPI支付配置信息
 */
@Component
public class WeChatPayUtil {

    @Autowired
    private SystemWeChatJsapiPayService systemWeChatJsapiPayService;

    private static SystemWeChatJsapiPayService staticSystemWeChatJsapiPayService;

    @PostConstruct
    public void init() {
        staticSystemWeChatJsapiPayService = this.systemWeChatJsapiPayService;
    }

    /**
     * 公众号appId
     *
     * @return
     */
    public static String getAppId() {
        return staticSystemWeChatJsapiPayService.getWeChatJsapiPay().getAppId();
    }

    /**
     * 商户号
     *
     * @return
     */
    public static String getMacId() {
        return staticSystemWeChatJsapiPayService.getWeChatJsapiPay().getMacId();
    }

    /**
     * 公众号密钥
     *
     * @return
     */
    public static String getSecret() {
        return staticSystemWeChatJsapiPayService.getWeChatJsapiPay().getSecret();
    }

    /**
     * 币种
     *
     * @return
     */
    public static String getCurrency() {
        return staticSystemWeChatJsapiPayService.getWeChatJsapiPay().getCurrency();
    }

    /**
     * APIv3密钥
     *
     * @return
     */
    public static String getApiV3Key() {
        return staticSystemWeChatJsapiPayService.getWeChatJsapiPay().getApiV3Key();
    }

    /**
     * 商户API证书序列号
     *
     * @return
     */
    public static String getMacSerialNo() {
        return staticSystemWeChatJsapiPayService.getWeChatJsapiPay().getMacSerialNo();
    }
}
