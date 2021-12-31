/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.exception;

import net.foundi.common.exception.FoundiException;

/**
 * 加密Filter异常
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class EncryptFilterException extends FoundiException {

    private static final long serialVersionUID = 7895026963628640902L;

    public EncryptFilterException() {}

    public EncryptFilterException(String message) {
        super(message);
    }

    public EncryptFilterException(Throwable cause) {
        super(cause);
    }

    public EncryptFilterException(String message, Throwable cause) {
        super(message, cause);
    }
}
