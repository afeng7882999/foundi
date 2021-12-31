/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.lang;


import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.TemporalAccessorUtil;
import net.foundi.common.constant.FoundiConst;

import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class DateUtils extends LocalDateTimeUtil {

    // 默认DateTimeFormatter，使用预设格式
    public static DateTimeFormatter defaultDateTimeFormatter = DateTimeFormatter.ofPattern(FoundiConst.DEFAULT_DATETIME_FORMAT);
    public static DateTimeFormatter defaultDateFormatter = DateTimeFormatter.ofPattern(FoundiConst.DEFAULT_DATE_FORMAT);
    public static DateTimeFormatter defaultTimeFormatter = DateTimeFormatter.ofPattern(FoundiConst.DEFAULT_TIME_FORMAT);

    /**
     * 获取当前时间戳
     *
     * @return long，时间戳
     */
    public static long currentMilli() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 获取当前日期时间
     * 使用预设时区，预设格式
     *
     * @return 日期时间字符串
     */
    public static String currentDateTimeStr() {
        LocalDateTime ldt = LocalDateTime.now();
        return dateTimeStr(ldt);
    }

    /**
     * LocalDateTime对象格式化输出日期时间
     * 使用预设格式
     *
     * @param date LocalDateTime对象
     * @return 日期时间字符串
     */
    public static String dateTimeStr(LocalDateTime date) {
        return date.format(defaultDateTimeFormatter);
    }

    /**
     * 日期时间字符串生成LocalDateTime对象
     * 使用预设格式
     *
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime对象
     */
    public static LocalDateTime dateTime(String dateTimeStr) {
        return of(defaultDateTimeFormatter.parse(dateTimeStr));
    }

    /**
     * 获取当前日期字符串
     * 使用预设时区，预设格式
     *
     * @return 日期字符串
     */
    public static String currentDateStr() {
        LocalDate ld = LocalDate.now();
        return dateStr(ld);
    }

    /**
     * Date对象格式化输出日期
     * 使用预设格式
     *
     * @param date LocalDate对象
     * @return 日期字符串
     */
    public static String dateStr(LocalDate date) {
        return date.format(defaultDateFormatter);
    }

    /**
     * 日期字符串生成LocalDateTime对象
     *
     * @param dateStr 日期字符串
     * @return LocalDate对象
     */
    public static LocalDate date(String dateStr) {
        return ofDate(defaultDateFormatter.parse(dateStr));
    }

    /**
     * 获取当前时间字符串
     * 使用预设时区，预设格式
     *
     * @return 时间字符串
     */
    public static String currentTimeStr() {
        LocalTime lt = LocalTime.now();
        return timeStr(lt);
    }

    /**
     * Date对象格式化输出时间
     * 使用预设格式
     *
     * @param time LocalTime对象
     * @return 时间字符串
     */
    public static String timeStr(LocalTime time) {
        return time.format(defaultTimeFormatter);
    }

    /**
     * TemporalAccessor转LocalTime
     *
     * @param temporalAccessor TemporalAccessor对象
     * @return LocalTime对象
     */
    public static LocalTime ofTime(TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        }

        if (temporalAccessor instanceof LocalDateTime) {
            return ((LocalDateTime) temporalAccessor).toLocalTime();
        }

        return LocalTime.of(
                TemporalAccessorUtil.get(temporalAccessor, ChronoField.HOUR_OF_DAY),
                TemporalAccessorUtil.get(temporalAccessor, ChronoField.MINUTE_OF_HOUR),
                TemporalAccessorUtil.get(temporalAccessor, ChronoField.SECOND_OF_MINUTE),
                TemporalAccessorUtil.get(temporalAccessor, ChronoField.NANO_OF_SECOND)
        );
    }

    /**
     * 时间字符串生成LocalTime对象
     * 使用预设格式
     *
     * @param timeStr 时间字符串
     * @return LocalTime对象
     */
    public static LocalTime time(String timeStr) {
        return ofTime(defaultTimeFormatter.parse(timeStr));
    }

    /**
     * 日期、时间字符串生成LocalDateTime对象
     * 支持 yyyy-MM-dd HH:mm:ss、yyyy-MM-dd、HH:mm:ss、时间戳
     *
     * @param dateOrTimeStr 日期、时间字符串
     * @return LocalDateTime对象
     */
    public static LocalDateTime dateOrTime(String dateOrTimeStr) throws ParseException {
        if (StringUtils.isEmpty(dateOrTimeStr)) {
            return null;
        }

        dateOrTimeStr = dateOrTimeStr.trim();
        boolean dash = dateOrTimeStr.contains("-");
        boolean colon = dateOrTimeStr.contains(":");

        //时间戳，使用预设时区
        if (dateOrTimeStr.matches(FoundiConst.DEFAULT_TIMESTAMP_FORMAT)) {
            return of(new Long(dateOrTimeStr));
        }

        //yyyy-MM-dd HH:mm:ss 格式
        if (dash && colon) {
            return dateTime(dateOrTimeStr);
        }

        //yyyy-MM-dd 格式，时间选择 00:00
        if (dash) {
            return date(dateOrTimeStr).atStartOfDay();
        }

        //HH:mm:ss 格式，使用预设时区当前日期
        if (colon) {
            return time(dateOrTimeStr).atDate(LocalDate.now());
        }

        throw new ParseException(StringUtils.format("无法将'{}'解析为LocalDateTime类型", dateOrTimeStr), 0);
    }

    /**
     * 使用预设时区，将LocalDateTime转换为Date
     *
     * @param ldt LocalDateTime
     * @return Date
     */
    public static Date localToDate(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 毫秒数转换为字符串，结果格式："xx天xx小时xx分钟"
     *
     * @param millis 毫秒
     * @return 字符串
     */
    public static String millisFormatStr(long millis) {
        return secondFormatStr(millis / 1000L);
    }

    /**
     * 秒数转换为字符串，结果格式："xx天xx小时xx分钟"
     *
     * @param second 秒
     * @return 字符串
     */
    public static String secondFormatStr(long second) {
        int dd = (int) second / (60 * 60 * 24);
        int hh = (int) (second - dd * 60 * 60 * 24) / (60 * 60);
        int mm = (int) (second - dd * 60 * 60 * 24 - hh * 60 * 60) / (60);
        StringBuilder sb = new StringBuilder();
        if (dd != 0) {
            sb.append(dd).append("天");
        }
        if (hh != 0) {
            sb.append(hh).append("小时");
        }
        if (mm != 0 && dd != 0 && hh == 0) {
            sb.append("0小时").append(mm).append("分钟");
            return sb.toString();
        }
        if (mm == 0 && dd == 0 && hh == 0) {
            return "0分钟";
        }
        if (mm != 0) {
            sb.append(mm).append("分钟");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LocalDate ldt = date("2021-12-23");
        System.out.println(ldt);
    }

}
