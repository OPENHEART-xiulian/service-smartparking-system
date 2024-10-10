package com.sp.cjgc.backend.domain;

import lombok.Data;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 10:46:32
 * @Description: 收益统计
 */
@Data
public class RevenueStatistics {

    private Double todayTemporaryAmount;                  // 临保今日收益
    private Double totalTemporaryAmount;                  // 临保总收益
    private Double todayPaymentAmount;                    // 月保今日收益
    private Double totalAccumulatePaymentAmount;          // 月保总收益
}
