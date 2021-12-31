/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

import org.springframework.stereotype.Service;

import net.foundi.admin.system.dao.TaskDao;
import net.foundi.admin.system.entity.domain.TaskDo;
import net.foundi.admin.system.service.TaskService;
import net.foundi.common.exception.BusinessException;
import net.foundi.framework.service.BaseServiceImpl;
import net.foundi.support.task.config.TaskConst;
import net.foundi.support.task.quartz.QuartzManager;
import net.foundi.support.task.quartz.ScheduleJob;

/**
* 系统任务Service
*
* @author Afeng
*/
@Service
public class TaskServiceImpl extends BaseServiceImpl<TaskDao, TaskDo> implements TaskService {

    private final QuartzManager quartzManager;

    public TaskServiceImpl(QuartzManager quartzManager) {
        this.quartzManager = quartzManager;
    }

    @Override
    public void initSchedule() {
        try {
            List<TaskDo> jobList = baseMapper.selectList(Wrappers.emptyWrapper());
            for (TaskDo aDo : jobList) {
                if (TaskConst.STATUS_RUNNING.equals(aDo.getJobStatus())) {
                    ScheduleJob job = TaskDo.toScheduleJob(aDo);
                    quartzManager.addJob(job);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("初始化任务出错", e);
        }
    }

    @Override
    public boolean save(TaskDo record) {
        try {
            if (record.getId() == null) {
                record.setId(IdWorker.getId());
            }
            record.setJobStatus(TaskConst.STATUS_NOT_RUNNING);
            return SqlHelper.retBool(baseMapper.insert(record));
        } catch (Exception e) {
            throw new BusinessException("新增项目出错", e);
        }
    }

    @Override
    public boolean update(TaskDo record) {
        record.setJobStatus(TaskConst.STATUS_NOT_RUNNING);
        try {
            return SqlHelper.retBool(baseMapper.update(record, null));
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    @Override
    public TaskDo saveAndGet(TaskDo record) {
        try {
            if (record.getId() == null) {
                record.setId(IdWorker.getId());
            }
            record.setJobStatus(TaskConst.STATUS_NOT_RUNNING);
            if (SqlHelper.retBool(baseMapper.insert(record))) {
                return baseMapper.selectById(record.getId());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("新增项目出错", e);
        }
    }

    @Override
    public TaskDo updateAndGet(TaskDo record) {
        try {
            Long id = record.getId();
            record.setJobStatus(TaskConst.STATUS_NOT_RUNNING);
            if (SqlHelper.retBool(baseMapper.updateById(record))) {
                return baseMapper.selectById(id);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("编辑项目出错", e);
        }
    }

    @Override
    public TaskDo changeStatus(Long id, String cmd) {
        try {
            TaskDo aDo = getById(id);
            if (aDo == null) {
                return null;
            }
            if (TaskConst.CMD_STOP.equals(cmd)) {
                quartzManager.deleteJob(TaskDo.toScheduleJob(aDo));
                aDo.setJobStatus(TaskConst.STATUS_NOT_RUNNING);
                updateById(aDo);
            } else if (TaskConst.CMD_START.equals(cmd)) {
                aDo.setJobStatus(TaskConst.STATUS_RUNNING);
                quartzManager.addJob(TaskDo.toScheduleJob(aDo));
                updateById(aDo);
            }
            return aDo;
        } catch (Exception e) {
            throw new BusinessException("改变任务状态出错", e);
        }
    }

    @Override
    public void updateCron(Long id) {
        try {
            TaskDo aDo = getById(id);
            if (aDo == null) {
                return;
            }
            if (TaskConst.STATUS_RUNNING.equals(aDo.getJobStatus())) {
                quartzManager.updateJobCron(TaskDo.toScheduleJob(aDo));
                updateById(aDo);
            }
        } catch (Exception e) {
            throw new BusinessException("更新Cron表达式出错", e);
        }
    }

    @Override
    public TaskDo runOnceImmediately(Long jobId) {
        try {
            TaskDo aDo = getById(jobId);
            if (aDo == null) {
                return null;
            }
            quartzManager.runJobImmediately(TaskDo.toScheduleJob(aDo));
            return aDo;
        } catch (Exception e) {
            throw new BusinessException("立即执行任务出错", e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            TaskDo aDo = getById(id);
            quartzManager.deleteJob(TaskDo.toScheduleJob(aDo));
            baseMapper.deleteById(id);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    public void removeBatch(List<Long> ids) {
        try {
            for (Long id : ids) {
                TaskDo aDo = getById(id);
                quartzManager.deleteJob(TaskDo.toScheduleJob(aDo));
            }
            baseMapper.deleteBatchIds(ids);
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

}