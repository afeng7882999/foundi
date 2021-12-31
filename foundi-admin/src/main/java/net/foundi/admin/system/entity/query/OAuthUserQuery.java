/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */
package net.foundi.admin.system.entity.query;

import lombok.Data;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Query;

/**
* OAuth用户Criteria
*
* @author Afeng
*/
@Data
public class OAuthUserQuery implements Query {

    /** 多字段模糊 */
    @Criterion(tableField="account", type = Criterion.Type.INNER_LIKE)
    @Criterion(tableField="nick_name", type = Criterion.Type.INNER_LIKE)
    private String account;

    /** 精确 */
    @Criterion
    private String oAuthTypeDict;

    /** 精确 */
    @Criterion
    private Long userId;
}