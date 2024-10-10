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
 * 车辆管理-vip月租车登记管理表(CarVpiMonthlyRent)输出DTO
 *
 * @author zoey
 * @since 2024-08-14 13:03:04
 */
public class CarVpiMonthlyRentResp implements Serializable {
    private static final long serialVersionUID = 181696413215469112L;
    
    @Getter
    @Setter
    @ApiModel(value = "CarVpiMonthlyRentResp.CarVpiMonthlyRentDTO", description = "车辆管理-vip月租车登记管理表输出DTO")
    public static class CarVpiMonthlyRentDTO implements Serializable {
        private static final long serialVersionUID = 233582857327905443L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                    
        @ApiModelProperty(value = "内地车牌号码", position = 2)
        private String mainlandLicensePlates; 
        @ApiModelProperty(value = "其他车牌号码，多个车牌号码英文逗号分隔", position = 3)
        private String otherLicensePlates;    
        @ApiModelProperty(value = "登记时间", position = 4)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createTime;     
        @ApiModelProperty(value = "修改时间", position = 5)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime updateTime;     
        //@formatter:on
    }
    
    @Getter
    @Setter
    @ApiModel(value = "CarVpiMonthlyRentResp.ListDTO", description = "车辆管理-vip月租车登记管理表-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<CarVpiMonthlyRentDTO> list;
    }
}
