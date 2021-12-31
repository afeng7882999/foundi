/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.oauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import net.foundi.common.utils.lang.StringUtils;
import net.foundi.support.oauth.qq.QqOAuthProperties;
import net.foundi.support.oauth.qq.QqOAuthService;
import net.foundi.support.oauth.weibo.WeiboOAuthProperties;
import net.foundi.support.oauth.weibo.WeiboOAuthService;
import net.foundi.support.oauth.weixin.WeixinOAuthProperties;
import net.foundi.support.oauth.weixin.WeixinOAuthService;

/**
 * OAuth配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
public class OAuthConfig {

    private static final Logger logger = LoggerFactory.getLogger(OAuthConfig.class);

    /**
     * QQ OAuth2
     */
    @Bean
    @ConditionalOnProperty(prefix = "foundi.oauth.qq", name = "appKey")
    public QqOAuthService qqOAuthService(QqOAuthProperties props, RestTemplate restTemplate,
                                        CacheManager cacheManager) {
        logger.info(StringUtils.withPrefix("启用QQ OAuth服务"));
        return new QqOAuthService(props, restTemplate, cacheManager.getCache("minute2"));
    }

    /**
     * WeiXin OAuth2
     */
    @Bean
    @ConditionalOnProperty(prefix = "foundi.oauth.weixin", name = "appSecret")
    public WeixinOAuthService weixinOAuthService(WeixinOAuthProperties props, RestTemplate restTemplate,
                                                CacheManager cacheManager) {
        logger.info(StringUtils.withPrefix("启用微信OAuth服务"));
        return new WeixinOAuthService(props, restTemplate, cacheManager.getCache("minute2"));
    }

    /**
     * Weibo OAuth2
     */
    @Bean
    @ConditionalOnProperty(prefix = "foundi.oauth.weibo", name = "appSecret")
    public WeiboOAuthService weiboOAuthService(WeiboOAuthProperties props, RestTemplate restTemplate,
                                              CacheManager cacheManager) {
        logger.info(StringUtils.withPrefix("启用微博OAuth服务"));
        return new WeiboOAuthService(props, restTemplate, cacheManager.getCache("minute2"));
    }
}
