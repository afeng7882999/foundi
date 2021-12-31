/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.cache.config;

/**
 * 缓存常量
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class CacheConst {

    // 缓存key前缀（redis）
    public static final String CACHE_KEY_PREFIX = "fd:cache";

    // 默认缓存时限
    public static final Long CACHE_KEY_EXPIRATION = 60L * 60 * 24;
}