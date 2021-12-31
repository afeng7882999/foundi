/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.foundi.admin.system.entity.domain.MessageDo;
import net.foundi.admin.system.entity.domain.UserMessageDo;
import net.foundi.admin.system.entity.enums.MessageStatus;
import net.foundi.framework.service.BaseService;

/**
 * 系统消息Service
 *
 * @author Afeng
 */
public interface MessageService extends BaseService<MessageDo> {

    /**
     * 通过ID获取一条消息
     *
     * @param messageId ID
     * @return MessageDo
     */
    MessageDo getById(Long messageId);

    /**
     * 保存一条消息
     *
     * @param record MessageDo对象
     * @return MessageDo对象
     */
    MessageDo saveAndGet(MessageDo record);

    /**
     * 更新一条消息
     *
     * @param record MessageDo对象
     * @return MessageDo对象
     */
    MessageDo updateAndGet(MessageDo record);

    /**
     * 删除一条消息
     *
     * @param messageId ID
     */
    void remove(Long messageId);

    /**
     * 批量删除消息
     *
     * @param messageIds ID列表
     */
    void removeBatch(List<Long> messageIds);

    /**
     * 获取特定用户的消息列表
     *
     * @param receiverId   用户ID
     * @param page         Page对象
     * @param queryWrapper 查询条件
     * @return UserMessageDo分页对象
     */
    IPage<UserMessageDo> pageByReceiver(Long receiverId, Page<UserMessageDo> page,
                                        QueryWrapper<UserMessageDo> queryWrapper);

    /**
     * 设置特定用户多个消息的状态
     *
     * @param messageIds 消息ID列表
     * @param receiverId 用户ID
     * @param stat       状态字典枚举
     */
    void setStatByReceiver(List<Long> messageIds, Long receiverId, MessageStatus stat);

    /**
     * 删除特定用户多个消息
     *
     * @param messageIds 消息ID列表
     * @param receiverId 用户ID
     */
    void removeBatchByReceiver(List<Long> messageIds, Long receiverId);

}