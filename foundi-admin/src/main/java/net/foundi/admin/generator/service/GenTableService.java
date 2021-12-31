/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.admin.generator.entity.domain.GenTableDo;
import net.foundi.admin.generator.entity.query.GenTableQuery;
import net.foundi.framework.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 业务表Service
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface GenTableService extends BaseService<GenTableDo> {

    /**
     * 从数据库中获取
     *
     * @param tableName 表名
     * @return GenTableDo
     */
    GenTableDo getOneFromDb(String tableName);

    /**
     * 从数据库中获取表列表
     *
     * @param tableNames 表名列表
     * @return GenTableDo列表
     */
    List<GenTableDo> listFromDb(List<String> tableNames);

    /**
     * 从数据库中获取表列表，分页
     *
     * @param page  分页
     * @param query 查询条件
     * @return IPage对象
     */
    IPage<GenTableDo> pageFromDb(Page<GenTableDo> page, GenTableQuery query);

    /**
     * 从数据库中导入
     *
     * @param tableNames 表名列表
     */
    void importFromDb(List<String> tableNames);

    /**
     * 与数据库同步表结构
     *
     * @param id 业务表ID
     */
    void syncFromDb(Long id);

    /**
     * 更新业务表及列数据
     *
     * @param table   业务表
     * @param columns 列数据
     */
    void update(GenTableDo table, List<GenTableColumnDo> columns);

    /**
     * 批量删除业务表
     *
     * @param ids 表ID列表
     */
    void removeBatchWithColumns(List<Long> ids);

    /**
     * 生成代码，文件形式
     *
     * @param id 表ID
     */
    void generateToFile(Long id);

    /**
     * 生成代码，Map形式
     *
     * @param id 表ID
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> generateToMap(Long id);

    /**
     * 生成代码，byte数组形式，用于下载
     *
     * @param ids 表ID列表
     * @return 字节数组
     */
    byte[] generateToBytes(List<Long> ids);
}
