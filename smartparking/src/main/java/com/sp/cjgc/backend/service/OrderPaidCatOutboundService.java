package com.sp.cjgc.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.cjgc.backend.domain.MonthlyStatistics;
import com.sp.cjgc.backend.domain.OrderPaidCatOutbound;
import com.sp.cjgc.backend.domain.RevenueStatistics;
import com.sp.cjgc.backend.domain.query.OrderPaidCatOutboundQuery;
import com.sp.cjgc.common.pageutil.PageInfoRespQuery;

import java.util.List;

/**
 * 订单-车辆出库已支付订单记录(OrderPaidCatOutbound)表服务接口
 *
 * @author zoey
 * @since 2024-08-20 13:50:20
 */
public interface OrderPaidCatOutboundService extends IService<OrderPaidCatOutbound> {

    /**
     * 订单-车辆出库已支付订单记录-分页查询列表
     *
     * @param query
     * @return
     */
    PageInfoRespQuery getPageList(OrderPaidCatOutboundQuery query);

    /**
     * 根据车牌号查询当天支付时间为最新的提前支付订单
     *
     * @param playStatus 支付状态
     * @param plates     车牌号
     * @return
     */
    OrderPaidCatOutbound getEntityByPlates(String playStatus, String plates);

    /**
     * 根据车牌号查询当天支付时间为最新的支付订单
     *
     * @param deviceIp 设备IP
     * @param plates   车牌号
     * @return
     */
    OrderPaidCatOutbound getEntityBy(String plates, String deviceIp);

    /**
     * 车辆在出库的时候查询预支付订单
     * 或提前支付的的时候生成预支付订单
     * 当有出库设备IP的时候，就是查询预支付订单
     * 当有车牌号或手机号码的时候，就是生成预支付订单
     *
     * @param license   车牌号或手机号码
     * @param deviceIp 出库设备IP
     * @return
     */
    OrderPaidCatOutbound getEntity(String license, String deviceIp);

    /**
     * 无牌车出场生成预支付订单
     *
     * @param phoneNumber 手机号码
     * @param deviceIp    出口设备IP
     * @return
     */
    OrderPaidCatOutbound createOrder(String phoneNumber, String deviceIp);

    /**
     * 订单统计
     *
     * @param year
     * @param userId
     * @return
     */
    List<MonthlyStatistics> getMonthlyStatistics(String year, String userId);

    /**
     * 临保收益统计
     *
     * @param userId
     * @return
     */
    RevenueStatistics getStatistics(String userId);

    /**
     * 创建订单
     *
     * @param entity
     * @return
     */
    OrderPaidCatOutbound createOrder(OrderPaidCatOutbound entity);

    /**
     * 根据订单号更新订单状态
     *
     * @param orderNo
     */
    void updateStatusByOrderNo(String orderNo);
}
