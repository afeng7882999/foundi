/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.monitor.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 数据源监控配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
@Data
public class DataSourceProperties {

    @Value("${server.port}")
    private Integer serverPort;

    @Value("${spring.datasource.druid.statViewServlet.login-username}")
    private String druidLoginUsername;

    @Value("${spring.datasource.druid.statViewServlet.login-password}")
    private String druidLoginPassword;
}
