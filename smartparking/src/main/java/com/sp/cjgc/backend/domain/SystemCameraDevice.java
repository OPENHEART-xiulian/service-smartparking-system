package com.sp.cjgc.backend.domain;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统管理-摄像头设备管理表(SystemCameraDevice)实体类
 *
 * @author zoey
 * @since 2024-08-14 10:25:42
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("system_camera_device")
public class SystemCameraDevice implements Serializable {
    private static final long serialVersionUID = 915020107741068490L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                //ID
    @TableField(value = "device_ip")
    private String deviceIp;          //设备IP地址
    @TableField(value = "device_port")
    private String devicePort;        //设备IP端口
    @TableField(value = "device_user_name")
    private String deviceUserName;    //设备用户名
    @TableField(value = "device_password")
    private String devicePassword;    //设备密码
    @TableField(value = "device_name")
    private String deviceName;        //设备名称
    @TableField(value = "device_location")
    private String deviceLocation;    //设备所在位置
    @TableField(value = "device_role")
    private Integer deviceRole;       //设备作用，1｜进；0｜出
    @TableField(value = "is_toll")
    private Integer isToll;           //是否在此处计费，0｜不计费；1｜计费；
    @TableField(value = "create_time")
    private LocalDateTime createTime; //创建时间
    @TableField(value = "update_time")
    private LocalDateTime updateTime; //修改时间
    @TableField(value = "group_id")
    private Integer groupId;          //组号，进出为一组，1｜主库；2｜东库；3｜西库
    @TableField(value = "user_id")
    private Integer userId;           //设备登陆句柄
    //@formatter:on
}
