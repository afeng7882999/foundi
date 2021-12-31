/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.file;

import net.foundi.common.enums.DictEnum;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文件类型字典枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum FileType implements DictEnum {

    IMAGE("1", "图片"),
    DOCUMENT("2", "文档"),
    AUDIO("3", "音频"),
    VIDEO("4", "视频"),
    UNKNOWN("99", "未知");

    private String key;
    private String val;

    FileType(String key, String val) {
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
        return Stream.of(FileType.values()).map(FileType::toMap).collect(Collectors.toList());
    }

    public static FileType byKey(String key) {
        return Stream.of(FileType.values())
                .filter(i -> key.equals(i.key()))
                .findAny()
                .orElse(null);
    }

    public static FileType byVal(String val) {
        return Stream.of(FileType.values())
                .filter(i -> val.equals(i.val()))
                .findAny()
                .orElse(null);
    }

}
