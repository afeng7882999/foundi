/*
* Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
* (email:afeng7882999@163.com, qq:7882999).
*/

package ${package}.${moduleName}.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import net.foundi.framework.web.WebReturn;
import net.foundi.framework.log.Log;
import net.foundi.framework.entity.validation.EditGroup;
import net.foundi.framework.entity.validation.AddGroup;
import net.foundi.framework.web.resubmit.Resubmit;
import net.foundi.framework.web.BaseController;
import net.foundi.common.utils.web.MultipartUtils;
import ${package}.${moduleName}.entity.domain.${className}Do;
import ${package}.${moduleName}.entity.dto.${className}Dto;
import ${package}.${moduleName}.entity.query.${className}Query;
import ${package}.${moduleName}.service.${className}Service;

/**
* ${tableComment}Controller
*
* @author ${author}
*/
@Api(tags = "${tableComment}管理")
@RestController
@Validated
@RequestMapping("/${moduleNameSlash}/${lowerClassName}")
public class ${className}Controller extends BaseController {

    private final ${className}Service ${lowerClassName}Service;

    public ${className}Controller(${className}Service ${lowerClassName}Service) {
        this.${lowerClassName}Service = ${lowerClassName}Service;
    }

    @ApiOperation("查询${tableComment}")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('${moduleNameCamel}:${lowerClassName}:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        ${className}Do aDo = ${lowerClassName}Service.getById(id);
        ${className}Dto result = ${className}Dto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

<#if isTree>
    @ApiOperation("查询${tableComment}")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('${moduleNameCamel}:${lowerClassName}:list')")
    public WebReturn list(${className}Query query) {
        List<${className}Do> dos = ${lowerClassName}Service.list(query);
        return WebReturn.ok()
            .page(page)
            .content(${className}Dto.fromDo(dos));
    }
<#else>
    @ApiOperation("查询${tableComment}")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('${moduleNameCamel}:${lowerClassName}:list')")
    public WebReturn list(${className}Query query) {
    IPage<${className}Do> page = ${lowerClassName}Service.page(getPage(), query);
        return WebReturn.ok()
        .page(page)
        .content(${className}Dto.fromDo(page.getRecords()));
    }
</#if>

    @ApiOperation("新增${tableComment}")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('${moduleNameCamel}:${lowerClassName}:add')")
    @Resubmit
    @Log("新增${tableComment}")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) ${className}Dto dto) {
        ${className}Do aDo = ${className}Dto.toDo(dto);
        ${className}Do newDo = ${lowerClassName}Service.saveAndGet(aDo);
        ${className}Dto result = ${className}Dto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改${tableComment}")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('${moduleNameCamel}:${lowerClassName}:edit')")
    @Resubmit
    @Log("修改${tableComment}")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) ${className}Dto dto) {
        ${className}Do aDo = ${className}Dto.toDo(dto);
        ${className}Do newDo = ${lowerClassName}Service.updateAndGet(aDo);
        ${className}Dto result = ${className}Dto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除${tableComment}")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('${moduleNameCamel}:${lowerClassName}:delete')")
    @Log("删除${tableComment}")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        ${lowerClassName}Service.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出数据")
    @GetMapping("/export")
    @PreAuthorize("@authz.hasPerm('${moduleNameCamel}:${lowerClassName}:export')")
    public void export(HttpServletResponse rep, ${className}Query query) throws IOException {
        MultipartUtils.downloadExcel(${className}Dto.toMap(${lowerClassName}Service.list(query)), rep);
    }

}
