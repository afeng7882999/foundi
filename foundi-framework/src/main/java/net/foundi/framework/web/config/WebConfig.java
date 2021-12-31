/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import net.foundi.common.constant.FoundiConst;
import net.foundi.framework.security.config.SecurityProperties;
import net.foundi.framework.web.encrypt.EncryptFilter;
import net.foundi.framework.web.xss.XssFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Web配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
@EnableConfigurationProperties({WebProperties.class, SecurityProperties.class})
public class WebConfig implements WebMvcConfigurer {

    private final WebProperties webProperties;
    private final SecurityProperties securityProperties;
    private final Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    public WebConfig(WebProperties webProperties, SecurityProperties securityProperties,
                     Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        this.webProperties = webProperties;
        this.securityProperties = securityProperties;
        this.jackson2ObjectMapperBuilder = jackson2ObjectMapperBuilder;

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = jackson2ObjectMapperBuilder.createXmlMapper(false).build();

        //simpleModule for Long to String
        SimpleModule simpleModuleForLong = new SimpleModule();
        simpleModuleForLong.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModuleForLong.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModuleForLong.addSerializer(Long.TYPE, ToStringSerializer.instance);

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.registerModule(simpleModuleForLong);

        converter.setObjectMapper(mapper);
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    /**
     * CORS filter
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * Api encryption filter
     */
    @Bean
    @SuppressWarnings({"unchecked", "rawtypes"})
    public FilterRegistrationBean encryptFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new EncryptFilter());
        // registration.addUrlPatterns("/**");
        registration.setName("encryptFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("urls", webProperties.getEncrypt().getUrls());
        initParameters.put("enabled", webProperties.getEncrypt().getEnabled().toString());
        initParameters.put("rsaPrivateKey", securityProperties.getRsaPrivateKey());
        registration.setInitParameters(initParameters);
        return registration;
    }

    /**
     * XSS filter
     */
    @Bean
    @SuppressWarnings({"unchecked", "rawtypes"})
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(webProperties.getXss().getUrlPatterns().split(","));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE + 1);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", webProperties.getXss().getExcludes());
        initParameters.put("enabled", webProperties.getXss().getEnabled().toString());
        registration.setInitParameters(initParameters);
        return registration;
    }

    /**
     * 配置Validator
     * Set fail fast mode and message source of validation.
     */
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.getValidationPropertyMap()
                .put("hibernate.validator.fail_fast", "false");
        factoryBean.setValidationMessageSource(messageSource);
        return factoryBean;
    }

    /**
     * HttpMessageConverter全局配置，设置Long到String的转换规则
     */
    @Bean
    public HttpMessageConverters httpMessageConverters(ObjectProvider<HttpMessageConverter<?>> convertersProvider) {
        //simpleModule for Long to String
        SimpleModule simpleModuleForLong = new SimpleModule();
        simpleModuleForLong.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModuleForLong.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModuleForLong.addSerializer(Long.TYPE, ToStringSerializer.instance);

        //converters registered by SpringBoot
        List<HttpMessageConverter<?>> converters = convertersProvider.orderedStream().collect(Collectors.toList());
//        for (HttpMessageConverter<?> converter : converters) {
//            if (converter instanceof MappingJackson2HttpMessageConverter) {
//                ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
//                mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//                //mapper.registerModule(simpleModuleForLong);
//            }
//        }
        return new HttpMessageConverters(converters);
    }

    /**
     * RestTemplate
     *
     * @param factory http client工厂类，注入simple, OkHttp, HttpClient
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);

        //replace UTF-8 StringHttpMessageConverter
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.removeIf(c -> c instanceof StringHttpMessageConverter);
        messageConverters.add(new StringHttpMessageConverter(Charset.forName(FoundiConst.DEFAULT_CHARSET)));

        return restTemplate;
    }

    /**
     * SimpleClientHttpRequestFactory
     */
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(15000);// ms
        factory.setConnectTimeout(15000);// ms
        return factory;
    }

}
