package com.sp.cjgc.backend.domain.query;

import com.sp.cjgc.common.pageutil.PageInfoQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商户-对账记录(MerchantReconciliation)查询类
 *
 * @author zoey
 * @since 2024-08-20 13:13:44
 */
@Getter
@Setter
@NoArgsConstructor
public class MerchantReconciliationQuery extends PageInfoQuery implements Serializable {
    private static final long serialVersionUID = 978207154090468810L;
    //@formatter:off
    private String id;                  //ID
    private String userId;              //用户ID
    private String totalDuration;       //总停车时长
    private String totalAmount;         //总计金额
    private String totalDiscountAmount; //总计优惠金额
    private String totalIncomeAmount;   //总计收入金额
    private String yearNumber;          //年份
    private String monthNumber;         //月份
    private String status;              //发放状态

    private String userName;            //用户名称
    private Integer assignedNumber;     //租赁车位数量
    //@formatter:on
}
