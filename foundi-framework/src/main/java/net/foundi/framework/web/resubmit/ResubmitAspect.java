/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.resubmit;

import net.foundi.common.utils.crypto.MD5;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpELUtils;
import net.foundi.common.utils.spring.SpringWebUtils;
import net.foundi.common.utils.web.IPUtils;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.web.exception.ResubmitException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 重复提交切面
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Aspect
@Component
public class ResubmitAspect {

    @Pointcut("@annotation(resubmit)")
    public void method(Resubmit resubmit) {
    }

    @Around(value = "method(resubmit)", argNames = "pjp, resubmit")
    public Object around(ProceedingJoinPoint pjp, Resubmit resubmit) throws Throwable {
        Signature signature = pjp.getSignature();
        Class<?> cls = pjp.getTarget().getClass();
        Method method = ((MethodSignature) signature).getMethod();

        // lock key: "user:app:method:params"
        String user = resubmit.checkFor() == Resubmit.CheckTarget.USER
                ? SecurityUtils.getCurrentUserOrEx().getUsername()
                : IPUtils.getIpAddrOrEx(SpringWebUtils.getRequestFromContext());
        String app = resubmit.checkFor() == Resubmit.CheckTarget.USER
                ? SecurityUtils.currentUser().map(UserContext::getAppName).orElse("any")
                : "any";
        StringBuilder key = new StringBuilder(resubmit.lockPrefix());
        key.append(user)
                .append(":")
                .append(app)
                .append(":")
                .append(cls.getName())
                .append(":")
                .append(method.getName());

        String spEL = resubmit.lockKey();
        if (StringUtils.hasValue(spEL)) {
            Object params = SpELUtils.paramsBySpEL(spEL, method, pjp.getArgs());
            String digest = MD5.digest2Str(params.toString());
            key.append(":").append(digest);
        } else {
            String digest = MD5.digest2Str(SpELUtils.paramsMap(method, pjp.getArgs()).toString());
            key.append(":").append(digest);
        }

        if (ResubmitCommand.checkResubmit(key.toString(), resubmit.interval(), TimeUnit.SECONDS)) {
            //resubmit
            throw new ResubmitException("数据重复提交");
        }

        // normal business
        return pjp.proceed();
    }
}
