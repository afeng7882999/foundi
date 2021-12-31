/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;

import java.time.LocalDateTime;

/**
 * 系统访问日志DO
 *
 * @author Afeng
 */
@TableName("sys_login_log")
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginLogDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 类型（字典：SysLoginLogType，0：login，1：logout） */
    private String typeDict;

    /** 用户账号 */
    private String userName;

    /** 登录IP地址 */
    private String ip;

    /** 登录地点 */
    private String location;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 登录方式（字典：SysAuthcType，0：账密，1：手机验证码，2：微信，3：微博，4：QQ） */
    private String authcTypeDict;

    /** 登录状态（字典：SysLoginLogStatus，0：成功，1：失败） */
    private String statusDict;

    /** 提示消息 */
    private String message;

    /** 登录、登出时间 */
    private LocalDateTime operTime;

    public static final String ColId = "id";
    public static final String ColTypeDict = "type_dict";
    public static final String ColUserName = "user_name";
    public static final String ColIp = "ip";
    public static final String ColLocation = "location";
    public static final String ColBrowser = "browser";
    public static final String ColOs = "os";
    public static final String ColAuthcTypeDict = "authc_type_dict";
    public static final String ColStatusDict = "status_dict";
    public static final String ColMessage = "message";
    public static final String ColOperTime = "oper_time";

}