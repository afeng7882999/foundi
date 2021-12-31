/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.FileDo;
import net.foundi.admin.system.entity.dto.FileDto;
import net.foundi.admin.system.entity.query.FileQuery;
import net.foundi.admin.system.service.FileService;
import net.foundi.framework.entity.validation.EditGroup;
import net.foundi.framework.log.Log;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.web.BaseController;
import net.foundi.framework.web.WebReturn;
import net.foundi.framework.web.resubmit.Resubmit;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* 文件上传Controller
*
* @author Afeng
*/
@Api(tags = "文件上传管理")
@RestController
@RequestMapping("/system/file")
public class FileController extends BaseController {

    private final FileService fileService;
    
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @ApiOperation("查询文件")
    @GetMapping("/{id}")
    @PreAuthorize("@authz.hasPerm('system:file:get')")
    public WebReturn getOne(@PathVariable("id") Long id) {
        FileDo aDo = fileService.getById(id);
        FileDto result = FileDto.fromDo(aDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("查询文件")
    @GetMapping
    @PreAuthorize("@authz.hasPerm('system:file:list')")
    public WebReturn list(FileQuery query) {
        IPage<FileDo> page = fileService.page(getPage(), query);
        return WebReturn.ok()
            .page(page)
            .content(FileDto.fromDo(page.getRecords()));
    }

    @ApiOperation("修改文件")
    @PutMapping
    @PreAuthorize("@authz.hasPerm('system:file:edit')")
    @Resubmit
    @Log("修改文件")
    public WebReturn edit(@RequestBody @Validated({EditGroup.class}) FileDto dto) {
        FileDo aDo = FileDto.toDo(dto);
        FileDo newDo = fileService.updateAndGet(aDo);
        FileDto result = FileDto.fromDo(newDo);
        return WebReturn.ok().content(result);
    }

    @ApiOperation("删除文件")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@authz.hasPerm('system:file:delete')")
    @Log("删除文件")
    public WebReturn deleteMulti(@PathVariable("ids") List<Long> ids) {
        fileService.removeBatch(ids);
        return WebReturn.ok();
    }

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    @PreAuthorize("@authz.hasPerm('system:file:upload')")
    @Log(value = "文件操作", param = "'上传文件: ' + #files.![originalFilename]")
    public WebReturn upload(@RequestParam(value = "files", required = true) MultipartFile[] files,
                            @RequestParam(required = false) String oss) {
        List<String> urls = fileService.upload(files, oss);
        return WebReturn.ok()
                .content(urls);
    }

    @ApiOperation("查询当前用户文件")
    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public WebReturn listByCurrentUser(FileQuery query) {
        Long userId = SecurityUtils.getCurrentUserOrEx().getId();
        query.setCreateBy(userId);
        IPage<FileDo> page = fileService.page(getPage(), query);
        return WebReturn.ok()
                .page(page)
                .content(FileDto.fromDo(page.getRecords()));
    }

}
