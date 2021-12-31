/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.OperLogDo;
import net.foundi.framework.entity.domain.Log;
import net.foundi.framework.service.BaseService;

/**
 * 系统操作日志Service
 *
 * @author Afeng
 */
public interface OperLogService extends BaseService<OperLogDo> {

    /**
     * 记录操作日志
     *
     * @param log LogDo对象
     */
    void logOperation(Log log);

}