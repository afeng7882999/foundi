/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.lang;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 集合工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
@SuppressWarnings("rawtypes")
public class CollectionUtils {

    /**
     * 判断集合是否为null或者空
     *
     * @param c 集合
     * @return true：null或者空
     */
    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    /**
     * 判断Map是否为null或者空
     *
     * @param m Map
     * @return true：null或者空
     */
    public static boolean isEmpty(Map m) {
        return m == null || m.isEmpty();
    }

    /**
     * 获取集合大小，集合为null返回0
     *
     * @param c 集合
     * @return int，集合大小
     */
    public static int size(Collection c) {
        return c != null ? c.size() : 0;
    }

    /**
     * 获取Map大小，集合为null返回0
     *
     * @param m Map
     * @return int，Map大小
     */
    public static int size(Map m) {
        return m != null ? m.size() : 0;
    }

    /**
     * 判断Map的指定键值是否有null或者空值
     *
     * @param map  Map
     * @param keys 指定键值
     * @return true：Map的指定键值有null或者空值
     */
    public static <K, V> boolean anyEmpty(Map<K, V> map, K... keys) {
        if (isEmpty(map))
            return true;
        for (K key : keys) {
            V value = map.get(key);
            if (value == null) return true;
            else if (value instanceof String && ((String) value).isEmpty()) return true;
        }
        return false;
    }

    /**
     * 获取特定元素在列表中的第一个位置，没有返回-1
     *
     * @param find 查找的元素
     * @param list 列表
     * @return 第一个位置，或者-1
     */
    public static <T> int indexFirst(T find, List<T> list) {
        if (find == null || isEmpty(list)) {
            return 0;
        }
        return IntStream.range(0, list.size())
                .filter(i -> find.equals(list.get(i)))
                .findFirst()
                .orElse(-1);
    }

    /**
     * 获取特定元素在列表中的所有位置
     *
     * @param find 查找的元素
     * @param list 列表
     * @return int[]，位置列表
     */
    public static <T> int[] indexAll(T find, List<T> list) {
        if (find == null || isEmpty(list)) {
            return new int[0];
        }
        return IntStream.range(0, list.size())
                .filter(i -> find.equals(list.get(i)))
                .toArray();
    }

    /**
     * 删除列表指定位置的元素，同时去除列表中的所有null元素
     *
     * @param list    列表
     * @param indexes 位置列表
     * @return 操作后的列表
     */
    public static <T> List<T> trimIndex(List<T> list, List<Integer> indexes) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        if (isEmpty(indexes)) {
            return list;
        }
        return doTrimIndex(list, indexes);
    }

    /**
     * 删除列表指定位置的元素，同时去除列表中的所有null元素
     *
     * @param list    列表
     * @param indexes 位置数组
     * @return 操作后的列表
     */
    public static <T> List<T> trimIndex(List<T> list, int[] indexes) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        if (indexes == null || indexes.length == 0) {
            return list;
        }
        //int array to Integer List
        List<Integer> indexList = Arrays.stream(indexes).boxed().collect(Collectors.toList());
        return doTrimIndex(list, indexList);
    }

    /**
     * 删除数组指定位置的元素，同时去除数组中的所有null元素
     *
     * @param list    数组
     * @param indexes 位置数组
     * @return 操作后返回列表
     */
    public static <T> List<T> trimIndex(T[] list, int[] indexes) {
        if (list == null || list.length == 0) {
            return new ArrayList<>();
        }
        if (indexes == null || indexes.length == 0) {
            return Arrays.asList(list);
        }
        //int array to Integer List
        List<Integer> indexList = Arrays.stream(indexes).boxed().collect(Collectors.toList());
        return doTrimIndex(Arrays.asList(list), indexList);
    }

    /**
     * 删除列表指定位置的元素，同时去除列表中的所有null元素
     *
     * @param list    列表
     * @param indexes 位置列表
     * @return 操作后的列表
     */
    private static <T> List<T> doTrimIndex(List<T> list, List<Integer> indexes) {
        return IntStream.range(0, list.size())
                .filter(i -> !indexes.contains(i))
                .mapToObj(list::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
