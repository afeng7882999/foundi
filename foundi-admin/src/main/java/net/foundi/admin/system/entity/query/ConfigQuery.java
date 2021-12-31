/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */
package net.foundi.admin.system.entity.query;

import lombok.Data;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Query;

import java.util.List;

/**
* 系统配置Criteria
*
* @author Afeng
*/
@Data
public class ConfigQuery implements Query {

    /** 模糊 */
    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String configKey;

    /** 包含 */
    @Criterion(type = Criterion.Type.IN)
    private List<Long> configTypeDict;

    /** 精确 */
    @Criterion
    private Boolean enabled;

}