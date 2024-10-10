package com.sp.cjgc.backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单-车辆出库已支付订单记录(OrderPaidCatOutbound)实体类
 *
 * @author zoey
 * @since 2024-08-20 13:50:20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("order_paid_cat_outbound")
public class OrderPaidCatOutbound implements Serializable {
    private static final long serialVersionUID = -42236693101612539L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                    //ID
    @TableField(value = "user_id")
    private String userId;                //用户ID
    @TableField(value = "mainland_license_plates")
    private String mainlandLicensePlates; //车牌号/手机号码(无牌车)
    @TableField(value = "start_time")
    private LocalDateTime startTime;      //进场时间
    @TableField(value = "end_time")
    private LocalDateTime endTime;        //出场时间
    @TableField(value = "total_duration")
    private String totalDuration;         //停车时长
    @TableField(value = "play_time")
    private LocalDateTime playTime;       //订单支付时间
    @TableField(value = "order_number")
    private String orderNumber;           //订单编号
    @TableField(value = "total_amount")
    private String totalAmount;           //总计金额
    @TableField(value = "total_discount_amount")
    private String totalDiscountAmount;   //优惠金额
    @TableField(value = "discount")
    private String discount;              //优惠信息
    @TableField(value = "total_income_amount")
    private String totalIncomeAmount;     //收入金额
    @TableField(value = "type_code")
    private String typeCode;              //类型编码
    @TableField(value = "type_name")
    private String typeName;              //类型名称
    @TableField(value = "play_status")
    private String playStatus;            //支付状态
    @TableField(value = "advance_payment")
    private Integer advancePayment;        //是否提前支付，0｜否；1｜是，默认为0
    @TableField(value = "create_time")
    private LocalDateTime createTime;     //订单创建时间
    @TableField(value = "device_ip")
    private String deviceIp;              //出口设备IP

    @TableField(exist = false)
    private String userName;              //用户名称
    //@formatter:on
}
