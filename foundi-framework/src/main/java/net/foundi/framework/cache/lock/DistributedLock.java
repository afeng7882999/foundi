/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.cache.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author Afeng (afeng7882999@163.com)
*/
public interface DistributedLock {

    /**
     * 加锁
     * @param expire   锁过期时间
     * @param timeUnit 时间单位
     */
    void lock(long expire, TimeUnit timeUnit) throws InterruptedException;

    /**
     * 加锁，被占用返回false
     * @param expire   锁过期时间
     * @param timeUnit 时间单位
     */
    boolean tryLock(long expire, TimeUnit timeUnit);

    /**
     * 加锁，被占用返回false，等待时间过长返回false
     * @param timeout  等待期限
     * @param expire   锁过期时间
     * @param timeUnit 时间单位
     */
    boolean tryLock(long timeout, long expire, TimeUnit timeUnit) throws InterruptedException;

    /**
     * 锁是否属于当前线程
     */
    boolean isHeldByCurrentThread();

    /**
     * 加锁是否成功
     */
    boolean isLocked();

    /**
     * 解锁
     */
    void unlock();

}
