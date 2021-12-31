/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.base;

import net.foundi.common.utils.lang.ConvertUtils;
import net.foundi.common.utils.lang.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 反射工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ReflectUtils {

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    private static final Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * 获取类、基类的所有字段
     *
     * @param clazz  类
     * @param fields 字段列表
     * @return 字段列表
     */
    public static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /**
     * 调用Getter方法，支持多级，如：对象名.对象名.方法
     *
     * @param obj          对象
     * @param propertyName Getter方法对应的属性名称
     * @return 方法返回值
     */
    public static <E> E invokeGetter(Object obj, String propertyName) {
        Object object = obj;
        for (String name : StringUtils.split(propertyName, ".")) {
            String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
            object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
        }
        return (E) object;
    }

    /**
     * 调用Setter方法, 仅匹配方法名，支持多级，如：对象名.对象名.方法
     *
     * @param obj          对象
     * @param propertyName Getter方法对应的属性名称
     * @param value        方法入参
     */
    public static <E> void invokeSetter(Object obj, String propertyName, E value) {
        Object object = obj;
        String[] names = StringUtils.split(propertyName, ".");
        for (int i = 0; i < names.length; i++) {
            if (i < names.length - 1) {
                String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
                object = invokeMethod(object, getterMethodName, new Class[]{}, new Object[]{});
            } else {
                String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
                invokeMethodByName(object, setterMethodName, new Object[]{value});
            }
        }
    }

    /**
     * 直接读取对象属性值，无视private、protected修饰符，不经过getter函数
     *
     * @param obj       对象
     * @param fieldName 属性名称
     * @return 属性值
     */
    public static <E> E getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            return null;
        }
        E result = null;
        try {
            result = (E) field.get(obj);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接设置对象属性值,视private/protected修饰符,不经过setter函数
     *
     * @param obj       对象
     * @param fieldName 属性名称
     * @param value     属性值
     */
    public static <E> void setFieldValue(final Object obj, final String fieldName, final E value) {
        Field field = getAccessibleField(obj, fieldName);
        if (field == null) {
            // throw new IllegalArgumentException("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
            return;
        }
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常: {}", e.getMessage());
        }
    }

    /**
     * 直接调用对象方法，无视private/protected修饰符
     * 用于一次性调用的情况，否则应使用getAccessibleMethod()函数获得Method后反复调用
     * 同时匹配方法名+参数类型
     *
     * @param obj            对象
     * @param methodName     方法名
     * @param parameterTypes 入参类型数组
     * @param args           入参
     * @return 方法返回值
     */
    public static <E> E invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
                                     final Object[] args) {
        if (obj == null || methodName == null) {
            return null;
        }
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
            return null;
        }
        try {
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            String msg = "method: " + method + ", obj: " + obj + ", args: " + Arrays.toString(args) + "";
            throw ExceptionUtils.unchecked(msg, e);
        }
    }

    /**
     * 直接调用对象方法,无视private/protected修饰符
     * 用于一次性调用的情况，否则应使用getAccessibleMethodByName()函数获得Method后反复调用
     * 只匹配函数名，如果有多个同名函数调用第一个
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param args       入参
     * @return 方法返回值
     */
    public static <E> E invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName, args.length);
        if (method == null) {
            // 如果为空不报错，直接返回空。
            logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
            return null;
        }
        try {
            // 类型转换（将参数数据类型转换为目标方法参数类型）
            Class<?>[] cs = method.getParameterTypes();
            for (int i = 0; i < cs.length; i++) {
                if (args[i] != null && !args[i].getClass().equals(cs[i])) {
                    if (cs[i] == String.class) {
                        args[i] = ConvertUtils.toStr(args[i]);
                        if (StringUtils.endsWith((String) args[i], ".0")) {
                            args[i] = StringUtils.substringBefore((String) args[i], ".0");
                        }
                    } else if (cs[i] == Integer.class) {
                        args[i] = ConvertUtils.toInt(args[i]);
                    } else if (cs[i] == Long.class) {
                        args[i] = ConvertUtils.toLong(args[i]);
                    } else if (cs[i] == Double.class) {
                        args[i] = ConvertUtils.toDouble(args[i]);
                    } else if (cs[i] == Float.class) {
                        args[i] = ConvertUtils.toFloat(args[i]);
                    } else if (cs[i] == Date.class) {
                        if (args[i] instanceof String) {
                            args[i] = DateUtils.dateTime(String.valueOf(args[i]));
                        } else {
                            args[i] = DateUtil.getJavaDate((Double) args[i]);
                        }
                    }
                }
            }
            return (E) method.invoke(obj, args);
        } catch (Exception e) {
            String msg = "方法：" + method + "，对象：" + obj + "，参数：" + Arrays.toString(args);
            throw ExceptionUtils.unchecked(msg, e);
        }
    }

    /**
     * 循环向上转型，获取对象的DeclaredField，并强制设置为可访问
     * 如向上转型到Object仍无法找到，返回null
     *
     * @param obj       对象
     * @param fieldName 字段名
     * @return Field对象
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        Validate.notBlank(fieldName, "字段名不能为空");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException ignored) {
            }
        }
        return null;
    }

    /**
     * 循环向上转型，获取对象的DeclaredMethod,并强制设置为可访问
     * 如向上转型到Object仍无法找到，返回null
     * 匹配函数名+参数类型。
     * 用于方法需要被多次调用的情况 先使用本函数先取得Method,然后调用MethodInvoke(Object obj, Object args)
     *
     * @param obj            对象
     * @param methodName     方法名
     * @param parameterTypes 入参类型数组
     * @return Method对象
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        Validate.notBlank(methodName, "方法名不能为空");
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException ignored) {
            }
        }
        return null;
    }

    /**
     * 循环向上转型，获取对象的DeclaredMethod，并强制设置为可访问
     * 如向上转型到Object仍无法找到，返回null
     * 只匹配函数名
     * 用于方法需要被多次调用的情况，先使用本函数先取得Method，然后调用Method.invoke(Object obj, Object... args)
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param argsNum    入参个数
     * @return Method对象
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName, int argsNum) {
        // 为空不报错。直接返回 null
        if (obj == null) {
            return null;
        }
        Validate.notBlank(methodName, "方法名不能为空");
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == argsNum) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 改变private/protected的方法为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨
     *
     * @param method Method对象
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * 改变private/protected的成员变量为public，尽量不调用实际改动的语句，避免JDK的SecurityManager抱怨
     *
     * @param field Field对象
     */
    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射，获得Class定义中声明的泛型参数的类型，注意泛型必须定义在父类处
     * 如无法找到，返回Object.class
     *
     * @param clazz 类类型
     * @return 泛型参数类型
     */
    public static <T> Class<T> getClassGenericType(final Class clazz) {
        return getClassGenericType(clazz, 0);
    }

    /**
     * 通过反射，获得Class定义中声明的父类的泛型参数的类型
     * 如无法找到，返回Object.class
     *
     * @param clazz 类类型
     * @param index 参数索引
     * @return 泛型参数类型
     */
    public static Class getClassGenericType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.debug(clazz.getSimpleName() + "的父类不是参数化类型");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.debug("index值超过" + clazz.getSimpleName() + "父类的泛型参数个数");
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.debug(clazz.getSimpleName() + "父类的泛型参数不是类");
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 获取对象类的父类
     *
     * @param instance 对象
     * @return 类类型
     */
    public static Class<?> getSupperClass(Object instance) {
        if (instance == null) {
            throw new RuntimeException("对象不能为null");
        }
        Class clazz = instance.getClass();
        if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;

    }

}
