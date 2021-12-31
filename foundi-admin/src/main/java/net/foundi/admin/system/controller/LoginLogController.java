/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.LoginLogDo;
import net.foundi.admin.system.entity.dto.LoginLogDto;
import net.foundi.admin.system.entity.query.LoginLogQuery;
import net.foundi.admin.system.service.LoginLogService;
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
* 系统访问日志Controller
*
* @author Afeng
*/
@Api(tags = "系统访问日志管理")
@RestController
@RequestMapping("/system/loginLog")
public class LoginLogController extends BaseController {

    private final LoginLogService loginLogService;

    public LoginLogController(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @ApiOperation("查询系统访问日志")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:sysLoginLog:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        LoginLogDo aDo = loginLogService.getById(id);
        LoginLogDto result = LoginLogDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询系统访问日志")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:loginLog:list')")
    public WebReturn list(LoginLogQuery query) {
        IPage<LoginLogDo> page = loginLogService.page(getPage(), query);
        return WebReturn.ok()
                .page(page)
                .content(LoginLogDto.fromDo(page.getRecords()));
    }


    @ApiOperation("删除系统访问日志")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:loginLog:delete')")
    @Log("删除系统访问日志")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        loginLogService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出数据")
    @GetMapping(value = "/export")
    @PreAuthorize("@authz.hasPerm('system:loginLog:export')")
    public void export(HttpServletResponse rep, LoginLogQuery query) throws IOException {
        MultipartUtils.downloadExcel(LoginLogDto.toMap(loginLogService.list(query)), rep);
    }

}
