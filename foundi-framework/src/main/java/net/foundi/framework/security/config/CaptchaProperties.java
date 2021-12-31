/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码配置属性
 *
 * @author Afeng (afeng7882999@163.com)
 */
@ConfigurationProperties(prefix = "foundi.web.captcha")
public class CaptchaProperties {

    // 验证码类型
    private CaptchaType type = CaptchaType.MATH;

    // 验证码过期时间，秒
    private long expiration = 120L;

    public CaptchaType getType() {
        return type;
    }

    public void setType(CaptchaType type) {
        this.type = type;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public enum CaptchaType {
        CHAR, MATH
    }
}


