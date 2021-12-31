/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.email;

import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.support.email.config.EMailConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Email验证类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class EMailValidator {

    private static final Logger logger = LoggerFactory.getLogger(EMailValidator.class);

    private EMailSender emailSender;
    private Cache cache;

    public EMailValidator() {
    }

    public EMailValidator(EMailSender sender, Cache cache) {
        this.emailSender = sender;
        this.cache = cache;
    }

    /**
     * 发送验证码
     *
     * @param to 接收地址
     */
    public void sendCode(String to) {
        String code = IDUtils.randomNumber(6);
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        emailSender.send(to, params, ValidType.FORGET_PASSWORD);
        if (StringUtils.hasValue(code)) {
            String key = EMailConst.EMAIL_CACHE_KEY + to;
            cache.put(key, code);

            if (logger.isDebugEnabled()) {
                logger.debug(StringUtils.withPrefix("EMAIL：cache.put({}, {})"), key, code);
            }
        }
    }

    /**
     * 核实验证码
     *
     * @param to   接收地址
     * @param code 需查证的验证码
     */
    public void verifyCode(String to, String code) {
        String key = EMailConst.EMAIL_CACHE_KEY + to;
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null || !code.equals(valueWrapper.get())) {
            throw new BusinessException("邮箱验证码验证失败", "");
        }
        cache.evict(key);

        if (logger.isDebugEnabled()) {
            logger.debug(StringUtils.withPrefix("EMAIL：cache.evict({}, {})"), key, code);
        }
    }
}
