/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.foundi.framework.entity.domain.Do;
import net.foundi.framework.entity.query.Query;

import java.util.List;

/**
 * 服务基类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface BaseService<T extends Do> extends IService<T> {

    /**
     * 查询列表
     */
    List<T> list(Query query);

    /**
     * 翻页查询
     */
    IPage<T> page(Page<T> page, Query query);

    /**
     * 插入一条数据
     */
    boolean save(T record);

    /**
     * 插入一条数据，并立即返回
     */
    T saveAndGet(T record);

    /**
     * 更新一条数据
     */
    boolean update(T record);

    /**
     * 更新一条数据，并立即返回
     */
    T updateAndGet(T record);

    /**
     * 更新一条数据，实用UpdateWrapper
     */
    boolean update(T record, UpdateWrapper<T> updateWrapper);

    /**
     * 更新一条数据，实用UpdateWrapper，并立即返回
     */
    T updateAndGet(T record, UpdateWrapper<T> updateWrapper);

    /**
     * 根据ID删除一条数据
     */
    void remove(Long id);

    /**
     * 根据ID列表删除多条数据
     */
    void removeBatch(List<Long> ids);

}
