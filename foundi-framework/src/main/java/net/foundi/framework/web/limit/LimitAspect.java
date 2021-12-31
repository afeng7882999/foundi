/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.limit;

import net.foundi.common.utils.crypto.MD5;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpELUtils;
import net.foundi.common.utils.spring.SpringWebUtils;
import net.foundi.common.utils.web.IPUtils;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.web.exception.ApiLimitException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * API限制访问切面
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Aspect
@Component
public class LimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);

    @Pointcut("@annotation(limit)")
    public void method(Limit limit) {
    }

    @Around(value = "method(limit)", argNames = "pjp, limit")
    public Object around(ProceedingJoinPoint pjp, Limit limit) throws Throwable {
        Signature signature = pjp.getSignature();
        Class<?> cls = pjp.getTarget().getClass();
        Method method = ((MethodSignature) signature).getMethod();

        // check key: "ip:method:params"
        // forbidden key: "ip:method:disabled"
        StringBuilder sb = new StringBuilder(limit.lockPrefix());
        if (limit.checkFor() == Limit.CheckTarget.USER) {
            sb.append(SecurityUtils.getCurrentUserOrEx().getId()).append(":");
        } else if (limit.checkFor() == Limit.CheckTarget.IP) {
            sb.append(IPUtils.getIpAddrOrEx(SpringWebUtils.getRequestFromContext())).append(":");
        }
        sb.append(cls.getName()).append(":").append(method.getName());

        String forbiddenKey = sb + ":disabled";

        String spEL = limit.lockKey();
        if (StringUtils.hasValue(spEL)) {
            Object params = SpELUtils.paramsBySpEL(spEL, method, pjp.getArgs());
            String digest = MD5.digest2Str(params.toString());
            sb.append(":").append(digest);
        }

        String checkKey = sb.toString();

        if (LimitCommand.checkLimit(checkKey, forbiddenKey, limit.maxRate(), limit.duration(), limit.forbiddenTime())) {
            //disabled, log and return message
            logger.error(StringUtils.withPrefix(" {} 在规定时间 {} 秒内已达到访问上限 {}，已禁止访问 {} 秒"),
                    checkKey, limit.duration(), limit.maxRate(), limit.forbiddenTime());
            String msg = "已达到接口访问次数上限，" + DateUtils.secondFormatStr(limit.forbiddenTime()) + "后恢复";
            throw new ApiLimitException(msg);
        }

        // normal business
        return pjp.proceed();
    }

}

