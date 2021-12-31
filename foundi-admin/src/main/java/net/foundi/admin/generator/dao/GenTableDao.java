/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.generator.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.foundi.admin.generator.entity.domain.GenTableDo;
import net.foundi.framework.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 业务表Dao
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Mapper
@Repository
public interface GenTableDao extends BaseDao<GenTableDo> {

    Map<String, Object> selectTableInfoByName(@Param("tableName") String tableName);

    List<Map<String, Object>> selectTableInfo(@Param("tableNames") List<String> tableNames);

    IPage<Map<String, Object>> selectTableInfoByPage(Page<GenTableDo> page,
                                                     @Param("ew") Wrapper<GenTableDo> queryWrapper
    );
}