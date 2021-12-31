/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.query;

import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 当前用户系统消息Criteria
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class UserMessageQuery implements Query {

    /** 模糊 */
    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String title;

    /** 包含 */
    @Criterion(type = Criterion.Type.IN)
    private List<Long> typeDict;

    /** 精确 */
    @Criterion
    private String statusDict;

    /** BETWEEN */
    @Criterion(type = Criterion.Type.BETWEEN)
    private List<LocalDateTime> createAt;
}
