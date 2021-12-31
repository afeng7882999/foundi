/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.TaskDo;
import net.foundi.framework.service.BaseService;

/**
 * 系统任务Service
 *
 * @author Afeng
 */
public interface TaskService extends BaseService<TaskDo> {

    /**
     * 初始化任务
     */
    void initSchedule();

    /**
     * 改变任务状态
     *
     * @param id  任务ID
     * @param cmd 命令字符串
     * @return TaskDo
     */
    TaskDo changeStatus(Long id, String cmd);

    /**
     * 更新任务定时时间
     *
     * @param id 任务ID
     */
    void updateCron(Long id);

    /**
     * 立即执行任务
     *
     * @param id 任务ID
     */
    TaskDo runOnceImmediately(Long id);

}