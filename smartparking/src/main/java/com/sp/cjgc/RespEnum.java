package com.sp.cjgc;

import com.sp.cjgc.common.exception.BaseErrorInfoInterface;
import lombok.Getter;

/**
 * @Author: Zoey
 * @Since: 2024-06-07 13:02:24
 * @Description: 自定义响应信息枚举
 */
@Getter
public enum RespEnum implements BaseErrorInfoInterface {

    SUCCESS(200, "操作成功"),
    FAILURE(-1, "操作失败"),
    PASS_WORD_ERROR(-2, "请重新登录"),
    ACCOUNT_ERROR(-3, "用户不存在"),
    PASSWORD_ERROR(-4, "原密码不正确"),
    OVERDUE_ERROR(-5, "动态码已过期"),
    INPUT_ERROR_1(-6, "请输入手机号码"),
    INPUT_ERROR_2(-7, "请输入出口设备IP"),
    INPUT_ERROR_3(-8, "请输入出口设备IP端口号"),
    NOT_FOUND_ERROR(-9, "尚未注册"),
    ROLE_ERROR(-10, "角色错误"),
    USER_ERROR_1(-10, "用户已锁定"),
    USER_ERROR_2(-10, "用户已禁用"),
    NOT_COUPONS_ERROR_1(-11, "暂无优惠券"),
    NOT_COUPONS_ERROR_2(-11, "优惠券已过期"),
    NOT_COUPONS_ERROR_3(-11, "未到优惠券领取时间"),
    NOT_COUPONS_ERROR_4(-11, "已无多余优惠卷可以领取"),
    NOT_COUPONS_ERROR_5(-11, "已领取优惠券"),
    NOT_COUPONS_ERROR_6(-11, "车辆尚未进场"),
    NOT_ERROR_1(-12, "未设置临保收费标准"),

    ;

    // 错误码
    private final Integer code;
    // 错误消息
    private final String message;

    RespEnum(Integer resultCode, String resultMsg) {
        this.code = resultCode;
        this.message = resultMsg;
    }

    @Override
    public Integer getResultCode() {
        return code;
    }

    @Override
    public String getResultMsg() {
        return message;
    }
}
