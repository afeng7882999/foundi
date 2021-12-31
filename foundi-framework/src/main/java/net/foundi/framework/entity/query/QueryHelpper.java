/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.entity.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.base.ReflectUtils;
import net.foundi.common.utils.lang.CollectionUtils;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.time.ZoneOffset;
import java.util.*;

/**
 * 条件构造器工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
@SuppressWarnings({"unchecked", "all"})
public class QueryHelpper {

    private static final Logger logger = LoggerFactory.getLogger(QueryHelpper.class);

    /**
     * 由前端条件对象生成QueryWrapper
     *
     * @param query 前端条件对象
     * @param <T>   实体类
     * @param <Q>   前端条件类
     * @return QueryWrapper
     */
    public static <T, Q extends Query> QueryWrapper<T> getQuery(Q query) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        return appendQuery(queryWrapper, query);
    }

    /**
     * 为QueryWrapper添加条件和排序
     *
     * @param query        前端条件对象
     * @param queryWrapper QueryWrapper
     * @return QueryWrapper
     */
    public static <T, Q extends Query> QueryWrapper<T> appendQuery(QueryWrapper<T> queryWrapper, Q query) {
        if (query == null) {
            return queryWrapper;
        }
        try {
            List<Field> fields = ReflectUtils.getAllFields(query.getClass(), new ArrayList<>());
            Field orderField = null;
            boolean orderFieldAccessible = true;
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                // 查询条件 WHERE
                appendCriteria(queryWrapper, query, field);
                // 是否是排序字段，仅支持0-1个
                Order[] orders = field.getAnnotationsByType(Order.class);
                if (orders != null && orders.length != 0) {
                    orderField = field;
                    orderFieldAccessible = accessible;
                } else {
                    field.setAccessible(accessible);
                }
            }
            // 查询排序 ORDER BY
            if (orderField != null) {
                // 查询条件为空，添加true，防止出错
                if(queryWrapper.isEmptyOfWhere()) {
                    queryWrapper.apply("1=1");
                }
                appendOrders(queryWrapper, query, orderField);
                orderField.setAccessible(orderFieldAccessible);
            }
        } catch (Exception e) {
            logger.error(StringUtils.withPrefix("Query条件对象生成失败"), e.getMessage());
        }

        return queryWrapper;
    }

    /**
     * 为QueryWrapper添加条件
     *
     * @param queryWrapper QueryWrapper
     * @param query        前端条件对象
     * @param field        Query字段
     * @return 添加条件后返回QueryWrapper
     */
    private static <T, Q extends Query> QueryWrapper<T> appendCriteria(QueryWrapper<T> queryWrapper, Q query, Field field) throws IllegalAccessException {
        Object val = field.get(query);
        if (val == null || "".equals(val)) {
            return queryWrapper;
        }
        Criterion[] criteria = field.getAnnotationsByType(Criterion.class);
        if (criteria != null && criteria.length > 1) {
            // 多字段查询
            queryWrapper.and(wrapper -> {
                for (int i = 0; i < criteria.length; i++) {
                    if (StringUtils.hasValue(criteria[i].tableField())) {
                        String column = StringUtils.camelToSnake(criteria[i].tableField());
                        wrapper.or();
                        appendCriterion(wrapper, column, criteria[i].type(), val);
                    }
                }
            });
            return queryWrapper;
        }
        if (criteria != null && criteria.length == 1) {
            // 查询
            String tableField = criteria[0].tableField();
            String column = StringUtils.isBlank(tableField) ? field.getName() : tableField;
            column = StringUtils.camelToSnake(column);
            appendCriterion(queryWrapper, column, criteria[0].type(), val);
            return queryWrapper;
        }

        return queryWrapper;
    }

    /**
     * 为QueryWrapper添加单个条件
     *
     * @param queryWrapper QueryWrapper
     * @param column       数据库列名
     * @param type         条件类型
     * @param value        值
     * @return 添加条件后返回QueryWrapper
     */
    private static <T> QueryWrapper<T> appendCriterion(QueryWrapper<T> queryWrapper, String column, Criterion.Type type, Object value) {
        try {
            switch (type) {
                case EQUAL:
                    queryWrapper.eq(column, value);
                    break;
                case GREATER_THAN:
                    queryWrapper.ge(column, value);
                    break;
                case LESS_THAN:
                    queryWrapper.le(column, value);
                    break;
                case GREATER_THAN_NQ:
                    queryWrapper.gt(column, value);
                    break;
                case LESS_THAN_NQ:
                    queryWrapper.lt(column, value);
                    break;
                case INNER_LIKE:
                    queryWrapper.like(column, value);
                    break;
                case LEFT_LIKE:
                    queryWrapper.likeLeft(column, value);
                    break;
                case RIGHT_LIKE:
                    queryWrapper.likeRight(column, value);
                    break;
                case IN:
                    if (!CollectionUtils.isEmpty((Collection<Object>) value)) {
                        queryWrapper.in(column, (Collection<Object>) value);
                    }
                    break;
                case NOT_EQUAL:
                    queryWrapper.ne(column, value);
                    break;
                case NOT_NULL:
                    queryWrapper.isNotNull(column);
                    break;
                case BETWEEN:
                    List<Object> between = (List<Object>) value;
                    queryWrapper.between(column, between.get(0), between.get(1));
                    break;
                case TIMESTAMPS:
                    List<Object> timestamps = (List<Object>) value;
                    if (!timestamps.isEmpty()) {
                        long time1 = DateUtils.toEpochMilli(DateUtils.dateTime(timestamps.get(0).toString()));
                        long time2 = DateUtils.toEpochMilli(DateUtils.dateTime(timestamps.get(1).toString()));
                        queryWrapper.between(column, time1, time2);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            logger.error(StringUtils.withPrefix("Query条件对象生成失败"), e.getMessage());
        }

        return queryWrapper;
    }

    /**
     * 为QueryWrapper添加排序
     *
     * @param queryWrapper QueryWrapper
     * @param fieldColumns DO字段对应数据库列名的Map
     * @param orderStrs    需解析的排序字符串
     * @return 添加排序后返回QueryWrapper
     */
    private static <T, Q extends Query> QueryWrapper<T> appendOrders(QueryWrapper<T> queryWrapper, Q query, Field field) throws IllegalAccessException {
        Order[] orders = field.getAnnotationsByType(Order.class);
        // 实体字段与数据库列
        Map<String, String> fieldColumns = new HashMap<>();
        // 实体字段与默认排序
        Map<String, Order.Type> fieldSorts = new HashMap<>();
        for (Order order : orders) {
            if (StringUtils.isBlank(order.field())) {
                continue;
            }
            String column = StringUtils.isBlank(order.tableField())
                    ? StringUtils.camelToSnake(order.field())
                    : order.tableField();
            fieldColumns.put(order.field(), column);
            fieldSorts.put(order.field(), order.sort());
        }

        Object val = field.get(query);
        // Query的排序字段值为空，使用默认排序
        if (val == null || "".equals(val)) {
            for (String f : fieldColumns.keySet()) {
                if (fieldSorts.get(f) == Order.Type.ASC) {
                    queryWrapper.orderByAsc(fieldColumns.get(f));
                } else if (fieldSorts.get(f) == Order.Type.DESC) {
                    queryWrapper.orderByDesc(fieldColumns.get(f));
                }
            }
            return queryWrapper;
        }
        // Query的排序字段不为空，解析生成排序，默认排序全部失效
        List<Object> orderList = (List<Object>) val;
        for (Object order : orderList) {
            String orderStr = order.toString();
            if (StringUtils.isBlank(orderStr)) {
                continue;
            }
            List<String> pairs = StringUtils.str2List(orderStr, ":");
            if (pairs.size() != 2) {
                continue;
            }
            String column = fieldColumns.get(pairs.get(0));
            if (StringUtils.hasValue(column)) {
                if ("asc".equalsIgnoreCase(pairs.get(1))) {
                    queryWrapper.orderByAsc(column);
                } else if ("desc".equalsIgnoreCase(pairs.get(1))) {
                    queryWrapper.orderByDesc(column);
                }
            }
        }
        return queryWrapper;
    }

}
