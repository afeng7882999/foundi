/*
* Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
* (email:afeng7882999@163.com, qq:7882999).
*/

package ${package}.${moduleName}.entity.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
<#if hasTime>
import java.time.LocalTime;
</#if>
<#if hasDate>
import java.time.LocalDate;
</#if>
<#if hasDateTime>
import java.time.LocalDateTime;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>

import net.foundi.framework.entity.domain.Do;

/**
* ${tableComment}DO
*
* @author ${author}
*/
@TableName("${tableName}")
@Data
@EqualsAndHashCode(callSuper = false)
public class ${className}Do implements Do {

    private static final long serialVersionUID = 1L;

<#list columns as column>
    <#if column.columnComment?hasContent>
    /** ${column.columnComment} */
    </#if>
    <#if column.isPk>
    @TableId
    </#if>
    <#if column.isSpecialName>
    @TableField(value = "${column.columnName}")
    </#if>
    <#if column.isAutoInsertField>
    @TableField(fill = FieldFill.INSERT)
    </#if>
    <#if column.isAutoUpdateField>
    @TableField(fill = FieldFill.UPDATE)
    </#if>
    <#if column.isVersionField>
    @Version
    </#if>
    <#if column.isLogicDeleteField>
    @TableLogic
    </#if>
    private ${column.fieldType} ${column.lowerFieldName};

</#list>
<#list columns as column>
//    public static final String Col${column.upperFieldName} = "${column.columnName}";
</#list>

}