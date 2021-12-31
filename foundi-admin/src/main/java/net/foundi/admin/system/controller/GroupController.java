/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.GroupDo;
import net.foundi.admin.system.entity.dto.GroupDto;
import net.foundi.admin.system.entity.query.GroupQuery;
import net.foundi.admin.system.service.GroupService;
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
* 系统用户组Controller
*
* @author Afeng
*/
@Api(tags = "系统用户组管理")
@RestController
@RequestMapping("/system/group")
public class GroupController extends BaseController {

    private final GroupService groupService;
    
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @ApiOperation("查询系统用户组")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:group:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        GroupDo aDo = groupService.getById(id);
        GroupDto result = GroupDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统用户组")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:group:list')")
    public WebReturn list(GroupQuery query) {
        IPage<GroupDo> page = groupService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(GroupDto.fromDo(page.getRecords()));
    }

    @ApiOperation("查询系统用户组")
    @GetMapping("/tree")
    @PreAuthorize("@authz.hasPerm('system:group:list')")
    public WebReturn tree(GroupQuery query) {
        List<GroupDo> dos = groupService.list(query);
        return WebReturn.ok()
            .content(TreeDto.buildTree(GroupDto.fromDo(dos)));
    }

    @ApiOperation("新增系统用户组")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:group:add')")
    @Resubmit
    @Log("新增系统用户组")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) GroupDto dto) {
        GroupDo aDo = GroupDto.toDo(dto);
        GroupDo newDo = groupService.saveAndGet(aDo);
        GroupDto result = GroupDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统用户组")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:group:edit')")
    @Resubmit
    @Log("修改系统用户组")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) GroupDto dto) {
        GroupDo aDo = GroupDto.toDo(dto);
        GroupDo newDo = groupService.updateAndGet(aDo);
        GroupDto result = GroupDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统用户组")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:group:delete')")
    @Log("删除系统用户组")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        groupService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出数据")
    @GetMapping("/export")
    @PreAuthorize("@authz.hasPerm('system:group:export')")
    public void export(HttpServletResponse rep, GroupQuery query) throws IOException {
        MultipartUtils.downloadExcel(GroupDto.toMap(groupService.list(query)), rep);
    }

}
