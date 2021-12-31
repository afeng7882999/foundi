/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.runner;

import net.foundi.admin.system.service.TaskService;
import net.foundi.common.utils.lang.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * init task
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
@Order(value = 2)
public class TaskInitRunner implements CommandLineRunner {

    private final TaskService taskService;

    public TaskInitRunner(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(StringUtils.withPrefix("初始化系统任务..."));
        taskService.initSchedule();
    }
}
