/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import net.foundi.common.utils.lang.CollectionUtils;
import net.foundi.common.utils.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import net.foundi.framework.service.BaseServiceImpl;
import net.foundi.admin.system.dao.MenuDao;
import net.foundi.admin.system.entity.domain.MenuDo;
import net.foundi.admin.system.service.MenuService;
import net.foundi.admin.system.service.impl.MenuServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* 系统菜单Service
*
* @author Afeng
*/
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuDao, MenuDo> implements MenuService {

    @Override
    @Cacheable(value = "perms", key = "#roleIds")
    public List<String> getPermsByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        List<String> records = baseMapper.getPermsByRoleIds(roleIds);
        return records.stream()
                .filter(StringUtils::hasValue)
                .map(StringUtils::str2List)
                .flatMap(Collection::stream)
                .map(String::trim)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "menus", key = "#roleIds")
    public List<MenuDo> getMenusByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getMenusByRoleIds(roleIds);
    }

    @Override
    @CacheEvict(value = {"perms", "menus"}, allEntries = true)
    public MenuDo saveAndGet(MenuDo record) {
        return super.saveAndGet(record);
    }

    @Override
    @CacheEvict(value = {"perms", "menus"}, allEntries = true)
    public MenuDo updateAndGet(MenuDo record) {
        return super.updateAndGet(record);
    }

    @Override
    @CacheEvict(value = {"perms", "menus"}, allEntries = true)
    public void remove(Long id) {
        super.remove(id);
    }

    @Override
    @CacheEvict(value = {"perms", "menus"}, allEntries = true)
    public void removeBatch(List<Long> ids) {
        super.removeBatch(ids);
    }
}