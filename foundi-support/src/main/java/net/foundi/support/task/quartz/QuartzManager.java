/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.task.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;

import net.foundi.common.exception.BusinessException;

/**
 * Quartz任务管理器
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class QuartzManager {

    private final Scheduler scheduler;

    public QuartzManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 增加一个任务
     *
     * @param job ScheduleJob
     */
    @SuppressWarnings("unchecked")
    public void addJob(ScheduleJob job) {
        try {
            Class<? extends Job> jobClass = (Class<? extends Job>) (Class.forName(job.getBeanClass()).newInstance()
                    .getClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getJobName(), job.getJobGroup()).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression())).startNow().build();
            scheduler.scheduleJob(jobDetail, trigger);
            // start
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new BusinessException("任务操作出错", e);
        }
    }

    /**
     * 获取所有任务
     *
     * @return ScheduleJob列表
     */
    public List<ScheduleJob> getAllJobs() {
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            List<ScheduleJob> jobList = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    ScheduleJob job = new ScheduleJob();
                    job.setJobName(jobKey.getName());
                    job.setJobGroup(jobKey.getGroup());
                    job.setDescription("触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setJobStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    jobList.add(job);
                }
            }
            return jobList;
        } catch (SchedulerException e) {
            throw new BusinessException("任务操作出错", e);
        }
    }

    /**
     * 获取正在运行的任务
     *
     * @return ScheduleJob列表
     */
    public List<ScheduleJob> getRunningJobs() {
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            List<ScheduleJob> jobList = new ArrayList<ScheduleJob>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                ScheduleJob job = new ScheduleJob();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
            return jobList;
        } catch (SchedulerException e) {
            throw new BusinessException("任务操作出错", e);
        }
    }

    /**
     * 暂定任务
     *
     * @param scheduleJob 任务
     */
    public void pauseJob(ScheduleJob scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new BusinessException("任务操作出错", e);
        }
    }

    /**
     * 继续任务
     *
     * @param scheduleJob 任务
     */
    public void resumeJob(ScheduleJob scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new BusinessException("任务操作出错", e);
        }
    }

    /**
     * 立即执行任务
     *
     * @param scheduleJob 任务
     */
    public void runJobImmediately(ScheduleJob scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new BusinessException("任务操作出错", e);
        }
    }

    /**
     * 删除一个任务
     *
     * @param scheduleJob 任务
     */
    public void deleteJob(ScheduleJob scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new BusinessException("任务操作出错", e);
        }
    }

    /**
     * 修改任务运行时间
     *
     * @param scheduleJob 任务
     */
    public void updateJobCron(ScheduleJob scheduleJob) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw new BusinessException("任务操作出错", e);
        }
    }

}
