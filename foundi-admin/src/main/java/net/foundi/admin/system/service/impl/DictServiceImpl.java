/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.foundi.admin.system.dao.DictDao;
import net.foundi.admin.system.dao.DictItemDao;
import net.foundi.admin.system.entity.domain.DictDo;
import net.foundi.admin.system.entity.domain.DictItemDo;
import net.foundi.admin.system.service.DictService;
import net.foundi.common.exception.BusinessException;
import net.foundi.framework.service.BaseServiceImpl;

/**
* 系统字典Service
*
* @author Afeng
*/
@Service
public class DictServiceImpl extends BaseServiceImpl<DictDao, DictDo> implements DictService {

    private final DictItemDao dictItemMapper;

    public DictServiceImpl(DictItemDao dictItemDao) {
        this.dictItemMapper = dictItemDao;
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public boolean save(DictDo record) {
        return super.save(record);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public boolean update(DictDo record) {
        return super.update(record);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public DictDo saveAndGet(DictDo record) {
        return super.saveAndGet(record);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public DictDo updateAndGet(DictDo record) {
        return super.updateAndGet(record);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        try {
            dictItemMapper.delete(new QueryWrapper<DictItemDo>().lambda().eq(DictItemDo::getDictId, id));
            baseMapper.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(List<Long> ids) {
        try {
            dictItemMapper.delete(new QueryWrapper<DictItemDo>().lambda().in(DictItemDo::getDictId, ids));
            baseMapper.deleteBatchIds(ids);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

}