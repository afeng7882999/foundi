/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.lang;

import org.springframework.util.Assert;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 断言工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class AssertUtils extends Assert {


    /**
     * 判断是否为null
     *
     * @param value 判断的对象
     * @return AssertResult，对象为null则抛出异常
     */
    public static AssertResult getNull(Object value) {
        if (value == null)
            return new AssertResult(false);
        else
            return new AssertResult(true);
    }

    /**
     * 判断多个对象是否包含null
     *
     * @param values 判断的对象列表
     * @return AssertResult，对象包含null则抛出异常
     */
    public static AssertResult anyNull(Object... values) {
        for (Object value : values) {
            if (value == null)
                return new AssertResult(false);
        }
        return new AssertResult(true);
    }

    /**
     * 判断字符串是否为空
     *
     * @param value 判断的字符串
     * @return AssertResult，对象为空则抛出异常
     */
    public static AssertResult getEmpty(String value) {
        if (StringUtils.isEmpty(value))
            return new AssertResult(false);
        else
            return new AssertResult(true);
    }

    /**
     * 判断Map中是否有空值
     *
     * @param map  判断的Map
     * @param keys 特定键
     * @return AssertResult，包含空则抛出异常
     */
    @SafeVarargs
    public static <K, V> AssertResult mapAnyEmpty(Map<K, V> map, K... keys) {
        if (CollectionUtils.anyEmpty(map, keys))
            return new AssertResult(false);
        return new AssertResult(true);
    }

    /**
     * 判断多个对象是否包含空值
     *
     * @param values 判断的对象列表
     * @return AssertResult，对象包含空值则抛出异常
     */
    public static AssertResult paramAnyEmpty(Object... values) {
        for (Object value : values) {
            if (value == null || (value instanceof String && ((String) value).isEmpty()))
                return new AssertResult(false);
        }
        return new AssertResult(true);
    }

    /**
     * 断言结果类
     */
    public static class AssertResult {

        private boolean ifSuccess;

        AssertResult(boolean ifSuccess) {
            this.ifSuccess = ifSuccess;
        }

        /**
         * 直接返回null
         *
         * @return null
         */
        public Object nil() {
            return null;
        }

        /**
         * 抛出RuntimeException
         *
         * @param supplier 函数，返回RuntimeException子类
         */
        public <T extends RuntimeException> void ex(Supplier<T> supplier) {
            if (!this.ifSuccess)
                throw supplier.get();
        }

    }

}
