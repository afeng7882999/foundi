/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.task.config;

import net.foundi.common.utils.lang.StringUtils;
import net.foundi.support.task.quartz.JobFactory;
import net.foundi.support.task.quartz.QuartzManager;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Quartz任务配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Configuration
public class TaskConfig {

    private static final Logger logger = LoggerFactory.getLogger(TaskConfig.class);

    /**
     * QuartzManager
     */
    @Bean
    public QuartzManager quartzManager(Scheduler scheduler) {
        return new QuartzManager(scheduler);
    }

    /**
     * JobFactory
     */
    @Bean
    public JobFactory jobFactory(AutowireCapableBeanFactory autowireCapableBeanFactory) {
        return new JobFactory(autowireCapableBeanFactory);
    }

    /**
     * SchedulerFactoryBean
     */
    @Bean
    public SchedulerFactoryBean  schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        try {
            factory.setOverwriteExistingJobs(true);
            Properties props = quartzProperties();
            factory.setQuartzProperties(props);
            //use datasource defined by foundi when clustered Quartz has no datasource defined in the properties file
            //in this case, the database must import Quartz tables from quartz.sql
            if ("true".equalsIgnoreCase(props.getProperty("org.quartz.jobStore.isClustered")) &&
                    StringUtils.isBlank(props.getProperty("org.quartz.jobStore.dataSource"))) {
                factory.setDataSource(dataSource);
            }
            factory.setJobFactory(jobFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return factory;
    }

    /**
     * SchedulerBean
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) {
        logger.info(StringUtils.withPrefix("启用TASK服务"));
        return schedulerFactoryBean.getScheduler();
    }

    private Properties quartzProperties() throws IOException {
        PropertiesFactoryBean factory = new PropertiesFactoryBean();
        factory.setLocation(new ClassPathResource("net/foundi/support/task/config/quartz.properties"));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

}
