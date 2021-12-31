/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.sms.config;

import java.util.HashMap;
import java.util.Map;

/**
 * SMS发送服务参数
 *
 *     huawei:
 *       url: https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1
 *       appKey: XXXXXXXX
 *       appSecret: XXXXXXXX
 *       scenes:
 *         validCode: {sender: csms12345678, templateId: 8ff55eac1d0b478ab3c06c3c6a492300, signature: 华为云短信测试}
 *
 *     aliyun:
 *       accessKeyId: XXXXXXXX
 *       accessKeySecret: XXXXXXXX
 *       scenes:
 *         validCode: {signName: Foundi, templateCode: SMS_170115099}
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class SmsProperties {

    // 服务提供商
    private SmsServiceProvider provider;

    // SMS发送服务参数
    private Map<String, String> properties = new HashMap<>();

    // SMS使用场景，模板名称：模板参数
    private Map<String, Map<String, String>> scenes = new HashMap<>();

    public static SmsProperties withProvider(SmsServiceProvider provider) {
        SmsProperties result = new SmsProperties();
        result.setProvider(provider);
        return result;
    }

    public SmsProperties add(String key, String value) {
        this.properties.put(key, value);
        return this;
    }

    public SmsProperties addScene(String scene, String key, String value) {
        this.scenes.computeIfAbsent(scene, k -> new HashMap<>());
        this.scenes.get(scene).put(key, value);
        return this;
    }

    public String get(String key) {
        return this.properties.get(key);
    }

    public Map<String, String> getScene(String scene) {
        return this.scenes.get(scene);
    }

    public SmsServiceProvider getProvider() {
        return provider;
    }

    public void setProvider(SmsServiceProvider provider) {
        this.provider = provider;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, Map<String, String>> getScenes() {
        return scenes;
    }

    public void setScenes(Map<String, Map<String, String>> scenes) {
        this.scenes = scenes;
    }
}
