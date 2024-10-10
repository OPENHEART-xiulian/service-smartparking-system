package com.sp.cjgc.backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 月保-缴费记录(MonthlyInsurancePayment)实体类
 *
 * @author zoey
 * @since 2024-08-16 13:23:17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("monthly_insurance_payment")
public class MonthlyInsurancePayment implements Serializable {
    private static final long serialVersionUID = 863406313949918540L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                      //ID
    @TableField(value = "mainland_license_plates")
    private String mainlandLicensePlates;   //内地车牌号码
    @TableField(value = "monthly_start_time")
    private LocalDateTime monthlyStartTime; //月保开始时间
    @TableField(value = "monthly_end_time")
    private LocalDateTime monthlyEndTime;   //月保结束时间
    @TableField(value = "car_type_code")
    private String carTypeCode;             //车辆类型编码
    @TableField(value = "car_type_name")
    private String carTypeName;             //车辆类型名称
    @TableField(value = "user_name")
    private String userName;                //姓名
    @TableField(value = "phone_number")
    private String phoneNumber;             //手机号码
    @TableField(value = "card_id")
    private String cardId;                  //身份证号码
    @TableField(value = "parking_lot_code")
    private String parkingLotCode;          //车位编号
    @TableField(value = "monthly_free")
    private String monthlyFree;             //月保费用/月
    @TableField(value = "payment_status")
    private Integer paymentStatus;          //缴费状态,0｜未缴费；1｜已缴费
    @TableField(value = "payment_amount")
    private String paymentAmount;           //缴费金额
    @TableField(value = "accumulate_payment_amount")
    private String accumulatePaymentAmount; //累计缴费金额
    @TableField(value = "long_term")
    private Integer longTerm;               //是否长期有效，0|否;1|是,默认否
    @TableField(value = "create_time")
    private LocalDateTime createTime;       //创建时间

    @TableField(exist = false)
    private String monthlyStatus;           //月保状态 1｜在保；2｜过保
    @TableField(exist = false)
    private String expirationDate;          //有效期
    //@formatter:on
}
