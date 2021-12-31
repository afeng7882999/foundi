/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.service.datafilter;

import net.foundi.common.utils.lang.CollectionUtils;
import net.foundi.framework.entity.domain.Role;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.security.config.SecurityConst;
import net.foundi.framework.service.AccountService;
import net.foundi.framework.service.config.ServiceConst;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限，生成当前用户数据权限Sql
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class DataFilter {

    private final AccountService accountService;

    public DataFilter(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 获取当前用户，生成相应数据权限Sql
     *
     * @return 条件Sql
     */
    public String byCurrentUser() {
        List<Role> roles = SecurityUtils.getCurrentUserOrEx().getRoles();
        // 超级管理员
        for (Role r : roles) {
            if (SecurityConst.SUPER_ADMIN.equals(r.getName())) {
                return "1=1";
            }
        }
        Set<String> scopes = roles.stream().map(Role::getDataScopeDict).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(scopes)) {
            return "1<>1";
        }
        // 全部数据权限
        if (scopes.contains(DataScope.ALL.key())) {
            return "1=1";
        }
        // 仅本人数据权限
        if (scopes.size() == 1 && scopes.contains(DataScope.SELF.key())) {
            return ServiceConst.USER_TABLE_COL + "=" + SecurityUtils.getCurrentUserOrEx().getId();
        }
        // 可访问的用户组
        Set<Long> groupIds = new HashSet<>();
        if (scopes.contains(DataScope.GROUP_AND_CHILDREN.key())) {
            groupIds.add(SecurityUtils.getCurrentUserOrEx().getGroupId());
            List<Long> ids = accountService.getSubGroupIdsByGroupId(SecurityUtils.getCurrentUserOrEx().getGroupId());
            groupIds.addAll(ids);
        } else if (scopes.contains(DataScope.GROUP.key())) {
            groupIds.add(SecurityUtils.getCurrentUserOrEx().getGroupId());
        }
        // 自定义用户组
        List<Role> subRoles = roles.stream().filter(r -> DataScope.CUSTOM.key().equals(r.getDataScopeDict()))
                .collect(Collectors.toList());
        if (subRoles.size() > 0) {
            List<Long> ids = accountService.getGroupIdsByRoles(subRoles);
            groupIds.addAll(ids);
        }

        return ServiceConst.GROUP_TABLE_COL + " IN(" +
                groupIds.stream().map(Object::toString).collect(Collectors.joining(",")) + ")";
    }

}
