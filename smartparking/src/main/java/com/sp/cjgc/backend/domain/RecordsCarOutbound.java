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
 * 记录-车辆出场记录表(RecordsCarOutbound)实体类
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@TableName("records_car_outbound")
public class RecordsCarOutbound implements Serializable {
    private static final long serialVersionUID = 136683875761268941L;
    //@formatter:off
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;                    //ID
    @TableField(value = "mainland_license_plates")
    private String mainlandLicensePlates; //内地车牌号码
    @TableField(value = "phone_number")
    private String phoneNumber;           //手机号码
    @TableField(value = "other_license_plates")
    private String otherLicensePlates;    //其他车牌号码，多个车牌号码英文逗号分隔
    @TableField(value = "end_camera_device_ip")
    private String endCameraDeviceIp;     //出库摄像头IP
    @TableField(value = "start_time")
    private LocalDateTime startTime;      //进库时间
    @TableField(value = "end_time")
    private LocalDateTime endTime;        //出库时间
    @TableField(value = "device_group_id")
    private Integer deviceGroupId;        //设备组号编码，1｜主库；2｜东库；3｜西库
    @TableField(value = "device_group_name")
    private String deviceGroupName;       //设备组号名称
    @TableField(value = "car_group_id")
    private Integer carGroupId;           //车辆分组编码，1｜临保；2｜月保；3｜访客车；4｜无牌车；5|其他；
    @TableField(value = "car_group_name")
    private String carGroupName;          //车辆分组名称
    @TableField(value = "is_toll")
    private Integer isToll;               //此处是否结算计费，0｜不结算; 1｜结算计费;默认不结算
    @TableField(value = "parking_duration")
    private String parkingDuration;       //停车时长
    //@formatter:on
}
