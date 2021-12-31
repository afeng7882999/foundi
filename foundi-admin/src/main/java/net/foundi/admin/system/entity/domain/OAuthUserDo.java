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
import net.foundi.framework.entity.domain.Do;

import java.time.LocalDateTime;

/**
 * OAuth用户DO
 *
 * @author Afeng
 */
@TableName("sys_oauth_user")
@Data
@EqualsAndHashCode(callSuper = false)
public class OAuthUserDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 账号 */
    private String account;

    /** 昵称 */
    private String nickName;

    /** 头像 */
    private String avatar;

    /** 性别（字典：Gender） */
    private String genderDict;

    /** OpenId */
    private String openId;

    /** 认证类型（字典：SysOAuthcType） */
    @TableField("oauth_type_dict")
    private String oAuthTypeDict;

    /** 关联user */
    private Long userId;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /** 修改时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;

}