package com.sp.cjgc.backend.endpoint.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 09:38:40
 * @Description:
 */
public class HomePageResp implements Serializable {
    private static final long serialVersionUID = 382540112616346005L;

    @Getter
    @Setter
    @ApiModel(value = "HomePageResp.CountOrderDTO", description = "首页-订单统计输出DTO")
    public static class CountOrderDTO implements Serializable {
        private static final long serialVersionUID = -43459889318881245L;
        //@formatter:off
        @ApiModelProperty(value = "年份-月份", position = 1)
        private String month;
        @ApiModelProperty(value = "优惠金额", position = 2)
        private Double totalDiscountAmount;
        @ApiModelProperty(value = "收入金额", position = 3)
        private Double totalIncomeAmount;
        @ApiModelProperty(value = "总收入金额", position = 4)
        private Double totalAmount;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "HomePageResp.RevenueStatisticsDTO", description = "首页-收益统计输出DTO")
    public static class RevenueStatisticsDTO implements Serializable {
        private static final long serialVersionUID = -43459889318881245L;
        //@formatter:off
        @ApiModelProperty(value = "临保今日收益", position = 1)
        private Double todayTemporaryAmount;
        @ApiModelProperty(value = "临保总收益", position = 2)
        private Double totalTemporaryAmount;
        @ApiModelProperty(value = "月保今日收益", position = 3)
        private Double todayPaymentAmount;
        @ApiModelProperty(value = "月保总收益", position = 4)
        private Double totalAccumulatePaymentAmount;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "HomePageResp.ParkingSpaceStatisticsDTO", description = "首页-车位统计输出DTO")
    public static class ParkingSpaceStatisticsDTO implements Serializable {
        private static final long serialVersionUID = -43459889318881245L;
        //@formatter:off
        @ApiModelProperty(value = "总车位", position = 1)
        private Long totalParkingSpaces;
        @ApiModelProperty(value = "剩余车位", position = 2)
        private Long remainingParkingSpaces;
        @ApiModelProperty(value = "已用车位", position = 3)
        private Long usedParkingSpaces;
        @ApiModelProperty(value = "租赁车位", position = 4)
        private Long rentalParkingSpace;
        @ApiModelProperty(value = "普通车位", position = 5)
        private Long ordinaryParkingSpace;
        @ApiModelProperty(value = "已发劵车位", position = 6)
        private Long issuedParkingSpaces;
        @ApiModelProperty(value = "未发劵车位", position = 7)
        private Long unlicensedParkingSpaces;
        //@formatter:on
    }
}
