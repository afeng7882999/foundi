/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import net.foundi.admin.system.entity.domain.FileDo;
import net.foundi.framework.dao.BaseDao;

/**
* 文件上传DAO
*
* @author Afeng
*/
@Mapper
@Repository
public interface FileDao extends BaseDao<FileDo> {
}