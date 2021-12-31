/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户DO
 *
 * @author Afeng
 */
@TableName("sys_user")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDo extends User {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 手机号 */
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /** 创建者id */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /** 修改者id */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateBy;

    /** 修改时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;

    @TableField(exist = false)
    private List<RoleDo> roleList;

}