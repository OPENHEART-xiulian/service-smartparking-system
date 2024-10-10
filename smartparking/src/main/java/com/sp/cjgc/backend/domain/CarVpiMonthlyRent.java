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
 * 车辆管理-vip月租车登记管理表(CarVpiMonthlyRent)实体类
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("car_vpi_monthly_rent")
public class CarVpiMonthlyRent implements Serializable {
    private static final long serialVersionUID = 911008725394668269L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                    //ID
    @TableField(value = "mainland_license_plates")
    private String mainlandLicensePlates; //内地车牌号码
    @TableField(value = "other_license_plates")
    private String otherLicensePlates;    //其他车牌号码，多个车牌号码英文逗号分隔
    @TableField(value = "create_time")
    private LocalDateTime createTime;     //登记时间
    @TableField(value = "update_time")
    private LocalDateTime updateTime;     //修改时间
    //@formatter:on
}
