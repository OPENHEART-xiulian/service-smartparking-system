package com.sp.cjgc.backend.domain.query;

import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 订单-车辆出库已支付订单记录(OrderPaidCatOutbound)查询类
 *
 * @author zoey
 * @since 2024-08-20 13:50:20
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderPaidCatOutboundQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = -17165114019670305L;
    //@formatter:off
    private String id;                    //ID
    private String userId;                //用户ID
    private String mainlandLicensePlates; //车牌号/手机号码(无牌车)
    private String startTime;             //进场时间
    private String endTime;               //出场时间
    private String totalDuration;         //停车时长
    private String playTime;              //订单支付时间
    private String orderNumber;           //订单编号
    private String totalAmount;           //总计金额
    private String totalDiscountAmount;   //优惠金额
    private String discount;              //优惠信息
    private String totalIncomeAmount;     //收入金额
    private String typeCode;              //类型编码
    private String typeName;              //类型名称

    private String userName;              //用户名称
    //@formatter:on
}
