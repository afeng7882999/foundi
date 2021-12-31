/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.dao.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import net.foundi.framework.dao.AddonSqlInjector;
import net.foundi.framework.dao.FieldMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisPlus配置
 *
 * @author Afeng (afeng7882999@163.com)
 */
@EnableTransactionManagement
@Configuration
public class MyBatisConfig {

    /**
     * MybatisPlus表字段自动填充
     */
    @Bean
    public MetaObjectHandler fieldMetaObjectHandler() {
        return new FieldMetaObjectHandler();
    }

    /**
     * 自定义 SqlInjector
     */
    @Bean
    public AddonSqlInjector addonSqlInjector() {
        return new AddonSqlInjector();
    }

    /**
     * 注册乐观锁和分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // MybatisPlus表乐观锁，更新时会判断其他线程是否已对数据进行了修改
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // MybatisPlus查询分页
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}