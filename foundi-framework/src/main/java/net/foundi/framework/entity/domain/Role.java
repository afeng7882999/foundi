/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.domain;

/**
 * 角色DO基类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class Role implements Do {

    private static final long serialVersionUID = 2271957803454382212L;

    /** 角色ID */
    private Long id;

    /** 角色名称 */
    private String name;

    /** 数据范围 */
    private String dataScopeDict;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataScopeDict() {
        return dataScopeDict;
    }

    public void setDataScopeDict(String dataScopeDict) {
        this.dataScopeDict = dataScopeDict;
    }
}
