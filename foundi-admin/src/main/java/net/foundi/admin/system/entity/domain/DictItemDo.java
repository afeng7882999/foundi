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
 * 系统字典条目DO
 *
 * @author Afeng
 */
@TableName("sys_dict_item")
@Data
@EqualsAndHashCode(callSuper = false)
public class DictItemDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 排序（升序） */
    private Integer sort;

    /** 字典项键值 */
    private String itemKey;

    /** 字典项值 */
    private String itemValue;

    /** 字典ID */
    private Long dictId;

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

}