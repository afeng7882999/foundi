/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import net.foundi.admin.system.dao.OperLogDao;
import net.foundi.admin.system.entity.domain.OperLogDo;
import net.foundi.admin.system.service.OperLogService;
import net.foundi.framework.entity.domain.Log;
import net.foundi.framework.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
* 系统操作日志Service
*
* @author Afeng
*/
@Service
public class OperLogServiceImpl extends BaseServiceImpl<OperLogDao, OperLogDo> implements OperLogService {

    @Override
    public void logOperation(Log log) {
        OperLogDo aDo = new OperLogDo();
        aDo.setTitle(log.getTitle());
        aDo.setMethod(log.getMethod());
        aDo.setRequestMethod(log.getRequestMethod());
        aDo.setOperUserId(log.getOperUserId());
        aDo.setOperUserName(log.getOperUserName());
        aDo.setOperUserRoles(log.getOperUserRoles());
        aDo.setGroupName(log.getGroupName());
        aDo.setOperUrl(log.getOperUrl());
        aDo.setOperIp(log.getOperIp());
        aDo.setOperLocation(log.getOperLocation());
        aDo.setOperParam(log.getOperParam());
        aDo.setJsonResult(log.getJsonResult());
        aDo.setStatusDict(log.getStatus());
        aDo.setErrorMsg(log.getErrorMsg());
        aDo.setOperTime(log.getOperTime());
        this.save(aDo);
    }
}