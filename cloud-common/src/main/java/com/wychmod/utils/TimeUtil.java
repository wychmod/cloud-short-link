package com.wychmod.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 时间工具类，提供日期转换、格式化等静态方法
 */
public class TimeUtil {
    /**
     * 默认日期格式
     */
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认日期格式化器
     */
    private static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER  = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    /**
     * 默认时区
     */
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    /**
     * 将LocalDateTime转换为字符串，指定日期格式
     * @param localDateTime 输入的LocalDateTime对象
     * @param pattern 日期格式字符串
     * @return 格式化后的日期字符串
     */
    public static String format(LocalDateTime localDateTime, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String timeStr = formatter.format(localDateTime.atZone(DEFAULT_ZONE_ID));
        return timeStr;
    }

    /**
     * 将Date转换为字符串，指定日期格式
     * @param time 输入的Date对象
     * @param pattern 日期格式字符串
     * @return 格式化后的日期字符串
     */
    public static String format(Date time, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String timeStr = formatter.format(time.toInstant().atZone(DEFAULT_ZONE_ID));
        return timeStr;
    }

    /**
     * 将Date转换为字符串，默认日期格式
     * @param time 输入的Date对象
     * @return 格式化后的日期字符串
     */
    public static String format(Date time){
        String timeStr = DEFAULT_DATE_TIME_FORMATTER.format(time.toInstant().atZone(DEFAULT_ZONE_ID));
        return timeStr;
    }

    /**
     * 将时间戳转换为字符串，默认日期格式
     * @param timestamp 输入的时间戳
     * @return 格式化后的日期字符串
     */
    public static String format(long timestamp) {
        String timeStr = DEFAULT_DATE_TIME_FORMATTER.format(new Date(timestamp).toInstant().atZone(DEFAULT_ZONE_ID));
        return timeStr;
    }

    /**
     * 将日期字符串转换为Date对象
     * @param time 输入的日期字符串
     * @return 转换后的Date对象
     */
    public static Date strToDate(String time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time, DEFAULT_DATE_TIME_FORMATTER);
        return Date.from(localDateTime.atZone(DEFAULT_ZONE_ID).toInstant());
    }

    /**
     * 获取当天剩余的秒数，用于流量包过期配置
     * @param currentDate 当前日期
     * @return 当天剩余的秒数
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                        ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);

        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }
}
