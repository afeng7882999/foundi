/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;

/**
 * 系统用户组DO
 *
 * @author Afeng
 */
@TableName("sys_group")
@Data
@EqualsAndHashCode(callSuper = false)
public class GroupDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 上级用户组ID，一级用户组为0 */
    private Long parentId;

    /** 排序 */
    private Integer sort;

    /** 用户组名称 */
    private String name;

    /** 是否删除 1：已删除 0：正常 */
    @TableLogic
    private Boolean delFlag;

}