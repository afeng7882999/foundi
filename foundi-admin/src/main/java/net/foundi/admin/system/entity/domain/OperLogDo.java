/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;

/**
 * 系统操作日志DO
 *
 * @author Afeng
 */
@TableName("sys_oper_log")
@Data
@EqualsAndHashCode(callSuper = false)
public class OperLogDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 模块标题 */
    private String title;

    /** 方法名称 */
    private String method;

    /** 请求方式 */
    private String requestMethod;

    /** 操作人员ID */
    private Long operUserId;

    /** 操作人员账号名 */
    private String operUserName;

    /** 操作人员角色 */
    private String operUserRoles;

    /** 用户组名称 */
    private String groupName;

    /** 请求URL */
    private String operUrl;

    /** 主机地址 */
    private String operIp;

    /** 操作地点 */
    private String operLocation;

    /** 请求参数 */
    private String operParam;

    /** 返回参数 */
    private String jsonResult;

    /** 操作状态（字典：SysOperLogStatus，0：正常，1：异常） */
    private String statusDict;

    /** 错误消息 */
    private String errorMsg;

    /** 操作时间 */
    private LocalDateTime operTime;

}