/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.DictDo;
import net.foundi.admin.system.entity.dto.DictDto;
import net.foundi.admin.system.entity.query.DictQuery;
import net.foundi.admin.system.entity.query.LoginLogQuery;
import net.foundi.admin.system.service.DictService;
import net.foundi.common.utils.web.MultipartUtils;
import net.foundi.framework.entity.validation.AddGroup;
import net.foundi.framework.entity.validation.EditGroup;
import net.foundi.framework.log.Log;
import net.foundi.framework.web.BaseController;
import net.foundi.framework.web.WebReturn;
import net.foundi.framework.web.resubmit.Resubmit;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* 系统字典Controller
*
* @author Afeng
*/
@Api(tags = "系统字典管理")
@RestController
@RequestMapping("/system/dict")
public class DictController extends BaseController {

    private final DictService dictService;
    
    public DictController(DictService dictService) {
        this.dictService = dictService;
    }

    @ApiOperation("查询系统字典")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:dict:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        DictDo aDo = dictService.getById(id);
        DictDto result = DictDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统字典")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:dict:list')")
    public WebReturn list(DictQuery query) {
        IPage<DictDo> page = dictService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(DictDto.fromDo(page.getRecords()));
    }

    @ApiOperation("新增系统字典")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:dict:add')")
    @Resubmit
    @Log("新增系统字典")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) DictDto dto) {
        DictDo aDo = DictDto.toDo(dto);
        DictDo newDo = dictService.saveAndGet(aDo);
        DictDto result = DictDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统字典")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:dict:edit')")
    @Resubmit
    @Log("修改系统字典")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) DictDto dto) {
        DictDo aDo = DictDto.toDo(dto);
        DictDo newDo = dictService.updateAndGet(aDo);
        DictDto result = DictDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统字典")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:dict:delete')")
    @Log("删除系统字典")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        dictService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出当前页数据")
    @GetMapping(value = "/exportPage")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportPage(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        IPage<DictDo> page = dictService.page(getPage(), query);
        MultipartUtils.downloadExcel(DictDto.toMap(page.getRecords()), rep);
    }

    @ApiOperation("导出全部数据")
    @GetMapping(value = "/exportAll")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportAll(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        MultipartUtils.downloadExcel(DictDto.toMap(dictService.list(query)), rep);
    }

}
