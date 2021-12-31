/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;

import java.time.LocalDateTime;

/**
 * 系统菜单DO
 *
 * @author Afeng
 */
@TableName("sys_menu")
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 父菜单ID，一级菜单为0 */
    private Long parentId;

    /** 菜单名称 */
    private String name;

    /** 菜单URL */
    private String url;

    /** 菜单跳转 */
    private String redirect;

    /** 授权（多个用逗号分隔，如：user:list,user:create） */
    private String perms;

    /** 类型（字典：SysMenuType，0：目录，1：菜单，2：按钮） */
    private String typeDict;

    /** 页面文件路径 */
    private String pagePath;

    /** 菜单图标 */
    private String icon;

    /** 菜单缩写 */
    private String abbr;

    /** 排序 */
    private Integer sort;

    /** 注释 */
    private String remark;

    /** 是否显示 */
    private Boolean visible;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /** 修改时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;

}