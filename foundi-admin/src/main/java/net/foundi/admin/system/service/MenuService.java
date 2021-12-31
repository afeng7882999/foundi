/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.MenuDo;
import net.foundi.framework.service.BaseService;

import java.util.List;

/**
 * 系统菜单Service
 *
 * @author Afeng
 */
public interface MenuService extends BaseService<MenuDo> {

    /**
     * 获取特定角色权限
     *
     * @param roleIds 角色ID列表
     * @return 角色权限字符串列表
     */
    List<String> getPermsByRoleIds(List<Long> roleIds);

    /**
     * 获取特定角色能够访问的菜单
     *
     * @param roleIds 角色ID列表
     * @return MenuDo列表
     */
    List<MenuDo> getMenusByRoleIds(List<Long> roleIds);

}