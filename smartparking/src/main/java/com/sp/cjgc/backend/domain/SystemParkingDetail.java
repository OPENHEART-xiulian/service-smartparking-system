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
 * 系统管理-车位数量明细表(SystemParkingDetail)实体类
 *
 * @author zoey
 * @since 2024-08-15 16:24:56
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("system_parking_detail")
public class SystemParkingDetail implements Serializable {
    private static final long serialVersionUID = 554754969218599312L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;               //ID
    @TableField(value = "user_id")
    private String userId;           //用户ID
    @TableField(value = "assigned_number")
    private Integer assignedNumber;  //租赁车位数量
    @TableField(value = "start_time")
    private LocalDateTime startTime; //开始时间
    @TableField(value = "end_time")
    private LocalDateTime endTime;   //结束时间

    @TableField(exist = false)
    private String userName;         //用户名称
    @TableField(exist = false)
    private String assignedStatus;   //租赁状态说明
    //@formatter:on
}
