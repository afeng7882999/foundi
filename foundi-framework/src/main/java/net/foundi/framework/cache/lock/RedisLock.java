/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.cache.lock;

import net.foundi.common.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * redis可重入分布式锁
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class RedisLock implements DistributedLock {
    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
    private Thread thread;
    private final String key;
    private final UUID id = UUID.randomUUID();
    private final Random random = new Random();
    private final int unlockRetry;

    public static RedisLock create(String key) {
        return create(key, 1);
    }

    public static RedisLock create(String key, int unlockRetry) {
        return new RedisLock(key, unlockRetry);
    }

    private RedisLock(String key, int unlockRetry) {
        this.key = key;
        this.unlockRetry = unlockRetry;
    }

    /**
     * 加锁
     *
     * @param expire   锁过期时间
     * @param timeUnit 时间单位
     */
    @Override
    public void lock(long expire, TimeUnit timeUnit) throws InterruptedException {
        if (expire <= 0L) throw new IllegalArgumentException("锁过期时间应大于0");
        String field = getLockName(Thread.currentThread().getId());
        boolean result;
        for (; ; ) {
            result = LockCommand.lock(key, field, timeUnit.toMillis(expire));
            if (result) {
                thread = Thread.currentThread();
                return;
            } else {
                Thread.sleep(random.nextInt(10));
            }
        }
    }

    /**
     * 加锁，被占用返回false
     *
     * @param expire   锁过期时间
     * @param timeUnit 时间单位
     */
    @Override
    public boolean tryLock(long expire, TimeUnit timeUnit) {
        String field = getLockName(Thread.currentThread().getId());
        boolean result = LockCommand.lock(key, field, timeUnit.toMillis(expire));
        if (result) {
            thread = Thread.currentThread();
            return true;
        }
        return false;
    }

    /**
     * 加锁，被占用返回false，等待时间过长返回false
     *
     * @param timeout  等待期限
     * @param expire   锁过期时间
     * @param timeUnit 时间单位
     */
    @Override
    public boolean tryLock(long timeout, long expire, TimeUnit timeUnit) {
        if (expire <= 0L) throw new IllegalArgumentException("锁过期时间应大于0");
        if (timeout <= 0L) throw new IllegalArgumentException("加锁等待时间应大于0");
        final long deadline = System.nanoTime() + timeUnit.toNanos(timeout);
        String field = getLockName(Thread.currentThread().getId());
        boolean result;
        for (; ; ) {
            result = LockCommand.lock(key, field, timeUnit.toMillis(expire));
            if (result) {
                thread = Thread.currentThread();
                return true;
            } else {
                long remaining = deadline - System.nanoTime();
                if (remaining <= 0L)
                    return false;
                LockSupport.parkNanos(remaining);
            }
        }
    }

    /**
     * 锁是否属于当前线程
     */
    @Override
    public boolean isHeldByCurrentThread() {
        return thread == Thread.currentThread();
    }

    /**
     * 加锁是否成功
     */
    @Override
    public boolean isLocked() {
        return LockCommand.isLocked(key, getLockName(Thread.currentThread().getId()));
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        if (thread != Thread.currentThread()) throw new IllegalMonitorStateException();
        String field = getLockName(Thread.currentThread().getId());
        for (int i = 0; i <= unlockRetry; i++) {
            try {
                LockCommand.unlock(key, field);
                break;
            } catch (Exception e) {
                logger.error(StringUtils.withPrefix("当前线程解锁异常,线程ID:{},error:{}"),
                        Thread.currentThread().getId(), e.getMessage());
            }
            if (unlockRetry == i) logger.warn(StringUtils.withPrefix("当前线程解锁异常,线程ID:{}"),
                    Thread.currentThread().getId());
        }
    }

    String getLockName(long threadId) {
        return this.id + ":" + threadId;
    }

}
