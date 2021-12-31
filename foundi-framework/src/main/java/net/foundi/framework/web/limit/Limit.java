/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.limit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * API访问限制
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Limit {

    // redis锁的前缀
    String lockPrefix() default "fd:lm:";

    // redis锁的spEL表达式
    String lockKey() default "";

    // 检查同一用户，同一IP或者不检测
    CheckTarget checkFor() default CheckTarget.NONE;

    // 访问限制次数
    int maxRate() default 10;

    // 限制访问的时间范围，秒，默认一分钟
    long duration() default 60L;

    // 触发限制条件后，禁止访问的时间，秒，默认一天
    long forbiddenTime() default 60 * 60 * 24L;

    enum CheckTarget {
        USER, IP, NONE
    }

}