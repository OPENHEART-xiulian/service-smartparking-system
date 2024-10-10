package com.sp.cjgc.backend.endpoint.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统管理-车位数量表(SystemParking)输出DTO
 *
 * @author zoey
 * @since 2024-08-15 16:24:55
 */
public class SystemParkingResp implements Serializable {
    private static final long serialVersionUID = -39756914905704505L;

    @Getter
    @Setter
    @ApiModel(value = "SystemParkingResp.SystemParkingDTO", description = "系统管理-车位数量表输出DTO")
    public static class SystemParkingDTO implements Serializable {
        private static final long serialVersionUID = -73479500544768705L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                
        @ApiModelProperty(value = "车位总数量", position = 2)
        private Integer parkingNumber;    
        @ApiModelProperty(value = "可分配车位总数量", position = 3)
        private Integer disposableNumber; 
        //@formatter:on
    }
}
