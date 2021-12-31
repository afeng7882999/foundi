/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.enums;

import net.foundi.common.enums.DictEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 重置密码方式
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum ResetPasswordType implements DictEnum {

    EMAIL("email", "邮件"),
    MOBILE("mobile", "手机短信");


    private String key;
    private String val;

    ResetPasswordType(String key, String val) {
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
        return Stream.of(ResetPasswordType.values()).map(ResetPasswordType::toMap).collect(Collectors.toList());
    }

    public static ResetPasswordType byKey(String key) {
        return Stream.of(ResetPasswordType.values())
                .filter(i -> key.equalsIgnoreCase(i.key()))
                .findAny()
                .orElse(null);
    }

    public static ResetPasswordType byVal(String val) {
        return Stream.of(ResetPasswordType.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }
}
