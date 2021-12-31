/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.domain;

/**
 * 树状DO接口
 *
 * @author Afeng (afeng7882999@163.com)
*/
public interface Tree extends Do {

    Long getParentId();

    void setParentId(Long ParentId);

    Integer getSort();

    void setSort(Integer sort);

}
