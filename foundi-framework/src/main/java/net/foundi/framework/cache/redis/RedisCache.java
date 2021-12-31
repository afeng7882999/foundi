/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.cache.redis;

import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {

    public final RedisTemplate redisTemplate;

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 加入缓存，基本类型数据，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间单位
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setObject(String key, T value, Long timeout, TimeUnit timeUnit) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value, timeout, timeUnit);
        return operation;
    }

    /**
     * 设置缓存对象过期时间
     *
     * @param key      缓存的键值
     * @param timeout  时间
     * @param timeUnit 时间单位
     */
    public void setTtl(String key, Long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 获得缓存的对象，基本类型数据
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getObject(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 缓存键值
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个对象
     *
     * @param keys 缓存键值集合
     */
    public void deleteObject(Collection keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 缓存List数据
     *
     * @param key  缓存的键值
     * @param list 待缓存的List数据
     * @return ListOperations
     */
    public <T> ListOperations<String, T> setList(String key, List<T> list) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != list) {
            //int size = list.size();
            for (T t : list) {
                listOperation.leftPush(key, t);
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        if (size != null) {
            for (int i = 0; i < size; i++) {
                dataList.add(listOperation.index(key, i));
            }
        }
        return dataList;
    }

    /**
     * 缓存Set数据
     *
     * @param key 缓存键值
     * @param set 缓存的数据
     * @return BoundSetOperations
     */
    public <T> BoundSetOperations<String, T> setSet(String key, Set<T> set) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        for (T t : set) {
            setOperation.add(t);
        }
        return setOperation;
    }

    /**
     * 获得缓存的set对象
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> Set<T> getSet(String key) {
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        return operation.members();
    }

    /**
     * 缓存Map数据
     *
     * @param key 缓存键值
     * @param map 缓存的数据
     * @return HashOperations
     */
    public <T> HashOperations<String, String, T> setMap(String key, Map<String, T> map) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (map != null) {
            for (Map.Entry<String, T> entry : map.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map对象
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> Map<String, T> getMap(String key) {
        return (Map<String, T>) redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 键值前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 运行lua脚本
     *
     * @param luaScript lua脚本
     * @param keys      传入脚本的键值列表
     * @param args      传入脚本的参数列表
     * @return 脚本运行返回值，Long
     */
    public Long execute(String luaScript, List<String> keys, Object... args) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(luaScript);
        script.setResultType(Long.class);
        return (Long) redisTemplate.execute(script, keys, args);
    }
}
