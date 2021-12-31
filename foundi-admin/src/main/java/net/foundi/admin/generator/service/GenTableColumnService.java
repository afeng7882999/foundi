/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.service;

import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.framework.service.BaseService;

import java.util.List;

/**
 * 业务表字段Service
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface GenTableColumnService extends BaseService<GenTableColumnDo> {
    /**
     * 从GenTable表中获取特定表的字段列表
     *
     * @param tableName 表名称
     * @return 字段列表
     */
    List<GenTableColumnDo> list(String tableName);

    /**
     * 从数据库中获取特定表的字段列表
     *
     * @param tableName 表名称
     * @return 字段列表
     */
    List<GenTableColumnDo> listFromDb(String tableName);

    /**
     * 同步GenTable表与数据库中特定表的字段
     *
     * @param tableName 表名称
     * @return 字段列表
     */
    List<GenTableColumnDo> syncFromDb(String tableName);

}
