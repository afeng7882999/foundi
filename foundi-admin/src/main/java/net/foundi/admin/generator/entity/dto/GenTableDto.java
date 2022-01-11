/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.entity.dto;

import lombok.Data;
import net.foundi.admin.generator.entity.domain.GenTableDo;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务表DTO
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Data
public class GenTableDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 表名 */
    @NotBlank(message = "表名不能为空")
    private String tableName;

    /** 表中文名 */
    private String tableComment;

    /** 表创建时间 */
    @JsonTimestamp
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


    public static GenTableDo toDo(GenTableDto dto) {
        if (dto == null) {
            return null;
        } else {
            GenTableDo aDo = new GenTableDo();
            aDo.setId(dto.getId());
            aDo.setTableName(dto.getTableName());
            aDo.setTableComment(dto.getTableComment());
            aDo.setTableCreateTime(dto.getTableCreateTime());
            aDo.setTableEngine(dto.getTableEngine());
            aDo.setTableEncoding(dto.getTableEncoding());
            aDo.setEntityName(dto.getEntityName());
            aDo.setModule(dto.getModule());
            aDo.setPack(dto.getPack());
            aDo.setFrontPath(dto.getFrontPath());
            aDo.setIsSub(dto.getIsSub());
            aDo.setIsTree(dto.getIsTree());
            aDo.setTreeId(dto.getTreeId());
            aDo.setTreeName(dto.getTreeName());
            aDo.setTreeParentId(dto.getTreeParentId());
            aDo.setTreeSort(dto.getTreeSort());
            aDo.setIsFrontEdit(dto.getIsFrontEdit());
            aDo.setIsFrontDetail(dto.getIsFrontDetail());
            aDo.setMenuTitle(dto.getMenuTitle());
            aDo.setMenuParentId(dto.getMenuParentId());
            aDo.setAuthor(dto.getAuthor());
            return aDo;
        }
    }

    public static List<GenTableDo> toDo(List<GenTableDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(GenTableDto::toDo).collect(Collectors.toList());
        }
    }

    public static GenTableDto fromDo(GenTableDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            GenTableDto dto = new GenTableDto();
            dto.setId(aDo.getId());
            dto.setTableName(aDo.getTableName());
            dto.setTableComment(aDo.getTableComment());
            dto.setTableCreateTime(aDo.getTableCreateTime());
            dto.setTableEngine(aDo.getTableEngine());
            dto.setTableEncoding(aDo.getTableEncoding());
            dto.setEntityName(aDo.getEntityName());
            dto.setModule(aDo.getModule());
            dto.setPack(aDo.getPack());
            dto.setFrontPath(aDo.getFrontPath());
            dto.setIsSub(aDo.getIsSub());
            dto.setIsTree(aDo.getIsTree());
            dto.setTreeId(aDo.getTreeId());
            dto.setTreeName(aDo.getTreeName());
            dto.setTreeParentId(aDo.getTreeParentId());
            dto.setTreeSort(aDo.getTreeSort());
            dto.setIsFrontEdit(aDo.getIsFrontEdit());
            dto.setIsFrontDetail(aDo.getIsFrontDetail());
            dto.setMenuTitle(aDo.getMenuTitle());
            dto.setMenuParentId(aDo.getMenuParentId());
            dto.setAuthor(aDo.getAuthor());
            return dto;
        }
    }

    public static List<GenTableDto> fromDo(List<GenTableDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(GenTableDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<GenTableDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (GenTableDo genTable : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("表名", genTable.getTableName());
            map.put("表中文名", genTable.getTableComment());
            map.put("表创建时间", genTable.getTableCreateTime());
            map.put("数据库引擎", genTable.getTableEngine());
            map.put("表编码集", genTable.getTableEncoding());
            map.put("实体名", genTable.getEntityName());
            map.put("模块名", genTable.getModule());
            map.put("包名", genTable.getPack());
            map.put("前端代码路径", genTable.getFrontPath());
            map.put("是否是树表", genTable.getIsTree());
            map.put("树编码字段", genTable.getTreeId());
            map.put("树名称字段" , genTable.getTreeName());
            map.put("树父编码字段", genTable.getTreeParentId());
            map.put("树排序字段", genTable.getTreeSort());
            map.put("前端编辑（新增）页面", genTable.getIsFrontEdit());
            map.put("前端详细页面", genTable.getIsFrontDetail());
            map.put("菜单标题", genTable.getMenuTitle());
            map.put("上级菜单ID", genTable.getMenuParentId());
            map.put("作者", genTable.getAuthor());
            list.add(map);
        }
        return list;
    }
}