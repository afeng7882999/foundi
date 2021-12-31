/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.admin.system.entity.dto.RoleDto;
import net.foundi.admin.system.entity.query.RoleQuery;
import net.foundi.admin.system.service.RoleService;
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
* 系统角色Controller
*
* @author Afeng
*/
@Api(tags = "系统角色管理")
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    private final RoleService roleService;
    
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation("查询系统角色")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:role:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        RoleDo aDo = roleService.getById(id);
        RoleDto result = RoleDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统角色")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:role:list')")
    public WebReturn list(RoleQuery query) {
        IPage<RoleDo> page = roleService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(RoleDto.fromDo(page.getRecords()));
    }

    @ApiOperation("新增系统角色")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:role:add')")
    @Resubmit
    @Log("新增系统角色")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) RoleDto dto) {
        RoleDo aDo = RoleDto.toDo(dto);
        RoleDo newDo = roleService.saveAndGet(aDo);
        RoleDto result = RoleDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统角色")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:role:edit')")
    @Resubmit
    @Log("修改系统角色")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) RoleDto dto) {
        RoleDo aDo = RoleDto.toDo(dto);
        RoleDo newDo = roleService.updateAndGet(aDo);
        RoleDto result = RoleDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统角色")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:role:delete')")
    @Log("删除系统角色")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        roleService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出数据")
    @GetMapping("/export")
    @PreAuthorize("@authz.hasPerm('system:role:export')")
    public void export(HttpServletResponse rep, RoleQuery query) throws IOException {
        MultipartUtils.downloadExcel(RoleDto.toMap(roleService.list(query)), rep);
    }

}
