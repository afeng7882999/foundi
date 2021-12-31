/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户DTO
 *
 * @author Afeng
 */
@Data
public class UserDto implements Dto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
    private Long id;

    /** 用户名 */
    @NotBlank(message = "用户名不能为空")
    @UsernameValid
    private String username;

    /** 密码 */
    @NotBlank(message = "密码不能为空", groups = {AddGroup.class})
    @PasswordValid(groups = {AddGroup.class})
    private String password;

    /** 手机号 */
    @PhoneValid
    private String mobile;

    /** 用户组 */
    private Long groupId;

    /** 姓名 */
    private String name;

    /** 头像 */
    private String avatar;

    /** 状态（字典：SysUserStatus，0：正常，1：禁用） */
    private String statusDict;

    /** 邮箱 */
    @Email(message = "邮箱地址格式不正确")
    private String email;

    /** 性别（字典：Gender，0：未知，1：男，2：女） */
    private String genderDict;

    /** 出生日期 */
    private LocalDate birthday;

    /** 住址 */
    private String address;

    /** 省份 */
    private String province;

    /** 所在城市 */
    private String city;

    /** 所在地区 */
    private String district;

    /** 创建时间 */
    @JsonTimestamp
    private LocalDateTime createAt;

    /** 创建者id */
    private Long createBy;

    /** 修改者id */
    private Long updateBy;

    /** 修改时间 */
    @JsonTimestamp
    private LocalDateTime updateAt;

    /** 角色 */
    private List<Long> roleIdList;

    /** 是否设置密码 */
    private Boolean hasPassword;

    /** 绑定的OAUth2用户 */
    @JsonProperty("oAuthUserList")
    private List<OAuthUserDto> oAuthUserList;


    public static UserDo toDo(UserDto dto) {
        if (dto == null) {
            return null;
        } else {
            UserDo aDo = new UserDo();
            aDo.setId(dto.getId());
            aDo.setUsername(dto.getUsername());
            aDo.setPassword(dto.getPassword());
            aDo.setMobile(dto.getMobile());
            aDo.setGroupId(dto.getGroupId());
            aDo.setName(dto.getName());
            aDo.setAvatar(dto.getAvatar());
            aDo.setStatusDict(dto.getStatusDict());
            aDo.setEmail(dto.getEmail());
            aDo.setGenderDict(dto.getGenderDict());
            aDo.setBirthday(dto.getBirthday());
            aDo.setAddress(dto.getAddress());
            aDo.setProvince(dto.getProvince());
            aDo.setCity(dto.getCity());
            aDo.setDistrict(dto.getDistrict());
            aDo.setCreateAt(dto.getCreateAt());
            aDo.setCreateBy(dto.getCreateBy());
            aDo.setUpdateBy(dto.getUpdateBy());
            aDo.setUpdateAt(dto.getUpdateAt());
            aDo.setRoleList(dto.getRoleIdList()
                    .stream()
                    .map(i -> {
                        RoleDo roleDo = new RoleDo();
                        roleDo.setId(i);
                        return roleDo;
                    })
                    .collect(Collectors.toList()));
            return aDo;
        }
    }

    public static List<UserDo> toDo(List<UserDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(UserDto::toDo).collect(Collectors.toList());
        }
    }

    public static UserDto fromDo(UserDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            UserDto dto = new UserDto();
            dto.setId(aDo.getId());
            dto.setUsername(aDo.getUsername());
            dto.setMobile(aDo.getMobile());
            dto.setGroupId(aDo.getGroupId());
            dto.setName(aDo.getName());
            dto.setAvatar(aDo.getAvatar());
            dto.setStatusDict(aDo.getStatusDict());
            dto.setEmail(aDo.getEmail());
            dto.setGenderDict(aDo.getGenderDict());
            dto.setBirthday(aDo.getBirthday());
            dto.setAddress(aDo.getAddress());
            dto.setProvince(aDo.getProvince());
            dto.setCity(aDo.getCity());
            dto.setDistrict(aDo.getDistrict());
            dto.setCreateAt(aDo.getCreateAt());
            dto.setCreateBy(aDo.getCreateBy());
            dto.setUpdateBy(aDo.getUpdateBy());
            dto.setUpdateAt(aDo.getUpdateAt());
            dto.setRoleIdList(aDo.getRoleList().stream().map(RoleDo::getId).collect(Collectors.toList()));
            dto.setHasPassword(StringUtils.hasValue(aDo.getPassword()));
            return dto;
        }
    }

    public static List<UserDto> fromDo(List<UserDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(UserDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<UserDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDo user : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", user.getUsername());
            // map.put("密码", user.getPassword());
            map.put("手机号", user.getMobile());
            map.put("用户组", user.getGroupId());
            map.put("姓名", user.getName());
            map.put("头像", user.getAvatar());
            map.put("状态（字典：SysUserStatus，0：正常，1：禁用）", user.getStatusDict());
            map.put("邮箱", user.getEmail());
            map.put("性别（字典：Gender，0：未知，1：男，2：女）", user.getGenderDict());
            map.put("出生日期", user.getBirthday());
            map.put("住址", user.getAddress());
            map.put("省份", user.getProvince());
            map.put("所在城市", user.getCity());
            map.put("所在地区", user.getDistrict());
            map.put("创建时间", user.getCreateAt());
            map.put("创建者id", user.getCreateBy());
            map.put("修改者id", user.getUpdateBy());
            map.put("修改时间", user.getUpdateAt());
            list.add(map);
        }
        return list;
    }

}