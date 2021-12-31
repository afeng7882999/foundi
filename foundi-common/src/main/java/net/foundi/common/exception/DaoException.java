/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.exception;

/**
 * DaoException
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class DaoException extends FoundiException {

    private static final long serialVersionUID = 6624254842711229482L;

    public DaoException() {}

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

}



