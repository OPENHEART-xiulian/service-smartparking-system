package com.sp.cjgc.backend.domain;

import lombok.Data;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 09:25:46
 * @Description: 月收费统计
 */
@Data
public class MonthlyStatistics {

    private String month;                // 月份
    private Double totalDiscountAmount;  // 优惠金额
    private Double totalIncomeAmount;    // 收入金额
    private Double totalAmount;          // 总收入金额
}
