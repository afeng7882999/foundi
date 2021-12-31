/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 线程相关工具类.
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class ThreadUtils {
    private static final Logger logger = LoggerFactory.getLogger(ThreadUtils.class);

    /**
     * 等待sleep，单位为毫秒
     *
     * @param milliseconds 等待毫秒数
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException ignored) {
        }
    }

    /**
     * 停止线程池
     * 先使用shutdown，停止接收新任务并尝试完成所有已存在任务
     * 如果超时，则调用shutdownNow，取消在workQueue中Pending的任务,，并中断所有阻塞函数
     * 如果仍超時，则強制退出
     * 另对在shutdown时线程本身被调用中断做了处理
     *
     * @param pool 线程池
     */
    public static void shutdownAndAwaitTermination(ExecutorService pool) {
        if (pool != null && !pool.isShutdown()) {
            pool.shutdown();
            try {
                if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                    if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
                        logger.info("Pool did not terminate");
                    }
                }
            }
            catch (InterruptedException ie) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 打印线程异常信息
     *
     * @param runnable Runnable对象
     * @param e 异常对象
     */
    public static void printException(Runnable runnable, Throwable e) {
        if (e == null && runnable instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) runnable;
                if (future.isDone()) {
                    future.get();
                }
            }
            catch (CancellationException ce) {
                e = ce;
            }
            catch (ExecutionException ee) {
                e = ee.getCause();
            }
            catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        if (e != null) {
            printException(e);
        }
    }

    /**
     * 打印线程异常信息
     *
     * @param e 异常对象
     */
    public static void printException(Throwable e) {
        logger.error(e.getMessage(), e);
    }
}
