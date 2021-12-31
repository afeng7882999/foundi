/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.sms.config;

import net.foundi.common.exception.NoImplException;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.support.sms.service.SmsServiceFactory;
import net.foundi.support.sms.service.SmsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SMS发送配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
public class SmsConfig {

    private static final Logger logger = LoggerFactory.getLogger(SmsConfig.class);

    /**
     * 没有实现用户服务接口，抛出NoImplException
     */
    @Bean
    @ConditionalOnMissingBean
    public SmsPropertyService smsPropertyService() {
        throw new NoImplException(SmsPropertyService.class.getName());
    }

    /**
     * SmsServiceFactory
     *
     * @param smsPropertyService 注入SmsPropertyService
     * @return SmsServiceFactory 实例
     */
    @Bean
    public SmsServiceFactory smsServiceFactory(SmsPropertyService smsPropertyService) {
        logger.info(StringUtils.withPrefix("启用SMS服务"));
        return new SmsServiceFactory(smsPropertyService);
    }

    /**
     * SmsValidator
     */
    @Bean
    SmsValidator smsValidator(SmsServiceFactory smsServiceFactory, CacheManager cacheManager) {
        //15minutes
        Cache cache = cacheManager.getCache("minute15");
        return new SmsValidator(smsServiceFactory, cache);
    }

}
