/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.UserMessageDo;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户系统消息DTO
 *
 * @author Afeng
 */
@Data
public class UserMessageDto {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "ID不能为空", groups = {EditGroup.class})
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
    @JsonTimestamp
    private LocalDateTime createAt;

    /** 接收方 */
    private Long receiverId;

    /** 消息状态(0：未读、1：已读、2：删除) */
    private String statusDict = "0";

    public static UserMessageDo toDo(UserMessageDto dto) {
        if (dto == null) {
            return null;
        } else {
            UserMessageDo aDo = new UserMessageDo();
            aDo.setId(dto.getId());
            aDo.setTitle(dto.getTitle());
            aDo.setContent(dto.getContent());
            aDo.setTypeDict(dto.getTypeDict());
            aDo.setSenderId(dto.getSenderId());
            aDo.setIsGroup(dto.getIsGroup());
            aDo.setGroupId(dto.getGroupId());
            aDo.setReceiverId(dto.getReceiverId());
            aDo.setStatusDict(dto.getStatusDict());
            aDo.setCreateAt(dto.getCreateAt());
            return aDo;
        }
    }

    public static List<UserMessageDo> toDo(List<UserMessageDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(UserMessageDto::toDo).collect(Collectors.toList());
        }
    }

    public static UserMessageDto fromDo(UserMessageDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            UserMessageDto dto = new UserMessageDto();
            dto.setId(aDo.getId());
            dto.setTitle(aDo.getTitle());
            dto.setContent(aDo.getContent());
            dto.setTypeDict(aDo.getTypeDict());
            dto.setSenderId(aDo.getSenderId());
            dto.setIsGroup(aDo.getIsGroup());
            dto.setGroupId(aDo.getGroupId());
            dto.setReceiverId(aDo.getReceiverId());
            dto.setStatusDict(aDo.getStatusDict());
            dto.setCreateAt(aDo.getCreateAt());
            return dto;
        }
    }

    public static List<UserMessageDto> fromDo(List<UserMessageDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(UserMessageDto::fromDo).collect(Collectors.toList());
        }
    }
    
}
