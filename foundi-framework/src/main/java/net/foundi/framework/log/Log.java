/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.log;

import java.lang.annotation.*;

/**
 * 操作日志记录注解
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    // 操作名称
    String value() default "";

    // 操作参数, spEL
    String param() default "";

    // 记录日志的条件
    LogAfter logAfter() default LogAfter.ANY;

    // 是否自动保存请求的参数
    boolean autoLogParam() default false;

    public enum LogAfter {
        ANY, NO_EXCEPTION, EXCEPTION
    }
}
