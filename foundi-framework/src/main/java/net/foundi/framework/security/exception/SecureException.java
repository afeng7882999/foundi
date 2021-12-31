/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.exception;

import net.foundi.common.exception.FoundiException;

/**
 * Spring Security 异常
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class SecureException extends FoundiException {

    private static final long serialVersionUID = -6943275235377180743L;

    public SecureException() {}

    public SecureException(String message) {
        super(message);
    }

    public SecureException(Throwable cause) {
        super(cause);
    }

    public SecureException(String message, Throwable cause) {
        super(message, cause);
    }
}
