/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.OperLogDo;
import net.foundi.admin.system.entity.dto.OperLogDto;
import net.foundi.admin.system.entity.query.LoginLogQuery;
import net.foundi.admin.system.entity.query.OperLogQuery;
import net.foundi.admin.system.service.OperLogService;
import net.foundi.common.utils.web.MultipartUtils;
import net.foundi.framework.log.Log;
import net.foundi.framework.web.BaseController;
import net.foundi.framework.web.WebReturn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* 系统操作日志Controller
*
* @author Afeng
*/
@Api(tags = "系统操作日志管理")
@RestController
@RequestMapping("/system/operLog")
public class OperLogController extends BaseController {

    private final OperLogService operLogService;
    
    public OperLogController(OperLogService operLogService) {
        this.operLogService = operLogService;
    }

    @ApiOperation("查询系统操作日志")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:operLog:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        OperLogDo aDo = operLogService.getById(id);
        OperLogDto result = OperLogDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统操作日志")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:operLog:list')")
    public WebReturn list(OperLogQuery query) {
        IPage<OperLogDo> page = operLogService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(OperLogDto.fromDo(page.getRecords()));
    }

    @ApiOperation("删除系统操作日志")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:operLog:delete')")
    @Log("删除系统操作日志")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        operLogService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出当前页数据")
    @GetMapping(value = "/exportPage")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportPage(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        IPage<OperLogDo> page = operLogService.page(getPage(), query);
        MultipartUtils.downloadExcel(OperLogDto.toMap(page.getRecords()), rep);
    }

    @ApiOperation("导出全部数据")
    @GetMapping(value = "/exportAll")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void exportAll(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        MultipartUtils.downloadExcel(OperLogDto.toMap(operLogService.list(query)), rep);
    }

}
