/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.framework.entity.validation.PasswordValid;
import net.foundi.framework.entity.validation.UsernameValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 注册DTO
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Data
public class RegisterDto {

    @NotBlank(message = "用户名不能为空")
    @UsernameValid
    private String username;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱地址格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    @PasswordValid(message = "密码格式不正确")
    private String password;
}
