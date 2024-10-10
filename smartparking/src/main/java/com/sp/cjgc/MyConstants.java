package com.sp.cjgc;

/**
 * @Author: Zoey
 * @Since: 2024-08-20 13:25:48
 * @Description: 自定义常量
 */
public class MyConstants {

    // 闸机开关指令常量 0|关闭;1|开启
    public static final Integer ZJ_CLOSE = 0;
    public static final Integer ZJ_OPEN = 1;
    // 闸机开关指令常量 0|关闭;1|开启

    // 商会对账记录的发放状态常量
    public static final String GRANTED = "已发放";
    public static final String UNWARRANTED = "未发放";

    // 定义提前支付后的有效出场时间(提前扫码支付后，30分钟以内出场不计费，超出30分钟以后出场，超时的部分重新计费)，单位：分钟。
    public static final int TIME = 30;

    // 扫码领劵的二维码有效时间，单位：分钟。
    public static final int QR_TIME = 30;

    // 封顶费用,单位：元
    public static final String CAPPING_FEE = "99";

    // 默认免费停车时间，单位：分钟，默认为 15 分钟
    public static final Integer FREE_DURATION = 15;

    // 临保收费标准，单位：元/小时,默认 15元/小时
    public static final String TOOL_STANDARD = "15";

    // 默认管理员用户ID
    public static final String ADMIN_USER_ID = "admin";

    /*第三方服务器配置信息，如果不使用第三方服务器，则无需配置，主要用于存储上传的文件信息*/
    public static final String THIRD_PARTY_IP = "";
    public static final int THIRD_PARTY_PORT = 0;
    public static final String THIRD_PARTY_USER_NAME = "";
    public static final String THIRD_PARTY_PASSWORD = "";

    // 查询订单创建时间 <= 当前时间 + MINUTE_TIM 以内的未支付订单，单位：分钟
    public static final Integer MINUTE_TIME = 10;

    /**************************微信JSAPI支付相关******************************/
    // 字符集编码
    public static final String CHARSET = "UTF-8";
    // JSAPI下单地址
    public static final String DOMAIN = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
    // 支付成功后的回调地址(也就是接口地址)
    public static final String WX_PAY_NOTIFY_URL = "${请填写指向接口的域名}/api/vxPay/payNotify";
    // code换取网页授权openid
    public static final String sessionUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid={}&secret={}&code={}";
    /**************************微信JSAPI支付相关******************************/

    // 可发放车位数量(剩余抵用券)
    public static final String KEY_NAME_1 = "assignedNumber";
    // 发放状态;0|已发放完毕;1|可发放
    public static final String KEY_NAME_2 = "assignedStatus";
    // 商户名称
    public static final String KEY_NAME_3 = "userName";
}
