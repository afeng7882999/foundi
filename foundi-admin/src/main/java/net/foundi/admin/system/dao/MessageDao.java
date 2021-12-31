/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.foundi.admin.system.entity.domain.UserMessageDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import net.foundi.admin.system.entity.domain.MessageDo;
import net.foundi.framework.dao.BaseDao;

import java.util.List;

/**
* 系统消息DAO
*
* @author Afeng
*/
@Mapper
@Repository
public interface MessageDao extends BaseDao<MessageDo> {

    IPage<UserMessageDo> selectPageByUser(@Param("pg") Page<UserMessageDo> page, @Param("ew") Wrapper<UserMessageDo> queryWrapper);
}