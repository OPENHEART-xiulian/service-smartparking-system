package com.sp.cjgc.backend.domain.query;

import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 记录-车辆出场记录表(RecordsCarOutbound)查询类
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
@Getter
@Setter
@NoArgsConstructor
public class RecordsCarOutboundQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = -95277095424096117L;
    //@formatter:off
    private String id;                    //ID
    private String mainlandLicensePlates; //内地车牌号码
    private String phoneNumber;           //手机号码
    private String otherLicensePlates;    //其他车牌号码，多个车牌号码英文逗号分隔
    private String endCameraDeviceIp;     //出库摄像头IP
    private Integer deviceGroupId;        //设备组号编码，1｜主库；2｜东库；3｜西库
    private String deviceGroupName;       //设备组号名称
    private Integer carGroupId;           //车辆分组编码，1｜临停车；2｜VIP月租车；3｜普通月租车；4｜访客车；5|无牌车；6｜其他
    private String carGroupName;          //车辆分组名称
    private String parkingDuration;       //停车时长
    private Integer isToll;               //此处是否结算计费，0｜不结算; 1｜结算计费;默认不结算

    private String startTime1;            //进库时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss
    private String endTime1;              //进库时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss

    private String startTime2;            //出库时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss
    private String endTime2;              //出库时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss
    //@formatter:on
}
