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
 * 记录-车辆出场记录表(RecordsCarOutbound)输出DTO
 *
 * @author zoey
 * @since 2024-08-14 13:50:45
 */
public class RecordsCarOutboundResp implements Serializable {
    private static final long serialVersionUID = -59996692895477616L;

    @Getter
    @Setter
    @ApiModel(value = "RecordsCarOutboundResp.RecordsCarOutboundDTO", description = "记录-车辆出场记录表输出DTO")
    public static class RecordsCarOutboundDTO implements Serializable {
        private static final long serialVersionUID = -16036803197765133L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                    
        @ApiModelProperty(value = "内地车牌号码", position = 2)
        private String mainlandLicensePlates; 
        @ApiModelProperty(value = "手机号码", position = 3)
        private String phoneNumber;           
        @ApiModelProperty(value = "其他车牌号码，多个车牌号码英文逗号分隔", position = 4)
        private String otherLicensePlates;    
        @ApiModelProperty(value = "出库摄像头IP", position = 5)
        private String endCameraDeviceIp;
        @ApiModelProperty(value = "进库时间", position = 6)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime startTime;
        @ApiModelProperty(value = "出库时间", position = 6)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime endTime;        
        @ApiModelProperty(value = "设备组号编码，1｜主库；2｜东库；3｜西库", position = 7)
        private Integer deviceGroupId;        
        @ApiModelProperty(value = "设备组号名称", position = 8)
        private String deviceGroupName;       
        @ApiModelProperty(value = "车辆分组编码，1｜临保；2｜月保；3｜访客车；4｜无牌车；5|其他；", position = 9)
        private Integer carGroupId;           
        @ApiModelProperty(value = "车辆分组名称", position = 10)
        private String carGroupName;
        @ApiModelProperty(value = "此处是否结算计费，0｜不结算; 1｜结算计费;默认不结算", position = 11)
        private Integer isToll;
        @ApiModelProperty(value = "停车时长", position = 12)
        private String parkingDuration;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "RecordsCarOutboundResp.ListDTO", description = "记录-车辆出场记录表-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<RecordsCarOutboundDTO> list;
    }
}
