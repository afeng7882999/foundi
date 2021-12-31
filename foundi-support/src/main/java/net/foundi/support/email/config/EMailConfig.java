/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.email.config;

import net.foundi.common.utils.lang.StringUtils;
import net.foundi.support.email.EMailSender;
import net.foundi.support.email.EMailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Email配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@ComponentScan(basePackages = "net.foundi.support")
@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class EMailConfig {

    private static final Logger logger = LoggerFactory.getLogger(EMailConfig.class);

    private final MailProperties props;

    public EMailConfig (MailProperties props) {
        this.props = props;
    }

    /**
     * JavaMailSender
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(props.getHost());
        sender.setPort(props.getPort());
        sender.setUsername(props.getUsername());
        //the authorization code
        sender.setPassword(props.getPassword());
        sender.setProtocol(props.getProtocol());
        Properties more = new Properties();
        props.getProperties().keySet().forEach(k -> more.put(k, props.getProperties().get(k)));
        sender.setJavaMailProperties(more);
        return sender;
    }

    /**
     * EMailSender
     */
    @Bean
    public EMailSender eMailSender(JavaMailSender javaMailSender) {
        logger.info(StringUtils.withPrefix("启用EMail服务"));
        return new EMailSender(javaMailSender, props.getUsername());
    }

    /**
     * EMailValidator
     */
    @Bean
    public EMailValidator eMailValidator(EMailSender eMailSender, CacheManager cacheManager) {
        //15minutes
        Cache cache = cacheManager.getCache("minute15");
        return new EMailValidator(eMailSender, cache);
    }

}
