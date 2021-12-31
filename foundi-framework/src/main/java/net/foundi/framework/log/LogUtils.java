/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.log;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.web.IPUtils;
import net.foundi.common.utils.web.WebUtils;
import net.foundi.framework.entity.domain.Log;
import net.foundi.framework.entity.domain.Role;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.thread.AsyncManager;

import java.util.stream.Collectors;

/**
 * 日志服务工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class LogUtils {

    /**
     * 记录操作日志
     *
     * @param title  日志标题
     * @param method 方法名
     * @param params 方法参数
     * @param result 操作结果
     * @param e      异常
     * @return Log
     */
    public static Log logOperation(String title, String method, String params, String result, Throwable e) {
        Log log = new Log();

        // Rest资源
        log.setOperTime(DateUtils.now());
        log.setOperIp(IPUtils.getIpAddr(WebUtils.getRequest()));
        log.setOperUrl(WebUtils.getRequest().getRequestURI());
        log.setRequestMethod(WebUtils.getRequest().getMethod());
        // 方法
        log.setMethod(method);
        log.setJsonResult(result);
        log.setOperParam(params);
        // 服务
        log.setTitle(title);
        log.setStatus(FoundiConst.SUCCESS);
        // 用户
        SecurityUtils.currentUser().ifPresent(u -> {
            log.setOperUserId(u.getId());
            log.setOperUserName(u.getUsername());
            log.setOperUserRoles(u.getRoles().stream().map(Role::getName).collect(Collectors.joining(",")));
        });
        // 异常
        if (e != null) {
            log.setStatus(FoundiConst.FAIL);
            log.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
        }
        // 保存数据库
        AsyncManager.getInstance().execute(AsyncLogFactory.logOperation(log));

        return log;
    }
}
