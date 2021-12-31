/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.log;

import com.alibaba.druid.support.json.JSONUtils;
import net.foundi.common.utils.lang.JsonUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpELUtils;
import net.foundi.common.utils.web.WebUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // 配置织入点
    @Pointcut("@annotation(net.foundi.framework.log.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        handleLog(joinPoint, null, result);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object result) {
        try {
            // 获得注解
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            if (method == null) {
                return;
            }
            Log methodLog = method.getAnnotation(Log.class);

            // 记录日志条件
            Log.LogAfter logAfter = methodLog.logAfter();
            if (Log.LogAfter.NO_EXCEPTION.equals(logAfter) && e != null) {
                return;
            }
            if (Log.LogAfter.EXCEPTION.equals(logAfter) && e == null) {
                return;
            }

            // 方法
            String logMethod = (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
            String logResult = JsonUtils.fromObject(result);
            // 方法参数
            String logParams;
            if (methodLog.autoLogParam()) {
                logParams = StringUtils.substring(getAutoRequestParam(joinPoint), 0, 2000);
            } else {
                logParams = StringUtils.substring(getRequestParam(joinPoint, methodLog, method), 0, 2000);
            }

            // 记录日志
            LogUtils.logOperation(methodLog.value(), logMethod, logParams, logResult, e);
        } catch (Exception exp) {
            logger.error(StringUtils.withPrefix("前置通知异常"));
            logger.error("异常信息:{}", exp.getMessage());
        }
    }

    /**
     * 获取请求的参数
     */
    private String getRequestParam(JoinPoint joinPoint, Log methodLog, Method method) {
        String paramSpEl = methodLog.param();
        String params = "";
        if (StringUtils.hasValue(paramSpEl)) {
            Object paramsObject = SpELUtils.paramsBySpEL(paramSpEl, method, joinPoint.getArgs());
            if (paramsObject instanceof String[]) {
                params = StringUtils.joinStrings(", ", (String[]) paramsObject);
            } else {
                params = paramsObject.toString();
            }
        } else {
            params = SpELUtils.paramsMap(method, joinPoint.getArgs()).toString();
        }

        return params;
    }

    /**
     * 自动获取请求的参数
     */
    private String getAutoRequestParam(JoinPoint joinPoint) {
        String requestMethod = WebUtils.getRequest().getMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            return argsArrayToString(joinPoint.getArgs());
        }

        Map<?, ?> paramsMap = (Map<?, ?>) WebUtils.getRequest()
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return paramsMap.toString();
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder sb = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                    // 过滤不需要保存的参数
                    continue;
                }
                sb.append(JSONUtils.toJSONString(o)).append(" ");
            }
        }
        return sb.toString().trim();
    }

}
