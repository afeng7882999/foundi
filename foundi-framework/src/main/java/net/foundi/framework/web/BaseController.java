/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpringWebUtils;
import net.foundi.framework.web.config.WebConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * Controller 基类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public abstract class BaseController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 由Http请求获取分页信息
     *
     * @return IPage
     */
    public static <T> Page<T> getPage() {
        return getPage(SpringWebUtils.getRequestFromContext());
    }

    /**
     * 由Http请求获取分页信息
     *
     * @param req HttpServletRequest
     * @return IPage
     */
    public static <T> Page<T> getPage(HttpServletRequest req) {
        long curPage = 1;
        long size = WebConst.DEFAULT_SIZE;

        if (StringUtils.hasValue(req.getParameter(WebConst.CURRENT))) {
            curPage = Long.parseLong(req.getParameter(WebConst.CURRENT));
        }
        if (StringUtils.hasValue(req.getParameter(WebConst.SIZE))) {
            size = Long.parseLong(req.getParameter(WebConst.SIZE));
        }

        return new Page<>(curPage, size);
    }

    /**
     * 全局Controller参数绑定设置
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //日期时间字符串转换为 LocalDateTime、LocalDate、LocalTime
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    setValue(DateUtils.dateOrTime(text));
                } catch (ParseException e) {
                    throw new TypeMismatchException(text, LocalDateTime.class);
                }
            }
        });
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    LocalDateTime ldt = DateUtils.dateOrTime(text);
                    if (ldt != null) {
                        setValue(ldt.toLocalDate());
                    }
                } catch (ParseException e) {
                    throw new TypeMismatchException(text, LocalDate.class);
                }
            }
        });
        binder.registerCustomEditor(LocalTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                try {
                    LocalDateTime ldt = DateUtils.dateOrTime(text);
                    if (ldt != null) {
                        setValue(ldt.toLocalTime());
                    }
                } catch (ParseException e) {
                    throw new TypeMismatchException(text, LocalTime.class);
                }
            }
        });
    }

 }
