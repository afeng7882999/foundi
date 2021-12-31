/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.sms.service;

import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.support.sms.config.SmsConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/**
 * SMS校验器
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class SmsValidator {

    private static Logger logger = LoggerFactory.getLogger(SmsValidator.class);

    private SmsServiceFactory smsServiceFactory;
    private Cache cache;

    public SmsValidator(SmsServiceFactory smsServiceFactory, Cache cache) {
        this.smsServiceFactory = smsServiceFactory;
        this.cache = cache;
    }

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     */
    public void sendCode(String mobile) {
        String code = IDUtils.randomNumber(5);
        smsServiceFactory.build().sendCode(mobile, "validCode", code);
        if (StringUtils.hasValue(code)) {
            String key = SmsConst.SMS_CACHE_KEY + mobile;
            cache.put(key, code);

            if (logger.isDebugEnabled()) {
                logger.debug("SMS：cache.put({}, {})", key, code);
            }
        }
    }

    /**
     * 验证已发送的验证码
     *
     * @param mobile 手机号
     * @param code   前端传回验证码
     */
    public void verifyCode(String mobile, String code) {
        String key = SmsConst.SMS_CACHE_KEY + mobile;
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null || !code.equals(valueWrapper.get())) {
            throw new BusinessException("短信验证码验证失败");
        }
        cache.evict(key);

        if (logger.isDebugEnabled()) {
            logger.debug("SMS：cache.evict({}, {})", key, code);
        }
    }
}
