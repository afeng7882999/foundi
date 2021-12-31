/*
* Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
* (email:afeng7882999@163.com, qq:7882999).
*/

package ${package}.${moduleName}.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import ${package}.${moduleName}.entity.domain.${className}Do;
import net.foundi.framework.dao.BaseDao;

/**
* ${tableComment}DAO
*
* @author ${author}
*/
@Mapper
@Repository
public interface ${className}Dao extends BaseDao<${className}Do> {
}