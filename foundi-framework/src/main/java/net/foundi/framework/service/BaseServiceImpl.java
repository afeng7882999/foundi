/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import net.foundi.common.exception.BusinessException;
import net.foundi.framework.dao.BaseDao;
import net.foundi.framework.entity.domain.Do;
import net.foundi.framework.entity.query.Query;
import net.foundi.framework.entity.query.QueryHelpper;

import java.util.List;

/**
 * 服务基类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class BaseServiceImpl<M extends BaseDao<T>, T extends Do> extends ServiceImpl<M, T> implements BaseService<T> {

    @Override
    public List<T> list(Query query) {
        try {
            return list(QueryHelpper.getQuery(query));
        } catch (Exception e) {
            throw new BusinessException("查询项目出错", e);
        }
    }

    @Override
    public IPage<T> page(Page<T> page, Query query) {
        try {
            if (query != null) {
                return page(page, QueryHelpper.getQuery(query));
            }
            return page(page);
        } catch (Exception e) {
            throw new BusinessException("查询项目出错", e);
        }
    }

    @Override
    public boolean save(T record) {
        try {
            if (record.getId() == null) {
                record.setId(IdWorker.getId());
            }
            return SqlHelper.retBool(baseMapper.insert(record));
        } catch (Exception e) {
            throw new BusinessException("新增项目出错", e);
        }
    }

    @Override
    public T saveAndGet(T record) {
        try {
            if (record.getId() == null) {
                record.setId(IdWorker.getId());
            }
            if (SqlHelper.retBool(baseMapper.insert(record))) {
                return baseMapper.selectById(record.getId());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("新增项目出错", e);
        }
    }

    @Override
    public boolean update(T record) {
        try {
            return SqlHelper.retBool(baseMapper.updateById(record));
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    @Override
    public T updateAndGet(T record) {
        try {
            Long id = record.getId();
            if (SqlHelper.retBool(baseMapper.updateById(record))) {
                return baseMapper.selectById(id);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    @Override
    public boolean update(T record, UpdateWrapper<T> updateWrapper) {
        try {
            return SqlHelper.retBool(baseMapper.update(record, updateWrapper));
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    @Override
    public T updateAndGet(T record, UpdateWrapper<T> updateWrapper) {
        try {
            Long id = record.getId();
            if (SqlHelper.retBool(baseMapper.update(record, updateWrapper))) {
                return baseMapper.selectById(id);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            baseMapper.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    public void removeBatch(List<Long> ids) {
        try {
            baseMapper.deleteBatchIds(ids);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

}
