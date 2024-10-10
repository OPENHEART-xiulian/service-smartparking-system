package com.sp.cjgc.backend.order;

import cn.hutool.core.util.IdUtil;
import com.sp.cjgc.MyConstants;
import com.sp.cjgc.backend.domain.OrderPaidCatOutbound;
import com.sp.cjgc.backend.domain.ParkCollectCoupons;
import com.sp.cjgc.backend.domain.RecordsCarOutbound;
import com.sp.cjgc.backend.enums.CarGroupEnum;
import com.sp.cjgc.backend.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: Zoey
 * @Since: 2024-08-29 14:45:03
 * @Description: 订单创建工具类
 */
public class OrderUtils {

    /**
     * 非领劵用户提前支付生成需要支付的订单记录
     *
     * @param license   车牌号
     * @param startTime 进场时间
     * @return
     */
    public static OrderPaidCatOutbound createOrder(String license, LocalDateTime startTime) {
        // 获取系统当前时间
        LocalDateTime now = LocalDateTime.now();
        // 创建订单对象
        OrderPaidCatOutbound order = new OrderPaidCatOutbound();
        order.setUserId(ChargeRulesUtil.getUserId());
        order.setMainlandLicensePlates(license);
        order.setStartTime(startTime);
        order.setEndTime(now);
        order.setPlayTime(now);
        order.setOrderNumber(getOrderNo());
        // 计算停车时长
        order.setTotalDuration(TemporaryUtil.getParkingDuration(startTime, now));
        // 实际收入
        String realityAmount = TemporaryUtil.temporaryFee(true, startTime, now);
        // 总收入
        String totalAmount = TemporaryUtil.temporaryFee(false, startTime, now);
        order.setTotalAmount(totalAmount);
        order.setTotalIncomeAmount(realityAmount);
        // 优惠收入
        order.setTotalDiscountAmount(new BigDecimal(totalAmount)
                .subtract(new BigDecimal(realityAmount)).toString());

        order.setDiscount("免费停车" + ChargeRulesUtil.getFreeDuration() + "分钟");
        order.setTypeCode(CarGroupEnum.LIN_BAN.getCarGroupId().toString());
        order.setTypeName(CarGroupEnum.LIN_BAN.getCarGroupName());
        order.setPlayStatus(OrderStatus.NOTPAY.getType());
        order.setAdvancePayment(1);
        order.setCreateTime(now);
        return order;
    }

    /**
     * 用户在商户领劵后生成需要支付的订单记录
     *
     * @param entity 用户领劵对象
     * @return
     */
    public static OrderPaidCatOutbound createOrder(ParkCollectCoupons entity) {
        // 获取系统当前时间
        LocalDateTime now = LocalDateTime.now();
        // 创建订单对象
        OrderPaidCatOutbound order = new OrderPaidCatOutbound();
        order.setUserId(entity.getUserId());
        order.setMainlandLicensePlates(entity.getMainlandLicensePlates());
        order.setStartTime(entity.getStartTime());
        order.setEndTime(entity.getEndTime());
        order.setTotalDuration(entity.getTotalDuration());
        order.setPlayTime(now);
        order.setOrderNumber(getOrderNo());
        order.setTotalAmount(entity.getTotalAmount());
        order.setTotalDiscountAmount(entity.getTotalDiscountAmount());
        order.setDiscount(entity.getDiscount());
        order.setTotalIncomeAmount(entity.getTotalIncomeAmount());
        order.setTypeCode(CarGroupEnum.LIN_BAN.getCarGroupId().toString());
        order.setTypeName(CarGroupEnum.LIN_BAN.getCarGroupName());
        order.setPlayStatus(OrderStatus.NOTPAY.getType());
        order.setAdvancePayment(0);
        order.setCreateTime(now);
        return order;
    }

    /**
     * 在出场的时候生成需要支付的订单记录
     *
     * @param userId        商户ID
     * @param realityAmount 实际收入金额
     * @param totalAmount   总收入金额
     * @param entity        车辆出库记录对象
     * @return
     */
    public static OrderPaidCatOutbound createOrder(String userId, String realityAmount, String totalAmount, RecordsCarOutbound entity) {
        // 获取系统当前时间
        LocalDateTime now = LocalDateTime.now();
        // 创建订单对象
        OrderPaidCatOutbound order = new OrderPaidCatOutbound();
        order.setUserId(userId);
        order.setMainlandLicensePlates(entity.getMainlandLicensePlates());
        order.setStartTime(entity.getStartTime());
        order.setEndTime(entity.getEndTime());
        order.setTotalDuration(entity.getParkingDuration());
        order.setPlayTime(now);
        order.setOrderNumber(getOrderNo());
        order.setTotalAmount(totalAmount);
        order.setDeviceIp(entity.getEndCameraDeviceIp());
        // 总收入减去实际收入 = 优惠金额
        order.setTotalDiscountAmount(new BigDecimal(totalAmount)
                .subtract(new BigDecimal(realityAmount)).toString());
        order.setDiscount("免费停车" + ChargeRulesUtil.getFreeDuration() + "分钟");
        order.setTotalIncomeAmount(realityAmount);
        order.setTypeCode(entity.getCarGroupId().toString());
        order.setTypeName(entity.getCarGroupName());
        order.setPlayStatus(OrderStatus.NOTPAY.getType());
        order.setAdvancePayment(0);
        order.setCreateTime(now);
        return order;
    }

    /**
     * 领劵用户，超时出场，提前支付时，重新计算费用
     *
     * @param order
     * @param startTime
     */
    public static void countFee(OrderPaidCatOutbound order, LocalDateTime startTime) {
        LocalDateTime endTime = LocalDateTime.now();
        order.setStartTime(startTime);
        order.setEndTime(endTime);
        // 计算停车时长
        order.setTotalDuration(TemporaryUtil.getParkingDuration(startTime, endTime));
        // 实际收入
        String realityAmount = TemporaryUtil.temporaryFee(true, startTime, endTime);
        // 总收入
        String totalAmount = TemporaryUtil.temporaryFee(false, startTime, endTime);
        order.setTotalAmount(totalAmount);
        order.setTotalIncomeAmount(realityAmount);
        order.setTotalDiscountAmount(new BigDecimal(totalAmount)
                .subtract(new BigDecimal(realityAmount)).toString());
        order.setAdvancePayment(1);
        order.setCreateTime(endTime);
    }

    /**
     * 获取订单编号
     *
     * @return
     */
    private static String getOrderNo() {
        return "ORDER_" + getNo();
    }

    /**
     * 获取退款单编号
     *
     * @return
     */
    private static String getRefundNo() {
        return "REFUND_" + getNo();
    }

    /**
     * 获取编号
     *
     * @return
     */
    private static String getNo() {
        return IdUtil.getSnowflake().nextIdStr();
    }
}
