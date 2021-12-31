/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.validation;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.exception.BusinessException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证文件名
 *
 * Match a valid filename (windows, only considering '.' of linux)
 * <br>
 *  ^                                        Anchor to start of string.<br>
 *  (?!.{256,})                              Not 256 chars or more.<br>
 *  (?!                                      Assert filename is not:<br>
 *    (?:CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])  CON, PRN, AUX, NUL, COM*, LPT*...<br>
 *    (?:\\.[^.]*)?$                         followed by optional extension<br>
 *  )                                        and end of string.<br>
 *  (?![\\.|\\ ].*)                          The first character cannot be a space or dot.<br>
 *  [^<>:\"/\\\\|?*\\x00-\\x1F]*             Zero or more valid filename chars.<br>
 *  [^<>:\"/\\\\|?*\\x00-\\x1F\\ \\.]        Last char is not a space or dot.<br>
 *  $                                        Anchor to end of string.<br>
 * <br>
 * @author Afeng (afeng7882999@163.com)
*/
public class FileNameValidator implements ConstraintValidator<FileNameValid, String> {

    public static final Pattern FILE_NAME_PATTERN = Pattern.compile(
                            "^(?!.{256,})(?!(?:CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(?:\\.[^.]*)?$)(?![\\.|\\ ].*)[^<>:\"/\\\\|?*\\x00-\\x1F]*[^<>:\"/\\\\|?*\\x00-\\x1F\\ \\.]$",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private boolean decodeUrl;

    @Override
    public void initialize(FileNameValid constraintAnnotation) {
        this.decodeUrl = constraintAnnotation.decodeUrl();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (this.decodeUrl) {
            try {
                value = URLDecoder.decode(value, FoundiConst.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                throw new BusinessException("参数无效");
            }
        }
        if (value == null || value.length() == 0) {
            return true;
        }
        Matcher m = FILE_NAME_PATTERN.matcher(value);
        return m.matches();
    }

}
