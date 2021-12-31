/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户名验证
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class UsernameValidator implements ConstraintValidator<UsernameValid, String> {


    // 校验登录名：只能输入5-20个以字母开头、可带数字、“_”、“.”的字串
    public static final Pattern USERNAME_PATTERN = Pattern.compile(
            "^[a-zA-Z\\u4e00-\\u9fa5]([a-zA-Z0-9\\u4e00-\\u9fa5]|[._]){4,19}$"
    );

    @Override
    public void initialize(UsernameValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }
        Matcher m = USERNAME_PATTERN.matcher(value);
        return m.matches();
    }
}
