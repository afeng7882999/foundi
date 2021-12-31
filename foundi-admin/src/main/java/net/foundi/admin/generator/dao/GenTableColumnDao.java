/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.dao;

import java.util.List;
import java.util.Map;

import net.foundi.admin.generator.entity.domain.GenTableColumnDo;
import org.apache.ibatis.annotations.Mapper;

import net.foundi.framework.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * 业务表字段Dao
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Mapper
@Repository
public interface GenTableColumnDao extends BaseDao<GenTableColumnDo> {

    List<Map<String, Object>> selectColumnInfo(String tableName);

    List<Map<String, Object>> selectColumnInfoByPage(String tableName);
}