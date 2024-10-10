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
 * 系统管理-收费规则表(SystemChargeRules)实体类
 *
 * @author zoey
 * @since 2024-08-15 14:32:02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("system_charge_rules")
public class SystemChargeRules implements Serializable {
    private static final long serialVersionUID = 612719230551440659L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                            //ID
    @TableField(value = "free_duration")
    private Integer freeDuration;                 //进场免费时长，单位：分钟
    @TableField(value = "toll_standard")
    private String tollStandard;                  //临保收费标准，单位：元/小时
    @TableField(value = "fee_cap")
    private String feeCap;                        //临保收费上限。单位：元/24小时
    @TableField(value = "monthly_internal_car")
    private String monthlyInternalCar;            //月保收费标准-内部车辆，单位：元/月
    @TableField(value = "monthly_enterprise_car")
    private String monthlyEnterpriseCar;          //月保收费标准-所属企业公车，单位：元/月
    @TableField(value = "monthly_external_car_machinery")
    private String monthlyExternalCarMachinery;   //月保收费标准-外部车辆(机械车位)，单位：元/月
    @TableField(value = "monthly_internal_car_no_machinery")
    private String monthlyInternalCarNoMachinery; //月保收费标准-外部车辆(非机械车位)，单位：元/月
    //@formatter:on
}
