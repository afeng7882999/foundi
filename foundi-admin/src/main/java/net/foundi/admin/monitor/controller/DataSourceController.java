/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.monitor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.admin.generator.entity.domain.GenTableDo;
import net.foundi.admin.generator.entity.dto.GenDto;
import net.foundi.admin.generator.entity.dto.GenTableColumnDto;
import net.foundi.admin.generator.entity.dto.GenTableDto;
import net.foundi.admin.monitor.service.DataSourceService;
import net.foundi.admin.system.entity.domain.DictDo;
import net.foundi.framework.web.BaseController;
import net.foundi.framework.web.WebReturn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据源监控Controller
 *
 * @author Afeng (afeng7882999@163.com)
 */
@RestController
@RequestMapping("/monitor/dataSource")
@Api(tags = "系统监控: 数据源监控")
public class DataSourceController extends BaseController {

    private final DataSourceService dataSourceService;

    public DataSourceController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @ApiOperation("获取Druid的监控信息")
    @GetMapping(value = "/{types}")
    @PreAuthorize("@authz.hasPerm('monitor:dataSource:get')")
    public WebReturn getInfo(@PathVariable("types") List<String> types) {
        Map<String, String> info = dataSourceService.getInfoByType(types);
        return WebReturn.ok().content(info);
    }

    @ApiOperation("通过ID获取FullSql信息")
    @GetMapping(value="/fullSql/{id}")
    @PreAuthorize("@authz.hasPerm('monitor:dataSource:get')")
    public WebReturn getFullSql(@PathVariable("id") String id) {
        String info = dataSourceService.getFullSqlById(id);
        return WebReturn.ok().content(info);
    }

    @ApiOperation("通过ID获取WebUri详细信息")
    @GetMapping(value="/webUriDetail/{id}")
    @PreAuthorize("@authz.hasPerm('monitor:dataSource:get')")
    public WebReturn getWebUriDetail(@PathVariable("id") String id) {
        String info = dataSourceService.getWebUriDetailById(id);
        return WebReturn.ok().content(info);
    }
}
