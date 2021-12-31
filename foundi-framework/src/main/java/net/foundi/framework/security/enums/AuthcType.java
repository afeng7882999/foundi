/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.enums;

import net.foundi.common.enums.DictEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户登录方式字典枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum AuthcType implements DictEnum {

    PASSWORD("0", "账号密码"),
    MOBILE("1", "手机验证码"),
    WEIXIN("2", "微信"),
    WEIBO("3", "微博"),
    QQ("4", "QQ");

    private String key;
    private String val;

    AuthcType(String key, String val) {
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
        return Stream.of(AuthcType.values()).map(AuthcType::toMap).collect(Collectors.toList());
    }

    public static AuthcType byKey(String key) {
        return Stream.of(AuthcType.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static AuthcType byVal(String val) {
        return Stream.of(AuthcType.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }
}
