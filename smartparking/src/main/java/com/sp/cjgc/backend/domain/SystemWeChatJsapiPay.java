package com.sp.cjgc.backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统管理-微信JSAPI支付配置(SystemWeChatJsapiPay)实体类
 *
 * @author zoey
 * @since 2024-09-06 14:05:24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("system_we_chat_jsapi_pay")
public class SystemWeChatJsapiPay implements Serializable {
    private static final long serialVersionUID = 883785609946029273L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;             //id
    @TableField(value = "mac_id")
    private String macId;          //商户号
    @TableField(value = "app_id")
    private String appId;          //公众号appId
    @TableField(value = "secret")
    private String secret;         //公众号密钥
    @TableField(value = "currency")
    private String currency;       //币种：CNY
    @TableField(value = "private_key_path")
    private String privateKeyPath; //商户私钥文件地址
    @TableField(value = "api_v3_key")
    private String apiV3Key;       //商户APIv3密钥
    @TableField(value = "mac_serial_no")
    private String macSerialNo;    //商户API证书序列号
    //@formatter:on
}
