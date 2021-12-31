/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.thread;

import net.foundi.common.utils.base.ThreadUtils;
import net.foundi.common.utils.spring.SpringAppUtils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class AsyncManager {

    // 操作延迟10毫秒
    private final int OPERATE_DELAY_TIME = 10;

    // 单例
    private static AsyncManager instance;

    // 异步操作任务调度线程池
    private static ScheduledExecutorService executor;

    private AsyncManager() {
    }

    /**
     * 获取实例
     *
     * @return AsyncManager实例
     */
    public static AsyncManager getInstance() {
        if (executor == null) {
            executor = SpringAppUtils.getBean("scheduledExecutorService");
        }
        if (instance == null) {
            instance = new AsyncManager();
        }
        return instance;
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown() {
        ThreadUtils.shutdownAndAwaitTermination(executor);
    }
}
