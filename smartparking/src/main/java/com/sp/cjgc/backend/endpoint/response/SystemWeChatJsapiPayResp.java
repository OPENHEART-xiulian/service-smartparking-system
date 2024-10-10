package com.sp.cjgc.backend.endpoint.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统管理-微信JSAPI支付配置(SystemWeChatJsapiPay)输出DTO
 *
 * @author zoey
 * @since 2024-09-06 14:05:32
 */
public class SystemWeChatJsapiPayResp implements Serializable {
    private static final long serialVersionUID = 102650162656979765L;
    
    @Getter
    @Setter
    @ApiModel(value = "SystemWeChatJsapiPayResp.SystemWeChatJsapiPayDTO", description = "系统管理-微信JSAPI支付配置输出DTO")
    public static class SystemWeChatJsapiPayDTO implements Serializable {
        private static final long serialVersionUID = -92051320047006672L;
        //@formatter:off
        @ApiModelProperty(value = "id", position = 1)
        private String id;
        @ApiModelProperty(value = "商户号", position = 1)
        private String macId;
        @ApiModelProperty(value = "公众号appId", position = 2)
        private String appId;          
        @ApiModelProperty(value = "公众号密钥", position = 3)
        private String secret;         
        @ApiModelProperty(value = "币种：CNY", position = 4)
        private String currency;       
        @ApiModelProperty(value = "商户私钥文件地址", position = 5)
        private String privateKeyPath; 
        @ApiModelProperty(value = "商户APIv3密钥", position = 6)
        private String apiV3Key;       
        @ApiModelProperty(value = "商户API证书序列号", position = 7)
        private String macSerialNo;    
        //@formatter:on
    }
}
