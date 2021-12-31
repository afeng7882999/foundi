/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.resubmit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 重复提交声明
 *
 * @author Afeng (afeng7882999@163.com)
*/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Resubmit {

    // redis锁的前缀
    String lockPrefix() default "fd:rb:";

    // redis锁的spEL表达式
    String lockKey() default "";

    // 检查同一用户，或者同一IP
    CheckTarget checkFor() default CheckTarget.USER;

    // 触发重复提交的时间间隔，秒
    long interval() default 5L;

    enum CheckTarget {
        USER, IP
    }

}
