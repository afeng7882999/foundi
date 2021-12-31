/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.foundi.admin.generator.dao.GenTableColumnDao;
import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import net.foundi.admin.generator.service.GenTableColumnService;
import net.foundi.admin.generator.utils.TemplateHelper;
import net.foundi.common.utils.lang.CollectionUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务表字段Service
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Service
public class GenTableColumnServiceImpl extends BaseServiceImpl<GenTableColumnDao, GenTableColumnDo> implements GenTableColumnService {

    @Override
    public List<GenTableColumnDo> list(String tableName) {
        List<GenTableColumnDo> result = this.list(new QueryWrapper<GenTableColumnDo>().lambda()
                .eq(GenTableColumnDo::getTableName, tableName).orderByAsc(GenTableColumnDo::getId));
        if (CollectionUtils.isEmpty(result)) {
            result = listFromDb(tableName);
            this.saveBatch(result);
        }
        return result;
    }

    @Override
    public List<GenTableColumnDo> listFromDb(String tableName) {
        List<Map<String, Object>> colInfos = this.baseMapper.selectColumnInfo(tableName);
        List<GenTableColumnDo> result = new ArrayList<>();
        for (int i = 0; i < colInfos.size(); i++) {
            GenTableColumnDo col = genTableColumnFromMap(colInfos.get(i), tableName);
            col.setSort(i + 1);
            result.add(col);
        }
        return result;
    }

    @Override
    public List<GenTableColumnDo> syncFromDb(String tableName) {
        List<GenTableColumnDo> cols = this.list(tableName);
        List<GenTableColumnDo> colsFromDb = this.listFromDb(tableName);

        for (GenTableColumnDo colFromDb : colsFromDb) {
            GenTableColumnDo col = cols
                    .stream()
                    .filter(c -> c.getColumnName().equals(colFromDb.getColumnName()))
                    .findFirst()
                    .orElse(null);
            if (col != null) {
                // 已存在字段
                col.setColumnType(colFromDb.getColumnType());
                col.setColumnExtra(colFromDb.getColumnExtra());
                col.setColumnKey(colFromDb.getColumnKey());
                if (StringUtils.isBlank(col.getColumnComment())) {
                    col.setColumnComment((colFromDb.getColumnComment()));
                }
            } else {
                // 新增的字段
                this.save(colFromDb);
            }
        }

        for (GenTableColumnDo col : cols) {
            boolean found = colsFromDb.stream().anyMatch(c -> c.getColumnName().equals(col.getColumnName()));
            if (!found) {
                // 删除的字段
                this.removeById(col.getId());
            }
        }

        return colsFromDb;
    }

    /**
     * Map转换为GenTableColumnDo
     *
     * @param columnInfo Map
     * @param tableName  表名称
     * @return GenTableColumnDo
     */
    private GenTableColumnDo genTableColumnFromMap(Map<String, Object> columnInfo, String tableName) {
        GenTableColumnDo result = new GenTableColumnDo();
        result.setTableName(tableName);
        boolean lCase = columnInfo.containsKey("column_name");

        result.setColumnName(columnInfo.get(lCase ? "column_name" : "COLUMN_NAME").toString());
        result.setColumnComment(columnInfo.get(lCase ? "column_comment" : "COLUMN_COMMENT").toString());
        result.setColumnType(columnInfo.get(lCase ? "data_type" : "DATA_TYPE").toString());

        String columnKeyKey = lCase ? "column_key" : "COLUMN_KEY";
        String key = columnInfo.get(columnKeyKey) != null ? columnInfo.get(columnKeyKey).toString() : null;
        result.setColumnKey(key);

        String extraKey = lCase ? "extra" : "EXTRA";
        String extra = columnInfo.get(extraKey) != null ? columnInfo.get(extraKey).toString() : null;
        result.setColumnExtra(extra);

        result.setFieldType(TemplateHelper.sqlToJavaType(result.getColumnType()));
        result.setFieldName(StringUtils.snakeToCamel(result.getColumnName(), false));

        result.setIsRequired("NO".equals(columnInfo.get(lCase ? "is_nullable" : "IS_NULLABLE").toString()));
        result.setIsList(true);
        result.setIsInsert(true);
        result.setIsEdit(true);
        result.setIsOrder(false);
        result.setHtmlType("input");

        if (TemplateHelper.PK.equalsIgnoreCase(key)) {
            result.setIsRequired(true);
            result.setColumnComment("主键");
            result.setIsList(false);
            result.setIsInsert(false);
            result.setIsEdit(false);
            result.setHtmlType("");
        }

        if (TemplateHelper.isAutoFilledCol(result)) {
            result.setIsInsert(false);
            result.setIsEdit(false);
        }

        result.setIsQuery(false);
        result.setQueryType(Criterion.Type.EQUAL.name());

        return result;
    }

}