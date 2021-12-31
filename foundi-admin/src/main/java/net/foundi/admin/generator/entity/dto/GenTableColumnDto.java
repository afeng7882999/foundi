/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.entity.dto;

import lombok.Data;
import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.validation.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 代码生成表字段DTO
*
* @author Afeng
*/
@Data
public class GenTableColumnDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 表名称 */
    @NotBlank(message = "表名称不能为空")
    private String tableName;

    /** 列名称 */
    private String columnName;

    /** 列描述 */
    private String columnComment;

    /** 列类型 */
    private String columnType;

    /** 列键类型 */
    private String columnKey;

    /** 列额外参数 */
    private String columnExtra;

    /** JAVA类型 */
    private String fieldType;

    /** 字段名称 */
    private String fieldName;

    /** 是否必填 */
    private Boolean isRequired;

    /** 是否为插入字段 */
    private Boolean isInsert;

    /** 是否编辑字段 */
    private Boolean isEdit;

    /** 是否列表字段 */
    private Boolean isList;

    /** 是否查询字段 */
    private Boolean isQuery;

    /** 是否排序字段 */
    private Boolean isOrder;

    /** 查询方式（等于、不等于、大于、小于、范围） */
    private String queryType;

    /** 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件等） */
    private String htmlType;

    /** 字典类型 */
    private String dictType;

    /** 排序 */
    private Integer sort;


    public static GenTableColumnDo toDo(GenTableColumnDto dto) {
        if (dto == null) {
            return null;
        } else {
            GenTableColumnDo aDo = new GenTableColumnDo();
            aDo.setId(dto.getId());
            aDo.setTableName(dto.getTableName());
            aDo.setColumnName(dto.getColumnName());
            aDo.setColumnComment(dto.getColumnComment());
            aDo.setColumnType(dto.getColumnType());
            aDo.setColumnKey(dto.getColumnKey());
            aDo.setColumnExtra(dto.getColumnExtra());
            aDo.setFieldType(dto.getFieldType());
            aDo.setFieldName(dto.getFieldName());
            aDo.setIsRequired(dto.getIsRequired());
            aDo.setIsInsert(dto.getIsInsert());
            aDo.setIsEdit(dto.getIsEdit());
            aDo.setIsList(dto.getIsList());
            aDo.setIsQuery(dto.getIsQuery());
            aDo.setIsOrder(dto.getIsOrder());
            aDo.setQueryType(dto.getQueryType());
            aDo.setHtmlType(dto.getHtmlType());
            aDo.setDictType(dto.getDictType());
            aDo.setSort(dto.getSort());
            return aDo;
        }
    }

    public static List<GenTableColumnDo> toDo(List<GenTableColumnDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(GenTableColumnDto::toDo).collect(Collectors.toList());
        }
    }

    public static GenTableColumnDto fromDo(GenTableColumnDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            GenTableColumnDto dto = new GenTableColumnDto();
            dto.setId(aDo.getId());
            dto.setTableName(aDo.getTableName());
            dto.setColumnName(aDo.getColumnName());
            dto.setColumnComment(aDo.getColumnComment());
            dto.setColumnType(aDo.getColumnType());
            dto.setColumnKey(aDo.getColumnKey());
            dto.setColumnExtra(aDo.getColumnExtra());
            dto.setFieldType(aDo.getFieldType());
            dto.setFieldName(aDo.getFieldName());
            dto.setIsRequired(aDo.getIsRequired());
            dto.setIsInsert(aDo.getIsInsert());
            dto.setIsEdit(aDo.getIsEdit());
            dto.setIsList(aDo.getIsList());
            dto.setIsQuery(aDo.getIsQuery());
            dto.setIsOrder(aDo.getIsOrder());
            dto.setQueryType(aDo.getQueryType());
            dto.setHtmlType(aDo.getHtmlType());
            dto.setDictType(aDo.getDictType());
            dto.setSort(aDo.getSort());
            return dto;
        }
    }

    public static List<GenTableColumnDto> fromDo(List<GenTableColumnDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(GenTableColumnDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<GenTableColumnDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (GenTableColumnDo genTableColumn : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("归属表编号", genTableColumn.getTableName());
            map.put("列名称", genTableColumn.getColumnName());
            map.put("列描述", genTableColumn.getColumnComment());
            map.put("列类型", genTableColumn.getColumnType());
            map.put("列键类型", genTableColumn.getColumnKey());
            map.put("列额外参数", genTableColumn.getColumnExtra());
            map.put("JAVA类型", genTableColumn.getFieldType());
            map.put("字段名称", genTableColumn.getFieldName());
            map.put("是否必填", genTableColumn.getIsRequired());
            map.put("是否为插入字段", genTableColumn.getIsInsert());
            map.put("是否编辑字段", genTableColumn.getIsEdit());
            map.put("是否列表字段", genTableColumn.getIsList());
            map.put("是否查询字段", genTableColumn.getIsQuery());
            map.put("是否排序字段", genTableColumn.getIsOrder());
            map.put("查询方式（等于、不等于、大于、小于、范围）", genTableColumn.getQueryType());
            map.put("显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）", genTableColumn.getHtmlType());
            map.put("字典类型", genTableColumn.getDictType());
            map.put("排序", genTableColumn.getSort());
            list.add(map);
        }
        return list;
    }

}