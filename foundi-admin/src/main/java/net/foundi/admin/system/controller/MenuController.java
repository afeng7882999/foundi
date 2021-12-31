/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.MenuDo;
import net.foundi.admin.system.entity.dto.DictItemDto;
import net.foundi.admin.system.entity.dto.MenuDto;
import net.foundi.admin.system.entity.query.MenuQuery;
import net.foundi.admin.system.service.DictItemService;
import net.foundi.admin.system.service.MenuService;
import net.foundi.common.utils.web.MultipartUtils;
import net.foundi.framework.entity.dto.TreeDto;
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
* 系统菜单Controller
*
* @author Afeng
*/
@Api(tags = "系统菜单管理")
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    private final MenuService menuService;
    private final DictItemService dictItemService;
    
    public MenuController(MenuService menuService, DictItemService dictItemService) {
        this.menuService = menuService;
        this.dictItemService = dictItemService;
    }

    @ApiOperation("查询系统菜单")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:menu:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        MenuDo aDo = menuService.getById(id);
        MenuDto result = MenuDto.fromDo(aDo);
        return WebReturn
                .ok()
                .content(result)
                .content("menuTypeDicts", DictItemDto.fromDo(this.dictItemService.listByDictName("SysMenuType")));
    }

    @ApiOperation("查询系统菜单")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:menu:list')")
    public WebReturn list(MenuQuery query) {
        List<MenuDo> dos = menuService.list(query);
        return WebReturn.ok()
            .content(MenuDto.fromDo(dos));
    }

    @ApiOperation("查询系统菜单")
    @GetMapping("/tree")
    @PreAuthorize("@authz.hasPerm('system:menu:list')")
    public WebReturn tree(MenuQuery query) {
        List<MenuDo> dos = menuService.list(query);
        return WebReturn.ok()
            .content(TreeDto.buildTree(MenuDto.fromDo(dos)));
    }

    @ApiOperation("新增系统菜单")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:menu:add')")
    @Resubmit
    @Log("新增系统菜单")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) MenuDto dto) {
        MenuDo aDo = MenuDto.toDo(dto);
        MenuDo newDo = menuService.saveAndGet(aDo);
        MenuDto result = MenuDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统菜单")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:menu:edit')")
    @Resubmit
    @Log("修改系统菜单")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) MenuDto dto) {
        MenuDo aDo = MenuDto.toDo(dto);
        MenuDo newDo = menuService.updateAndGet(aDo);
        MenuDto result = MenuDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统菜单")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:menu:delete')")
    @Log("删除系统菜单")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        menuService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出数据")
    @GetMapping("/export")
    @PreAuthorize("@authz.hasPerm('system:menu:export')")
    public void export(HttpServletResponse rep, MenuQuery query) throws IOException {
        MultipartUtils.downloadExcel(MenuDto.toMap(menuService.list(query)), rep);
    }

}
