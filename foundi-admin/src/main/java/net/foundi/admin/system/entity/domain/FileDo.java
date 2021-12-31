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
 * 文件上传DO
 *
 * @author Afeng
 */
@TableName("sys_file")
@Data
@EqualsAndHashCode(callSuper = false)
public class FileDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 文件名 */
    private String name;

    /** 文件上传的OSS配置键值 */
    private String oss;

    /** 文件类型（字典：SysFileType） */
    private String typeDict;

    /** URL地址 */
    private String url;

    /** 创建人 */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

}