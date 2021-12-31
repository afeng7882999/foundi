/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.model;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import net.foundi.framework.security.enums.AuthcType;

/**
 * 外部认证Token，使用短信、微信等认证
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class ExternalAuthcToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = -8869074715371937031L;

    private final UserContext principal;
    private AuthcType authcType;

    public ExternalAuthcToken(UserContext principal, AuthcType authcType) {
        super(null);
        this.principal = principal;
        this.authcType = authcType;
        setAuthenticated(false);
    }

    public ExternalAuthcToken(UserContext principal, AuthcType authcType,
                              Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.authcType = authcType;
        this.setDetails(principal);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.authcType;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void eraseCredentials() {
        this.authcType = null;
    }
}
