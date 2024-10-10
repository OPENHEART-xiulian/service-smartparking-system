package com.sp.cjgc.backend.endpoint.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统管理-收费规则表(SystemChargeRules)输入DTO
 *
 * @author zoey
 * @since 2024-08-15 14:32:03
 */
public class SystemChargeRulesReq implements Serializable {
    private static final long serialVersionUID = -95816294454932660L;

    @Getter
    @Setter
    @ApiModel(value = "SystemChargeRulesReq.UpdateDTO", description = "系统管理-收费规则表修改DTO")
    public static class UpdateDTO implements Serializable {
        private static final long serialVersionUID = 383289005805012679L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1, required = true)
        private String id;                            
        @ApiModelProperty(value = "进场免费时长，单位：分钟", position = 2, required = true)
        private Integer freeDuration;                 
        @ApiModelProperty(value = "临保收费标准，单位：元/小时", position = 3, required = true)
        private String tollStandard;                  
        @ApiModelProperty(value = "月保收费标准-内部车辆，单位：元/月", position = 4, required = true)
        private String monthlyInternalCar;            
        @ApiModelProperty(value = "月保收费标准-所属企业公车，单位：元/月", position = 5, required = true)
        private String monthlyEnterpriseCar;          
        @ApiModelProperty(value = "月保收费标准-外部车辆(机械车位)，单位：元/月", position = 6, required = true)
        private String monthlyExternalCarMachinery;   
        @ApiModelProperty(value = "月保收费标准-外部车辆(非机械车位)，单位：元/月", position = 7, required = true)
        private String monthlyInternalCarNoMachinery;
        @ApiModelProperty(value = "临保收费上限。单位：元/24小时", position = 8)
        private String feeCap;
        //@formatter:on
    }
}
