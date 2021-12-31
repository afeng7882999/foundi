/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.tools.config;

import io.swagger.annotations.ApiOperation;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.web.config.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Swagger2 配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
@EnableConfigurationProperties({SwaggerProperties.class, AppProperties.class})
@EnableSwagger2
public class SwaggerConfig {

    private final SwaggerProperties swaggerProperties;
    private final AppProperties appProperties;

    public SwaggerConfig(SwaggerProperties swaggerProperties, AppProperties appProperties) {
        this.swaggerProperties = swaggerProperties;
        this.appProperties = appProperties;
    }

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(this.swaggerProperties.isEnabled())
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                // .apis(RequestHandlerSelectors.basePackage("net.foundi.admin"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                /* 设置安全模式，swagger可以设置访问token */
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .pathMapping(this.swaggerProperties.getPathMapping());
    }

    /**
     * 安全模式，这里指定token通过Authorization头请求头传递
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        Set<String> anonymousUrls = SecurityUtils.anonymousUrls();
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                // 除@AnonymousAccess声明的都需认证
                .forPaths(p -> {
                    if (p != null) {
                        AntPathMatcher matcher = new AntPathMatcher();
                        return anonymousUrls.stream().noneMatch(a -> matcher.match(a, p));
                    }
                    return true;
                })
                .build();
        securityContexts.add(securityContext);
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(appProperties.getName() + "-api")
                .description("©" + appProperties.getCopyrightYear() + "Copyright. Powered By " +
                        appProperties.getName() + ".")
                .contact(new Contact(appProperties.getAuthor(), "", appProperties.getEmail()))
                .version("Version: " + appProperties.getVersion())
                .build();
    }
}
