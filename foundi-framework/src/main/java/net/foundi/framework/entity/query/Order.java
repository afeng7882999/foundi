/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.query;

import java.lang.annotation.*;

/**
 * 查询排序构造器声明
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Orders.class)
public @interface Order {

    // 参与排序的DO字段名
    String field() default "";

    // 对应数据库列名
    String tableField() default "";

    // 默认排序
    Type sort() default Type.CUSTOM;

    enum Type {
        // 增序
        ASC,
        // 降序
        DESC,
        // 自定义
        CUSTOM,
    }
}
