/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import net.foundi.admin.system.dao.UserDao;
import org.springframework.stereotype.Service;

import net.foundi.admin.system.dao.MessageDao;
import net.foundi.admin.system.dao.MessageHistoryDao;
import net.foundi.admin.system.entity.domain.MessageDo;
import net.foundi.admin.system.entity.domain.MessageHistoryDo;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.entity.domain.UserMessageDo;
import net.foundi.admin.system.entity.enums.MessageStatus;
import net.foundi.admin.system.service.GroupService;
import net.foundi.admin.system.service.MessageService;
import net.foundi.admin.system.service.UserService;
import net.foundi.common.exception.BusinessException;
import net.foundi.framework.service.BaseServiceImpl;
import net.foundi.framework.service.config.ServiceConst;

/**
* 系统消息Service
*
* @author Afeng
*/
@Service
public class MessageServiceImpl extends BaseServiceImpl<MessageDao, MessageDo> implements MessageService {

    private final MessageHistoryDao messageHistoryMapper;
    private final GroupService groupService;
    private final UserDao userMapper;

    public MessageServiceImpl(MessageHistoryDao messageHistoryMapper, GroupService groupService,
                              UserDao userMapper) {
        this.messageHistoryMapper = messageHistoryMapper;
        this.groupService = groupService;
        this.userMapper = userMapper;
    }

    @Override
    public MessageDo getById(Long messageId) {
        MessageDo result = this.baseMapper.selectById(messageId);
        if (!result.getIsGroup()) {
            List<MessageHistoryDo> mhs = messageHistoryMapper.selectList(new QueryWrapper<MessageHistoryDo>()
                    .lambda().eq(MessageHistoryDo::getMessageId, messageId));
            result.setReceiverIds(mhs.stream()
                    .map(MessageHistoryDo::getReceiverId)
                    .collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public MessageDo saveAndGet(MessageDo record) {
        try {
            if (record.getId() == null) {
                record.setId(IdWorker.getId());
            }
            if(!record.getIsGroup()) {
                this.addMessageHistories(record.getId(), record.getReceiverIds());
            }
            if (SqlHelper.retBool(baseMapper.insert(record))) {
                return this.getById(record.getId());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("新增项目出错", e);
        }
    }

    @Override
    public MessageDo updateAndGet(MessageDo record) {
        try {
            //not change receivers of message has been sent
            record.setIsGroup(null);
            record.setGroupId(null);

            if (SqlHelper.retBool(baseMapper.updateById(record))) {
                return this.getById(record.getId());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    private void addMessageHistories(Long messageId, List<Long> receiverIds) {
        List<MessageHistoryDo> mhs = new ArrayList<>();
        for (Long receiverId : receiverIds) {
            MessageHistoryDo mh = new MessageHistoryDo();
            mh.setMessageId(messageId);
            mh.setReceiverId(receiverId);
            mh.setStatusDict(MessageStatus.UNREAD.key());
            mhs.add(mh);
        }
        messageHistoryMapper.insertBatchSomeColumn(mhs);
    }

    @Override
    public void remove(Long messageId) {
        try {
            messageHistoryMapper.delete(new QueryWrapper<MessageHistoryDo>().lambda()
                    .eq(MessageHistoryDo::getMessageId, messageId));
            this.baseMapper.deleteById(messageId);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    public void removeBatch(List<Long> messageIds) {
        try {
            messageHistoryMapper.delete(new QueryWrapper<MessageHistoryDo>().lambda()
                    .in(MessageHistoryDo::getMessageId, messageIds));
            this.baseMapper.deleteBatchIds(messageIds);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    public IPage<UserMessageDo> pageByReceiver(Long receiverId, Page<UserMessageDo> page,
                                               QueryWrapper<UserMessageDo> queryWrapper) {
        QueryWrapper<UserMessageDo> qw1 = this.arrangeQueryWrapper(queryWrapper, receiverId);
        QueryWrapper<UserMessageDo> qw2 = qw1.orderByAsc(UserMessageDo.ColStatusDict)
                .orderByDesc(UserMessageDo.ColCreateAt);

        IPage<UserMessageDo> result = this.baseMapper.selectPageByUser(page, qw2);
        return result;
    }

    private QueryWrapper<UserMessageDo> arrangeQueryWrapper(QueryWrapper<UserMessageDo> queryWrapper, Long receiverId) {
        UserDo user = userMapper.selectById(receiverId);
        if (user == null) {
            throw new BusinessException("查找项目出错，未找到用户");
        }
        //parent groups
        List<Long> groupIds = Optional.ofNullable(user.getGroupId())
                .map(groupService::getParentGroupIds)
                .orElse(Collections.singletonList(ServiceConst.TREE_ROOT_ID));
        //where condition
        QueryWrapper<UserMessageDo> qw = queryWrapper.and(q -> q.eq(UserMessageDo.ColIsGroup, true)
                .in(UserMessageDo.ColGroupId, groupIds).or()
                .eq(UserMessageDo.ColIsGroup, false)
                .eq(UserMessageDo.ColReceiverId, receiverId))
                .and(q -> q.ne(UserMessageDo.ColStatusDict, MessageStatus.DELETE.key()).or()
                        .isNull(UserMessageDo.ColStatusDict));
        return qw;
    }

    public void setStatByReceiver(List<Long> messageIds, Long receiverId, MessageStatus stat) {
        try {
            for (Long messageId : messageIds) {
                MessageHistoryDo mh = messageHistoryMapper.selectOne(new QueryWrapper<MessageHistoryDo>().lambda()
                        .eq(MessageHistoryDo::getMessageId, messageId)
                        .eq(MessageHistoryDo::getReceiverId, receiverId));
                if (mh != null) {
                    mh.setStatusDict(stat.key());
                    messageHistoryMapper.updateById(mh);
                } else {
                    MessageHistoryDo newMh = new MessageHistoryDo();
                    newMh.setMessageId(messageId);
                    newMh.setReceiverId(receiverId);
                    newMh.setStatusDict(MessageStatus.UNREAD.key());
                    messageHistoryMapper.insert(newMh);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    public void removeBatchByReceiver(List<Long> messageIds, Long receiverId) {
        this.setStatByReceiver(messageIds, receiverId, MessageStatus.DELETE);
    }

}