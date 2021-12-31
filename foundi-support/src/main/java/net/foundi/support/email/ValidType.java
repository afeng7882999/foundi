/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.email;

/**
 * Email验证类型枚举
 *
 * @author Afeng (afeng7882999@163.com)
 */
public enum ValidType {
    REGISTER("注册"),
    BINDING_PHONE("绑定手机"),
    BINDING_EMAIL("绑定邮箱"),
    FORGET_PASSWORD("忘记密码"),
    CHANGE_MOBILE("更换手机"),
    CHANGE_EMAIL("更换邮箱"),
    PAYMENT_PASSWORD("设置支付密码");

    private String message;

    ValidType(String message) {
        this.message = message;
    }

    String message() {
        return this.message;
    }
}
