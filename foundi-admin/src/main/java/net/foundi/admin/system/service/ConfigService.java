/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.ConfigDo;
import net.foundi.framework.service.BaseService;

/**
 * 系统配置Service
 *
 * @author Afeng
 */
public interface ConfigService extends BaseService<ConfigDo> {

    /**
     * 根据配置键值获取配置
     *
     * @param key 配置键值
     * @return 配置值
     */
    String getEnabledByKey(String key);

    /**
     * 加载resource文件中的配置信息
     *
     * @param path resource文件路径
     * @return 配置信息
     */
    String getConfigFromResource(String path);

}