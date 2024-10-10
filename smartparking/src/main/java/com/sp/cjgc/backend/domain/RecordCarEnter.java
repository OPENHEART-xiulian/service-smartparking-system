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
 * 记录-车辆进场记录表(RecordCarEnter)实体类
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("record_car_enter")
public class RecordCarEnter implements Serializable {
    private static final long serialVersionUID = -20319291722107416L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                    //ID
    @TableField(value = "mainland_license_plates")
    private String mainlandLicensePlates; //内地车牌号码
    @TableField(value = "phone_number")
    private String phoneNumber;           //手机号码
    @TableField(value = "other_license_plates")
    private String otherLicensePlates;    //其他车牌号码，多个车牌号码英文逗号分隔
    @TableField(value = "start_camera_device_ip")
    private String startCameraDeviceIp;   //进库摄像头IP
    @TableField(value = "start_time")
    private LocalDateTime startTime;      //进库时间
    @TableField(value = "device_group_id")
    private Integer deviceGroupId;        //设备组号编码，1｜主库；2｜东库；3｜西库
    @TableField(value = "device_group_name")
    private String deviceGroupName;       //设备组号名称
    @TableField(value = "car_group_id")
    private Integer carGroupId;           //车辆分组编码，1｜临停车；2｜VIP月租车；3｜普通月租车；4｜访客车；5|无牌车；6｜其他
    @TableField(value = "car_group_name")
    private String carGroupName;          //车辆分组名称
    @TableField(value = "is_toll")
    private Integer isToll;               //此处是否开始计费，0｜不计费; 1｜开始计费;默认不计费
    @TableField(value = "status")
    private Integer status;               //订单是否结束，0｜未结束；1｜已结束,默认未结束
    //@formatter:on
}
