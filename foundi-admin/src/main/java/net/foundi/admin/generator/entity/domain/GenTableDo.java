/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.entity.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;

import java.time.LocalDateTime;

/**
 * 业务表
 *
 * @author Afeng (afeng7882999@163.com)
 */
@TableName("gen_table")
@Data
@EqualsAndHashCode(callSuper = false)
public class GenTableDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 表名 */
    private String tableName;

    /** 表中文名 */
    private String tableComment;

    /** 表创建时间 */
    private LocalDateTime tableCreateTime;

    /** 数据库引擎 */
    private String tableEngine;

    /** 表编码集 */
    private String tableEncoding;

    /** 实体名 */
    private String entityName;

    /** 模块名 */
    private String module;

    /** 包名 */
    private String pack;

    /** 前端代码路径 */
    private String frontPath;

    /** 是否是子表 */
    private Boolean isSub;

    /** 是否是树表 */
    private Boolean isTree;

    /** 树编码字段 */
    private String treeId;

    /** 树名称字段 */
    private String treeName;

    /** 树父编码字段 */
    private String treeParentId;

    /** 树排序字段 */
    private String treeSort;

    /** 前端编辑（新增）页面 */
    private Boolean isFrontEdit;

    /** 前端详细页面 */
    private Boolean isFrontDetail;

    /** 菜单标题 */
    private String menuTitle;

    /** 上级菜单ID */
    private Long menuParentId;

    /** 作者 */
    private String author;

}