/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.framework.entity.validation.PasswordValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 密码DTO
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Data
public class PasswordDto {

    @NotNull(message = "id不能为空")
    private Long id;

    @NotBlank(message = "旧密码不能为空")
    private String oldPass;

    @NotBlank(message = "新密码不能为空")
    @PasswordValid
    private String password;
}
