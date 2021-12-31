/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.web.WebUtils;
import net.foundi.framework.entity.domain.Role;
import net.foundi.framework.security.config.SecurityConst;
import net.foundi.framework.security.model.UserContext;

/**
 * 权限服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Service("authz")
public class AuthzService {

    private final TokenService tokenService;

    public AuthzService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 判断用户是否具备特定权限
     *
     * @param permission 权限字符串
     * @return true：用户具有特定权限
     */
    public boolean hasPerm(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        UserContext loginUser = tokenService.getLoginUser(WebUtils.getRequest());
        if (loginUser == null || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        return permsAnyMatch(loginUser.getPermissions(), permission);
    }

    /**
     * 判断用户是否具有任意一个权限
     *
     * @param permissions 以 "," 分隔的权限列表
     * @return true：用户具有任意一个权限
     */
    public boolean hasAnyPerm(String permissions) {
        if (StringUtils.isEmpty(permissions)) {
            return false;
        }
        UserContext loginUser = tokenService.getLoginUser(WebUtils.getRequest());
        if (loginUser == null || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        List<String> ps = loginUser.getPermissions();
        for (String p : permissions.split(",")) {
            if (StringUtils.hasValue(p) && permsAnyMatch(ps, p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色字符串
     * @return true：用户具备某个角色
     */
    public boolean hasRole(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        UserContext loginUser = tokenService.getLoginUser(WebUtils.getRequest());
        if (loginUser == null || CollectionUtils.isEmpty(loginUser.getRoles())) {
            return false;
        }
        for (Role r : loginUser.getRoles()) {
            if (SecurityConst.SUPER_ADMIN.equals(r.getName()) || role.equals(r.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否具有任意一个角色
     *
     * @param roles 以 "," 为分隔符的角色列表
     * @return true: 用户具有任意一个角色
     */
    public boolean hasAnyRole(String roles) {
        if (StringUtils.isEmpty(roles)) {
            return false;
        }
        UserContext loginUser = tokenService.getLoginUser(WebUtils.getRequest());
        if (loginUser == null || CollectionUtils.isEmpty(loginUser.getRoles())) {
            return false;
        }
        List<Role> rs = loginUser.getRoles();
        for (String r : roles.split(",")) {
            if (StringUtils.hasValue(r) && rolesAnyAdminOrMatch(rs, r)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return true：用户备某权限
     */
    private boolean permsAnyMatch(List<String> permissions, String permission) {
        return permissions.contains(SecurityConst.ALL_PERMISSION) || permissions.contains(permission.trim());
    }

    /**
     * 判断是否拥有某角色，或为超级管理员
     *
     * @param roles 角色列表
     * @param role  角色字符串
     * @return true：用户拥有某角色，或为超级管理员
     */
    private boolean rolesAnyAdminOrMatch(List<Role> roles, String role) {
        for (Role r : roles) {
            if (SecurityConst.SUPER_ADMIN.equals(r.getName()) || role.equals(r.getName())) {
                return true;
            }
        }
        return false;
    }
}
