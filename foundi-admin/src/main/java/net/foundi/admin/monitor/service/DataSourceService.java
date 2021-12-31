/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.monitor.service;

import java.util.List;
import java.util.Map;

/**
 * 数据源监控信息（ Druid ）Service
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface DataSourceService {

    /**
     * 获取Druid的监控信息
     *
     * @param types 类型列表
     * @return Map<String, String>，类型：信息
     */
    Map<String, String> getInfoByType(List<String> types);

    /**
     * 通过ID获取FullSql信息
     *
     * @param id Sql语句的ID
     * @return String
     */
    String getFullSqlById(String id);

    /**
     * 通过ID获取WebUri详细信息
     *
     * @param id Uri的ID
     * @return String
     */
    String getWebUriDetailById(String id);
}
