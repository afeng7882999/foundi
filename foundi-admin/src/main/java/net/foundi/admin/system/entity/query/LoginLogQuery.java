/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */
package net.foundi.admin.system.entity.query;

import lombok.Data;
import net.foundi.framework.entity.jackson.JsonTimestamp;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Order;
import net.foundi.framework.entity.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
* 系统访问日志Criteria
*
* @author Afeng
*/
@Data
public class LoginLogQuery implements Query {

    /** BETWEEN */
    @Criterion(type = Criterion.Type.BETWEEN)
    private List<LocalDateTime> operTime;

    /** 包含 */
    @Criterion(type = Criterion.Type.IN)
    private List<Long> typeDict;

    /** 包含 */
    @Criterion(type = Criterion.Type.IN)
    private List<Long> authcTypeDict;

    /** 模糊 */
    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String userName;

    /** 模糊 */
    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String ip;

    /** 精确 */
    @Criterion
    private Long statusDict;

    /** 排序 */
    @Order(field = "userName")
    @Order(field = "ip")
    private List<String> orderByList;
}