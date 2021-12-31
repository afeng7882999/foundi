/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.exception;

import net.foundi.common.exception.FoundiException;

/**
 * 登录达到限定次数异常
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class LoginRetryLimitException extends FoundiException {

    private static final long serialVersionUID = -3937649429508131239L;

    public LoginRetryLimitException() {}

    public LoginRetryLimitException(String message) {
        super(message);
    }

    public LoginRetryLimitException(Throwable cause) {
        super(cause);
    }

    public LoginRetryLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
