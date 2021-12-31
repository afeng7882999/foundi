/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.GroupDo;
import net.foundi.framework.service.BaseService;

import java.util.List;

/**
 * 系统用户组Service
 *
 * @author Afeng
 */
public interface GroupService extends BaseService<GroupDo> {

    /**
     * 获取角色能够访问的用户组
     *
     * @param roleIds 角色ID列表
     * @return 用户组ID列表
     */
    List<Long> getGroupIdsByRoleIds(List<Long> roleIds);

    /**
     * 获取角色能够访问的用户组
     *
     * @param roleIds 角色ID列表
     * @return 用户组列表
     */
    List<GroupDo> getGroupsByRoleIds(List<Long> roleIds);

    /**
     * 获取下级用户组
     *
     * @param groupId 父用户组
     * @return 下级用户组列表
     */
    List<Long> getSubGroupIds(Long groupId);

    /**
     * 获取下级用户组
     *
     * @param groupIds 父用户组列表
     * @return 下级用户组列表
     */
    List<Long> getSubGroupIds(List<Long> groupIds);

    /**
     * 获取特定用户组的所有上级
     *
     * @param groupId 用户组ID
     * @return 所有上级用户组ID列表
     */
    List<Long> getParentGroupIds(Long groupId);

}