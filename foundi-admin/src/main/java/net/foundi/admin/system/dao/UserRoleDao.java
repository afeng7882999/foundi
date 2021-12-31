/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import net.foundi.admin.system.entity.domain.UserRoleDo;
import net.foundi.framework.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
* 系统用户与角色对应关系DAO
*
* @author Afeng
*/
@Mapper
@Repository
public interface UserRoleDao extends BaseDao<UserRoleDo> {

    List<Map<String, Long>> selectAllMatch(List<Long> roleIds);

    int batchInsert(List<UserRoleDo> list);
}