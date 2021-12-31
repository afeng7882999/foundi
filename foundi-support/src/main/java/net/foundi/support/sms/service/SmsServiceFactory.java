/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.sms.service;

import net.foundi.support.sms.config.SmsProperties;
import net.foundi.support.sms.config.SmsPropertyService;
import net.foundi.support.sms.config.SmsServiceProvider;

/**
 * SMS发送服务工厂类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class SmsServiceFactory {

    private SmsPropertyService smsPropertyService;

    public SmsServiceFactory(SmsPropertyService smsPropertyService) {
        this.smsPropertyService = smsPropertyService;
    }

    /**
     * 新建一个SMS服务
     *
     * @return SmsService 对象
     */
    public SmsService build() {
        SmsProperties sp = this.smsPropertyService.getEnabledSmsConfig();
        SmsServiceProvider ot = sp.getProvider();

        if (SmsServiceProvider.ALIYUN.equals(ot)) {
            return new AliyunSmsService(sp);
        }

        if (SmsServiceProvider.HUAWEI.equals(ot)) {
            return new HuaweiSmsService(sp);
        }

        return null;
    }
}
