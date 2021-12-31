/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户系统消息DO
 *
 * @author Afeng
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMessageDo extends MessageDo {

    private static final long serialVersionUID = 1L;

    /** 接收方 */
    private Long receiverId;

    /** 消息状态(0：未读、1：已读、2：删除) */
    private String statusDict = "0";

    public static final String ColId = "id";
    public static final String ColTitle = "title";
    public static final String ColContent = "content";
    public static final String ColTypeDict = "type_dict";
    public static final String ColSenderId = "sender_id";
    public static final String ColIsGroup = "is_group";
    public static final String ColGroupId = "group_id";
    public static final String ColCreateAt = "create_at";
    public static final String ColReceiverId = "receiver_id";
    public static final String ColStatusDict = "status_dict";
}