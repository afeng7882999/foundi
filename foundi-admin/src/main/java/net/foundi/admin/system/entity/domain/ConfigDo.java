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
 * 系统配置DO
 *
 * @author Afeng
 */
@TableName("sys_config")
@Data
@EqualsAndHashCode(callSuper = false)
public class ConfigDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 配置分类（字典：SysConfigType) */
    private String configTypeDict;

    /** 键 */
    private String configKey;

    /** 值 */
    private String configValue;

    /** 是否启用 */
    private Boolean enabled;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

}