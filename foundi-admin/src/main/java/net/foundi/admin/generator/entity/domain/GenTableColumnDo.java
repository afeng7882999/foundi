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

/**
 * 代码生成表字段DO
 *
 * @author Afeng
 */
@TableName("gen_table_column")
@Data
@EqualsAndHashCode(callSuper = false)
public class GenTableColumnDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 表名称 */
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

    /** 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、IN数组、BETWEEN范围） */
    private String queryType;

    /** 显示类型（input文本框、textarea文本域、select下拉框、checkbox复选框、radio单选框、datetime日期控件、image图片上传控件、upload文件上传控件、editor富文本控件） */
    private String htmlType;

    /** 字典类型 */
    private String dictType;

    /** 排序 */
    private Integer sort;

}