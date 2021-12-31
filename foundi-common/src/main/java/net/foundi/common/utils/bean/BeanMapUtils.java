/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Bean与Map转换工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
@SuppressWarnings("rawtypes")
public class BeanMapUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat(FoundiConst.DEFAULT_DATETIME_FORMAT));
    }

    /**
     * Bean转换为Map
     *
     * @param bean 需转换的Bean
     * @return Map 转换为LinkedHashMap
     */
    public static <T> Map beanToMap(T bean) throws IllegalArgumentException {
        return objectMapper.convertValue(bean, LinkedHashMap.class);
    }

    /**
     * Map转换为Bean
     *
     * @param map   需转换的Map
     * @param clazz 目标Bean的类型
     * @return T 转换的Bean
     */
    public static <T> T mapToBean(Map map, Class<T> clazz) throws IllegalArgumentException {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * 将Request中的参数Map转换为Bean
     *
     * @param req   HttpServletRequest
     * @param clazz 目标Bean的类型
     * @return T 转换的Bean
     */
    public static <T> T requestToBean(HttpServletRequest req, Class<T> clazz) throws IllegalArgumentException {
        Map<String, String> singleValueMap = new LinkedHashMap<>();
        Map<String, String[]> paramMap = req.getParameterMap();
        //only get the first value of each key
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            if (entry.getValue().length > 0 && StringUtils.hasValue(entry.getValue()[0])) {
                singleValueMap.put(entry.getKey(), entry.getValue()[0]);
            }
        }
        return objectMapper.convertValue(singleValueMap, clazz);
    }

}
