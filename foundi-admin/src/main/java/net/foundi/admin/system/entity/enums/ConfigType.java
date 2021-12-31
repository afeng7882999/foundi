/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.enums;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.foundi.common.enums.DictEnum;

/**
 * 系统配置类型字典枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum ConfigType implements DictEnum {

    SYS_GLOBAL("0", "系统配置"),

    OSS_ALIYUN("100", "OSS（阿里云）"),
    OSS_HUAWEI("101", "OSS（华为云）"),
    OSS_QINIU("102", "OSS（七牛云）"),
    OSS_LOCAL("103", "OSS（本地）"),

    SMS_ALIYUN("200", "SMS（阿里云）"),
    SMS_HUAWEI("201", "SMS（华为云）");


    private String key;
    private String val;

    ConfigType(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public String key() {
        return key;
    }

    public String val() {
        return val;
    }

    public static List<Map<String, String>> toList() {
        return Stream.of(ConfigType.values()).map(ConfigType::toMap).collect(Collectors.toList());
    }

    public static ConfigType byKey(String key) {
        return Stream.of(ConfigType.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static ConfigType byVal(String val) {
        return Stream.of(ConfigType.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }
}
