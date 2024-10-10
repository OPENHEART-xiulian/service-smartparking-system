package com.sp.cjgc.backend.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: Zoey
 * @Since: 2024-08-15 09:48:30
 * @Description:
 */
public class DateTimeUtil {

    // 时间格式化
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalDateTime getNowTime() {
        return LocalDateTime.now();
    }

    /**
     * 将时间字符串转为LocalDateTime
     *
     * @param time
     * @return
     */
    public static LocalDateTime getTime(String time) {
        return LocalDateTime.parse(time, formatter);
    }

    /**
     * 将当前时间格式化 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTimeStr() {
        return getNowTime().format(formatter);
    }

    /**
     * 将传入的时间格式化 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowTimeStr(LocalDateTime time) {
        return time.format(formatter);
    }

    /**
     * 获取当前年份
     *
     * @param dateTime
     * @return
     */
    public static int getYear(LocalDateTime dateTime) {
        return dateTime.getYear();
    }

    /**
     * 获取当前月份
     *
     * @param dateTime
     * @return
     */
    public static int getMonth(LocalDateTime dateTime) {
        return dateTime.getMonthValue();
    }

    /**
     * 获取当前日期
     *
     * @param dateTime
     * @return
     */
    public static int getDay(LocalDateTime dateTime) {
        return dateTime.getDayOfMonth();
    }

    /**
     * 获取当前月份的天数
     *
     * @param dateTime
     * @return
     */
    public static int getDaysInMonth(LocalDateTime dateTime) {
        // 转换为 LocalDate
        LocalDate date = dateTime.toLocalDate();
        // 获取当前月份的天数
        return date.lengthOfMonth();
    }

    /**
     * 时间转换,手动补充日期和时间
     *
     * @param time   时间格式：YYYY-MM
     * @param status 开始时间和结束时间标识 1｜开始时间；2｜结束时间
     * @return
     */
    public static LocalDateTime timeUtils(String time, Integer status) {
        // 提取 time总的 "yyyy-MM"
        time = time.substring(0, 7);
        // 获取系统当前时间
        String nowTimeStr = getNowTimeStr();
        // 提取日期部分 提取 nowTimeStr中的 "-dd"
        String datePart = nowTimeStr.substring(7, 10);
        // 拼接字符串
        String finalTime = time + datePart + " " + nowTimeStr.substring(11);
        // 时间格式化
        LocalDateTime parse = LocalDateTime.parse(finalTime, formatter);
        // 判断是开始时间还是结束时间，开始时间 是补充当前日期，结束时间是补充最后一天的日期，时间都为系统当前时间
        if (status == 1) return parse;
        // 获取当前月份的天数
        int daysInMonth = getDaysInMonth(parse);
        // 拼接字符串
        String finalTimeEnd = time + "-" + daysInMonth + " " + nowTimeStr.substring(11);
        // 时间格式化
        return LocalDateTime.parse(finalTimeEnd, formatter);
    }
}
