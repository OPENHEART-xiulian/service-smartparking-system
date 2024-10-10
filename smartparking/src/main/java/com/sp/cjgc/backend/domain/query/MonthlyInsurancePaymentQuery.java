package com.sp.cjgc.backend.domain.query;

import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 月保-缴费记录(MonthlyInsurancePayment)查询类
 *
 * @author zoey
 * @since 2024-08-16 13:23:19
 */
@Getter
@Setter
@NoArgsConstructor
public class MonthlyInsurancePaymentQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = -11589506841134235L;
    //@formatter:off
    private String id;                      //ID
    private String mainlandLicensePlates;   //内地车牌号码
    private String monthlyStartTime;        //月保开始时间
    private String monthlyEndTime;          //月保结束时间
    private String carTypeCode;             //车辆类型编码；1|内部车辆；2|所属企业公车；3|外部车辆(机械车位)；4|外部车辆(非机械车位)
    private String carTypeName;             //车辆类型名称
    private String userName;                //姓名
    private String phoneNumber;             //手机号码
    private String cardId;                  //身份证号码
    private String parkingLotCode;          //车位编号
    private String monthlyFree;             //月保费用/月
    private String monthlyStatus;           //月保状态 1｜在保；2｜过保
    private String paymentStatus;           //缴费状态
    private String paymentAmount;           //缴费金额
    private Integer longTerm;               //是否长期有效，0|否;1|是,默认否
    //@formatter:on
}
