/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.base;

import java.util.concurrent.Callable;

/**
 * 异常工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class ExceptionUtils {

    /**
     * 将检查异常转换为非检查异常
     *
     * @param e Exception
     * @return RuntimeException
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }

    /**
     * 将检查异常转换为非检查异常
     *
     * @param e Exception
     * @return RuntimeException
     */
    public static RuntimeException unchecked(String msg, Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(msg, e);
        }
    }

    /**
     * 将方法抛出的检查异常作为非检查异常抛出
     *
     * @param callable 方法
     * @param <T>      方法的返回类型
     * @return 方法的返回值
     */
    public static <T> T uncheckCall(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw unchecked(e);
        }
    }

}
