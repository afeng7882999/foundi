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
 * 密码验证
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

    //校验密码：只能输入6-20个字母、数字、下划线
    public static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(\\w){6,20}$"
    );

    @Override
    public void initialize(PasswordValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }
        Matcher m = PASSWORD_PATTERN.matcher(value);
        return m.matches();
    }
}
