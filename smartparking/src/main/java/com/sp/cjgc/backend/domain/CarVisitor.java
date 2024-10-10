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
 * 车辆管理-访客车登记管理表(CarVisitor)实体类
 *
 * @author zoey
 * @since 2024-08-14 13:03:02
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("car_visitor")
public class CarVisitor implements Serializable {
    private static final long serialVersionUID = 705175912968737242L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                    //ID
    @TableField(value = "mainland_license_plates")
    private String mainlandLicensePlates; //内地车牌号码
    @TableField(value = "other_license_plates")
    private String otherLicensePlates;    //其他车牌号码，多个车牌号码英文逗号分隔
    @TableField(value = "start_time")
    private LocalDateTime startTime;      //免收费开始时间
    @TableField(value = "end_time")
    private LocalDateTime endTime;        //免收费结束时间
    @TableField(value = "create_time")
    private LocalDateTime createTime;     //创建时间
    @TableField(value = "update_time")
    private LocalDateTime updateTime;     //修改时间

    @TableField(exist = false)
    private Integer isFree;               //是否失效，1｜失效；2｜有效
    //@formatter:on
}
