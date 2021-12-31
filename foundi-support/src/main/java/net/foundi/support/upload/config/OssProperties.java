/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.config;

import java.util.HashMap;
import java.util.Map;

/**
 * OSS参数
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class OssProperties {

    // OSS服务提供商
    private OssProvider provider;

    // OSS服务参数
    private Map<String, String> properties = new HashMap<>();

    public static OssProperties withProvider(OssProvider ossProvider) {
        OssProperties result = new OssProperties();
        result.setProvider(ossProvider);
        return result;
    }

    public OssProperties add(String key, String value) {
        this.properties.put(key, value);
        return this;
    }

    public String get(String key) {
        return this.properties.get(key);
    }

    public OssProvider getProvider() {
        return provider;
    }

    public void setProvider(OssProvider provider) {
        this.provider = provider;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
