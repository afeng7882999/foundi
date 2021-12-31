/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.tools.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger2 属性
 *
 * @author Afeng (afeng7882999@163.com)
 */
@ConfigurationProperties(prefix = "foundi.swagger")
public class SwaggerProperties {

    // 是否开启swagger
    private boolean enabled = true;

    // 设置请求的统一前缀
    private String pathMapping = "/dev-api";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPathMapping() {
        return pathMapping;
    }

    public void setPathMapping(String pathMapping) {
        this.pathMapping = pathMapping;
    }
}
