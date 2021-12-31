/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;

import java.time.LocalDateTime;

/**
 * 系统字典DO
 *
 * @author Afeng
 */
@TableName("sys_dict")
@Data
@EqualsAndHashCode(callSuper = false)
public class DictDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 字典名 */
    private String name;

    /** 字典中文名 */
    private String nameCn;

    /** 备注信息 */
    private String remarks;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /** 更新者 */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;

    /** 是否删除 1：已删除 0：正常 */
    @TableLogic
    private Boolean delFlag;

    public static final String ColId = "id";
    public static final String ColName = "name";
    public static final String ColNameCn = "name_cn";
    public static final String ColRemarks = "remarks";
    public static final String ColCreateBy = "create_by";
    public static final String ColCreateAt = "create_at";
    public static final String ColUpdateBy = "update_by";
    public static final String ColUpdateAt = "update_at";
    public static final String ColDelFlag = "del_flag";

}