/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.dao.config;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * foundi
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
public class DruidConfig {

    /**
     * Register StatViewServlet
     */
//    @Bean
//    public ServletRegistrationBean statViewServletRegistrationBean() {
//        ServletRegistrationBean bean = new ServletRegistrationBean();
//        bean.setServlet(new StatViewServlet());
//        bean.addUrlMappings(props.getUrlPattern());
//        bean.addInitParameter("allow", props.getAllow());
//        bean.addInitParameter("deny", props.getDeny());
//        bean.addInitParameter("loginUsername", props.getLoginUsername());
//        bean.addInitParameter("loginPassword", props.getLoginPassword());
//        bean.addInitParameter("resetEnable", props.getResetEnable());
//
//        return bean;
//    }
}
