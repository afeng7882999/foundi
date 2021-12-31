/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.exception;

import net.foundi.common.exception.FoundiException;

/**
 * 接口受限异常
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class ApiLimitException extends FoundiException {

    private static final long serialVersionUID = 5713605475748015457L;

    public ApiLimitException() {}

    public ApiLimitException(String message) {
        super(message);
    }

    public ApiLimitException(Throwable cause) {
        super(cause);
    }

    public ApiLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
