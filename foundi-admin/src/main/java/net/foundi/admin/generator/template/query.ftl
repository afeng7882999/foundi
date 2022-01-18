/*
* Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
* (email:afeng7882999@163.com, qq:7882999).
*/
package ${package}.${moduleName}.entity.query;

import lombok.Data;
import net.foundi.framework.entity.query.Criterion;
<#if hasOrder>
import net.foundi.framework.entity.query.Order;
</#if>

<#if queryHasTime>
import java.time.LocalTime;
</#if>
<#if queryHasDate>
import java.time.LocalDate;
</#if>
<#if queryHasDateTime>
import java.time.LocalDateTime;
</#if>
<#if queryHasBigDecimal>
</#if>
<#if queryBetweenColumns??>
import java.util.List;
</#if>

/**
* ${tableComment}Query
*
* @author ${author}
*/
@Data
public class ${className}Query implements Query {
<#if hasQuery>
    <#list queryColumns as column>
        <#if column.queryType = 'EQUAL'>
    /** 精确 */
    @Criterion<#if column.isSpecialName>(tableField  = "${column.columnName}")</#if>
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'INNER_LIKE'>
    /** 模糊 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.INNER_LIKE)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'RIGHT_LIKE'>
    /** 右模糊 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.RIGHT_LIKE)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'LEFT_LIKE'>
    /** 左模糊 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.LEFT_LIKE)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'NOT_EQUAL'>
    /** 不等于 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.NOT_EQUAL)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'NOT_NULL'>
    /** 不为空 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.NOT_NULL)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'GREATER_THAN'>
    /** 大于等于 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.GREATER_THAN)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'LESS_THAN'>
    /** 小于等于 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.LESS_THAN)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'GREATER_THAN_NQ'>
    /** 大于 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.GREATER_THAN_NQ)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'LESS_THAN_NQ'>
    /** 小于 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.LESS_THAN_NQ)
    private ${column.fieldType} ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'IN'>
    /** 包含 */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.IN)
    private List<Long> ${column.lowerFieldName};

        </#if>
        <#if column.queryType = 'BETWEEN'>
    /** BETWEEN */
    @Criterion(<#if column.isSpecialName>tableField  = "${column.columnName}", </#if>type = Criterion.Type.BETWEEN)
    private List<${column.fieldType}> ${column.lowerFieldName};

        </#if>
    </#list>
</#if>
<#if hasOrder>
    /** 排序 */
    <#list columns as column>
        <#if column.isOrder>
    @Order(field = "${column.lowerFieldName}"<#if column.isSpecialName>, tableField  = "${column.columnName}"</#if>)
        </#if>
    </#list>
    private List<String> orderByList;
</#if>

}