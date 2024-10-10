package com.sp.cjgc.backend.endpoint.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商户-停车领劵(ParkCollectCoupons)输出DTO
 *
 * @author zoey
 * @since 2024-08-22 09:56:37
 */
public class ParkCollectCouponsResp implements Serializable {
    private static final long serialVersionUID = -32845787241193632L;

    @Getter
    @Setter
    @ApiModel(value = "ParkCollectCouponsResp.ParkCollectCouponsDTO", description = "商户-停车领劵输出DTO")
    public static class ParkCollectCouponsDTO implements Serializable {
        private static final long serialVersionUID = -10551317569608734L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                    
        @ApiModelProperty(value = "商户ID", position = 2)
        private String userId;                
        @ApiModelProperty(value = "车牌号/手机号码(无牌车)", position = 3)
        private String mainlandLicensePlates; 
        @ApiModelProperty(value = "进场时间", position = 4)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime startTime;      
        @ApiModelProperty(value = "出场时间", position = 5)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime endTime;        
        @ApiModelProperty(value = "停车时长", position = 6)
        private String totalDuration;         
        @ApiModelProperty(value = "总计金额", position = 7)
        private String totalAmount;           
        @ApiModelProperty(value = "优惠金额", position = 8)
        private String totalDiscountAmount;   
        @ApiModelProperty(value = "优惠信息", position = 9)
        private String discount;              
        @ApiModelProperty(value = "需付金额", position = 10)
        private String totalIncomeAmount;     
        @ApiModelProperty(value = "领劵时间", position = 11)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime receiveTime;    
        @ApiModelProperty(value = "是否已出场,0|未出场；1|已出场", position = 12)
        private Integer isItOver;             
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "ParkCollectCouponsResp.ParkCollectDTO", description = "商户-停车领劵输出DTO")
    public static class ParkCollectDTO implements Serializable {
        private static final long serialVersionUID = -10551317569608734L;
        //@formatter:off
        @ApiModelProperty(value = "商户名称", position = 1)
        private String userName;
        @ApiModelProperty(value = "发放状态;0|已发放完毕;1|可发放", position = 2)
        private String assignedStatus;
        @ApiModelProperty(value = "可发放车位数量(剩余抵用券)", position = 3)
        private String assignedNumber;
        //@formatter:on
    }
}
