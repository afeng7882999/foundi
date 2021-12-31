/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import lombok.Data;
import net.foundi.admin.system.entity.domain.OperLogDo;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;

/**
* 系统操作日志DTO
*
* @author Afeng
*/
@Data
public class OperLogDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
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
    @JsonTimestamp
    private LocalDateTime operTime;


    public static OperLogDo toDo(OperLogDto dto) {
        if (dto == null) {
            return null;
        } else {
            OperLogDo aDo = new OperLogDo();
            aDo.setId(dto.getId());
            aDo.setTitle(dto.getTitle());
            aDo.setMethod(dto.getMethod());
            aDo.setRequestMethod(dto.getRequestMethod());
            aDo.setOperUserId(dto.getOperUserId());
            aDo.setOperUserName(dto.getOperUserName());
            aDo.setOperUserRoles(dto.getOperUserRoles());
            aDo.setGroupName(dto.getGroupName());
            aDo.setOperUrl(dto.getOperUrl());
            aDo.setOperIp(dto.getOperIp());
            aDo.setOperLocation(dto.getOperLocation());
            aDo.setOperParam(dto.getOperParam());
            aDo.setJsonResult(dto.getJsonResult());
            aDo.setStatusDict(dto.getStatusDict());
            aDo.setErrorMsg(dto.getErrorMsg());
            aDo.setOperTime(dto.getOperTime());
            return aDo;
        }
    }

    public static List<OperLogDo> toDo(List<OperLogDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(OperLogDto::toDo).collect(Collectors.toList());
        }
    }

    public static OperLogDto fromDo(OperLogDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            OperLogDto dto = new OperLogDto();
            dto.setId(aDo.getId());
            dto.setTitle(aDo.getTitle());
            dto.setMethod(aDo.getMethod());
            dto.setRequestMethod(aDo.getRequestMethod());
            dto.setOperUserId(aDo.getOperUserId());
            dto.setOperUserName(aDo.getOperUserName());
            dto.setOperUserRoles(aDo.getOperUserRoles());
            dto.setGroupName(aDo.getGroupName());
            dto.setOperUrl(aDo.getOperUrl());
            dto.setOperIp(aDo.getOperIp());
            dto.setOperLocation(aDo.getOperLocation());
            dto.setOperParam(aDo.getOperParam());
            dto.setJsonResult(aDo.getJsonResult());
            dto.setStatusDict(aDo.getStatusDict());
            dto.setErrorMsg(aDo.getErrorMsg());
            dto.setOperTime(aDo.getOperTime());
            return dto;
        }
    }

    public static List<OperLogDto> fromDo(List<OperLogDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(OperLogDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<OperLogDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OperLogDo operLog : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("模块标题", operLog.getTitle());
            map.put("方法名称", operLog.getMethod());
            map.put("请求方式", operLog.getRequestMethod());
            map.put("操作人员ID", operLog.getOperUserId());
            map.put("操作人员账号名", operLog.getOperUserName());
            map.put("操作人员角色", operLog.getOperUserRoles());
            map.put("用户组名称", operLog.getGroupName());
            map.put("请求URL", operLog.getOperUrl());
            map.put("主机地址", operLog.getOperIp());
            map.put("操作地点", operLog.getOperLocation());
            map.put("请求参数", operLog.getOperParam());
            map.put("返回参数", operLog.getJsonResult());
            map.put("操作状态（字典：SysOperLogStatus，0：正常，1：异常）", operLog.getStatusDict());
            map.put("错误消息", operLog.getErrorMsg());
            map.put("操作时间", operLog.getOperTime());
            list.add(map);
        }
        return list;
    }

}