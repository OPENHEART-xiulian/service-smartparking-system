package com.sp.cjgc.backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统管理-车位数量表(SystemParking)实体类
 *
 * @author zoey
 * @since 2024-08-15 16:24:46
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("system_parking")
public class SystemParking implements Serializable {
    private static final long serialVersionUID = -35044626109031424L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                //ID
    @TableField(value = "parking_number")
    private Integer parkingNumber;    //车位总数量
    @TableField(value = "disposable_number")
    private Integer disposableNumber; //可分配车位总数量
    //@formatter:on
}
