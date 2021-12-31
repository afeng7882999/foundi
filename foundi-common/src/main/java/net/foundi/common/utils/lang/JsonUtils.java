/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.lang;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.spring.SpringAppUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Json工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class JsonUtils {

    private static ObjectMapper objectMapper;

    /**
     * 获取ObjectMapper的Bean
     *
     * @return ObjectMapper对象
     */
    public static ObjectMapper getObjectMapper() {
        if (JsonUtils.objectMapper == null) {
            ObjectMapper objectMapper = SpringAppUtils.getBean(ObjectMapper.class);
            AssertUtils.getNull(objectMapper).ex(() -> new IllegalStateException("无法获取ObjectMapper"));
            JsonUtils.objectMapper = objectMapper;
        }
        return JsonUtils.objectMapper;
    }

    /**
     * Json转化为Map
     *
     * @param json json
     * @return Map
     */
    public static Map<String, Object> toMap(String json) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, Object.class);
        return objectMapper.readValue(json, javaType);
    }

    /**
     * Json转化为对象
     *
     * @param json json
     * @param type pojo类
     * @param <T>  pojo类类型
     * @return pojo对象
     */
    public static <T> T toObject(String json, Class<T> type) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.readValue(json, type);
    }

    /**
     * 对象转化为Json
     *
     * @param object pojo类
     * @return Json
     */
    public static String fromObject(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = getObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Json转化为列表
     * 如："[{\"id\":\"1\",\"values\":[\"1\",\"2\"]},{\"id\":\"2\",\"values\":[\"11\",\"22\"]}]"
     *
     * @param simpleArrayJson json
     * @param clazz           列表元素的类
     * @return 列表
     */
    public static <T> List<T> toList(String simpleArrayJson, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        return objectMapper.readValue(simpleArrayJson, javaType);
    }

    /**
     * Json转化为Map<String, String>.
     * 如："{\"id\":\"1\",\"name\":\"Mike\",\"password\":\"1234ert\"}"
     *
     * @param simpleObjectJson json
     * @return Map<String, String>
     */
    public static Map<String, String> toStringMap(String simpleObjectJson) throws IOException {
        ObjectMapper objectMapper = getObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, String.class);
        return objectMapper.readValue(simpleObjectJson, javaType);
    }

}
