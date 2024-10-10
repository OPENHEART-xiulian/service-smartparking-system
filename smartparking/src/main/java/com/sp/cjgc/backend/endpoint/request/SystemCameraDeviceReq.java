package com.sp.cjgc.backend.endpoint.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 系统管理-摄像头设备管理表(SystemCameraDevice)输入DTO
 *
 * @author zoey
 * @since 2024-08-14 10:25:42
 */
public class SystemCameraDeviceReq implements Serializable {
    private static final long serialVersionUID = 994267853279889747L;

    @Getter
    @Setter
    @ApiModel(value = "SystemCameraDeviceReq.CreateOrUpdateDTO", description = "系统管理-摄像头设备管理表新增或更新DTO")
    public static class CreateOrUpdateDTO implements Serializable {
        private static final long serialVersionUID = 646091349974246550L;
        //@formatter:off
        @ApiModelProperty(value = "ID,新增不传，更新必传", position = 1)
        private String id;
        @ApiModelProperty(value = "设备IP地址", position = 2, required = true)
        private String deviceIp;
        @ApiModelProperty(value = "设备IP端口", position = 3, required = true)
        private String devicePort;
        @ApiModelProperty(value = "设备名称", position = 4)
        private String deviceName;
        @ApiModelProperty(value = "设备所在位置", position = 5)
        private String deviceLocation;
        @ApiModelProperty(value = "设备作用，1｜进；0｜出", position = 6, required = true)
        private Integer deviceRole;
        @ApiModelProperty(value = "是否在此处计费，0｜不计费; 1｜开始计费;2｜结算计费", position = 7, required = true)
        private Integer isToll;
        @ApiModelProperty(value = "组号，进出为一组，1｜主库；2｜东库；3｜西库", position = 8, required = true)
        private Integer groupId;
        @ApiModelProperty(value = "设备账号", position = 9, required = true)
        private String deviceUserName;
        @ApiModelProperty(value = "设备密码", position = 10, required = true)
        private String devicePassword;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemCameraDeviceReq.QueryDTO", description = "系统管理-摄像头设备管理表查询DTO")
    public static class QueryDTO implements Serializable {
        private static final long serialVersionUID = -54482523050945499L;
        //@formatter:off
        @ApiModelProperty(value = "设备IP地址", position = 1)
        private String deviceIp;
        @ApiModelProperty(value = "设备IP端口", position = 2)
        private String devicePort;
        @ApiModelProperty(value = "设备名称", position = 3)
        private String deviceName;
        @ApiModelProperty(value = "设备所在位置", position = 4)
        private String deviceLocation;
        @ApiModelProperty(value = "设备作用，1｜进；0｜出", position = 5)
        private Integer deviceRole;
        @ApiModelProperty(value = "是否在此处计费，0｜不计费; 1｜开始计费;2｜结算计费", position = 6)
        private Integer isToll;
        @ApiModelProperty(value = "组号，进出为一组，1｜主库；2｜东库；3｜西库", position = 7)
        private Integer groupId;
        @ApiModelProperty(value = "创建时间段开始时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 8)
        private String startTime;
        @ApiModelProperty(value = "创建时间段结束时间,时间格式: yyyy-MM-dd HH:mm:ss", position = 9)
        private String endTime;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemCameraDeviceReq.DeleteDTO", description = "系统管理-摄像头设备管理表删除DTO")
    public static class DeleteDTO implements Serializable {
        private static final long serialVersionUID = -35293709870447150L;
        //@formatter:off
        @ApiModelProperty(value = "ids", position = 1, required = true)
        private List<String> ids;
        //@formatter:on
    }
}
