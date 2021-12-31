/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.spring;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.*;

/**
 * SpEL工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class SpELUtils {

    private static final DefaultParameterNameDiscoverer parameterNameDiscoverer =
            new DefaultParameterNameDiscoverer();

    /**
     * 解析SpEL，获取方法的某个入参
     *
     * @param spEL   SpEL语句
     * @param method 方法
     * @param args   入参值数组
     * @return 解析SpEL返回对象
     */
    public static Object paramsBySpEL(String spEL, Method method, Object[] args) {
        return parseSpEl(spEL, paramsMap(method, args));
    }

    /**
     * 解析SpEL，获取Map中的某个值
     *
     * @param spEL SpEL语句
     * @param map  SpEL语句参数
     * @return 解析SpEL返回对象
     */
    public static Object parseSpEl(String spEL, Map<String, Object> map) {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariables(map);
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(spEL);
        return exp.getValue(context);
    }

    /**
     * 获取方法的参数和入参值
     *
     * @param method 方法
     * @param args   入参值数组
     * @return Map，参数名:参数值
     */
    public static Map<String, Object> paramsMap(Method method, Object[] args) {
        String[] names = parameterNameDiscoverer.getParameterNames(method);
        Map<String, Object> nameAndValues = new HashMap<>();
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                nameAndValues.put(names[i], args[i]);
            }
        }
        return nameAndValues;
    }
}
