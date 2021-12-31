/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.exception;

import net.foundi.common.exception.FoundiException;

/**
 * Xss Filter 异常
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class XssFilterException extends FoundiException {

    private static final long serialVersionUID = -3911582002949968971L;

    public XssFilterException() {}

    public XssFilterException(String message) {
        super(message);
    }

    public XssFilterException(Throwable cause) {
        super(cause);
    }

    public XssFilterException(String message, Throwable cause) {
        super(message, cause);
    }
}
