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
 * 系统角色与用户组对应关系DO
 *
 * @author Afeng
 */
@TableName("sys_role_group")
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleGroupDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 角色ID */
    private Long roleId;

    /** 用户组ID */
    private Long groupId;

}