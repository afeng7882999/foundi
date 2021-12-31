/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.cache.lock;

import net.foundi.common.utils.spring.SpringAppUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.List;

/**
 * 可重入分布式锁，redis实现
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class LockCommand {

    private static final String LOCK_SCRIPT = "if (redis.call('exists', KEYS[1]) == 0) then " +
            "redis.call('HSET', KEYS[1],ARGV[1], 1); " +
            "redis.call('PEXPIRE', KEYS[1], ARGV[2]); " +
            "return nil; end; " +
            "if (redis.call('HEXISTS', KEYS[1], ARGV[1]) == 1) then " +
            "redis.call('HINCRBY', KEYS[1], ARGV[1], 1); " +
            "redis.call('PEXPIRE', KEYS[1], ARGV[2]); " +
            "return nil; end; " +
            "return redis.call('PTTL', KEYS[1]);";

    private static final String UNLOCK_SCRIPT = "if (redis.call('EXISTS', KEYS[1]) == 0) then return 0; end; " +
            "if (redis.call('HEXISTS', KEYS[1], ARGV[1]) == 0) then return 0; end; " +
            "local counter = redis.call('HINCRBY', KEYS[1], ARGV[1], -1); " +
            "if (counter > 0) then return 1; " +
            "else redis.call('DEL', KEYS[1]); return 1; end;";

    private static final String ISLOCK_SCRIPT = "if (redis.call('HEXISTS', KEYS[1], ARGV[1]) == 0) then " +
            "return 0; end; " +
            "return 1;";

    private static StringRedisTemplate redisTemplate;

    /**
     * 加锁
     *
     * @param key    redis哈希表key
     * @param id     redis哈希表字段field
     * @param expire 过期时间
     * @return 加锁成功返回true
     */
    public static boolean lock(String key, String id, Long expire) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        //对同一key，相同id可重复加锁
        script.setScriptText(LOCK_SCRIPT);
        script.setResultType(Long.class);
        List<String> list = new ArrayList<>();
        list.add(key);
        if (redisTemplate == null) {
            redisTemplate = SpringAppUtils.getBean(StringRedisTemplate.class);
        }
        Long result = redisTemplate.execute(script, list, id, expire.toString());
        //为null时,加锁成功
        return result == null;
    }

    /**
     * 解锁
     *
     * @param key redis哈希表key
     * @param id  redis哈希表字段field
     */
    public static void unlock(String key, String id) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(UNLOCK_SCRIPT);
        script.setResultType(Long.class);
        List<String> list = new ArrayList<>();
        list.add(key);
        if (redisTemplate == null) {
            redisTemplate = SpringAppUtils.getBean(StringRedisTemplate.class);
        }
        redisTemplate.execute(script, list, id);
    }

    /**
     * 加锁是否成功
     *
     * @param key redis哈希表key
     * @param id  redis哈希表字段field
     * @return 加锁成功返回true
     */
    public static boolean isLocked(String key, String id) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(ISLOCK_SCRIPT);
        script.setResultType(Long.class);
        List<String> list = new ArrayList<>();
        list.add(key);
        if (redisTemplate == null) {
            redisTemplate = SpringAppUtils.getBean(StringRedisTemplate.class);
        }
        Long result = redisTemplate.execute(script, list, id);
        return result == 1L;
    }

}
