package com.sp.cjgc.backend.order;

import com.sp.cjgc.backend.domain.ParkCollectCoupons;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Zoey
 * @Since: 2024-09-02 16:45:26
 * @Description: 计算临停费用
 */
public class TemporaryUtil {

    /**
     * 计算减去免费停车时长后，需要缴纳多少停车费用，单位：元
     * 计算实际收入 和 总收入
     * flg = false 的时候 计算的是总收入
     * flg = true 的时候 计算的是实际收入
     *
     * @param flg       是否需要计算减去免费停车时长 true｜需要减去免费停车时长 false｜不需要减去免费停车时长
     * @param startTime 停车开始时间
     * @param endTime   停车结束时间
     */
    public static String temporaryFee(Boolean flg, LocalDateTime startTime, LocalDateTime endTime) {
        // 获取临保收费标准，单位：元/小时
        String tollStandard = ChargeRulesUtil.getTollStandard();
        // 获取免费停车时长,单位：分钟
        Integer freeTime = ChargeRulesUtil.getFreeDuration();

        // 计算总停车时长
        Duration duration = Duration.between(startTime, endTime);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        // 计算总停车时长，单位：分钟
        long totalMinutes = (days * 24 * 60) + (hours * 60L) + minutes;

        // 定义封顶费用为0
        String cappingFee = "0";
        // 判断 days 是否有值
        if (days > 0) {
            // 计算封顶费用
            cappingFee = new BigDecimal(days).multiply(new BigDecimal(ChargeRulesUtil.getFeeCap())).toString();
            // 计算减去封顶时长后的剩余时间，单位：分钟
            totalMinutes -= days * 24 * 60;

            // 减去免费停车时长，单位：分钟
            if (flg) totalMinutes -= freeTime;

            // 判断是否大于0
            if (totalMinutes > 0) {
                // 计算收费时间:单位小时，不足一小时按照一小时计算
                double totalHours = Math.ceil(totalMinutes / 60.0);
                // 临保收费标准，单位：元/小时
                return new BigDecimal(tollStandard)
                        .multiply(BigDecimal.valueOf(totalHours))
                        .add(new BigDecimal(cappingFee))
                        .setScale(2, RoundingMode.HALF_UP)
                        .toString();
            } else {
                return cappingFee;
            }
        } else {
            // 减去免费停车时长
            if (flg) totalMinutes -= freeTime;
            // 计算收费时间:单位小时，不足一小时按照一小时计算
            double totalHours = Math.ceil(totalMinutes / 60.0);
            // 临保收费标准，单位：元/小时
            return new BigDecimal(tollStandard)
                    .multiply(BigDecimal.valueOf(totalHours))
                    .add(new BigDecimal(cappingFee))
                    .setScale(2, RoundingMode.HALF_UP)
                    .toString();
        }
    }

    /**
     * 计算从进场时间到出场时间的停车时长，并返回表示为“几小时几分”的字符串。
     *
     * @param startTime 进场时间
     * @param endTime   出场时间
     * @return 停车时长的字符串表示
     */
    public static String getParkingDuration(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        StringBuilder result = new StringBuilder();
        if (days > 0) result.append(days).append("天");
        if (hours > 0) result.append(hours).append("小时");
        if (minutes > 0) result.append(minutes).append("分钟");
        if (minutes == 0) result.append("1分钟");
        // 返回格式化的字符串
        return result.toString();
    }

    /**
     * 解析停车时长字符串，并返回以小时为单位的总小时数。
     *
     * @param durationStr 停车时长的字符串表示
     * @return 总小时数
     */
    public static double parseDurationToHours(String durationStr) {
        Pattern pattern = Pattern.compile("(\\d+)天|(\\d+)小时|(\\d+)分钟");
        Matcher matcher = pattern.matcher(durationStr);

        int days = 0;
        int hours = 0;
        int minutes = 0;

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                days = Integer.parseInt(matcher.group(1));
            }
            if (matcher.group(2) != null) {
                hours = Integer.parseInt(matcher.group(2));
            }
            if (matcher.group(3) != null) {
                minutes = Integer.parseInt(matcher.group(3));
            }
        }
        // 计算总小时数
        return days * 24 + hours + minutes / 60.0;
    }


    /**
     * 计算停车总金额、优惠金额、优惠时长、优惠信息
     *
     * @param parkCollectCoupons 商户-停车领劵(ParkCollectCoupons)实体类
     * @param freeTime           该商户的免费停车时长，单位：分钟
     * @param tollStandardStr    临保收费标准，单位：元/小时
     * @return
     */
    public static void computationalCost(ParkCollectCoupons parkCollectCoupons, Integer freeTime, String tollStandardStr) {
        // 临保收费标准，单位：元/小时
        BigDecimal tollStandard = new BigDecimal(tollStandardStr);
        // 将免费停车时长从分钟转换为小时
        double freeHours = freeTime / 60.0;
        // 计算两个时间之间的差值
        Duration duration = Duration.between(parkCollectCoupons.getStartTime(), parkCollectCoupons.getEndTime());
        // 将总停车时长转换为小时
        double totalParkingHours = Math.ceil(duration.toMinutes() / 60.0);
        // 计算总停车费用
        BigDecimal totalParkingCost = tollStandard.multiply(BigDecimal.valueOf(totalParkingHours)).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 总计金额
        parkCollectCoupons.setTotalAmount(totalParkingCost.toString());
        // 计算免费金额
        BigDecimal freeAmount = tollStandard.multiply(BigDecimal.valueOf(freeHours)).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 优惠金额
        parkCollectCoupons.setTotalDiscountAmount(freeAmount.toString());
        // 计算需付金额
        BigDecimal payableAmount = totalParkingCost.subtract(freeAmount).max(BigDecimal.ZERO); // 确保不会出现负数
        // 需付金额
        parkCollectCoupons.setTotalIncomeAmount(payableAmount.toString());
        // 优惠信息
        parkCollectCoupons.setDiscount("免费" + freeHours + "小时");
    }
}
