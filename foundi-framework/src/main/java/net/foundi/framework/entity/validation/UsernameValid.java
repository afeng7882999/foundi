/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 用户名验证
 *
 * @author Afeng (afeng7882999@163.com)
*/
@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameValid {

    String message() default "请输入长度5-20，汉字、字母开头的用户名(可包含字母, 数字, \"_\", \".\")";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

}
