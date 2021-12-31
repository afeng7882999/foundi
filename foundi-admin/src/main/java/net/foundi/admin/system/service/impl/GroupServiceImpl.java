/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import net.foundi.common.utils.spring.SpringAppUtils;
import net.foundi.framework.service.config.ServiceConst;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import net.foundi.framework.service.BaseServiceImpl;
import net.foundi.admin.system.dao.GroupDao;
import net.foundi.admin.system.entity.domain.GroupDo;
import net.foundi.admin.system.service.GroupService;
import net.foundi.admin.system.service.impl.GroupServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* 系统用户组Service
*
* @author Afeng
*/
@Service
public class GroupServiceImpl extends BaseServiceImpl<GroupDao, GroupDo> implements GroupService {

    @Override
    public List<Long> getGroupIdsByRoleIds(List<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return SpringAppUtils.getBean(GroupService.class)
                .getGroupsByRoleIds(roleIds)
                .stream()
                .map(GroupDo::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value="groups", key="#roleIds")
    public List<GroupDo> getGroupsByRoleIds(List<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getGroupsByRoleIds(roleIds);
    }

    @Override
    public List<Long> getSubGroupIds(Long groupId) {
        return SpringAppUtils.getBean(GroupService.class)
                .getSubGroupIds(Collections.singletonList(groupId));
    }

    @Override
    @Cacheable(value="subGroups", key="#groupIds")
    public List<Long> getSubGroupIds(List<Long> groupIds) {
        List<Long> subIds = new ArrayList<>();
        getSubIdsRecursive(groupIds, subIds);
        return subIds;
    }

    @Override
    @Cacheable(value="parentGroups", key="#groupId")
    public List<Long> getParentGroupIds(Long groupId) {
        List<Long> parentIds = new ArrayList<>();
        getParentIdsRecursive(groupId, parentIds);
        return parentIds;
    }

    @Override
    @CacheEvict(value = {"groups", "subGroups", "parentGroups"}, allEntries = true)
    public GroupDo saveAndGet(GroupDo record) {
        return super.saveAndGet(record);
    }

    @Override
    @CacheEvict(value = {"groups", "subGroups", "parentGroups"}, allEntries = true)
    public GroupDo updateAndGet(GroupDo record) {
        return super.updateAndGet(record);
    }

    @Override
    @CacheEvict(value = {"groups", "subGroups", "parentGroups"}, allEntries = true)
    public void remove(Long id) {
        super.remove(id);
    }

    @Override
    @CacheEvict(value = {"groups", "subGroups", "parentGroups"}, allEntries = true)
    public void removeBatch(List<Long> ids) {
        super.removeBatch(ids);
    }

    private void getSubIdsRecursive(List<Long> parentIds, List<Long> returnIds) {
        for (Long parentId : parentIds) {
            List<Long> childIds = baseMapper.getIdsByParentId(parentId);
            if (childIds.size() > 0) {
                getSubIdsRecursive(childIds, returnIds);
            }
            returnIds.add(parentId);
        }
    }

    private void getParentIdsRecursive(Long subId, List<Long> returnIds) {
        GroupDo sub = baseMapper.selectById(subId);
        if (sub != null && !sub.getParentId().equals(ServiceConst.TREE_ROOT_ID)) {
            getParentIdsRecursive(sub.getParentId(), returnIds);
        }
        returnIds.add(subId);
    }

}