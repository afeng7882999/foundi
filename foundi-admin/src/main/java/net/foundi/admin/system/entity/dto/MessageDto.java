/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.dto;

import lombok.Data;
import net.foundi.admin.system.entity.domain.MessageDo;
import net.foundi.framework.entity.dto.Dto;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.validation.EditGroup;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* 系统消息DTO
*
* @author Afeng
*/
@Data
public class MessageDto implements Dto {

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

    /** 接收方列表 */
    private List<Long> receiverIds;


    public static MessageDo toDo(MessageDto dto) {
        if (dto == null) {
            return null;
        } else {
            MessageDo aDo = new MessageDo();
            aDo.setId(dto.getId());
            aDo.setTitle(dto.getTitle());
            aDo.setContent(dto.getContent());
            aDo.setTypeDict(dto.getTypeDict());
            aDo.setSenderId(dto.getSenderId());
            aDo.setIsGroup(dto.getIsGroup());
            aDo.setGroupId(dto.getGroupId());
            aDo.setReceiverIds(dto.getReceiverIds());
            aDo.setCreateAt(dto.getCreateAt());
            return aDo;
        }
    }

    public static List<MessageDo> toDo(List<MessageDto> dtos) {
        if (dtos == null) {
            return null;
        } else {
            return dtos.stream().map(MessageDto::toDo).collect(Collectors.toList());
        }
    }

    public static MessageDto fromDo(MessageDo aDo) {
        if (aDo == null) {
            return null;
        } else {
            MessageDto dto = new MessageDto();
            dto.setId(aDo.getId());
            dto.setTitle(aDo.getTitle());
            dto.setContent(aDo.getContent());
            dto.setTypeDict(aDo.getTypeDict());
            dto.setSenderId(aDo.getSenderId());
            dto.setIsGroup(aDo.getIsGroup());
            dto.setGroupId(aDo.getGroupId());
            dto.setReceiverIds(aDo.getReceiverIds());
            dto.setCreateAt(aDo.getCreateAt());
            return dto;
        }
    }

    public static List<MessageDto> fromDo(List<MessageDo> dos) {
        if (dos == null) {
            return null;
        } else {
            return dos.stream().map(MessageDto::fromDo).collect(Collectors.toList());
        }
    }

    public static List<Map<String, Object>> toMap(List<MessageDo> dos) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MessageDo message : dos) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("标题", message.getTitle());
            map.put("信息内容", message.getContent());
            map.put("信息类型（字典：SysMessageType）", message.getTypeDict());
            map.put("发送方", message.getSenderId());
            map.put("是否群发", message.getIsGroup());
            map.put("接收组", message.getGroupId());
            map.put("接收方列表", message.getReceiverIds());
            map.put("发送时间", message.getCreateAt());
            list.add(map);
        }
        return list;
    }

}