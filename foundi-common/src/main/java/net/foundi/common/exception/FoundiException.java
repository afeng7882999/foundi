/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.exception;

/**
 * FoundiException
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class FoundiException extends RuntimeException {

    private static final long serialVersionUID = 5041280831931501055L;

    public FoundiException() {}

    public FoundiException(String message) {
        super(message);
    }

    public FoundiException(Throwable cause) {
        super(cause);
    }

    public FoundiException(String message, Throwable cause) {
        super(message, cause);
    }

}



