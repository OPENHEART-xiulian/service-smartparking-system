package com.sp.cjgc.backend.endpoint.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统管理-车位数量表(SystemParking)输入DTO
 *
 * @author zoey
 * @since 2024-08-15 16:24:53
 */
public class SystemParkingReq implements Serializable {
    private static final long serialVersionUID = -42651075216838461L;

    @Getter
    @Setter
    @ApiModel(value = "SystemParkingReq.UpdateDTO", description = "系统管理-车位数量表修改DTO")
    public static class UpdateDTO implements Serializable {
        private static final long serialVersionUID = 869909433170530125L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1, required = true)
        private String id;                
        @ApiModelProperty(value = "车位总数量", position = 2)
        private Integer parkingNumber;    
        @ApiModelProperty(value = "可分配车位总数量", position = 3)
        private Integer disposableNumber; 
        //@formatter:on
    }
}
