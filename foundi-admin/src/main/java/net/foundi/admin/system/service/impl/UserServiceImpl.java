/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import net.foundi.admin.system.dao.OAuthUserDao;
import net.foundi.admin.system.dao.UserDao;
import net.foundi.admin.system.dao.UserRoleDao;
import net.foundi.admin.system.entity.domain.OAuthUserDo;
import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.entity.domain.UserRoleDo;
import net.foundi.admin.system.entity.dto.OAuthUserDto;
import net.foundi.admin.system.entity.dto.UserDto;
import net.foundi.admin.system.entity.query.UserQuery;
import net.foundi.admin.system.service.GroupService;
import net.foundi.admin.system.service.UserService;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.entity.query.QueryHelpper;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* 系统用户Service
*
* @author Afeng
*/
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, UserDo> implements UserService {

    private final UserRoleDao userRoleMapper;
    private final OAuthUserDao oAuthUserMapper;
    private final GroupService groupService;

    public UserServiceImpl(UserRoleDao userRoleMapper, OAuthUserDao oAuthUserMapper, GroupService groupService) {
        this.userRoleMapper = userRoleMapper;
        this.oAuthUserMapper = oAuthUserMapper;
        this.groupService = groupService;
    }

    @Override
    public List<Long> getRoleIdsById(Long userId) {
        return baseMapper.getRoleIdsById(userId);
    }

    @Override
    public UserDo getById(Long id) {
        return baseMapper.selectOneOfUserDo(new QueryWrapper<UserDo>().lambda().eq(UserDo::getId, id));
    }

    @Override
    public UserDo getByUsernameOrEmailOrMobile(String principal) {
        UserDo result = baseMapper.selectOneOfUserDo(new QueryWrapper<UserDo>().lambda()
                .eq(UserDo::getUsername, principal)
                .or()
                .eq(UserDo::getEmail, principal)
                .or()
                .eq(UserDo::getMobile, principal));
        return result;
    }

    @Override
    public UserDo getByUsername(String username) {
        UserDo result = baseMapper.selectOneOfUserDo(new QueryWrapper<UserDo>().lambda().
                eq(UserDo::getUsername, username));
        return result;
    }

    @Override
    public UserDo getByMobile(String mobile) {
        UserDo result = baseMapper.selectOneOfUserDo(new QueryWrapper<UserDo>().lambda().eq(UserDo::getMobile, mobile));
        return result;
    }

    @Override
    public IPage<UserDo> page(Page<UserDo> page, UserQuery query) {
        if (query.getGroupId() != null) {
            List<Long> groupIds = groupService.getSubGroupIds(query.getGroupId());
            List<Long> distinctIds = groupIds.stream().distinct().collect(Collectors.toList());
            query.setGroupId(distinctIds);
        }
        return baseMapper.selectPageOfUserDo(page, QueryHelpper.getQuery(query));
    }

    @Override
    public UserDo saveAndGet(UserDo record) {
        try {
            if (record.getId() == null) {
                record.setId(IdWorker.getId());
            }
            if (StringUtils.hasValue(record.getPassword())) {
                record.setPassword(SecurityUtils.encryptPassword(record.getPassword()));
            }
            if (SqlHelper.retBool(baseMapper.insert(record))) {
                this.updateRole(record);
                return this.getById(record.getId());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("新增项目出错", e);
        }
    }

    @Override
    public UserDo updateAndGet(UserDo record) {
        try {
            if (StringUtils.hasValue(record.getPassword())) {
                // 不更新密码
                record.setPassword(null);
            }
            if (SqlHelper.retBool(baseMapper.updateById(record))) {
                this.updateRole(record);
                return this.getById(record.getId());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    private void updateRole(UserDo record) {
        if (record.getRoleList() == null || record.getRoleList().size() == 0) {
            return;
        }
        List<UserRoleDo> urList = new ArrayList<>();
        for (RoleDo role : record.getRoleList()) {
            UserRoleDo ur = new UserRoleDo();
            ur.setUserId(record.getId());
            ur.setRoleId(role.getId());
            urList.add(ur);
        }
        userRoleMapper.delete(new QueryWrapper<UserRoleDo>().lambda()
                .eq(UserRoleDo::getUserId, record.getId()));
        if (urList.size() > 0) {
            userRoleMapper.batchInsert(urList);
        }
    }

    @Override
    public Boolean updatePassword(Long id, String oldPassword, String newPassword) {
        UserDo aDo = this.getById(id);
        if (aDo != null) {
            if (StringUtils.isEmpty(oldPassword) && StringUtils.isEmpty(aDo.getPassword())) {
                //old password is empty, add new one
                String password = SecurityUtils.encryptPassword(newPassword);
                UserDo newDo = new UserDo();
                newDo.setId(id);
                newDo.setPassword(password);
                return SqlHelper.retBool(baseMapper.updateById(newDo));
            } else {
                //match and update old one
                if (SecurityUtils.passwordMatches(oldPassword, aDo.getPassword())) {
                    String password = SecurityUtils.encryptPassword(newPassword);
                    UserDo newDo = new UserDo();
                    newDo.setId(id);
                    newDo.setPassword(password);
                    return SqlHelper.retBool(baseMapper.updateById(newDo));
                }
            }
        }
        throw new BusinessException("原密码错误");
    }

    @Override
    public void remove(Long id) {
        try {
            oAuthUserMapper.delete(new QueryWrapper<OAuthUserDo>().lambda().eq(OAuthUserDo::getUserId, id));
            userRoleMapper.delete(new QueryWrapper<UserRoleDo>().lambda().eq(UserRoleDo::getUserId, id));
            baseMapper.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    public void removeBatch(List<Long> ids) {
        try {
            oAuthUserMapper.delete(new QueryWrapper<OAuthUserDo>().lambda().in(OAuthUserDo::getUserId, ids));
            userRoleMapper.delete(new QueryWrapper<UserRoleDo>().lambda().in(UserRoleDo::getUserId, ids));
            baseMapper.deleteBatchIds(ids);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    public Boolean checkNoDuplicate(Long id, SFunction<UserDo, ?> column, Object val) {
        LambdaQueryWrapper<UserDo> qw = new QueryWrapper<UserDo>().lambda().eq(column, val);
        if (id == null) {
            qw.ne(UserDo::getId, id);
        }
        return this.count(qw) == 0;
    }

    @Override
    public IPage<UserDto> pageWithOAuth(Page<UserDo> page, UserQuery query) {
        IPage<UserDo> doPage = baseMapper.selectPageOfUserDo(page, QueryHelpper.getQuery(query));
        Page<UserDto> result = new Page<>();
        result.setCurrent(doPage.getCurrent());
        result.setSize(doPage.getSize());
        result.setTotal(doPage.getTotal());
        result.setPages(doPage.getPages());
        result.setOrders(doPage.orders());

        List<UserDto> list = UserDto.fromDo(page.getRecords());
        result.setRecords(this.setOAuth(list));

        return result;
    }

    @Override
    public List<UserDto> setOAuth(List<UserDto> dtos) {
        if (dtos == null || dtos.size() == 0) {
            return dtos;
        }
        List<Long> ids = dtos.stream().map(UserDto::getId).collect(Collectors.toList());
        LambdaQueryWrapper<OAuthUserDo> qw = new QueryWrapper<OAuthUserDo>().lambda().in(OAuthUserDo::getUserId, ids);
        List<OAuthUserDo> oAuthUsers = oAuthUserMapper.selectList(qw);
        for (UserDto dto : dtos) {
            List<OAuthUserDo> oAuthUserList = oAuthUsers.stream()
                    .filter(t -> t.getUserId().equals(dto.getId()))
                    .collect(Collectors.toList());
            dto.setOAuthUserList(OAuthUserDto.fromDo(oAuthUserList));
        }
        return dtos;
    }

    @Override
    public UserDto getByIdWithOAuth(Long id) {
        UserDo aDo = baseMapper.selectOneOfUserDo(new QueryWrapper<UserDo>().lambda().eq(UserDo::getId, id));
        return this.setOAuth(UserDto.fromDo(aDo));
    }

    @Override
    public UserDto setOAuth(UserDto dto) {
        LambdaQueryWrapper<OAuthUserDo> qw = new QueryWrapper<OAuthUserDo>().lambda().eq(OAuthUserDo::getUserId, dto.getId());
        List<OAuthUserDo> oAuthUserList = oAuthUserMapper.selectList(qw);
        dto.setOAuthUserList(OAuthUserDto.fromDo(oAuthUserList));
        return dto;
    }
    
    @Override
    public UserContext getUserContext(UserDo user, List<String> permissions, AuthcType authcType, String appName) {
        UserContext context = new UserContext();
        context.setId(user.getId());
        context.setUsername(user.getUsername());
        context.setPassword(user.getPassword());
        context.setStatus(user.getStatusDict());
        context.setRoles(user.getRoleList().stream().map(RoleDo::toRole).collect(Collectors.toList()));
        context.setPermissions(permissions);
        context.setGroupId(user.getGroupId());
        context.setAuthcType(authcType);
        context.setAppName(appName);
        return context;
    }

}