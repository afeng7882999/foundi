/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.log;

import eu.bitwalker.useragentutils.UserAgent;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpringAppUtils;
import net.foundi.common.utils.web.AddressUtils;
import net.foundi.common.utils.web.IPUtils;
import net.foundi.common.utils.web.WebUtils;
import net.foundi.framework.entity.domain.Log;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.TimerTask;

/**
 * 日志异步工厂
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class AsyncLogFactory {
    private static final Logger logger = LoggerFactory.getLogger(AsyncLogFactory.class);

    /**
     * 记录登陆信息
     *
     * @param username 用户名
     * @param message  消息
     * @param type     类型，login、logout
     * @param status   状态，成功、失败
     * @param time     操作时间
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask logLogin(String username, String message, AuthcType authcType, String type, String status,
                                     LocalDateTime time, Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(WebUtils.getRequest().getHeader("User-Agent"));
        final String ip = IPUtils.getIpAddr(WebUtils.getRequest());
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                // 打印信息到日志
                String s = StringUtils.withBlock(ip) +
                        address +
                        StringUtils.withBlock(username) +
                        StringUtils.withBlock(status) +
                        StringUtils.withBlock(message);
                logger.info(s, args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 插入数据
                SpringAppUtils.getBean(AccountService.class).logLogin(username, ip, address, browser, os, message,
                        authcType, type, status, time);
            }
        };
    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask logOperation(Log operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringAppUtils.getBean(AccountService.class).logOperation(operLog);
            }
        };
    }
}
