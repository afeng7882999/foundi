/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.OAuthUserDo;
import net.foundi.admin.system.entity.dto.OAuthUserDto;
import net.foundi.admin.system.entity.query.OAuthUserQuery;
import net.foundi.admin.system.service.OAuthUserService;
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
 * OAuth用户Controller
 *
 * @author Afeng
 */
@Api(tags = "系统OAuth用户管理")
@RestController
@RequestMapping("/system/oauthUser")
public class OAuthUserController extends BaseController {

    private final OAuthUserService oauthUserService;

    public OAuthUserController(OAuthUserService oauthUserService) {
        this.oauthUserService = oauthUserService;
    }

    @ApiOperation("查询OAuth用户")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:oauthUser:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        OAuthUserDo aDo = oauthUserService.getById(id);
        OAuthUserDto result = OAuthUserDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询OAuth用户")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:oauthUser:list')")
    public WebReturn list(OAuthUserQuery query) {
        IPage<OAuthUserDo> page = oauthUserService.page(getPage(), query);
        return WebReturn.ok()
                .page(page)
                .content(OAuthUserDto.fromDo(page.getRecords()));
    }

    @ApiOperation("新增OAuth用户")
    @PostMapping
    @PreAuthorize("@authz.hasPerm('system:oauthUser:add')")
    @Resubmit
    @Log("新增OAuth用户")
    public WebReturn add(@RequestBody @Validated({AddGroup.class}) OAuthUserDto dto) {
        OAuthUserDo aDo = OAuthUserDto.toDo(dto);
        OAuthUserDo newDo = oauthUserService.saveAndGet(aDo);
        OAuthUserDto result = OAuthUserDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("修改OAuth用户")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:oauthUser:edit')")
    @Resubmit
    @Log("修改OAuth用户")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) OAuthUserDto dto) {
        OAuthUserDo aDo = OAuthUserDto.toDo(dto);
        OAuthUserDo newDo = oauthUserService.updateAndGet(aDo);
        OAuthUserDto result = OAuthUserDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除OAuth用户")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:oauthUser:delete')")
    @Log("删除OAuth用户")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        oauthUserService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("导出数据")
    @GetMapping("/export")
    @PreAuthorize("@authz.hasPerm('system:oauthUser:export')")
    public void export(HttpServletResponse rep, OAuthUserQuery query) throws IOException {
        MultipartUtils.downloadExcel(OAuthUserDto.toMap(oauthUserService.list(query)), rep);
    }

}
