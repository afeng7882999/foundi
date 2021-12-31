/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.framework.service.BaseService;

import java.util.List;

/**
 * 系统角色Service
 *
 * @author Afeng
 */
public interface RoleService extends BaseService<RoleDo> {

    /**
     * 通过ID获取
     *
     * @param id ID
     * @return RoleDo对象
     */
    RoleDo getById(Long id);

    /**
     * 通过names获取
     *
     * @param names 角色名数组
     * @return RoleDo对象列表
     */
    List<RoleDo> getByNames(List<String> names);

    /**
     * 获取特定用户的角色列表
     *
     * @param userId 用户ID
     * @return RoleDo列表
     */
    List<RoleDo> getRolesByUserId(Long userId);

    /**
     * 获取多个RoleDo对象
     *
     * @param roleIds ID列表
     * @return RoleDo列表
     */
    List<RoleDo> getRolesByIds(List<Long> roleIds);

}