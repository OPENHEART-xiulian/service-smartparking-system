package com.sp.cjgc.backend.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * @Author: Zoey
 * @Since: 2024-08-16 15:12:06
 * @Description: 月保费用计算工具类
 */
public class MonthlyUtil {

    /**
     * 计算长期需要缴费的费用
     *
     * @param free 月保费用/月
     * @return
     */
    public static String countLongTerm(String free) {
        // 长期按照999年计算
//        BigDecimal dailyFree = new BigDecimal(free).multiply(BigDecimal.valueOf(12)).multiply(BigDecimal.valueOf(999));
//        return dailyFree.setScale(2, RoundingMode.HALF_UP).toString();
        return "0";
    }

    /**
     * 计算月保费用
     *
     * @param typeCode  车辆类型编码；1|内部车辆；2|所属企业公车；3|外部车辆(机械车位)；4|外部车辆(非机械车位)
     * @param free      车辆类型对应的月保金额
     * @param startTime 月保开始时间
     * @param endTime   月保结束时间
     * @return
     */
    public static String countFree(String typeCode, String free, LocalDateTime startTime, LocalDateTime endTime) {
        // 判断 开始时间和结束时间的 年份 是否填写规范
        if (DateTimeUtil.getYear(startTime) < DateTimeUtil.getYear(LocalDateTime.now())
                || DateTimeUtil.getYear(endTime) < DateTimeUtil.getYear(LocalDateTime.now())
                || DateTimeUtil.getYear(startTime) > DateTimeUtil.getYear(endTime)
        ) throw new RuntimeException("开始时间和结束时间填写不规范");

        // 根据车辆类型获取日费率
        BigDecimal dailyRate = getDailyRate(typeCode);
        // 判断开始、结束时间年份、月份是等于 当前时间的年份、月份
        if (DateTimeUtil.getYear(startTime) == DateTimeUtil.getYear(endTime)) {
            // 计算 开始时间和结束时间 的年份为 同一年的月保费用
            return currentYear(dailyRate, free, startTime, endTime);
        } else {
            // 计算 开始时间和结束时间 的年份为 不同的年 的月保费用
            return noCurrentYear(dailyRate, free, startTime, endTime);
        }
    }

    /**
     * 根据车辆类型编码获取日费率
     *
     * @param typeCode 车辆类型编码
     * @return 日费率
     */
    private static BigDecimal getDailyRate(String typeCode) {
        switch (typeCode) {
            case "1":
                return new BigDecimal("7"); // 内部车辆
            case "2":
                return new BigDecimal("27"); // 所属企业公车
            case "3":
                return new BigDecimal("27"); // 外部车辆(机械车位)
            case "4":
                return new BigDecimal("40"); // 外部车辆(非机械车位)
            default:
                return BigDecimal.ZERO; // 默认值
        }
    }

    /**
     * 计算开始时间和结束时间的年份为同一年的月保费用
     *
     * @param dailyRate 根据车辆类型编码获取日费率
     * @param free      月保费用
     * @param startTime 月保开始时间
     * @param endTime   月保结束时间
     * @return 月保费用
     */
    private static String currentYear(BigDecimal dailyRate, String free, LocalDateTime startTime, LocalDateTime endTime) {
        // 获取开始时间的月份、日期、以及当前月份的天数
        int start_month = DateTimeUtil.getMonth(startTime);
        int start_day = DateTimeUtil.getDay(startTime);
        int start_daysInMonth = DateTimeUtil.getDaysInMonth(startTime);

        // 获取结束时间的月份、日期、以及当前月份的天数
        int end_month = DateTimeUtil.getMonth(endTime);
        int end_day = DateTimeUtil.getDay(endTime);
        int end_daysInMonth = DateTimeUtil.getDaysInMonth(endTime);

        // 判断开时间和结束时间的月份是否为同一个月
        if (start_month == end_month) {
            // 判断 开始时间和结束时间的日期 是否是该月的 第一天和最后一天
            if (start_day == 1 && end_day == end_daysInMonth) {
                // 将每月的月保费用作为 收费依据
                return free;
            } else {
                // 如果开始时间和结束时间的日期 不是该月的 第一天或者最后一天  则按照天数收费，这里需要加上开始时间的当天
                int remainingDays = end_day - start_day + 1;
                // 计算剩余天数的费用，保留两位小数
                return dailyRate
                        .multiply(BigDecimal.valueOf(remainingDays))
                        .setScale(2, RoundingMode.HALF_UP).toString();
            }
        } else {
            // 计算 开始时间和结束时间 之间相隔几个月，需要将结束的月份排除，所以开始时间和结束之间的月份差，在排除开始和结束时间的月份 需要再减1
            int month = end_month - start_month - 1;
            // 计算月费
            BigDecimal dailyFree = new BigDecimal(free).multiply(BigDecimal.valueOf(month));

            // 1. 开始时间的日期为 该月第一天，结束时间的日期 为 该月最后一天
            if (start_day == 1 && end_day == end_daysInMonth) {
                // 开始时间和结束时间之间相隔的月份所需要的月费 + 两个月的月费 作为收费依据
                return dailyFree
                        .add(new BigDecimal(free).multiply(BigDecimal.valueOf(2)))
                        .setScale(2, RoundingMode.HALF_UP).toString();
            } else {
                // 如果开始时间的日期为该月第一天
                if (start_day == 1) {
                    // 将结束时间的日期 按照天数计费
                    BigDecimal multiply = dailyRate.multiply(BigDecimal.valueOf(end_day));
                    // 开始时间和结束时间之间相隔的月份所需要的月费 + 1个月的月费 + 结束时间的日期按照天数计费 作为收费依据
                    return dailyFree
                            .add(new BigDecimal(free))
                            .add(multiply)
                            .setScale(2, RoundingMode.HALF_UP).toString();
                }
                // 如果 结束时间的日期为该月的最后一天
                else if (end_day == end_daysInMonth) {
                    // 将开始时间的日期 按照天数计费
                    BigDecimal multiply = dailyRate.multiply(BigDecimal.valueOf(start_daysInMonth - start_day + 1));
                    // 开始时间和结束时间之间相隔的月份所需要的月费 + 1个月的月费 + 开始时间的日期按照天数计费 作为收费依据
                    return dailyFree
                            .add(new BigDecimal(free))
                            .add(multiply)
                            .setScale(2, RoundingMode.HALF_UP).toString();
                }
                // 如果开始时间的日期不是 该月的第一天，结束时间的日期不是该月的最后一天
                else {
                    // 计算开始时间的日期 + 结束时间的日期 是否为 30 天，散装的 以30 天作为 一个月计算
                    int dayAll = start_daysInMonth - start_day + end_day + 1;
                    // 以30天为一个月计算，计算余数
                    int i = dayAll % 30;
                    // 计算商
                    int a = dayAll / 30;
                    // 开始时间和结束时间之间相隔的月份所需要的月费 + 月费*商 作为收费依据
                    BigDecimal decimal = dailyFree.add(new BigDecimal(free).multiply(BigDecimal.valueOf(a)));
                    // 判断余数是否为0
                    if (i == 0) {
                        // 开始时间和结束时间 之间相隔几个月 的月费 + 月费*商 作为 收费依据
                        return decimal.setScale(2, RoundingMode.HALF_UP).toString();
                    } else {
                        // 开始时间和结束时间 之间相隔几个月 的月费 + 月费*商 + 按天数计费*余数 作为 收费依据
                        return decimal
                                .add(dailyRate.multiply(BigDecimal.valueOf(i)))
                                .setScale(2, RoundingMode.HALF_UP).toString();
                    }
                }
            }
        }
    }

    /**
     * 计算开始时间和结束时间的年份不为同一年的月保费用
     *
     * @param dailyRate 根据车辆类型编码获取日费率
     * @param free      月保费用
     * @param startTime 月保开始时间
     * @param endTime   月保结束时间
     * @return 月保费用
     */
    private static String noCurrentYear(BigDecimal dailyRate, String free, LocalDateTime startTime, LocalDateTime endTime) {
        // 获取开始时间的月份、日期、以及当前月份的天数
        int start_year = DateTimeUtil.getYear(startTime);
        int start_month = DateTimeUtil.getMonth(startTime);
        int start_day = DateTimeUtil.getDay(startTime);
        int start_daysInMonth = DateTimeUtil.getDaysInMonth(startTime);

        // 获取结束时间的月份、日期、以及当前月份的天数
        int end_year = DateTimeUtil.getYear(endTime);
        int end_month = DateTimeUtil.getMonth(endTime);

        // 计算出年份差 * 12 得到需要缴纳的月保月份数量
        int monthAll = (end_year - start_year - 1) * 12;
        // 计算年费
        BigDecimal multiply = BigDecimal.valueOf(monthAll).multiply(new BigDecimal(free));

        // 计算开始时间距离年尾还有几个月
        int month = 12 - start_month;
        // 判断开始时间的日期是否是该月的第一天
        if (1 == start_day) {
            // 年费 + 月费*（12-开始时间的月份 + 1 +结束时间的月份） 作为收费依据
            return multiply
                    .add(
                            BigDecimal.valueOf(month + 1 + end_month).multiply(new BigDecimal(free))
                    )
                    .setScale(2, RoundingMode.HALF_UP).toString();
        } else {
            // 年费 + 月费*（12-开始时间的月份 +结束时间的月份） + 开始时间的日期按照天数计费 作为收费依据
            return multiply
                    .add(
                            BigDecimal.valueOf(month + end_month).multiply(new BigDecimal(free))
                    )
                    .add(
                            BigDecimal.valueOf(start_daysInMonth - start_day + 1).multiply(dailyRate)
                    )
                    .setScale(2, RoundingMode.HALF_UP).toString();
        }
    }
}
