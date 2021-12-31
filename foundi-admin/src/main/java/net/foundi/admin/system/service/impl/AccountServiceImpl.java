/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.service.*;
import net.foundi.framework.entity.domain.Log;
import net.foundi.framework.entity.domain.Role;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AccountService实现
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final UserService userService;
    private final GroupService groupService;
    private final MenuService menuService;
    private final LoginLogService loginLogService;
    private final OperLogService operLogService;

    public AccountServiceImpl(UserService userService,
                              MenuService menuService,
                              GroupService groupService,
                              LoginLogService loginLogService,
                              OperLogService operLogService) {
        this.userService = userService;
        this.groupService = groupService;
        this.menuService = menuService;
        this.loginLogService = loginLogService;
        this.operLogService = operLogService;
    }

    @Override
    public UserContext getUserContextByUsername(String username) {
        UserDo userDo = this.userService.getByUsernameOrEmailOrMobile(username);
        if (userDo == null) {
            return null;
        }
        List<Long> roleIds = userDo.getRoleList().stream().map(RoleDo::getId).collect(Collectors.toList());
        List<String> perms = menuService.getPermsByRoleIds(roleIds);
        return userService.getUserContext(userDo, perms, null, null);
    }

    @Override
    public List<Long> getSubGroupIdsByGroupId(Long groupId) {
        return groupService.getSubGroupIds(Collections.singletonList(groupId));
    }

    @Override
    public List<Long> getGroupIdsByRoles(List<Role> roles) {
        return this.groupService.getGroupIdsByRoleIds(roles.stream().map(Role::getId).collect(Collectors.toList()));
    }

    @Override
    public void logLogin(String username, String ip, String location, String browser, String os, String message,
                         AuthcType authcType, String type, String status, LocalDateTime time) {
        loginLogService.logLogin(username, ip, location, browser, os, message, authcType,
                type, status, time);
    }

    @Override
    public void logOperation(Log log) {
        operLogService.logOperation(log);
    }
}
