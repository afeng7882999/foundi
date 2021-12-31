/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * App 配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@ConfigurationProperties(prefix = "foundi.app")
public class AppProperties {

    // 项目名称
    private String name = "Foundi";

    // 版本
    private String version = "3.0.0";

    // 版权年份
    private String copyrightYear = "2020";

    // 作者
    private String author = "Afeng";

    // email
    private String email = "afeng7882999@163.com";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCopyrightYear() {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear) {
        this.copyrightYear = copyrightYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
