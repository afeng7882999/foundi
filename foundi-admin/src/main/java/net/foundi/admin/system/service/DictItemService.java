/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.DictItemDo;
import net.foundi.framework.service.BaseService;

import java.util.List;

/**
 * 系统字典项Service
 *
 * @author Afeng
 */
public interface DictItemService extends BaseService<DictItemDo> {

    /**
     * 获取特定字典的字典项列表
     *
     * @param dictName 字典名
     * @return 字典项列表
     */
    List<DictItemDo> listByDictName(String dictName);

    /**
     * 通过名称获取字典项
     *
     * @param dictName 字典名
     * @param key      字典项名称
     * @return DictItemDo
     */
    DictItemDo getByKey(String dictName, String key);

    /**
     * 通过制获取字典项
     *
     * @param dictName 字典名
     * @param value    字典项值
     * @return DictItemDo
     */
    DictItemDo getByValue(String dictName, String value);

}