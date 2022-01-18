/*
* Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
* (email:afeng7882999@163.com, qq:7882999).
*/

package ${package}.${moduleName}.entity.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import java.util.*;
import java.util.stream.Collectors;

import net.foundi.framework.entity.validation.EditGroup;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import ${package}.${moduleName}.entity.domain.${className}Do;
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

/**
* ${tableComment}DTO
*
* @author ${author}
*/
@Data
<#if isTree>
@EqualsAndHashCode(callSuper = true)
</#if>
public class ${className}Dto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

<#list noPkColumns as column>
    <#if column.columnComment?hasContent>
    /** ${column.columnComment} */
    </#if>
    <#if column.isRequired>
        <#if column.fieldType="String">
    @NotBlank(message = "${column.columnComment}不能为空"<#if column.isPk>, groups = {EditGroup.class}</#if>)
        <#else>
    @NotNull(message = "${column.columnComment}不能为空")
        </#if>
    </#if>
    <#if column.fieldType="LocalDateTime">
    @JsonTimestamp
    </#if>
    private ${column.fieldType} ${column.lowerFieldName};

</#list>

    public static ${className}Do toDo(${className}Dto dto) {
        if (dto == null) {
            return null;
        } else {
            ${className}Do aDo = new ${className}Do();
            <#list columns as column>
            aDo.set${column.upperFieldName}(dto.get${column.upperFieldName}());
            </#list>
            return aDo;
        }
    }

    public static List<${className}Do> toDo(List<${className}Dto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(${className}Dto::toDo).collect(Collectors.toList());
        }
    }

    public static ${className}Dto fromDo(${className}Do aDo) {
        if (aDo == null) {
            return null;
        } else {
            ${className}Dto dto = new ${className}Dto();
            <#list columns as column>
            dto.set${column.upperFieldName}(aDo.get${column.upperFieldName}());
            </#list>
            return dto;
        }
    }

    public static List<${className}Dto> fromDo(List<${className}Do> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(${className}Dto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<${className}Do> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (${className}Do ${lowerClassName} : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            <#list columns as column>
                <#if !column.isPk>
                    <#if column.columnComment?hasContent>
            map.put("${column.columnComment}", ${lowerClassName}.get${column.upperFieldName}());
                    <#else>
            map.put(" ${column.lowerFieldName}",  ${lowerClassName}.get${column.upperFieldName}());
                    </#if>
                </#if>
            </#list>
            list.add(map);
        }
        return list;
    }

}