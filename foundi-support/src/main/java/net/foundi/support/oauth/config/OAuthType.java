/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.oauth.config;

import net.foundi.common.enums.DictEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户OAuth2登录方式
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum OAuthType implements DictEnum {

    WEIXIN("2", "微信"),
    WEIBO("3", "微博"),
    QQ("4", "QQ");

    private String key;
    private String val;

    OAuthType(String key, String val) {
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
        return Stream.of(OAuthType.values()).map(OAuthType::toMap).collect(Collectors.toList());
    }

    public static OAuthType byKey(String key) {
        return Stream.of(OAuthType.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static OAuthType byVal(String val) {
        return Stream.of(OAuthType.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }
}
