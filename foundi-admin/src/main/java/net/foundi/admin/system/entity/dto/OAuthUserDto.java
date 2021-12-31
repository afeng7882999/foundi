/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.foundi.admin.system.entity.domain.OAuthUserDo;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* OAuth用户DTO
*
* @author Afeng
*/
@Data
public class OAuthUserDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 账号 */
    @NotBlank(message = "账号不能为空")
    private String account;

    /** 昵称 */
    private String nickName;

    /** 头像 */
    private String avatar;

    /** 性别（字典：Gender） */
    private String genderDict;

    /** OpenId */
    private String openId;

    /** 认证类型（字典：SysOAuthType） */
    @JsonProperty("oAuthTypeDict")
    private String oAuthTypeDict;

    /** 关联user */
    private Long userId;

    /** 创建时间 */
    @JsonTimestamp
    private LocalDateTime createAt;

    /** 修改时间 */
    @JsonTimestamp
    private LocalDateTime updateAt;


    public static OAuthUserDo toDo(OAuthUserDto dto) {
        if (dto == null) {
            return null;
        } else {
            OAuthUserDo aDo = new OAuthUserDo();
            aDo.setId(dto.getId());
            aDo.setAccount(dto.getAccount());
            aDo.setNickName(dto.getNickName());
            aDo.setAvatar(dto.getAvatar());
            aDo.setGenderDict(dto.getGenderDict());
            aDo.setOpenId(dto.getOpenId());
            aDo.setOAuthTypeDict(dto.getOAuthTypeDict());
            aDo.setUserId(dto.getUserId());
            aDo.setCreateAt(dto.getCreateAt());
            aDo.setUpdateAt(dto.getUpdateAt());
            return aDo;
        }
    }

    public static List<OAuthUserDo> toDo(List<OAuthUserDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(OAuthUserDto::toDo).collect(Collectors.toList());
        }
    }

    public static OAuthUserDto fromDo(OAuthUserDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            OAuthUserDto dto = new OAuthUserDto();
            dto.setId(aDo.getId());
            dto.setAccount(aDo.getAccount());
            dto.setNickName(aDo.getNickName());
            dto.setAvatar(aDo.getAvatar());
            dto.setGenderDict(aDo.getGenderDict());
            dto.setOpenId(aDo.getOpenId());
            dto.setOAuthTypeDict(aDo.getOAuthTypeDict());
            dto.setUserId(aDo.getUserId());
            dto.setCreateAt(aDo.getCreateAt());
            dto.setUpdateAt(aDo.getUpdateAt());
            return dto;
        }
    }

    public static List<OAuthUserDto> fromDo(List<OAuthUserDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(OAuthUserDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<OAuthUserDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OAuthUserDo oauthUser : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("账号", oauthUser.getAccount());
            map.put("昵称", oauthUser.getNickName());
            map.put("头像", oauthUser.getAvatar());
            map.put("性别", oauthUser.getGenderDict());
            map.put("OpenId", oauthUser.getOpenId());
            map.put("OAuth2认证类型", oauthUser.getOAuthTypeDict());
            map.put("关联user", oauthUser.getUserId());
            map.put("创建时间", oauthUser.getCreateAt());
            map.put("修改时间", oauthUser.getUpdateAt());
            list.add(map);
        }
        return list;
    }

}