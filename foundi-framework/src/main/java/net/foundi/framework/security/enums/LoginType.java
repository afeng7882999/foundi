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
 * 登录、登出字典枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum LoginType implements DictEnum {

    LOGIN("0", "登录"),
    LOGOUT("1", "登出");

    private String key;
    private String val;

    LoginType(String key, String val) {
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
        return Stream.of(LoginType.values()).map(LoginType::toMap).collect(Collectors.toList());
    }

    public static LoginType byKey(String key) {
        return Stream.of(LoginType.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static LoginType byVal(String val) {
        return Stream.of(LoginType.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }

}
