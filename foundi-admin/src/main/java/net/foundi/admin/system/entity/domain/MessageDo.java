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
import java.util.List;

/**
 * 系统消息DO
 *
 * @author Afeng
 */
@TableName("sys_message")
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 标题 */
    private String title;

    /** 信息内容 */
    private String content;

    /** 信息类型（字典：SysMessageType） */
    private String typeDict;

    /** 发送方 */
    private Long senderId;

    /** 是否群发 */
    private Boolean isGroup;

    /** 接收组 */
    private Long groupId;

    /** 发送时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /** 接收方列表 */
    @TableField(exist = false)
    private List<Long> receiverIds;

}