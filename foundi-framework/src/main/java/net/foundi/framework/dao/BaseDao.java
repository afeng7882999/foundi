/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import net.foundi.framework.entity.domain.Do;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao基类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface BaseDao<T extends Do> extends BaseMapper<T> {

    /**
     * 批量新增数据，MySQL
     */
    int insertBatchSomeColumn(List<T> entities);

    /**
     * 逻辑删除并填充某些字段
     */
    int deleteByIdWithFill(T entity);

    /**
     * 固定更新某些字段(不包含逻辑删除字段)
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
}
