/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;

/**
 * 系统用户与角色对应关系DO
 *
 * @author Afeng
 */
@TableName("sys_user_role")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRoleDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;

}