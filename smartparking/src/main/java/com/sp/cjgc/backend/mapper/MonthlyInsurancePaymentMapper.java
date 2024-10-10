package com.sp.cjgc.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sp.cjgc.backend.domain.MonthlyInsurancePayment;
import com.sp.cjgc.backend.domain.query.MonthlyInsurancePaymentQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 月保-缴费记录(MonthlyInsurancePayment)表数据库访问层
 *
 * @author zoey
 * @since 2024-08-16 13:23:20
 */
@Repository
public interface MonthlyInsurancePaymentMapper extends BaseMapper<MonthlyInsurancePayment> {

    /**
     * 统计 月保-缴费记录 总数
     *
     * @param query
     * @return
     */
    Long countTotal(@Param("query") MonthlyInsurancePaymentQuery query);

    /**
     * 分页查询 月保-缴费记录 列表
     *
     * @param query
     * @return
     */
    List<MonthlyInsurancePayment> getPageList(@Param("query") MonthlyInsurancePaymentQuery query);

    /**
     * 查询车辆月保状态
     *
     * @param paymentStatus 1 已缴费 ｜0 未缴费
     * @param plates        车牌号
     * @return
     */
    MonthlyInsurancePayment getCarStatus(@Param("paymentStatus") Integer paymentStatus, @Param("plates") String plates);

    /**
     * 统计今日月保缴费记录
     *
     * @return
     */
    Double getTodayPayments();

    /**
     * 统计总月保缴费记录
     *
     * @return
     */
    Double getTotalPayments();
}
