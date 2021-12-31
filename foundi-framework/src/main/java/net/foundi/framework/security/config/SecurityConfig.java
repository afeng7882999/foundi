/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.CorsFilter;

import net.foundi.common.constant.FoundiConst;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.security.filter.TokenAuthcFilter;
import net.foundi.framework.security.handler.TokenAuthcFailEntryPoint;
import net.foundi.framework.security.provider.ExtAuthcProvider;

/**
 * spring security配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackages = "net.foundi.framework")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenAuthcFilter tokenAuthcFilter;
    private final CorsFilter corsFilter;
    private final TokenAuthcFailEntryPoint tokenAuthcFailEntryPoint;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final UserDetailsService userDetailsService;
    private final ExtAuthcProvider extAuthcProvider;

    public SecurityConfig(TokenAuthcFilter tokenAuthcFilter,
                          CorsFilter corsFilter,
                          TokenAuthcFailEntryPoint tokenAuthcFailEntryPoint,
                          LogoutSuccessHandler logoutSuccessHandler,
                          @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                          ExtAuthcProvider extAuthcProvider) {
        this.tokenAuthcFilter = tokenAuthcFilter;
        this.corsFilter = corsFilter;
        this.tokenAuthcFailEntryPoint = tokenAuthcFailEntryPoint;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userDetailsService = userDetailsService;
        this.extAuthcProvider = extAuthcProvider;
    }

    /**
     * Security配置
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @AnonymousAccess处理
        Set<String> anonymousUrls = SecurityUtils.anonymousUrls();
        httpSecurity
                // 禁用 CSRF
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // 授权异常
                .exceptionHandling()
                .authenticationEntryPoint(tokenAuthcFailEntryPoint)
                // 防止iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable()
                // 不创建会话
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 请求认证
                .and()
                .authorizeRequests()
                // 静态资源
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                // 文件
                .antMatchers(FoundiConst.LOCAL_UPLOAD_URL + "**").permitAll()
                // 第三方
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .antMatchers("/druid/**").permitAll()
                // OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 自定义匿名访问
                .antMatchers(anonymousUrls.toArray(new String[0])).permitAll()
                // 除上面的所有请求全部需要认证
                .anyRequest().authenticated();
        // logout
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(tokenAuthcFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, TokenAuthcFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    
    }


    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(extAuthcProvider);
    }

    /**
     * 显式获取AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
