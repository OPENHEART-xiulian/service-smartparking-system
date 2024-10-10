package com.sp.cjgc.backend.endpoint.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统管理-摄像头设备管理表(SystemCameraDevice)输出DTO
 *
 * @author zoey
 * @since 2024-08-14 10:25:42
 */
public class SystemCameraDeviceResp implements Serializable {
    private static final long serialVersionUID = -58145033613370430L;

    @Getter
    @Setter
    @ApiModel(value = "SystemCameraDeviceResp.SystemCameraDeviceDTO", description = "系统管理-摄像头设备管理表输出DTO")
    public static class SystemCameraDeviceDTO implements Serializable {
        private static final long serialVersionUID = -47360373175049182L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                
        @ApiModelProperty(value = "设备IP地址", position = 2)
        private String deviceIp;          
        @ApiModelProperty(value = "设备IP端口", position = 3)
        private String devicePort;        
        @ApiModelProperty(value = "设备用户名", position = 4)
        private String deviceUserName;    
        @ApiModelProperty(value = "设备密码", position = 5)
        private String devicePassword;    
        @ApiModelProperty(value = "设备名称", position = 6)
        private String deviceName;        
        @ApiModelProperty(value = "设备所在位置", position = 7)
        private String deviceLocation;    
        @ApiModelProperty(value = "设备作用，1｜进；0｜出", position = 8)
        private Integer deviceRole;       
        @ApiModelProperty(value = "是否在此处计费，1｜计费；0｜不计费", position = 9)
        private Integer isToll;           
        @ApiModelProperty(value = "创建时间", position = 10)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createTime; 
        @ApiModelProperty(value = "修改时间", position = 11)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime updateTime; 
        @ApiModelProperty(value = "组号，进出为一组，1｜主库；2｜东库；3｜西库", position = 12)
        private Integer groupId;          
        //@formatter:on
    }
}
