/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.spring;

import net.foundi.common.utils.lang.ConvertUtils;
import net.foundi.common.utils.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * WebApplicationContext工具类，只能用于Spring Web环境
 */
public class SpringWebUtils {

    private static final String SPRING_WEB_EX =
            StringUtils.withPrefix("WebApplicationContext只能用于Spring Web环境");

    /**
     * 由RequestContextHolder获取WebApplicationContext
     */
    public static WebApplicationContext getWebApplicationContext() {

        return Optional.ofNullable(getRequestFromContext().getSession().getServletContext())
                .map(WebApplicationContextUtils::getRequiredWebApplicationContext)
                .orElseThrow(() ->
                        new IllegalStateException(SPRING_WEB_EX));

    }

    /**
     * 获取request
     *
     * @return HttpServletRequest对象
     */
    public static HttpServletRequest getRequestFromContext() {

        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(a -> ((ServletRequestAttributes) a).getRequest())
                .orElseThrow(() ->
                        new IllegalStateException(SPRING_WEB_EX));

    }

    /**
     * 获取response
     *
     * @return HttpServletResponse对象
     */
    public static HttpServletResponse getResponseFromContext() {

        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(a -> ((ServletRequestAttributes) a).getResponse())
                .orElseThrow(() ->
                        new IllegalStateException(SPRING_WEB_EX));

    }

    /**
     * 通过名称获取Bean
     *
     * @param name Bean的名称
     * @return Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) getWebApplicationContext().getBean(name);
    }

    /**
     * 通过类型获取Bean
     *
     * @param requiredType Bean的类
     * @return Bean
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return getWebApplicationContext().getBean(requiredType);
    }

    /**
     * 通过名称和类型获取Bean
     *
     * @param name         Bean的名称
     * @param requiredType Bean的类
     * @return Bean
     */
    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getWebApplicationContext().getBean(name, requiredType);
    }

    /**
     * 判断Bean是否存在
     *
     * @param name Bean的名称
     * @return true：Bean存在
     */
    public static boolean containsBean(String name) {
        return getWebApplicationContext().containsBean(name);
    }

    /**
     * 获取Spring程序的属性值
     *
     * @param key 属性名称
     * @return 属性值
     */
    public static String getProperty(String key) throws IllegalStateException {
        return getWebApplicationContext().getEnvironment().getProperty(key);
    }

    /**
     * 获取String参数
     *
     * @param name 参数名称
     * @return 参数
     */
    public static String getParameter(String name) {
        return getRequestFromContext().getParameter(name);
    }

    /**
     * 获取String参数
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 参数
     */
    public static String getParameter(String name, String defaultValue) {
        return ConvertUtils.toStr(getRequestFromContext().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     *
     * @param name 参数名称
     * @return 参数
     */
    public static Integer getParameterToInt(String name) {
        return ConvertUtils.toInt(getRequestFromContext().getParameter(name));
    }

    /**
     * 获取Integer参数
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return ConvertUtils.toInt(getRequestFromContext().getParameter(name), defaultValue);
    }

    /**
     * 获取session
     *
     * @return HttpSession对象
     */
    public static HttpSession getSession() {
        return getRequestFromContext().getSession();
    }

}
