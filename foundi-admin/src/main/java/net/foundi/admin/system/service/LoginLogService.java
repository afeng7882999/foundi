/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.LoginLogDo;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.service.BaseService;

import java.time.LocalDateTime;

/**
 * 系统访问日志Service
 *
 * @author Afeng
 */
public interface LoginLogService extends BaseService<LoginLogDo> {

    /**
     * 记录登录、登出日志
     *
     * @param username 用户名
     * @param ip       IP
     * @param location 用户地址
     * @param browser  浏览器类型
     * @param os       OS类型
     * @param message  消息
     * @param type     登录、登出
     * @param status   成功、失败
     * @param operTime 登录（登出）时间
     */
    void logLogin(String username, String ip, String location, String browser, String os, String message,
                  AuthcType authcType, String type, String status, LocalDateTime operTime);

}