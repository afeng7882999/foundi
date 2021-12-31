/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.dao;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.framework.security.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * MetaObjectHandler for auto filled field
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class FieldMetaObjectHandler implements MetaObjectHandler {

    public static final String CREATE_BY_FIELD = "createBy";
    public static final String CREATE_AT_FIELD = "createAt";
    public static final String UPDATE_BY_FIELD = "updateBy";
    public static final String UPDATE_AT_FIELD = "updateAt";

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime time = DateUtils.now();
        setFieldValByName(CREATE_AT_FIELD, time, metaObject);
        SecurityUtils.currentUser().ifPresent(u -> setFieldValByName(CREATE_BY_FIELD, u.getId(), metaObject));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime time = DateUtils.now();
        setFieldValByName(UPDATE_AT_FIELD, time, metaObject);
        SecurityUtils.currentUser().ifPresent(u -> setFieldValByName(UPDATE_BY_FIELD, u.getId(), metaObject));
    }
}
