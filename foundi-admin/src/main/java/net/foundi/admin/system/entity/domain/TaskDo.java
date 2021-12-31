/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.entity.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.foundi.framework.entity.domain.Do;
import net.foundi.support.task.quartz.ScheduleJob;

import java.time.LocalDateTime;

/**
 * 系统任务DO
 *
 * @author Afeng
 */
@TableName("sys_task")
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDo implements Do {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 任务名 */
    private String jobName;

    /** 任务分组 */
    private String jobGroup;

    /** 任务状态 */
    private String jobStatus;

    /** 任务是否并发 */
    private Boolean isConcurrent;

    /** cron表达式 */
    private String cronExpression;

    /** 任务描述 */
    private String description;

    /** 任务执行时调用哪个类的方法 包名+类名 */
    private String beanClass;

    /** Spring bean */
    private String springBean;

    /** 任务调用的方法名 */
    private String methodName;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /** 创建者 */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /** 更新时间 */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;

    /** 更新者 */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateBy;

    /**
     * Static method of converting to ScheduleJob
     */
    public static ScheduleJob toScheduleJob(TaskDo aDo) {
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setBeanClass(aDo.getBeanClass());
        scheduleJob.setCronExpression(aDo.getCronExpression());
        scheduleJob.setDescription(aDo.getDescription());
        scheduleJob.setIsConcurrent(aDo.getIsConcurrent());
        scheduleJob.setJobName(aDo.getJobName());
        scheduleJob.setJobGroup(aDo.getJobGroup());
        scheduleJob.setJobStatus(aDo.getJobStatus());
        scheduleJob.setMethodName(aDo.getMethodName());
        scheduleJob.setSpringBean(aDo.getSpringBean());
        return scheduleJob;
    }

}