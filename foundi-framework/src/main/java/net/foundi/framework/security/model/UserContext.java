/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.model;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import net.foundi.framework.entity.domain.Role;
import net.foundi.framework.security.enums.AuthcType;

/**
 * 登录用户详细信息
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class UserContext implements UserDetails {
    private static final long serialVersionUID = -1075508815366068446L;

    // 用户ID
    private Long id;

    // 用户名
    private String username;

    // 终端标识
    private String appName;

    // 登陆时间
    private Long loginTime;

    // 过期时间
    private Long expireTime;

    // 登录IP地址
    private String ip;

    // 登录地点
    private String location;

    // 浏览器类型
    private String browser;

    // 操作系统
    private String os;

    // 用户状态
    private String status;

    // 角色列表
    private List<Role> roles;

    // 权限列表
    private List<String> permissions;

    // 用户组ID
    private Long groupId;

    // 令牌
    private String token;

    // 认证类型
    private AuthcType authcType = AuthcType.PASSWORD;

    // 用户密码，不缓存，仅登录请求有效
    @JsonIgnore
    private String password;

    // 新发放的、或刷新的JWT令牌，不缓存，当次请求有效
    @JsonIgnore
    private String refreshToken;

    /**
     * 获取用户名
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 获取密码
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 获取权限
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.stream().map(Role::getName).toArray(String[]::new));
    }

    /**
     * 账户是否未过期，过期无法验证，未使用
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁，锁定的用户无法进行身份验证，未使用
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据（密码），过期的凭据防止认证，未使用
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用,禁用的用户不能身份验证，未使用
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthcType getAuthcType() {
        return authcType;
    }

    public void setAuthcType(AuthcType authcType) {
        this.authcType = authcType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
