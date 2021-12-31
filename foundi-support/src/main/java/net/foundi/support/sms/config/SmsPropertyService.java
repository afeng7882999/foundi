/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.sms.config;

/**
 * SMS配置服务接口
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface SmsPropertyService {

    /**
     * 由数据库获取激活的SMS配置参数
     *
     * @return SmsProperties
     */
    SmsProperties getEnabledSmsConfig();

}
