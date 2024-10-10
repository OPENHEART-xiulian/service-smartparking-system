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
 * 商户-停车领劵(ParkCollectCoupons)实体类
 *
 * @author zoey
 * @since 2024-08-22 09:56:37
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("park_collect_coupons")
public class ParkCollectCoupons implements Serializable {
    private static final long serialVersionUID = -43460921590446638L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                    //ID
    @TableField(value = "user_id")
    private String userId;                //商户ID
    @TableField(value = "mainland_license_plates")
    private String mainlandLicensePlates; //车牌号/手机号码(无牌车)
    @TableField(value = "start_time")
    private LocalDateTime startTime;      //进场时间
    @TableField(value = "end_time")
    private LocalDateTime endTime;        //出场时间
    @TableField(value = "total_duration")
    private String totalDuration;         //停车时长
    @TableField(value = "total_amount")
    private String totalAmount;           //总计金额
    @TableField(value = "total_discount_amount")
    private String totalDiscountAmount;   //优惠金额
    @TableField(value = "discount")
    private String discount;              //优惠信息
    @TableField(value = "total_income_amount")
    private String totalIncomeAmount;     //需付金额
    @TableField(value = "receive_time")
    private LocalDateTime receiveTime;    //领劵时间
    @TableField(value = "is_it_over")
    private Integer isItOver;             //是否已出场,0|未出场；1|已出场
    @TableField(value = "is_play")
    private Integer isPlay;               //订单是否已支付,0|未支付；1|已支付
    @TableField(value = "play_time")
    private LocalDateTime playTime;       //订单支付成功时间

    @TableField(exist = false)
    private Integer freeTime;             //免费时长，单位:分钟,默认免费停车15分钟
    //@formatter:on
}
