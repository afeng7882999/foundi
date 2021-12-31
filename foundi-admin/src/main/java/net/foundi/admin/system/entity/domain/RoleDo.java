/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Role;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统角色DO
 *
 * @author Afeng
 */
@TableName("sys_role")
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleDo extends Role {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 角色名称 */
    private String name;

    /** 角色标识 */
    private String label;

    /** 备注 */
    private String remark;

    /** 数据范围（字典：SysRoleDataScope） */
    private String dataScopeDict;

    /** 创建用户id */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /** 修改用户id */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateBy;

    /** 修改时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;

    /** 是否删除 1：已删除 0：正常 */
    @TableLogic
    private Boolean delFlag;

    @TableField(exist = false)
    private List<Long> menuIdList;

    @TableField(exist = false)
    private List<Long> groupIdList;

    /**
     * RoleDo转换为Role
     *
     * @return Role
     */
    public Role toRole() {
        Role result = new Role();
        result.setId(this.getId());
        result.setName(this.getName());
        result.setDataScopeDict(this.getDataScopeDict());
        return result;
    }

}