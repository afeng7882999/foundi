/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.exception.DaoException;
import net.foundi.common.exception.FoundiException;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.security.exception.LoginRetryLimitException;
import net.foundi.framework.security.exception.SecureException;
import net.foundi.framework.web.config.WebConst;
import net.foundi.framework.web.exception.ApiLimitException;
import net.foundi.framework.web.exception.EncryptFilterException;
import net.foundi.framework.web.exception.ResubmitException;
import net.foundi.framework.web.exception.XssFilterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ??????????????????
 *
 * @author Afeng (afeng7882999@163.com)
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * EncryptFilterException, XSSFilterException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({XssFilterException.class, EncryptFilterException.class})
    public WebReturn handleFilterException(FoundiException e) {
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .ex(e);
    }

    /**
     * Method Exceptions
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentConversionNotSupportedException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class,
    })
    public WebReturn handleMethodException(Exception e, HttpServletRequest req) {
        logError("????????????", e, req);
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message("????????????")
                .ex(e);
    }

    /**
     * ConstraintViolationException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public WebReturn handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest req) {
        List<String> causes = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        logError("????????????" + ": " + causes.toString(), e, req);
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message("????????????" + ": " + causes.toString())
                .ex(e);
    }

    /**
     * MethodArgumentNotValidException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebReturn handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest req) {
        List<String> causes = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        logError("????????????" + ": " + causes.toString(), e, req);
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message("????????????" + ": " + causes.toString())
                .ex(e);
    }

    /**
     * NoHandlerFoundException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoHandlerFoundException.class)
    public WebReturn handlerNoFoundException(NoHandlerFoundException e, HttpServletRequest req) {
        logError("?????????????????????????????????????????????", e, req);
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message("?????????????????????????????????????????????")
                .ex(e);
    }

    /**
     * AccessDeniedException
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public WebReturn handleAccessDeniedException(AccessDeniedException e, HttpServletRequest req) {
        logError("???????????????????????????????????????", e, req);
        return WebReturn
                .fail(HttpStatus.UNAUTHORIZED)
                .message("???????????????????????????????????????")
                .ex(e);
    }

    /**
     * SecurityException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SecureException.class)
    public WebReturn handleUnauthorizedException(SecureException e, HttpServletRequest req) {
        logError("????????????/????????????", e, req);
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .ex(e);
    }

    /**
     * LoginRetryLimitException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginRetryLimitException.class)
    public WebReturn handleLoginRetryLimitException(LoginRetryLimitException e, HttpServletRequest req) {
        logError("??????????????????????????????", e, req);
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .ex(e);
    }

    /**
     * ResubmitException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResubmitException.class)
    public WebReturn handleResubmitException(ResubmitException e) {
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .ex(e);
    }

    /**
     * ApiLimitException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiLimitException.class)
    public WebReturn handleApiLimitException(ApiLimitException e) {
        return WebReturn
                .fail(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .ex(e);
    }

    /**
     * RedisSystemException
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RedisSystemException.class)
    public WebReturn handleRedisSystemException(Exception e, HttpServletRequest req) {
        logError("????????????", e, req);
        return WebReturn
                .fail(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("????????????")
                .ex(e);
    }

    /**
     * DataAccessException
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({
            DataAccessException.class,
            DaoException.class
    })
    public WebReturn handleDataAccessException(Exception e, HttpServletRequest req) {
        logError("????????????", e, req);
        return WebReturn
                .fail(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("????????????")
                .ex(e);
    }

    /**
     * BusinessException
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public WebReturn handleBusinessException(BusinessException e, HttpServletRequest req) {
        if (e.getData() != null) {
            logError("?????????????????????\n" + e.getData().toString(), e, req);
        } else {
            logError("??????????????????", e, req);
        }
        return WebReturn.ok()
                .code(WebConst.BUSINESS_FAIL)
                .message(e.getMessage())
                .ex(e);
    }

    /**
     * Others
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public WebReturn handleException(Exception e, HttpServletRequest req) {
        logError("????????????", e, req);
        return WebReturn.fail(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("?????????????????????????????????")
                .ex(e);
    }

    /**
     * log for Error
     */
    private void logError(String message, Exception e, HttpServletRequest req) {
        String msg = StringUtils.format("{}???????????????URI???{}, ?????????{}, ?????????{}",
                FoundiConst.GLOBAL_LOG_PREFIX, req.getRequestURI(), message + ", " +e.getMessage(), e.getClass().getName());
        logger.error(msg, e);
    }
}