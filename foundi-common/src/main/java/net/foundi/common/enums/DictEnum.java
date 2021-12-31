/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface DictEnum {

    String key();

    String val();

    default Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>();
        result.put("key", key());
        result.put("val", val());
        return result;
    }

}
