/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.admin.system.entity.domain.TaskDo;
import net.foundi.admin.system.entity.dto.RoleDto;
import net.foundi.admin.system.entity.dto.TaskDto;
import net.foundi.admin.system.entity.query.LoginLogQuery;
import net.foundi.admin.system.entity.query.TaskQuery;
import net.foundi.admin.system.service.TaskService;
import net.foundi.common.utils.web.MultipartUtils;
import net.foundi.framework.entity.validation.AddGroup;
import net.foundi.framework.entity.validation.EditGroup;
import net.foundi.framework.log.Log;
import net.foundi.framework.web.BaseController;
import net.foundi.framework.web.WebReturn;
import net.foundi.framework.web.resubmit.Resubmit;
import net.foundi.support.task.config.TaskConst;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* 系统任务Controller
*
* @author Afeng
*/
@Api(tags = "系统任务管理")
@RestController
@RequestMapping("/system/task")
public class TaskController extends BaseController {

    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation("查询系统任务")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:task:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        TaskDo aDo = taskService.getById(id);
        TaskDto result = TaskDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统任务")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:task:list')")
    public WebReturn list(TaskQuery query) {
        IPage<TaskDo> page = taskService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(TaskDto.fromDo(page.getRecords()));
    }

    @ApiOperation("新增系统任务")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:task:add')")
    @Resubmit
    @Log("新增系统任务")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) TaskDto dto) {
        TaskDo aDo = TaskDto.toDo(dto);
        TaskDo newDo = taskService.saveAndGet(aDo);
        TaskDto result = TaskDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统任务")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:task:edit')")
    @Resubmit
    @Log("修改系统任务")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) TaskDto dto) {
        TaskDo aDo = TaskDto.toDo(dto);
        TaskDo newDo = taskService.updateAndGet(aDo);
        TaskDto result = TaskDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统任务")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:task:delete')")
    @Log("删除系统任务")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        taskService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("启动/停止任务")
    @GetMapping("/status")
    @PreAuthorize("@authz.hasPerm('system:task:edit')")
    @Log("启动/停止任务")
    public WebReturn changeTaskStatus(@RequestParam Long id, @RequestParam String cmd) {
        TaskDo aDo = taskService.changeStatus(id, TaskConst.CMD_START.equalsIgnoreCase(cmd)
                ? TaskConst.CMD_START
                : TaskConst.CMD_STOP);
        return WebReturn.ok().message("任务\""+ aDo.getJobName() + "\"启动成功");
    }

    @ApiOperation("立即运行任务")
    @GetMapping("/run")
    @PreAuthorize("@authz.hasPerm('system:task:edit')")
    @Log("立即运行任务")
    public WebReturn runOnceImmediately(@RequestParam Long id) {
        TaskDo aDo = taskService.runOnceImmediately(id);
        return WebReturn.ok().message("任务\""+ aDo.getJobName() + "\"运行成功");
    }

    @ApiOperation("导出当前页数据")
    @GetMapping(value = "/exportPage")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportPage(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        IPage<TaskDo> page = taskService.page(getPage(), query);
        MultipartUtils.downloadExcel(TaskDto.toMap(page.getRecords()), rep);
    }

    @ApiOperation("导出全部数据")
    @GetMapping(value = "/exportAll")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportAll(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        MultipartUtils.downloadExcel(TaskDto.toMap(taskService.list(query)), rep);
    }

}
