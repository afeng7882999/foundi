/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.foundi.common.exception.NoImplException;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.support.upload.service.UploadServiceFactory;

/**
 * 业务配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
public class UploadConfig {

    private static final Logger logger = LoggerFactory.getLogger(UploadConfig.class);

    /**
     * 没有实现用户服务接口，抛出NoImplException
     */
    @Bean
    @ConditionalOnMissingBean
    public UploadPropertyService uploadPropertyService() {
        throw new NoImplException(UploadPropertyService.class.getName());
    }

    /**
     * UploadServiceFactory
     *
     * @param uploadPropertyService 注入UploadPropertyService
     * @return UploadServiceFactory 实例
     */
    @Bean
    public UploadServiceFactory uploadServiceFactory(UploadPropertyService uploadPropertyService) {
        logger.info(StringUtils.withPrefix("启用UPLOAD服务"));
        return new UploadServiceFactory(uploadPropertyService);
    }

}
