/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.exception;

/**
 * 业务异常基类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class BusinessException extends FoundiException {

    private static final long serialVersionUID = 1709092521882506933L;

    // 额外数据
    private Object data;

    public BusinessException() {}

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public BusinessException(String message, Object data, Throwable cause) {
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
