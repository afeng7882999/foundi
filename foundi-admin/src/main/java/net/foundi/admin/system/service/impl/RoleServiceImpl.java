/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import net.foundi.admin.system.dao.RoleDao;
import net.foundi.admin.system.dao.RoleGroupDao;
import net.foundi.admin.system.dao.RoleMenuDao;
import net.foundi.admin.system.entity.domain.MenuDo;
import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.admin.system.entity.domain.RoleGroupDo;
import net.foundi.admin.system.entity.domain.RoleMenuDo;
import net.foundi.admin.system.service.GroupService;
import net.foundi.admin.system.service.MenuService;
import net.foundi.admin.system.service.RoleService;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.lang.CollectionUtils;
import net.foundi.framework.entity.query.Query;
import net.foundi.framework.service.BaseServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
* 系统角色Service
*
* @author Afeng
*/
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, RoleDo> implements RoleService {

    private final MenuService menuService;
    private final GroupService groupService;
    private final RoleMenuDao roleMenuMapper;
    private final RoleGroupDao roleGroupMapper;

    public RoleServiceImpl(MenuService menuService, GroupService groupService, RoleMenuDao roleMenuMapper,
                           RoleGroupDao roleGroupMapper) {
        this.menuService = menuService;
        this.groupService = groupService;
        this.roleMenuMapper = roleMenuMapper;
        this.roleGroupMapper = roleGroupMapper;
    }

    @Override
    public IPage<RoleDo> page(Page<RoleDo> page, Query query) {
        IPage<RoleDo> dos = super.page(page, query);
        for (RoleDo aDo : dos.getRecords()) {
            this.setMenuAndGroup(aDo);
        }
        return dos;
    }

    @Override
    public List<RoleDo> getRolesByUserId(Long userId) {
        return baseMapper.getRolesByUserId(userId);
    }

    @Override
    public List<RoleDo> getRolesByIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getRolesByIds(roleIds);
    }

    @Override
    public RoleDo getById(Long id) {
        RoleDo result = super.getById(id);
        this.setMenuAndGroup(result);
        return result;
    }

    /**
     * 设置角色的业务权限和数据权限字段
     *
     * @param role RoleDo
     * @return RoleDo
     */
    private RoleDo setMenuAndGroup(RoleDo role) {
        List<Long> menuIds = menuService.getMenusByRoleIds(Collections.singletonList(role.getId()))
                .stream().map(MenuDo::getId).collect(Collectors.toList());
        List<Long> groupIds = groupService.getGroupIdsByRoleIds(Collections.singletonList(role.getId()));
        role.setMenuIdList(menuIds);
        role.setGroupIdList(groupIds);
        return role;
    }

    @Override
    public List<RoleDo> getByNames(List<String> names) {
        return baseMapper.selectList(new QueryWrapper<RoleDo>().lambda().in(RoleDo::getName, names));
    }

    @Override
    @CacheEvict(value = {"perms", "menus", "groups"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public RoleDo saveAndGet(RoleDo record) {
        try {
            if (record.getId() == null) {
                record.setId(IdWorker.getId());
            }
            if (SqlHelper.retBool(baseMapper.insert(record))) {
                this.updateMenuAndGroup(record);
                return this.getById(record.getId());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("新增项目出错", e);
        }
    }

    @Override
    @CacheEvict(value = {"perms", "menus", "groups"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public RoleDo updateAndGet(RoleDo record) {
        try {
            if (SqlHelper.retBool(baseMapper.updateById(record))) {
                this.updateMenuAndGroup(record);
                return this.getById(record.getId());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    private void updateMenuAndGroup(RoleDo record) {
        List<RoleMenuDo> rms = new ArrayList<>();
        for (Long menuId : record.getMenuIdList()) {
            RoleMenuDo rm = new RoleMenuDo();
            rm.setMenuId(menuId);
            rm.setRoleId(record.getId());
            rms.add(rm);
        }
        roleMenuMapper.delete(new QueryWrapper<RoleMenuDo>().lambda()
                .eq(RoleMenuDo::getRoleId, record.getId()));
        if (rms.size() > 0) {
            roleMenuMapper.batchInsert(rms);
        }

        List<RoleGroupDo> rds = new ArrayList<>();
        for (Long groupId : record.getGroupIdList()) {
            RoleGroupDo rd = new RoleGroupDo();
            rd.setGroupId(groupId);
            rd.setRoleId(record.getId());
            rds.add(rd);
        }
        roleGroupMapper.delete(new QueryWrapper<RoleGroupDo>().lambda()
                .eq(RoleGroupDo::getRoleId, record.getId()));
        if (rds.size() > 0) {
            roleGroupMapper.batchInsert(rds);
        }
    }

    @Override
    @CacheEvict(value = {"perms", "menus", "groups"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        try {
            roleMenuMapper.delete(new QueryWrapper<RoleMenuDo>().lambda().eq(RoleMenuDo::getRoleId, id));
            roleGroupMapper.delete(new QueryWrapper<RoleGroupDo>().lambda().eq(RoleGroupDo::getRoleId, id));
            baseMapper.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    @CacheEvict(value = {"perms", "menus", "groups"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(List<Long> ids) {
        try {
            roleMenuMapper.delete(new QueryWrapper<RoleMenuDo>().lambda().in(RoleMenuDo::getRoleId, ids));
            roleGroupMapper.delete(new QueryWrapper<RoleGroupDo>().lambda().in(RoleGroupDo::getRoleId, ids));
            baseMapper.deleteBatchIds(ids);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

}