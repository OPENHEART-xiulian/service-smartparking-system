package com.sp.cjgc.backend.endpoint.request;

import com.sp.cjgc.common.pageutil.PageInfoReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 月保-缴费记录(MonthlyInsurancePayment)输入DTO
 *
 * @author zoey
 * @since 2024-08-16 13:23:24
 */
public class MonthlyInsurancePaymentReq implements Serializable {
    private static final long serialVersionUID = -42681011692763671L;

    @Getter
    @Setter
    @ApiModel(value = "MonthlyInsurancePaymentReq.CreateOrUpdateDTO", description = "月保-缴费记录新增或更新DTO")
    public static class CreateOrUpdateDTO implements Serializable {
        private static final long serialVersionUID = 329018417871461834L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                      
        @ApiModelProperty(value = "内地车牌号码", position = 2, required = true)
        private String mainlandLicensePlates;   
        @ApiModelProperty(value = "月保开始时间,格式：yyyy-MM", position = 3, required = true)
        private String monthlyStartTime;
        @ApiModelProperty(value = "月保结束时间,格式：yyyy-MM", position = 4, required = true)
        private String monthlyEndTime;
        @ApiModelProperty(value = "车辆类型编码；1|内部车辆；2|所属企业公车；3|外部车辆(机械车位)；4|外部车辆(非机械车位)", position = 5, required = true)
        private String carTypeCode;
        @ApiModelProperty(value = "姓名", position = 6)
        private String userName;                
        @ApiModelProperty(value = "手机号码", position = 7)
        private String phoneNumber;             
        @ApiModelProperty(value = "身份证号码", position = 8)
        private String cardId;                  
        @ApiModelProperty(value = "车位编号", position = 9)
        private String parkingLotCode;          
        @ApiModelProperty(value = "月保费用/月", position = 10, required = true)
        private String monthlyFree;
        @ApiModelProperty(value = "是否长期有效，0|否;1|是,默认否", position = 11)
        private Integer longTerm;
        @ApiModelProperty(value = "缴费状态", position = 12)
        private String paymentStatus;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "MonthlyInsurancePaymentReq.QueryDTO", description = "月保-缴费记录查询DTO")
    public static class QueryDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = 823606908480238913L;
        //@formatter:off
        @ApiModelProperty(value = "内地车牌号码", position = 2)
        private String mainlandLicensePlates;
        @ApiModelProperty(value = "月保开始时间，时间格式：yyyy-MM-dd HH:mm:ss", position = 3)
        private String monthlyStartTime;
        @ApiModelProperty(value = "月保结束时间，时间格式：yyyy-MM-dd HH:mm:ss", position = 4)
        private String monthlyEndTime;
        @ApiModelProperty(value = "车辆类型编码；1|内部车辆；2|所属企业公车；3|外部车辆(机械车位)；4|外部车辆(非机械车位)", position = 5)
        private String carTypeCode;
        @ApiModelProperty(value = "姓名", position = 6)
        private String userName;
        @ApiModelProperty(value = "手机号码", position = 7)
        private String phoneNumber;
        @ApiModelProperty(value = "身份证号码", position = 8)
        private String cardId;
        @ApiModelProperty(value = "车位编号", position = 9)
        private String parkingLotCode;
        @ApiModelProperty(value = "月保费用/月", position = 10)
        private String monthlyFree;
        @ApiModelProperty(value = "月保状态 1｜在保；2｜过保", position = 11)
        private String monthlyStatus;
        @ApiModelProperty(value = "缴费状态", position = 12)
        private String paymentStatus;
        @ApiModelProperty(value = "是否长期有效，0|否;1|是", position = 13)
        private Integer longTerm;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "MonthlyInsurancePaymentReq.DeleteDTO", description = "月保-缴费记录删除DTO")
    public static class DeleteDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = 823606908480238913L;
        //@formatter:off
        @ApiModelProperty(value = "id", position = 1)
        private List<String> ids;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "MonthlyInsurancePaymentReq.UpdateStatusDTO", description = "月保-缴费记录状态修改DTO")
    public static class UpdateStatusDTO extends PageInfoReq implements Serializable {
        private static final long serialVersionUID = 823606908480238913L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1,required = true)
        private String id;
        @ApiModelProperty(value = "是否长期有效，0|否;1|是", position = 2,required = true)
        private Integer longTerm;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "MonthlyInsurancePaymentReq.CountMoneyDTO", description = "月保-缴费金额计算DTO")
    public static class CountMoneyDTO implements Serializable {
        private static final long serialVersionUID = 329018417871461834L;
        //@formatter:off
        @ApiModelProperty(value = "月保开始时间,格式：yyyy-MM", position = 1, required = true)
        private String monthlyStartTime;
        @ApiModelProperty(value = "月保结束时间,格式：yyyy-MM", position = 2, required = true)
        private String monthlyEndTime;
        @ApiModelProperty(value = "车辆类型编码；1|内部车辆；2|所属企业公车；3|外部车辆(机械车位)；4|外部车辆(非机械车位)", position = 3, required = true)
        private String carTypeCode;
        @ApiModelProperty(value = "月保费用/月", position = 4, required = true)
        private String monthlyFree;
        @ApiModelProperty(value = "是否长期有效，0|否;1|是,默认否", position = 5, required = true)
        private Integer longTerm;
        //@formatter:on
    }
}
