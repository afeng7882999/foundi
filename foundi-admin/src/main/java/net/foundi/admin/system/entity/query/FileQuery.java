/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */
package net.foundi.admin.system.entity.query;

import lombok.Data;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Order;
import net.foundi.framework.entity.query.Query;

import java.util.List;

/**
* 文件上传Criteria
*
* @author Afeng
*/
@Data
public class FileQuery implements Query {

    /** 模糊 */
    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String name;

    /** 精确 */
    @Criterion
    private String oss;

    /** 精确 */
    @Criterion
    private String typeDict;

    /** 精确 */
    @Criterion
    private Long createBy;

    /** 排序 */
    @Order(field = "id", sort = Order.Type.DESC)
    private List<String> orderByList;
}