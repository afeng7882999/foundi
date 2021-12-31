/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.config;

import net.foundi.common.utils.lang.ByteUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * spring security 参数配置
 *
 * @author Afeng (afeng7882999@163.com)
*/
@ConfigurationProperties(prefix = "foundi.security")
public class SecurityProperties {

    // 令牌密钥
    private String tokenSecret;

    // 令牌有效期，单位小时
    private Integer tokenExpiration = 2;

    // 令牌刷新窗口，单位小时
    private Integer tokenRefreshPeriod = 1;

    // 登录重试次数限制，超过显示验证码
    private Integer loginRetryLimit = 3;

    // 登录重试限制时长，秒
    private Integer loginRetryLimitTime = 2 * 60 * 60;

    // 验证码有效期，单位秒
    private Integer captchaExpiration = 2 * 60;

    // 加密RSA私钥
    private String rsaPrivateKey = "";

    // 加密RSA公钥
    private String rsaPublicKey = "";

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        if (ByteUtils.getBytes(tokenSecret).length < 64) {
            throw new IllegalArgumentException("令牌签名密钥长度最少为64字节");
        }
        this.tokenSecret = tokenSecret;
    }

    public Integer getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Integer tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public Integer getTokenRefreshPeriod() {
        return tokenRefreshPeriod;
    }

    public void setTokenRefreshPeriod(Integer tokenRefreshPeriod) {
        this.tokenRefreshPeriod = tokenRefreshPeriod;
    }

    public Integer getLoginRetryLimit() {
        return loginRetryLimit;
    }

    public void setLoginRetryLimit(Integer loginRetryLimit) {
        this.loginRetryLimit = loginRetryLimit;
    }

    public Integer getLoginRetryLimitTime() {
        return loginRetryLimitTime;
    }

    public void setLoginRetryLimitTime(Integer loginRetryLimitTime) {
        this.loginRetryLimitTime = loginRetryLimitTime;
    }

    public Integer getCaptchaExpiration() {
        return captchaExpiration;
    }

    public void setCaptchaExpiration(Integer captchaExpiration) {
        this.captchaExpiration = captchaExpiration;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }
}
