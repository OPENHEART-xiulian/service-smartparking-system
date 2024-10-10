package com.sp.cjgc.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Zoey
 * @Since: 2024-08-27 13:53:33
 * @Description: 订单支付枚举
 */
@Getter
@AllArgsConstructor
public enum OrderStatus {

    /**
     * 未支付
     */
    NOTPAY("等待支付中"),


    /**
     * 支付成功
     */
    SUCCESS("支付成功"),

    /**
     * 已关闭
     */
    CLOSED("超时已关闭"),

    /**
     * 已取消
     */
    CANCEL("用户已取消"),

    /**
     * 退款中
     */
    REFUND_PROCESSING("退款中"),

    /**
     * 已退款
     */
    REFUND_SUCCESS("已退款"),

    /**
     * 退款异常
     */
    REFUND_ABNORMAL("退款异常"),

    ;
    /**
     * 类型
     */
    private final String type;
}
