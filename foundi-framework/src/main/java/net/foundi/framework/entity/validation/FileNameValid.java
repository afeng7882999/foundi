/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证文件名
 *
 * @author Afeng (afeng7882999@163.com)
*/
@Documented
@Constraint( validatedBy = FileNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileNameValid {

    String message() default "不能包含'/:*?\"<>|'或非空格的空字符, 长度不能超过255, 结尾不能为'.'";

    Class<?>[] groups() default {};

    boolean decodeUrl() default false;

    Class<? extends Payload>[] payload() default { };
}
