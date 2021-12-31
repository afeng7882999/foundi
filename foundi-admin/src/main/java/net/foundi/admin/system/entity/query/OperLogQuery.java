/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */
package net.foundi.admin.system.entity.query;

import lombok.Data;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Order;
import net.foundi.framework.entity.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
* 系统操作日志Criteria
*
* @author Afeng
*/
@Data
public class OperLogQuery implements Query {

    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String title;

    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String operUserName;

    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String method;

    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String operIp;

    @Criterion(type = Criterion.Type.BETWEEN)
    private List<LocalDateTime> operTime;

    /** 排序 */
    @Order(field = "title")
    @Order(field = "operUrl")
    @Order(field = "operTime", sort = Order.Type.DESC)
    private List<String> orderByList;
}