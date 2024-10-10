package com.sp.cjgc.backend.endpoint.response;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 月保-缴费记录(MonthlyInsurancePayment)输出DTO
 *
 * @author zoey
 * @since 2024-08-16 13:23:25
 */
public class MonthlyInsurancePaymentResp implements Serializable {
    private static final long serialVersionUID = -17956375633876880L;

    @Getter
    @Setter
    @ApiModel(value = "MonthlyInsurancePaymentResp.MonthlyInsurancePaymentDTO", description = "月保-缴费记录输出DTO")
    public static class MonthlyInsurancePaymentDTO implements Serializable {
        private static final long serialVersionUID = -59565814640583690L;
        //@formatter:off
        @ApiModelProperty(value = "ID", position = 1)
        private String id;                      
        @ApiModelProperty(value = "内地车牌号码", position = 2)
        private String mainlandLicensePlates;   
        @ApiModelProperty(value = "月保开始时间", position = 3)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime monthlyStartTime; 
        @ApiModelProperty(value = "月保结束时间", position = 4)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime monthlyEndTime;   
        @ApiModelProperty(value = "车辆类型编码；1|内部车辆；2|所属企业公车；3|外部车辆(机械车位)；4|外部车辆(非机械车位)", position = 5)
        private String carTypeCode;
        @ApiModelProperty(value = "车辆类型名称", position = 5)
        private String carTypeName;
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
        @ApiModelProperty(value = "有效期", position = 12)
        private String expirationDate;
        @ApiModelProperty(value = "缴费状态,0｜未缴费；1｜已缴费", position = 13)
        private Integer paymentStatus;
        @ApiModelProperty(value = "缴费金额", position = 14)
        private String paymentAmount;
        @ApiModelProperty(value = "累计缴费金额", position = 15)
        private String accumulatePaymentAmount;
        @ApiModelProperty(value = "是否长期有效，0|否;1|是,默认否", position = 16)
        private Integer longTerm;
        //@formatter:on
    }

    @Getter
    @Setter
    @ApiModel(value = "MonthlyInsurancePaymentResp.ListDTO", description = "月保-缴费记录-列表输出DTO")
    public static class ListDTO extends PageInfoResp implements Serializable {
        private static final long serialVersionUID = 213748121243632372L;
        @ApiModelProperty(value = "数据列表", position = 4)
        private List<MonthlyInsurancePaymentDTO> list;
    }
}
