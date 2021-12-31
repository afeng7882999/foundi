/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */
package net.foundi.admin.system.entity.query;

import lombok.Data;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Query;

/**
* 系统菜单Criteria
*
* @author Afeng
*/
@Data
public class MenuQuery implements Query {

    /** 精确 */
    @Criterion
    private Long parentId;
}