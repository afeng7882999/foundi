/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.task.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * Quartz任务工厂
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class JobFactory extends AdaptableJobFactory {

    private final AutowireCapableBeanFactory beanFactory;

    public JobFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        // inject
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
