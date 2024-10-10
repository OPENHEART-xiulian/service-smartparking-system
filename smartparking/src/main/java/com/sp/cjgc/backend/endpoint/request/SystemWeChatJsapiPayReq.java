package com.sp.cjgc.backend.endpoint.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * 系统管理-微信JSAPI支付配置(SystemWeChatJsapiPay)输入DTO
 *
 * @author zoey
 * @since 2024-09-06 14:05:30
 */
public class SystemWeChatJsapiPayReq implements Serializable {
    private static final long serialVersionUID = -74178363975029545L;
    
    @Getter
    @Setter
    @ApiModel(value = "SystemWeChatJsapiPayReq.CreateOrUpdateDTO", description = "系统管理-微信JSAPI支付配置新增或更新DTO")
    public static class CreateOrUpdateDTO implements Serializable {
        private static final long serialVersionUID = -43355946137091835L;
        //@formatter:off
        @ApiModelProperty(value = "id", position = 1)
        private String id;
        @ApiModelProperty(value = "商户号", position = 1, required = true)
        private String macId;
        @ApiModelProperty(value = "公众号appId", position = 2, required = true)
        private String appId;          
        @ApiModelProperty(value = "公众号密钥", position = 3, required = true)
        private String secret;         
        @ApiModelProperty(value = "币种：CNY", position = 4, required = true)
        private String currency;       
        @ApiModelProperty(value = "商户私钥文件地址", position = 5, required = true)
        private String privateKeyPath; 
        @ApiModelProperty(value = "商户APIv3密钥", position = 6, required = true)
        private String apiV3Key;       
        @ApiModelProperty(value = "商户API证书序列号", position = 7, required = true)
        private String macSerialNo;    
        //@formatter:on
    }
}
