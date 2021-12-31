/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.exception;

import net.foundi.common.exception.FoundiException;

/**
 * 重复提交异常
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class ResubmitException extends FoundiException {

    private static final long serialVersionUID = -3382856580299937779L;

    public ResubmitException() {}

    public ResubmitException(String message) {
        super(message);
    }

    public ResubmitException(Throwable cause) {
        super(cause);
    }

    public ResubmitException(String message, Throwable cause) {
        super(message, cause);
    }
}
