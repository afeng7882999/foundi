/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.thread;

import net.foundi.common.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * ShutdownManager，确保应用退出时能关闭后台线程
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class ShutdownManager {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownManager.class);

    @PreDestroy
    public void destroy() {
        shutdownAsyncManager();
    }

    /**
     * 停止异步执行任务
     */
    private void shutdownAsyncManager() {
        try {
            logger.info(StringUtils.withPrefix("关闭后台任务任务线程池"));
            AsyncManager.getInstance().shutdown();
        } catch (Exception e) {
            logger.error(StringUtils.withPrefix(e.getMessage()), e);
        }
    }
}
