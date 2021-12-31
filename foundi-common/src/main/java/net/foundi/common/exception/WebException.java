/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.exception;

/**
 * Web 异常基类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class WebException extends FoundiException {

    private static final long serialVersionUID = 3720637885807100766L;

    // 额外数据
    private Object data;

    public WebException() {}

    public WebException(String message) {
        super(message);
    }

    public WebException(Throwable cause) {
        super(cause);
    }

    public WebException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public WebException(String message, Object data, Throwable cause) {
        super(message, cause);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
