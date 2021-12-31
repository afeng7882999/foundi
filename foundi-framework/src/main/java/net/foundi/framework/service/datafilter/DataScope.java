/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.service.datafilter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.foundi.common.enums.DictEnum;

/**
 * 数据权限范围
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum DataScope implements DictEnum {

    ALL("0", "全部数据权限"),
    CUSTOM("1", "自定数据权限"),
    GROUP("2", "本级数据权限"),
    GROUP_AND_CHILDREN("3", "本级及下级数据权限"),
    SELF("4", "仅本人数据权限");

    private String key;
    private String val;

    DataScope(String key, String val) {
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
        return Stream.of(DataScope.values()).map(DataScope::toMap).collect(Collectors.toList());
    }

    public static DataScope byKey(String key) {
        return Stream.of(DataScope.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static DataScope byVal(String val) {
        return Stream.of(DataScope.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }

}
