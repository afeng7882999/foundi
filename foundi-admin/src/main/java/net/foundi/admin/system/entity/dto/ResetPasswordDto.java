/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.framework.entity.validation.PasswordValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 重置密码DTO
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Data
public class ResetPasswordDto {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "验证码不能为空")
    private String code;

    @NotNull(message = "验证类型不能为空")
    @Pattern(regexp = "^(email|mobile)$", message = "验证类型参数不正确")
    private String type;

    @NotBlank(message = "新密码不能为空")
    @PasswordValid
    private String password;
}
