/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;

/**
 * 系统消息历史DO
 *
 * @author Afeng
 */
@TableName("sys_message_history")
@Data
@EqualsAndHashCode(callSuper = false)
public class MessageHistoryDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 接收方 */
    private Long receiverId;

    /** 信息内容 */
    private Long messageId;

    /** 消息状态（字典：SysMessageStatus，未读、已读、删除) */
    private String statusDict;

}