/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.exception;

/**
 * 未实现用户服务接口异常
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class NoImplException extends FoundiException {

    private static final long serialVersionUID = 3724365087081700563L;

    public NoImplException(String bean) {
        super("必须实现类型为: " + bean + "的Bean");
    }
}
