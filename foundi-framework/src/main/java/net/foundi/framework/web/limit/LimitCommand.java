/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.limit;

import net.foundi.common.utils.spring.SpringAppUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;

/**
 * API限制访问的Redis操作
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class LimitCommand {

    private static StringRedisTemplate redisTemplate;

    private static final String COMMAND_SCRIPT = "if redis.call('EXISTS',KEYS[2]) == 1 then return 0 end\n" +
            "local c\n" +
            "c = redis.call('GET',KEYS[1])\n" +
            "if c and tonumber(c) > tonumber(ARGV[1]) then redis.call('SETEX',KEYS[2],ARGV[3],1) return 0 end\n" +
            "c = redis.call('INCR',KEYS[1])\n" +
            "if tonumber(c) == 1 then redis.call('EXPIRE',KEYS[1],ARGV[2]) end\n" +
            "return 1";

    /**
     * 检查是否达到限制访问条件
     *
     * @param checkKey      检测的key
     * @param forbiddenKey  禁止的key
     * @param maxRate       次数
     * @param duration      检测间隔
     * @param forbiddenTime 禁止时间
     * @return true，即限制访问
     */
    public static boolean checkLimit(String checkKey, String forbiddenKey, int maxRate, long duration,
                                     long forbiddenTime) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(COMMAND_SCRIPT);
        script.setResultType(Long.class);
        if (redisTemplate == null) {
            redisTemplate = SpringAppUtils.getBean(StringRedisTemplate.class);
        }
        Long result = redisTemplate.execute(script, Arrays.asList(checkKey, forbiddenKey), Integer.toString(maxRate),
                Long.toString(duration), Long.toString(forbiddenTime));

        return result != null && result.equals(0L);
    }
}