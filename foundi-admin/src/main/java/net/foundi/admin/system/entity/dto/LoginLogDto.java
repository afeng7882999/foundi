/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.LoginLogDo;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;
import org.apache.commons.collections4.map.HashedMap;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 系统访问日志DTO
*
* @author Afeng
*/
@Data
public class LoginLogDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
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
    @JsonTimestamp
    private LocalDateTime operTime;

    @JsonTimestamp
    private Map<String, LocalDateTime> test;

    public static LoginLogDo toDo(LoginLogDto dto) {
        if (dto == null) {
            return null;
        } else {
            LoginLogDo aDo = new LoginLogDo();
            aDo.setId(dto.getId());
            aDo.setTypeDict(dto.getTypeDict());
            aDo.setUserName(dto.getUserName());
            aDo.setIp(dto.getIp());
            aDo.setLocation(dto.getLocation());
            aDo.setBrowser(dto.getBrowser());
            aDo.setOs(dto.getOs());
            aDo.setAuthcTypeDict(dto.getAuthcTypeDict());
            aDo.setStatusDict(dto.getStatusDict());
            aDo.setMessage(dto.getMessage());
            aDo.setOperTime(dto.getOperTime());
            return aDo;
        }
    }

    public static List<LoginLogDo> toDo(List<LoginLogDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(LoginLogDto::toDo).collect(Collectors.toList());
        }
    }

    public static LoginLogDto fromDo(LoginLogDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            LoginLogDto dto = new LoginLogDto();
            dto.setId(aDo.getId());
            dto.setTypeDict(aDo.getTypeDict());
            dto.setUserName(aDo.getUserName());
            dto.setIp(aDo.getIp());
            dto.setLocation(aDo.getLocation());
            dto.setBrowser(aDo.getBrowser());
            dto.setOs(aDo.getOs());
            dto.setAuthcTypeDict(aDo.getAuthcTypeDict());
            dto.setStatusDict(aDo.getStatusDict());
            dto.setMessage(aDo.getMessage());
            dto.setOperTime(aDo.getOperTime());

            Map<String, LocalDateTime> test = new HashedMap<>();
            test.put("test1", LocalDateTime.now());
            test.put("test2", DateUtils.dateTime("2021-03-02 12:00:00"));
            dto.test = test;

            return dto;
        }
    }

    public static List<LoginLogDto> fromDo(List<LoginLogDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(LoginLogDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<LoginLogDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (LoginLogDo loginLog : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("类型（字典：SysLoginLogStatus，0：login，1：logout）", loginLog.getTypeDict());
            map.put("用户账号", loginLog.getUserName());
            map.put("登录IP地址", loginLog.getIp());
            map.put("登录地点", loginLog.getLocation());
            map.put("浏览器类型", loginLog.getBrowser());
            map.put("操作系统", loginLog.getOs());
            map.put("登录方式（字典：SysAuthcType，0：账密，1：手机验证码，2：微信，3：微博，4：QQ）", loginLog.getAuthcTypeDict());
            map.put("登录状态（字典：SysLoginLogStatus，0：成功，1：失败）", loginLog.getStatusDict());
            map.put("提示消息", loginLog.getMessage());
            map.put("登录、登出时间", loginLog.getOperTime());
            list.add(map);
        }
        return list;
    }

}