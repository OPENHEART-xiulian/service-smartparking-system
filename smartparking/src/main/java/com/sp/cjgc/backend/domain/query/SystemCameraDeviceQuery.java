package com.sp.cjgc.backend.domain.query;

import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统管理-摄像头设备管理表(SystemCameraDevice)查询类
 *
 * @author zoey
 * @since 2024-08-14 10:25:42
 */
@Getter
@Setter
@NoArgsConstructor
public class SystemCameraDeviceQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = -51321392970638242L;
    //@formatter:off
    private String id;                //ID
    private String deviceIp;          //设备IP地址
    private String devicePort;        //设备IP端口
    private String deviceUserName;    //设备用户名
    private String devicePassword;    //设备密码
    private String deviceName;        //设备名称
    private String deviceLocation;    //设备所在位置
    private Integer deviceRole;       //设备作用，1｜进；0｜出
    private Integer isToll;           //是否在此处计费，1｜计费；0｜不计费
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
    private Integer groupId;          //组号，进出为一组，1｜主库；2｜东库；3｜西库
    private Integer userId;           //设备登陆句柄

    private String startTime;         //创建时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss
    private String endTime;           //创建时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss
    //@formatter:on
}
