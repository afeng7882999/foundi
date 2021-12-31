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
* 系统用户Criteria
*
* @author Afeng
*/
@Data
public class UserQuery implements Query {

    /** 多字段模糊 */
    @Criterion(tableField="username", type = Criterion.Type.INNER_LIKE)
    @Criterion(tableField="mobile", type = Criterion.Type.INNER_LIKE)
    @Criterion(tableField="email", type = Criterion.Type.INNER_LIKE)
    private String account;

    /** 包含 */
    @Criterion(type = Criterion.Type.IN)
    private List<Long> roleId;

    /** 包含 */
    @Criterion(type = Criterion.Type.IN)
    private List<Long> groupId;

    /** 精确 */
    @Criterion
    private String statusDict;

    /** 排序 */
    @Order(field = "id", sort = Order.Type.DESC)
    List<String> orderByList;
}