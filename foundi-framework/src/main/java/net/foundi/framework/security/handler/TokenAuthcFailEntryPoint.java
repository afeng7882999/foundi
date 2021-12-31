/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.handler;

import net.foundi.common.utils.web.WebUtils;
import net.foundi.framework.web.WebReturn;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 认证失败处理类，返回401
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class TokenAuthcFailEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
    {
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
        WebUtils.returnObj(response, WebReturn.fail(HttpStatus.UNAUTHORIZED).message(msg));
    }
}
