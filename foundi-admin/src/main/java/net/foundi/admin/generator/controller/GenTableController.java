/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.DictDo;
import net.foundi.admin.system.service.DictService;
import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.admin.generator.entity.domain.GenTableDo;
import net.foundi.admin.generator.entity.dto.GenDto;
import net.foundi.admin.generator.entity.dto.GenTableColumnDto;
import net.foundi.admin.generator.entity.dto.GenTableDto;
import net.foundi.admin.generator.entity.query.GenTableQuery;
import net.foundi.admin.generator.service.GenTableColumnService;
import net.foundi.admin.generator.service.GenTableService;
import net.foundi.common.utils.web.MultipartUtils;
import net.foundi.framework.log.Log;
import net.foundi.framework.web.BaseController;
import net.foundi.framework.web.WebReturn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 业务表Controller
 *
 * @author Afeng (afeng7882999@163.com)
 */
@RestController
@RequestMapping("/generator/genTable")
@Api(tags = "代码生成: 业务表管理")
public class GenTableController extends BaseController {

    private final GenTableService genTableService;
    private final GenTableColumnService genTableColumnService;
    private final DictService dictService;

    public GenTableController(GenTableService genTableService, GenTableColumnService genTableColumnService, DictService dictService) {
        this.genTableService = genTableService;
        this.genTableColumnService = genTableColumnService;
        this.dictService = dictService;
    }

    @ApiOperation("查询业务表")
    @GetMapping(value = "/{id}")
    @PreAuthorize("@authz.hasPerm('generator:genTable:get')")
    public WebReturn getOne(@PathVariable Long id) {
        GenTableDo table = genTableService.getById(id);
        List<GenTableColumnDo> columns = genTableColumnService.list(table.getTableName());
        GenDto dto = new GenDto();
        dto.setTable(GenTableDto.fromDo(table));
        dto.setColumns(GenTableColumnDto.fromDo(columns));
        List<DictDo> dicts = dictService.list();
        return WebReturn
                .ok()
                .content(dto)
                .content("dicts", dicts);
    }

    @ApiOperation("查询业务表")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('generator:genTable:list')")
    public WebReturn list(GenTableQuery query) {
        IPage<GenTableDo> pageDos = genTableService.page(getPage(), query);
        return WebReturn
                .ok()
                .page(pageDos)
                .content(GenTableDto.fromDo(pageDos.getRecords()));
    }

    @ApiOperation("由数据库查询业务表")
    @GetMapping(value = "/listDb")
    @PreAuthorize("@authz.hasPerm('generator:genTable:list')")
    public WebReturn listFromDb(GenTableQuery query) {
        IPage<GenTableDo> pageDos = genTableService.pageFromDb(getPage(), query);
        return WebReturn
                .ok()
                .page(pageDos)
                .content(GenTableDto.fromDo(pageDos.getRecords()));
    }

    @ApiOperation("由数据库导入业务表")
    @PostMapping(value = "/importDb")
    @PreAuthorize("@authz.hasPerm('generator:genTable:edit')")
    public WebReturn importFromDb(@RequestBody List<String> tableNames) {
        genTableService.importFromDb(tableNames);
        return WebReturn.ok();
    }

    @ApiOperation("修改业务表")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('generator:genTable:edit')")
    public WebReturn edit(@Validated @RequestBody GenDto dto) {
        GenTableDo table = GenTableDto.toDo(dto.getTable());
        List<GenTableColumnDo> columns = GenTableColumnDto.toDo(dto.getColumns());
        genTableService.update(table, columns);
        return WebReturn.ok();
    }

    @ApiOperation("同步字段数据")
    @GetMapping(value = "/syncDb/{id}")
    @PreAuthorize("@authz.hasPerm('generator:genTable:edit')")
    public WebReturn syncFromDb(@PathVariable Long id) {
        genTableService.syncFromDb(id);
        return WebReturn.ok();
    }

    @ApiOperation("生成代码并预览")
    @GetMapping(value = "/preview/{id}")
    @PreAuthorize("@authz.hasPerm('generator:genTable:edit')")
    public WebReturn generator(@PathVariable Long id) {
        List<Map<String, Object>> result = genTableService.generateToMap(id);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("生成代码并下载")
    @GetMapping(value = "/download/{ids}")
    @PreAuthorize("@authz.hasPerm('generator:genTable:edit')")
    public void generator(@PathVariable("ids") List<Long> ids, HttpServletResponse rep) throws IOException {
        byte[] zip = genTableService.generateToBytes(ids);
        MultipartUtils.downloadBytes(rep, zip, "generate.zip");
    }

    @ApiOperation("删除业务表")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('generator:genTable:delete')")
    @Log("删除业务表")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        genTableService.removeBatchWithColumns(ids);
        return WebReturn.ok();
    }
}
