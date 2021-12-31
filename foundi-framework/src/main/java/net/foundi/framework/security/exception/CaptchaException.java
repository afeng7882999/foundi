/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.exception;

/**
 * 验证码异常
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class CaptchaException extends SecureException {

    private static final long serialVersionUID = -4716092310762388171L;

    public CaptchaException() {}

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }
}
