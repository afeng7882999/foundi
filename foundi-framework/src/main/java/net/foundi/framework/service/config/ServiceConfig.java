/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.service.config;

import net.foundi.framework.service.AccountService;
import net.foundi.common.exception.NoImplException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 业务配置
 *
 * @author Afeng (afeng7882999@163.com)
*/
@Configuration
public class ServiceConfig {

    /**
     * 没有实现用户服务接口，抛出NoImplException
     */
    @Bean
    @ConditionalOnMissingBean
    public AccountService accountService() {
        throw new NoImplException(AccountService.class.getName());
    }

}
