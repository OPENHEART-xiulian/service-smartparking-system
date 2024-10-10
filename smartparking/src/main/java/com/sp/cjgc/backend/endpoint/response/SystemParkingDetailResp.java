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
 * 系统管理-车位数量明细表(SystemParkingDetail)输出DTO
 *
 * @author zoey
 * @since 2024-08-15 16:24:56
 */
public class SystemParkingDetailResp implements Serializable {
    private static final long serialVersionUID = 243917134779551102L;

    @Getter
    @Setter
    @ApiModel(value = "SystemParkingDetailResp.SystemParkingDetailDTO", description = "系统管理-车位数量明细表输出DTO")
    public static class SystemParkingDetailDTO implements Serializable {
        private static final long serialVersionUID = 530529580638580657L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;               
        @ApiModelProperty(value = "用户ID", position = 2)
        private String userId;           
        @ApiModelProperty(value = "租赁车位数量", position = 3)
        private Integer assignedNumber;  
        @ApiModelProperty(value = "开始时间", position = 4)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime startTime; 
        @ApiModelProperty(value = "结束时间", position = 5)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime endTime;
        @ApiModelProperty(value = "用户名称", position = 6)
        private String userName;
        @ApiModelProperty(value = "租赁状态说明", position = 7)
        private String assignedStatus;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "SystemParkingDetailResp.ListDTO", description = "系统管理-车位数量明细表-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<SystemParkingDetailDTO> list;
    }
}
