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
 * 订单-车辆出库已支付订单记录(OrderPaidCatOutbound)输出DTO
 *
 * @author zoey
 * @since 2024-08-20 13:50:21
 */
public class OrderPaidCatOutboundResp implements Serializable {
    private static final long serialVersionUID = 783975144552055803L;

    @Getter
    @Setter
    @ApiModel(value = "OrderPaidCatOutboundResp.OrderPaidCatOutboundDTO", description = "订单-车辆出库已支付订单记录输出DTO")
    public static class OrderPaidCatOutboundDTO implements Serializable {
        private static final long serialVersionUID = -42410459425062866L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                    
        @ApiModelProperty(value = "商户ID", position = 2)
        private String userId;
        @ApiModelProperty(value = "商户名称", position = 2)
        private String userName;
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
        @ApiModelProperty(value = "订单支付时间", position = 7)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime playTime;       
        @ApiModelProperty(value = "订单编号", position = 8)
        private String orderNumber;           
        @ApiModelProperty(value = "总计金额", position = 9)
        private String totalAmount;           
        @ApiModelProperty(value = "优惠金额", position = 10)
        private String totalDiscountAmount;   
        @ApiModelProperty(value = "优惠信息", position = 11)
        private String discount;              
        @ApiModelProperty(value = "收入金额", position = 12)
        private String totalIncomeAmount;     
        @ApiModelProperty(value = "车辆类型编码，1｜临保；2｜月保；3｜访客车；4｜无车牌；5|其他", position = 13)
        private String typeCode;              
        @ApiModelProperty(value = "类型名称", position = 14)
        private String typeName;
        @ApiModelProperty(value = "支付状态", position = 15)
        private String playStatus;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "OrderPaidCatOutboundResp.ListDTO", description = "订单-车辆出库已支付订单记录-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<OrderPaidCatOutboundDTO> list;
    }
}
