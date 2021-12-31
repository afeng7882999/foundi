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
 * 用户状态枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum AccountStatus implements DictEnum {
    NORMAL("0", "正常"),
    LOCKED("1", "锁定");

    private String key;
    private String val;

    AccountStatus(String key, String val) {
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
        return Stream.of(AccountStatus.values()).map(AccountStatus::toMap).collect(Collectors.toList());
    }

    public static AccountStatus byKey(String key) {
        return Stream.of(AccountStatus.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static AccountStatus byVal(String val) {
        return Stream.of(AccountStatus.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }

}
