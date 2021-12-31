/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.task.jobs;

import net.foundi.common.utils.lang.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * foundi
 *
 * @author Afeng (afeng7882999@163.com)
 * @since 2019/7/3
 */
@Component
public class TestTask implements Job {

    @Override
    public void execute(JobExecutionContext context){
        System.err.println("测试任务1执行 | " + DateUtils.currentDateTimeStr());
    }
}
