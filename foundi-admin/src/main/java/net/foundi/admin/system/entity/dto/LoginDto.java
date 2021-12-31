/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录DTO
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Data
public class LoginDto {

    /** 用户名 */
    @NotBlank
    private String username;

    /** 用户密码 */
    @NotBlank
    private String password;

    /** 终端标识 */
    private String appName;

    /** 验证码ID */
    private String uuid = "";
}
