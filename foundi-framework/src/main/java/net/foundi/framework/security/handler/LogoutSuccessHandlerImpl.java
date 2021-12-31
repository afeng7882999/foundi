/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.handler;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.web.WebUtils;
import net.foundi.framework.log.AsyncLogFactory;
import net.foundi.framework.security.enums.LoginType;
import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.security.service.TokenService;
import net.foundi.framework.thread.AsyncManager;
import net.foundi.framework.web.WebReturn;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户退出处理类
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final TokenService tokenService;

    public LogoutSuccessHandlerImpl(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * 退出处理
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserContext ctx = tokenService.getLoginUser(request);
        if (ctx != null) {
            String userName = ctx.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(ctx.getToken());
            // 记录用户退出日志
            AsyncManager.getInstance().execute(AsyncLogFactory.logLogin(userName, "退出成功", null,
                    LoginType.LOGOUT.key(), FoundiConst.SUCCESS, DateUtils.now()));
        }
        WebUtils.returnObj(response, WebReturn.ok().message("退出成功"));
    }
}
