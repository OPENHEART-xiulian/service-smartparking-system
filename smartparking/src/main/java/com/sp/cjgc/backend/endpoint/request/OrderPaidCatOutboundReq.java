package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 订单-车辆出库已支付订单记录(OrderPaidCatOutbound)输入DTO
 *
 * @author zoey
 * @since 2024-08-20 13:50:21
 */
public class OrderPaidCatOutboundReq implements Serializable {
    private static final long serialVersionUID = -65594802613986923L;

    @Getter
    @Setter
    @ApiModel(value = "OrderPaidCatOutboundReq.QueryDTO", description = "订单-车辆出库已支付订单记录查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = 125626120493057390L;
        //@formatter:off
        @ApiModelProperty(value = "车牌号/手机号码(无牌车)", position = 1)
        private String mainlandLicensePlates;
        @ApiModelProperty(value = "进场时间,时间格式:yyyy-MM-dd HH:mm:ss", position = 2)
        private String startTime;
        @ApiModelProperty(value = "出场时间,时间格式:yyyy-MM-dd HH:mm:ss", position = 3)
        private String endTime;
        @ApiModelProperty(value = "停车时长", position = 4)
        private String totalDuration;
        @ApiModelProperty(value = "订单编号", position = 6)
        private String orderNumber;
        @ApiModelProperty(value = "总计金额", position = 7)
        private String totalAmount;
        @ApiModelProperty(value = "优惠金额", position = 8)
        private String totalDiscountAmount;
        @ApiModelProperty(value = "优惠信息", position = 9)
        private String discount;
        @ApiModelProperty(value = "收入金额", position = 10)
        private String totalIncomeAmount;
        @ApiModelProperty(value = "车辆类型编码，1｜临保；2｜月保；3｜访客车；4｜无车牌；5|其他", position = 11)
        private String typeCode;
        @ApiModelProperty(value = "类型名称", position = 12)
        private String typeName;
        @ApiModelProperty(value = "商户名称", position = 13)
        private String userName;
        @ApiModelProperty(value = "商户id", position = 14)
        private String userId;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "OrderPaidCatOutboundReq.QueryOrderDTO", description = "订单-车辆出库已支付订单记录查询DTO")
    public static class QueryOrderDTO implements Serializable {
        private static final long serialVersionUID = 125626120493057390L;
        //@formatter:off
        @ApiModelProperty(value = "车牌号/手机号码(无牌车)", position = 1)
        private String plates;
        @ApiModelProperty(value = "出库设备IP(有车牌号，就不需要出库设备IP，没车牌号，就一定需要出库设备IP)", position = 2)
        private String deviceIp;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "OrderPaidCatOutboundReq.Query1DTO", description = "无牌车进出场输入DTO")
    public static class Query1DTO implements Serializable {
        private static final long serialVersionUID = 125626120493057390L;
        //@formatter:off
        @ApiModelProperty(value = "车牌号/手机号码(无牌车)", position = 1, required = true)
        private String phoneNumber;
        @ApiModelProperty(value = "进出库设备IP", position = 2, required = true)
        private String deviceIp;
        @ApiModelProperty(value = "进出库设备端口号", position = 2, required = true)
        private String devicePort;
        //@formatter:on
    }
}
