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
 * 商户-对账记录(MerchantReconciliation)实体类
 *
 * @author zoey
 * @since 2024-08-20 13:13:44
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("merchant_reconciliation")
public class MerchantReconciliation implements Serializable {
    private static final long serialVersionUID = -78654534386368855L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                  //ID
    @TableField(value = "user_id")
    private String userId;              //用户ID
    @TableField(value = "total_duration")
    private String totalDuration;       //总停车时长
    @TableField(value = "total_amount")
    private String totalAmount;         //总计金额
    @TableField(value = "total_discount_amount")
    private String totalDiscountAmount; //总计优惠金额
    @TableField(value = "total_income_amount")
    private String totalIncomeAmount;   //总计收入金额
    @TableField(value = "year_number")
    private String yearNumber;          //年份
    @TableField(value = "month_number")
    private String monthNumber;         //月份
    @TableField(value = "status")
    private String status;              //发放状态

    @TableField(exist = false)
    private String userName;            //用户名称
    @TableField(exist = false)
    private Integer assignedNumber;     //租赁车位数量
    //@formatter:on
}
