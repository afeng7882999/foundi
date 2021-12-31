/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.enums;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 性别字典枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum Gender implements DictEnum {

    UNKNOWN("0","未知"),
    MALE("1","男"),
    FEMALE("2","女");

    private String key;
    private String val;

    Gender(String key, String val) {
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
        return Stream.of(Gender.values()).map(Gender::toMap).collect(Collectors.toList());
    }

    public static Gender byKey(String key) {
        return Stream.of(Gender.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static Gender byVal(String val) {
        return Stream.of(Gender.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }
}
