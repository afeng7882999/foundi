/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.lang.DateUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Jackson2Json配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
public class JacksonConfig {

    /**
     * 定义全局 ObjectMapper Builder
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            builder.simpleDateFormat(FoundiConst.DEFAULT_DATETIME_FORMAT);

            // 指定LocalDateTime序列化格式
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateUtils.defaultDateTimeFormatter));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateUtils.defaultDateFormatter));
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateUtils.defaultTimeFormatter));
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateUtils.defaultDateTimeFormatter));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateUtils.defaultDateFormatter));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateUtils.defaultDateTimeFormatter));

            builder.modules(javaTimeModule);
        };
    }


    /**
     * 定义全局 ObjectMapper
     * MappingJackson2HttpMessageConverter、JsonUtils使用
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //simpleModule for Long to String
        SimpleModule simpleModuleForLong = new SimpleModule();
        simpleModuleForLong.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModuleForLong.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModuleForLong.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModuleForLong);

        return objectMapper;
    }

    /**
     * 定义Jackson2JsonRedisSerializer使用的ObjectMapper
     */
    @Bean("objectMapperForRedis")
    public ObjectMapper objectMapperForRedis(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 除final外的的属性都参与序列化，不校验，生成key-value的Json格式
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        return objectMapper;
    }
}
