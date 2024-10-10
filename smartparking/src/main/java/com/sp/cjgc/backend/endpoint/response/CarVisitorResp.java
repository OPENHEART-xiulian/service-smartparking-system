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
 * 车辆管理-访客车登记管理表(CarVisitor)输出DTO
 *
 * @author zoey
 * @since 2024-08-14 13:03:03
 */
public class CarVisitorResp implements Serializable {
    private static final long serialVersionUID = 382540112616346005L;
    
    @Getter
    @Setter
    @ApiModel(value = "CarVisitorResp.CarVisitorDTO", description = "车辆管理-访客车登记管理表输出DTO")
    public static class CarVisitorDTO implements Serializable {
        private static final long serialVersionUID = -43459889318881245L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                    
        @ApiModelProperty(value = "内地车牌号码", position = 2)
        private String mainlandLicensePlates; 
        @ApiModelProperty(value = "其他车牌号码，多个车牌号码英文逗号分隔", position = 3)
        private String otherLicensePlates;    
        @ApiModelProperty(value = "免收费开始时间", position = 4)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime startTime;      
        @ApiModelProperty(value = "免收费结束时间", position = 5)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime endTime;
        @ApiModelProperty(value = "是否失效，1｜失效；2｜有效", position = 6)
        private Integer isFree;
        @ApiModelProperty(value = "创建时间", position = 7)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createTime;     
        @ApiModelProperty(value = "修改时间", position = 8)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime updateTime;     
        //@formatter:on
    }
    
    @Getter
    @Setter
    @ApiModel(value = "CarVisitorResp.ListDTO", description = "车辆管理-访客车登记管理表-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<CarVisitorDTO> list;
    }
}
