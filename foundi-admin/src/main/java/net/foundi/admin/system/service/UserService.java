/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.entity.dto.UserDto;
import net.foundi.admin.system.entity.query.UserQuery;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.service.BaseService;

import java.util.List;

/**
 * 系统用户Service
 *
 * @author Afeng
 */
public interface UserService extends BaseService<UserDo> {

    /**
     * 通过手机号获取用户
     *
     * @param mobile 手机号
     * @return UserDo对象
     */
    UserDo getByMobile(String mobile);

    /**
     * 通过用户标识（用户名、邮箱）获取用户
     *
     * @param principal 用户名、邮箱、手机号
     * @return UserDo对象
     */
    UserDo getByUsernameOrEmailOrMobile(String principal);

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return UserDo对象
     */
    UserDo getByUsername(String username);

    /**
     * 获取特定用户的角色ID列表
     *
     * @param userId 用户ID
     * @return 角色ID列表
     */
    List<Long> getRoleIdsById(Long userId);

    /**
     * 通过ID获取用户
     *
     * @param id 用户ID
     * @return UserDo对象
     */
    UserDo getById(Long id);

    /**
     * 获取用户分页列表
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return UserDo分页对象
     */
    IPage<UserDo> page(Page<UserDo> page, UserQuery query);

    /**
     * 更换密码
     *
     * @param id          用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return Boolean
     */
    Boolean updatePassword(Long id, String oldPassword, String newPassword);

    /**
     * 检测特定用户修改的信息（用户名等），是否有重复
     *
     * @param id     用户ID
     * @param column 表列名
     * @param val    修改的值
     * @return 是否有重复
     */
    Boolean checkNoDuplicate(Long id, SFunction<UserDo, ?> column, Object val);

    /**
     * 获取用户分页列表
     *
     * @param page  分页对象
     * @param query 查询条件
     * @return UserDo分页对象
     */
    IPage<UserDto> pageWithOAuth(Page<UserDo> page, UserQuery query);

    /**
     * 通过ID获取用户，包含OAuth信息
     *
     * @param id 用户ID
     * @return UserDto对象
     */
    UserDto getByIdWithOAuth(Long id);

    /**
     * 设置UserDto中的OAuth认证信息
     *
     * @param dto UserDto
     * @return UserDto
     */
    UserDto setOAuth(UserDto dto);

    /**
     * 设置多个UserDto的OAuth认证信息
     *
     * @param dtos UserDto列表
     * @return UserDto列表
     */
    List<UserDto> setOAuth(List<UserDto> dtos);

    /**
     * UserDo转换为UserContext
     *
     * @param permissions 权限字符串列表
     * @param authcType   认证类型
     * @param appName     终端名称
     * @return UserContext对象
     */
    UserContext getUserContext(UserDo user, List<String> permissions, AuthcType authcType, String appName);

}