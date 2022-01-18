/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.foundi.admin.generator.dao.GenTableColumnDao;
import net.foundi.admin.generator.dao.GenTableDao;
import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.admin.generator.entity.domain.GenTableDo;
import net.foundi.admin.generator.entity.query.GenTableQuery;
import net.foundi.admin.generator.service.GenTableColumnService;
import net.foundi.admin.generator.service.GenTableService;
import net.foundi.admin.generator.utils.TemplateHelper;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.entity.query.QueryHelpper;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 业务表Service
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Service
public class GenTableServiceImpl extends BaseServiceImpl<GenTableDao, GenTableDo> implements GenTableService {

    private final GenTableColumnDao genTableColumnMapper;
    private final GenTableColumnService genTableColumnService;

    public GenTableServiceImpl(GenTableColumnDao genTableColumnMapper, GenTableColumnService genTableColumnService) {
        this.genTableColumnMapper = genTableColumnMapper;
        this.genTableColumnService = genTableColumnService;
    }

    @Override
    public GenTableDo getOneFromDb(String tableName) {
        return Optional.ofNullable(this.baseMapper.selectTableInfoByName(tableName))
                .map(this::genTableFromMap)
                .orElse(null);
    }

    @Override
    public List<GenTableDo> listFromDb(List<String> tableNames) {
        return this.baseMapper.selectTableInfo(tableNames)
                .stream()
                .map(this::genTableFromMap)
                .collect(Collectors.toList());
    }

    @Override
    public IPage<GenTableDo> pageFromDb(Page<GenTableDo> page, GenTableQuery query) {
        IPage<Map<String, Object>> infos = this.baseMapper
                .selectTableInfoByPage(page, QueryHelpper.getQuery(query));
        List<GenTableDo> records = infos.getRecords()
                .stream()
                .map(this::genTableFromMap)
                .collect(Collectors.toList());
        Page<GenTableDo> newPage = new Page<>();
        newPage.setCurrent(infos.getCurrent());
        newPage.setSize(infos.getSize());
        newPage.setTotal(infos.getTotal());
        newPage.setRecords(records);
        return newPage;
    }

    @Override
    public void importFromDb(List<String> tableNames) {
        List<GenTableDo> tablesFromDb = this.listFromDb(tableNames);
        List<GenTableDo> tables = this.list(new QueryWrapper<GenTableDo>().lambda()
                .in(GenTableDo::getTableName, tableNames));

        for (GenTableDo tableDb : tablesFromDb) {
            // 更新业务表列
            List<GenTableColumnDo> columns = this.genTableColumnService.syncFromDb(tableDb.getTableName());
            GenTableDo table = tables
                    .stream()
                    .filter(t -> t.getTableName().equals(tableDb.getTableName()))
                    .findFirst()
                    .orElse(null);
            // 初始化业务表字段
            this.initGenTableDo(tableDb, columns);
            if (table != null) {
                // 已经导入过
                table.setTableComment(tableDb.getTableComment());
                table.setTableEncoding(tableDb.getTableEncoding());
                table.setTableEngine(tableDb.getTableEngine());
                table.setTableCreateTime(tableDb.getTableCreateTime());
                table.setMenuTitle(tableDb.getMenuTitle());
                table.setMenuParentId(tableDb.getMenuParentId());
                table.setAuthor(tableDb.getAuthor());
                table.setIsTree(tableDb.getIsTree());
                if (tableDb.getIsTree()) {
                    table.setTreeId(tableDb.getTreeId());
                    table.setTreeParentId(tableDb.getTreeParentId());
                    table.setTreeSort(tableDb.getTreeSort());
                }
                this.saveOrUpdate(table);
            } else {
                // 未导入过
                this.save(tableDb);
            }
        }
    }

    @Override
    public void syncFromDb(Long id) {
        GenTableDo table = this.getById(id);
        if (table == null) {
            throw new BusinessException("未找到相关业务表");
        }
        genTableColumnService.syncFromDb(table.getTableName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(GenTableDo table, List<GenTableColumnDo> columns) {
        boolean result = false;
        try {
            result = this.genTableColumnService.updateBatchById(columns);
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
        if (result) {
            this.update(table);
        } else {
            throw new BusinessException("编辑项目出错");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatchWithColumns(List<Long> ids) {
        try {
            List<GenTableDo> tables = this.list(new QueryWrapper<GenTableDo>().lambda().in(GenTableDo::getId, ids));
            List<String> tableNames = tables.stream().map(GenTableDo::getTableName).collect(Collectors.toList());
            genTableColumnMapper.delete(new QueryWrapper<GenTableColumnDo>().lambda()
                    .in(GenTableColumnDo::getTableName, tableNames));
            this.removeBatch(ids);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    public void generateToFile(Long id) {
        GenTableDo table = this.getById(id);
        if (table == null) {
            throw new BusinessException("请先配置生成参数");
        }
        List<GenTableColumnDo> columns = genTableColumnMapper.selectList(new QueryWrapper<GenTableColumnDo>().lambda()
                .eq(GenTableColumnDo::getTableName, table.getTableName()));
        try {
            TemplateHelper.generateFile(columns, table, true);
        } catch (Exception e) {
            throw new BusinessException("生成失败，请先配置生成参数");
        }
    }

    @Override
    public List<Map<String, Object>> generateToMap(List<Long> ids) {
        List<GenTableDo> tableList = new ArrayList<>();
        List<List<GenTableColumnDo>> columnsList = new ArrayList<>();

        for (Long id : ids) {
            GenTableDo table = this.getById(id);
            if (table == null) {
                throw new BusinessException("请先配置生成参数");
            }
            List<GenTableColumnDo> columns = genTableColumnMapper.selectList(new QueryWrapper<GenTableColumnDo>().lambda()
                    .eq(GenTableColumnDo::getTableName, table.getTableName()));
            tableList.add(table);
            columnsList.add(columns);
        }

        try {
            return TemplateHelper.generateToMap(columnsList, tableList);
        } catch (Exception e) {
            throw new BusinessException("生成失败，请先配置生成参数", e);
        }
    }

    @Override
    public byte[] generateToBytes(List<Long> ids) {
        List<GenTableDo> tableList = new ArrayList<>();
        List<List<GenTableColumnDo>> columnsList = new ArrayList<>();

        for (Long id : ids) {
            GenTableDo table = this.getById(id);
            if (table == null) {
                throw new BusinessException("请先配置生成参数");
            }
            List<GenTableColumnDo> columns = genTableColumnMapper.selectList(new QueryWrapper<GenTableColumnDo>().lambda()
                    .eq(GenTableColumnDo::getTableName, table.getTableName()));
            tableList.add(table);
            columnsList.add(columns);
        }


        try {
            return TemplateHelper.generateToZip(columnsList, tableList);
        } catch (Exception e) {
            throw new BusinessException("生成失败，请先配置生成参数");
        }
    }

    /**
     * Map转换为GenTableDo
     *
     * @param tableInfo Map
     * @return GenTableDo
     */
    private GenTableDo genTableFromMap(Map<String, Object> tableInfo) {
        GenTableDo result = new GenTableDo();
        boolean lCase = tableInfo.containsKey("table_name");

        result.setTableName(tableInfo.get(lCase ? "table_name" : "TABLE_NAME").toString());
        result.setTableComment(tableInfo.get(lCase ? "table_comment" : "TABLE_COMMENT").toString());
        result.setTableEncoding(tableInfo.get(lCase ? "table_collation" : "TABLE_COLLATION").toString());
        result.setTableEngine(tableInfo.get(lCase ? "engine" : "ENGINE").toString());
        String createTime = tableInfo.get(lCase ? "create_time" : "CREATE_TIME").toString();

        try {
            result.setTableCreateTime(DateUtils.parse(createTime));
        } catch (Exception e) {
            throw new BusinessException("获取数据库表数据出错", e);
        }

        return result;
    }

    /**
     * 初始化业务表的特定字段
     *
     * @param table   业务表
     * @param columns 列列表
     */
    private void initGenTableDo(GenTableDo table, List<GenTableColumnDo> columns) {
        table.setEntityName(StringUtils.snakeToCamel(table.getTableName(), true));
        table.setIsSub(false);

        if (TemplateHelper.isTree(columns)) {
            // 由是否包含特定列，确定是否是树表
            table.setIsTree(true);
            table.setTreeId(TemplateHelper.TREE_ID_FIELD);
            table.setTreeName(TemplateHelper.TREE_NAME_FIELD);
            table.setTreeParentId(TemplateHelper.PARENT_ID_FIELD);
            table.setTreeSort(TemplateHelper.SORT_ORDER_FIELD);
        } else {
            table.setIsTree(false);
        }

        table.setPack("net.foundi.admin");
        table.setModule("system");
        table.setMenuTitle(table.getTableComment());
        table.setMenuParentId(20L);
        table.setAuthor(SecurityUtils.getCurrentUserOrEx().getUsername());
    }

}