<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${package}.${moduleName}.dao.${className}Dao">

    <resultMap id="BaseResultMap" type="${package}.${moduleName}.entity.domain.${className}Do">
    <#list columns as column>
        <#if column.isPk>
        <id column="${column.columnName}" property="${column.lowerFieldName}" />
        <#else>
        <result column="${column.columnName}" property="${column.lowerFieldName}" />
        </#if>
    </#list>
    </resultMap>

</mapper>