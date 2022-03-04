/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.MessageDo;
import net.foundi.admin.system.entity.domain.UserMessageDo;
import net.foundi.admin.system.entity.dto.MessageDto;
import net.foundi.admin.system.entity.enums.MessageStatus;
import net.foundi.admin.system.entity.query.LoginLogQuery;
import net.foundi.admin.system.entity.query.MessageQuery;
import net.foundi.admin.system.entity.query.UserMessageQuery;
import net.foundi.admin.system.service.MessageService;
import net.foundi.common.utils.web.MultipartUtils;
import net.foundi.framework.entity.query.QueryHelpper;
import net.foundi.framework.entity.validation.AddGroup;
import net.foundi.framework.entity.validation.EditGroup;
import net.foundi.framework.log.Log;
import net.foundi.framework.security.SecurityUtils;
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
* 系统消息Controller
*
* @author Afeng
*/
@Api(tags = "系统消息管理")
@RestController
@RequestMapping("/system/message")
public class MessageController extends BaseController {

    private final MessageService messageService;
    
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiOperation("查询系统消息")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:message:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        MessageDo aDo = messageService.getById(id);
        MessageDto result = MessageDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统消息")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:message:list')")
    public WebReturn list(MessageQuery query) {
        IPage<MessageDo> page = messageService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(MessageDto.fromDo(page.getRecords()));
    }

    @ApiOperation("新增系统消息")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:message:add')")
    @Resubmit
    @Log("新增系统消息")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) MessageDto dto) {
        MessageDo aDo = MessageDto.toDo(dto);
        MessageDo newDo = messageService.saveAndGet(aDo);
        MessageDto result = MessageDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统消息")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:message:edit')")
    @Resubmit
    @Log("修改系统消息")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) MessageDto dto) {
        MessageDo aDo = MessageDto.toDo(dto);
        MessageDo newDo = messageService.updateAndGet(aDo);
        MessageDto result = MessageDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统消息")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:message:delete')")
    @Log("删除系统消息")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        messageService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出当前页数据")
    @GetMapping(value = "/exportPage")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportPage(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        IPage<MessageDo> page = messageService.page(getPage(), query);
        MultipartUtils.downloadExcel(MessageDto.toMap(page.getRecords()), rep);
    }

    @ApiOperation("导出全部数据")
    @GetMapping(value = "/exportAll")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportAll(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        MultipartUtils.downloadExcel(MessageDto.toMap(messageService.list(query)), rep);
    }

    //************************************************************
    //*  Current User Message
    //************************************************************

    @ApiOperation("当前用户消息列表")
    @GetMapping("/current/list")
    @PreAuthorize("isAuthenticated()")
    public WebReturn currentList(UserMessageQuery query) {
        Long currentId = SecurityUtils.getCurrentUserOrEx().getId();
        QueryWrapper<UserMessageDo> qw = new QueryWrapper<>();
        IPage<UserMessageDo> result = messageService.pageByReceiver(currentId, getPage(), QueryHelpper.getQuery(query));
        return WebReturn
                .ok()
                .page(result);
    }

    @ApiOperation("当前用户消息设置已读")
    @PutMapping("/current/{ids}")
    @PreAuthorize("isAuthenticated()")
    public WebReturn currentSetReadMulti(@PathVariable("ids") List<Long> ids) {
        Long currentId = SecurityUtils.getCurrentUserOrEx().getId();
        messageService.setStatByReceiver(ids, currentId, MessageStatus.READ);
        return WebReturn.ok();
    }

    @ApiOperation("删除当前用户消息")
    @DeleteMapping("/current/{ids}")
    @PreAuthorize("isAuthenticated()")
    public WebReturn currentDelete(@PathVariable("ids") List<Long> ids) {
        Long currentId = SecurityUtils.getCurrentUserOrEx().getId();
        messageService.removeBatchByReceiver(ids, currentId);
        return WebReturn.ok();
    }

}
