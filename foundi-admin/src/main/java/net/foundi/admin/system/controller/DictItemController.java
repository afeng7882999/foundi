/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.DictItemDo;
import net.foundi.admin.system.entity.dto.DictItemDto;
import net.foundi.admin.system.entity.query.DictItemQuery;
import net.foundi.admin.system.service.DictItemService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 系统字典条目Controller
*
* @author Afeng
*/
@Api(tags = "系统字典条目管理")
@RestController
@RequestMapping("/system/dictItem")
public class DictItemController extends BaseController {

    private final DictItemService dictItemService;
    
    public DictItemController(DictItemService dictItemService) {
        this.dictItemService = dictItemService;
    }

    @ApiOperation("查询系统字典条目")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:dictItem:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        DictItemDo aDo = dictItemService.getById(id);
        DictItemDto result = DictItemDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统字典条目")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:dictItem:list')")
    public WebReturn list(DictItemQuery query) {
        IPage<DictItemDo> page = dictItemService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(DictItemDto.fromDo(page.getRecords()));
    }

    @ApiOperation("通过字典名获取系统字典条目")
    @GetMapping("listByName/{dictNames}")
    @PreAuthorize("@authz.hasPerm('system:dictItem:list')")
    public WebReturn listByName(@PathVariable("dictNames") List<String> dictNames) {
        Map<String, List<DictItemDto>> map = new HashMap<>();
        for (String dictName : dictNames) {
            List<DictItemDo> dos = dictItemService.listByDictName(dictName);
            map.put(dictName, DictItemDto.fromDo(dos));
        }
        return WebReturn.ok().content(map);
    }

    @ApiOperation("新增系统字典条目")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:dictItem:add')")
    @Resubmit
    @Log("新增系统字典条目")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) DictItemDto dto) {
        DictItemDo aDo = DictItemDto.toDo(dto);
        DictItemDo newDo = dictItemService.saveAndGet(aDo);
        DictItemDto result = DictItemDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统字典条目")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:dictItem:edit')")
    @Resubmit
    @Log("修改系统字典条目")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) DictItemDto dto) {
        DictItemDo aDo = DictItemDto.toDo(dto);
        DictItemDo newDo = dictItemService.updateAndGet(aDo);
        DictItemDto result = DictItemDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统字典条目")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:dictItem:delete')")
    @Log("删除系统字典条目")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        dictItemService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出数据")
    @GetMapping("/export")
    @PreAuthorize("@authz.hasPerm('system:dictItem:export')")
    public void export(HttpServletResponse rep, DictItemQuery query) throws IOException {
        MultipartUtils.downloadExcel(DictItemDto.toMap(dictItemService.list(query)), rep);
    }

}
