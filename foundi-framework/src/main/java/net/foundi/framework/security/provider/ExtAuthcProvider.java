/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.provider;

import net.foundi.common.utils.lang.CollectionUtils;
import net.foundi.framework.security.enums.AccountStatus;
import net.foundi.framework.security.exception.SecureException;
import net.foundi.framework.security.model.ExternalAuthcToken;
import net.foundi.framework.security.model.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 外部认证Provider
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class ExtAuthcProvider implements AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(ExtAuthcProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserContext user = (UserContext) authentication.getPrincipal();
        if (user == null || CollectionUtils.isEmpty(user.getAuthorities())) {
            return null;
        }
        if (AccountStatus.LOCKED.key().equals(user.getStatus())) {
            log.info("登录用户已被停用");
            throw new SecureException("对不起，您的账号已停用");
        }
        // 返回一个认证通过的ExternalAuthcToken
        return new ExternalAuthcToken(user, user.getAuthcType(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 此provider处理ExternalAuthcToken
        return ExternalAuthcToken.class.isAssignableFrom(authentication);
    }
}
