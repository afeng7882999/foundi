/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Web 配置
 *
 * @author Afeng (afeng7882999@163.com)
*/
@ConfigurationProperties(prefix = "foundi.web")
public class WebProperties {

    // 下载临时路径
    private String downloadTmpDir = "D:/downloadTmp/";

    // 获取地址开关
    private Boolean addressEnabled = true;

    // XSS配置
    private Xss xss = new Xss();

    // Api加密配置
    private Encrypt encrypt = new Encrypt();

    public static class Xss {

        // XSS是否开启
        private Boolean enabled = true;

        // XSS包含URL
        private String urlPatterns = "";

        // XSS排除URL
        private String excludes = "";

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getExcludes() {
            return excludes;
        }

        public void setExcludes(String excludes) {
            this.excludes = excludes;
        }

        public String getUrlPatterns() {
            return urlPatterns;
        }

        public void setUrlPatterns(String urlPatterns) {
            this.urlPatterns = urlPatterns;
        }
    }

    public static class Encrypt {

        // Api加密是否开启
        private Boolean enabled = true;

        // Api加密包含URL
        private String urls = "";

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getUrls() {
            return urls;
        }

        public void setUrls(String urls) {
            this.urls = urls;
        }
    }

    public String getDownloadTmpDir() {
        return downloadTmpDir;
    }

    public void setDownloadTmpDir(String downloadTmpDir) {
        this.downloadTmpDir = downloadTmpDir;
    }

    public Boolean getAddressEnabled() {
        return addressEnabled;
    }

    public void setAddressEnabled(Boolean addressEnabled) {
        this.addressEnabled = addressEnabled;
    }

    public Xss getXss() {
        return xss;
    }

    public void setXss(Xss xss) {
        this.xss = xss;
    }

    public Encrypt getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Encrypt encrypt) {
        this.encrypt = encrypt;
    }
}
