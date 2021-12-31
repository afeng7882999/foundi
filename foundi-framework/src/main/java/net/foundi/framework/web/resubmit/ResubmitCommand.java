/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.resubmit;

import net.foundi.common.utils.spring.SpringAppUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 重复提交Redis命令
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class ResubmitCommand {

    private static final String COMMAND_SCRIPT = "if redis.call('SETNX',KEYS[1],1)==1 then " +
            "return redis.call('EXPIRE',KEYS[1],ARGV[1]) else return nil end";

    private static StringRedisTemplate redisTemplate;

    /**
     * 是否重复提交
     *
     * @param key    redis的key
     * @param expire 过期时间
     * @return 加锁成功返回true
     */
    public static boolean checkResubmit(String key, Long expire, TimeUnit timeUnit) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(COMMAND_SCRIPT);
        script.setResultType(Long.class);
        if (redisTemplate == null) {
            redisTemplate = SpringAppUtils.getBean(StringRedisTemplate.class);
        }
        Long result = redisTemplate.execute(script, Collections.singletonList(key),
                Long.toString(timeUnit.toSeconds(expire)));
        //为null时，触发重复提交
        return result == null;
    }
}
