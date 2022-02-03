/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */
package net.foundi.admin.system.entity.query;

import lombok.Data;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Query;

/**
* 系统字典条目Criteria
*
* @author Afeng
*/
@Data
public class DictItemQuery implements Query {

    /** 相等 */
    @Criterion
    private Long dictId;

    /** 模糊 */
    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String itemKey;

    /** 模糊 */
    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String itemValue;
}