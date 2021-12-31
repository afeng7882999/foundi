/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.query;

import java.lang.annotation.*;

/**
 * 查询条件构造器声明
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Criteria.class)
public @interface Criterion {

    // 对应数据库列名
    String tableField() default "";

    // 查询方式
    Type type() default Type.EQUAL;

    enum Type {
        // 相等
        EQUAL,
        // 大于等于
        GREATER_THAN,
        // 小于等于
        LESS_THAN,
        // 中模糊查询
        INNER_LIKE,
        // 左模糊查询
        LEFT_LIKE,
        // 右模糊查询
        RIGHT_LIKE,
        // 小于
        LESS_THAN_NQ,
        // 大于
        GREATER_THAN_NQ,
        // 包含
        IN,
        // 不等于
        NOT_EQUAL,
        // 范围
        BETWEEN,
        // 不为空
        NOT_NULL,
        // 时间范围
        TIMESTAMPS
    }

}

