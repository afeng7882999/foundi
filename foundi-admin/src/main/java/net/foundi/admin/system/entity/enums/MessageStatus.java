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
 * 系统消息状态枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum MessageStatus implements DictEnum {
    UNREAD("0", "未读"),
    READ("1", "已读"),
    DELETE("2", "删除");

    private String key;
    private String val;

    MessageStatus(String key, String val) {
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
        return Stream.of(MessageStatus.values()).map(MessageStatus::toMap).collect(Collectors.toList());
    }

    public static MessageStatus byKey(String key) {
        return Stream.of(MessageStatus.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static MessageStatus byVal(String val) {
        return Stream.of(MessageStatus.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }

}

