/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.dao;

import org.apache.commons.lang3.StringUtils;

/**
 * SQL工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class SQLUtils {

    // SQL仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
    public static String SQL_PATTERN = "[a-zA-Z0-9_ ,]+";

    /**
     * 去除非法 order by 语句
     *
     * @param value SQL语句
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     *
     * @param value SQL语句
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }
}
