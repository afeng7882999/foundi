/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.ConfigDo;
import net.foundi.admin.system.entity.dto.ConfigDto;
import net.foundi.admin.system.entity.query.ConfigQuery;
import net.foundi.admin.system.service.ConfigService;
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
* 系统配置Controller
*
* @author Afeng
*/
@Api(tags = "系统配置管理")
@RestController
@RequestMapping("/system/config")
public class ConfigController extends BaseController {

    private final ConfigService configService;
    
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @ApiOperation("查询系统配置")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:config:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        ConfigDo aDo = configService.getById(id);
        ConfigDto result = ConfigDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统配置")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:config:list')")
    public WebReturn list(ConfigQuery query) {
        IPage<ConfigDo> page = configService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(ConfigDto.fromDo(page.getRecords()));
    }

    @ApiOperation("新增系统配置")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:config:add')")
    @Resubmit
    @Log("新增系统配置")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) ConfigDto dto) {
        ConfigDo aDo = ConfigDto.toDo(dto);
        ConfigDo newDo = configService.saveAndGet(aDo);
        ConfigDto result = ConfigDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改系统配置")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:config:edit')")
    @Resubmit
    @Log("修改系统配置")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) ConfigDto dto) {
        ConfigDo aDo = ConfigDto.toDo(dto);
        ConfigDo newDo = configService.updateAndGet(aDo);
        ConfigDto result = ConfigDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除系统配置")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:config:delete')")
    @Log("删除系统配置")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        configService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出数据")
    @GetMapping("/export")
    @PreAuthorize("@authz.hasPerm('system:config:export')")
    public void export(HttpServletResponse rep, ConfigQuery query) throws IOException {
        MultipartUtils.downloadExcel(ConfigDto.toMap(configService.list(query)), rep);
    }

}
