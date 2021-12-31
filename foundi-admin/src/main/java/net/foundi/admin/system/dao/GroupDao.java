/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import net.foundi.admin.system.entity.domain.GroupDo;
import net.foundi.framework.dao.BaseDao;

import java.util.List;

/**
* 系统用户组DAO
*
* @author Afeng
*/
@Mapper
@Repository
public interface GroupDao extends BaseDao<GroupDo> {

    List<GroupDo> getGroupsByRoleIds(List<Long> roleIds);

    List<Long> getIdsByParentId(Long parentId);
}