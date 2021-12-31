/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.constant;

/**
 * foundi全局常量
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class FoundiConst {

    // 默认字符集
    public static final String DEFAULT_CHARSET = "UTF-8";

    // 默认日期时间格式
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String DEFAULT_TIMESTAMP_FORMAT = "^\\d+$";

    // 默认时区
    public static final String DEFAULT_TIMEZONE = "GMT+8";

    // 通用成功、失败标识
    public static final String SUCCESS = "0";
    public static final String FAIL = "1";

    // 日志前缀
    public static final String GLOBAL_LOG_PREFIX = "【FOUNDI】";

    // 默认文件上传URL相对路径, 结尾包含 '/'
    public static final String LOCAL_UPLOAD_URL = "/upload/";

    // KB、MB、GB
    public static long KB = 1024L;
    public static long MB = 1024L * 1024;
    public static long GB = 1024L * 1024 * 1024;

}
