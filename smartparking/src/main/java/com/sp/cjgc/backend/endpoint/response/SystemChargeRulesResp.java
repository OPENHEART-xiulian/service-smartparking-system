package com.sp.cjgc.backend.endpoint.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统管理-收费规则表(SystemChargeRules)输出DTO
 *
 * @author zoey
 * @since 2024-08-15 14:32:03
 */
public class SystemChargeRulesResp implements Serializable {
    private static final long serialVersionUID = -45917941845506353L;

    @Getter
    @Setter
    @ApiModel(value = "SystemChargeRulesResp.SystemChargeRulesDTO", description = "系统管理-收费规则表输出DTO")
    public static class SystemChargeRulesDTO implements Serializable {
        private static final long serialVersionUID = 805583940785756121L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                            
        @ApiModelProperty(value = "进场免费时长，单位：分钟", position = 2)
        private Integer freeDuration;                 
        @ApiModelProperty(value = "临保收费标准，单位：元/小时", position = 3)
        private String tollStandard;                  
        @ApiModelProperty(value = "月保收费标准-内部车辆，单位：元/月", position = 4)
        private String monthlyInternalCar;            
        @ApiModelProperty(value = "月保收费标准-所属企业公车，单位：元/月", position = 5)
        private String monthlyEnterpriseCar;          
        @ApiModelProperty(value = "月保收费标准-外部车辆(机械车位)，单位：元/月", position = 6)
        private String monthlyExternalCarMachinery;   
        @ApiModelProperty(value = "月保收费标准-外部车辆(非机械车位)，单位：元/月", position = 7)
        private String monthlyInternalCarNoMachinery;
        @ApiModelProperty(value = "临保收费上限。单位：元/24小时", position = 8)
        private String feeCap;
        //@formatter:on
    }
}
