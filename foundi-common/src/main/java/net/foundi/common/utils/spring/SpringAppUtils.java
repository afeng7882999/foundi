/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.spring;

import net.foundi.common.utils.lang.AssertUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContext 工具集
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class SpringAppUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringAppUtils.applicationContext = applicationContext;
    }

    /**
     * 通过名称获取Bean
     *
     * @param name Bean的名称
     * @return Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * 通过类型获取Bean
     *
     * @param requiredType Bean的类
     * @return Bean
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return getApplicationContext().getBean(requiredType);
    }

    /**
     * 通过名称和类型获取Bean
     *
     * @param name         Bean的名称
     * @param requiredType Bean的类
     * @return Bean
     */
    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getApplicationContext().getBean(name, requiredType);
    }

    public static ApplicationContext getApplicationContext() {
        AssertUtils.getNull(applicationContext)
                .ex(() -> new IllegalStateException("无法获取ApplicationContext"));
        return applicationContext;
    }
}
