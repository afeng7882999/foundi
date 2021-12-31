/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import net.foundi.admin.system.dao.DictItemDao;
import net.foundi.admin.system.entity.domain.DictItemDo;
import net.foundi.admin.system.service.DictItemService;
import net.foundi.common.utils.spring.SpringAppUtils;
import net.foundi.framework.service.BaseServiceImpl;

/**
* 系统字典条目Service
*
* @author Afeng
*/
@Service
public class DictItemServiceImpl extends BaseServiceImpl<DictItemDao, DictItemDo> implements DictItemService {

    @Override
    @Cacheable(value = "dictItems", key = "#dictName")
    public List<DictItemDo> listByDictName(String dictName) {
        return baseMapper.selectByDictName(dictName);
    }

    @Override
    public DictItemDo getByKey(String dictName, String key) {
        return SpringAppUtils.getBean(DictItemService.class).listByDictName(dictName)
                .stream().filter(d -> d.getItemValue().equals(key)).findFirst().orElse(null);
    }

    @Override
    public DictItemDo getByValue(String dictName, String value) {
        return SpringAppUtils.getBean(DictItemService.class).listByDictName(dictName)
                .stream().filter(d -> d.getItemValue().equals(value)).findFirst().orElse(null);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public boolean save(DictItemDo record) {
        return super.save(record);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public boolean update(DictItemDo record) {
        return super.update(record);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public DictItemDo saveAndGet(DictItemDo record) {
        return super.saveAndGet(record);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public DictItemDo updateAndGet(DictItemDo record) {
        return super.updateAndGet(record);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public void remove(Long id) {
        super.remove(id);
    }

    @Override
    @CacheEvict(value = {"dictItems"}, allEntries = true)
    public void removeBatch(List<Long> ids) {
        super.removeBatch(ids);
    }

}