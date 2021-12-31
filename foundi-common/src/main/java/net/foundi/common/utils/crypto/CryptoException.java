/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.crypto;

/**
 * CryptoException
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class CryptoException extends RuntimeException {

    private static final long serialVersionUID = -4108944016921889002L;

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(Throwable cause) {
        super(cause);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }

}

