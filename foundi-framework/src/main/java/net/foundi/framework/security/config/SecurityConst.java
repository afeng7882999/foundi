/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.config;

/**
 * Spring Security 常量
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class SecurityConst {

    // Request Header
    public static final String REQUEST_AUTHC_HEADER = "Authorization";

    // 令牌前缀
    public static final String REQUEST_TOKEN_PREFIX = "Bearer ";

    // 令牌中用户标识前缀
    public static final String USER_KEY_OF_CLAIM = "account";

    // 登录用户 redis key
    public static final String TOKEN_KEY_PREFIX_REDIS = "fd:tk:";

    // 令牌刷新加锁 redis key
    public static final String TOKEN_LOCK_PREFIX_REDIS = "fd:tk_lock:";

    // 登录重试次数 redis key
    public static final String LOGIN_TRY_TIMES_PREFIX_REDIS = "fd:login_retry:";

    // 令牌刷新加锁时间
    public static final Long TOKEN_LOCK_TIME = 90L * 1000;

    // 令牌刷新后，旧令牌过期时间
    public static final Long OLD_TOKEN_EXPIRATION = 60L * 1000;

    // 所有权限标识
    public static final String ALL_PERMISSION = "*:*:*";

    // 超级管理员角色权限标识
    public static final String SUPER_ADMIN = "super_admin";

    // 超级管理员用户ID
    public static final Long SUPER_ADMIN_ID = 1L;

    // 验证码 redis key
    public static final String CAPTCHA_PREFIX_REDIS = "fd:captcha:";

    // 验证码验证结果 redis key
    public static final String CAPTCHA_VERIFIED_PREFIX_REDIS = "fd:captcha_valid:";

}
