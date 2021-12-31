/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.model;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class AuthcRequest
{
    // 用户名
    @NotBlank
    private String username;

    // 用户密码
    @NotBlank
    private String password;

    // 终端标识
    @NotBlank
    private String appName;

    // 验证码
    private String code;

    // 唯一标识
    private String uuid = "";

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
