package com.sp.cjgc.backend.endpoint.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sp.cjgc.common.pageutil.PageInfoResp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 记录-车辆进场记录表(RecordCarEnter)输出DTO
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
public class RecordCarEnterResp implements Serializable {
    private static final long serialVersionUID = 292681389618378800L;
    
    @Getter
    @Setter
    @ApiModel(value = "RecordCarEnterResp.RecordCarEnterDTO", description = "记录-车辆进场记录表输出DTO")
    public static class RecordCarEnterDTO implements Serializable {
        private static final long serialVersionUID = -55643872709514407L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                    
        @ApiModelProperty(value = "内地车牌号码", position = 2)
        private String mainlandLicensePlates; 
        @ApiModelProperty(value = "手机号码", position = 3)
        private String phoneNumber;           
        @ApiModelProperty(value = "其他车牌号码，多个车牌号码英文逗号分隔", position = 4)
        private String otherLicensePlates;    
        @ApiModelProperty(value = "进库摄像头IP", position = 5)
        private String startCameraDeviceIp;   
        @ApiModelProperty(value = "进库时间", position = 6)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime startTime;      
        @ApiModelProperty(value = "设备组号编码，1｜主库；2｜东库；3｜西库", position = 7)
        private Integer deviceGroupId;        
        @ApiModelProperty(value = "设备组号名称", position = 8)
        private String deviceGroupName;       
        @ApiModelProperty(value = "车辆分组编码，1｜临停车；2｜VIP月租车；3｜普通月租车；4｜访客车；5|无牌车；6｜其他", position = 9)
        private Integer carGroupId;           
        @ApiModelProperty(value = "车辆分组名称", position = 10)
        private String carGroupName;          
        @ApiModelProperty(value = "此处是否开始计费，0｜不计费; 1｜开始计费;默认不计费", position = 11)
        private Integer isToll;               
        @ApiModelProperty(value = "订单是否结束，0｜未结束；1｜已结束,默认未结束", position = 12)
        private Integer status;               
        //@formatter:on
    }
    
    @Getter
    @Setter
    @ApiModel(value = "RecordCarEnterResp.ListDTO", description = "记录-车辆进场记录表-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<RecordCarEnterDTO> list;
    }
}
