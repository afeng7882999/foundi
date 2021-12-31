/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.framework.dao.BaseDao;

import java.util.List;

/**
* 系统角色DAO
*
* @author Afeng
*/
@Mapper
@Repository
public interface RoleDao extends BaseDao<RoleDo> {

    List<RoleDo> getRolesByUserId(Long userId);

    List<RoleDo> getRolesByIds(List<Long> roleIds);
}