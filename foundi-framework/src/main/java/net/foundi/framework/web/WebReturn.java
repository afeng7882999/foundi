/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpringWebUtils;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.web.config.WebConst;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Rest服务返回对象
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class WebReturn extends HashMap<String, Object> {

    private static final long serialVersionUID = -626682743235743817L;

    /**
     * 返回success
     */
    public static WebReturn ok() {
        return new WebReturn()
                .status(HttpStatus.OK)
                .code(WebConst.BUSINESS_SUCCESS)
                .message("成功")
                .checkRefreshToken();
    }

    /**
     * 返回fail
     *
     * @param status Http状态码
     */
    public static WebReturn fail(HttpStatus status) {
        return new WebReturn()
                .status(status)
                .message("失败");
    }

    /**
     * 返回fail，Http状态码缺省为400
     */
    public static WebReturn fail() {
        return fail(HttpStatus.BAD_REQUEST);
    }

    /**
     * 设置Http状态码
     *
     * @param status Http状态码
     */
    public WebReturn status(HttpStatus status) {
        HttpServletResponse rep = SpringWebUtils.getResponseFromContext();
        rep.setStatus(status.value());
        return this;
    }

    /**
     * 设置业务状态码
     *
     * @param code 状态码
     */
    public WebReturn code(Integer code) {
        this.put(WebConst.BUSINESS_CODE, code);
        return this;
    }

    /**
     * 设置返回消息
     */
    public WebReturn message(String... messages) {
        this.put(WebConst.MESSAGE, messages);
        return this;
    }

    /**
     * 设置返回异常名称
     */
    public WebReturn ex(Throwable throwable) {
        return ex(throwable, false);
    }

    /**
     * 设置返回异常名称
     *
     * @param withFullPath 是否包含异常的包名
     */
    public WebReturn ex(Throwable throwable, boolean withFullPath) {
        String className = withFullPath ? throwable.getClass().getName() : throwable.getClass().getSimpleName();
        this.put(WebConst.EXCEPTION, className);
        return this;
    }

    /**
     * 设置任意需携带数据
     *
     * @param key  指定键
     * @param data 数据
     */
    public WebReturn content(String key, Object data) {
        this.put(StringUtils.hasValue(key) ? key : WebConst.DEFAULT_DATA_KEY, data);
        return this;
    }

    /**
     * 设置任意需携带数据，使用默认键
     *
     * @param data 数据
     */
    public WebReturn content(Object data) {
        this.put(WebConst.DEFAULT_DATA_KEY, data);
        return this;
    }

    /**
     * 设置分页信息
     */
    @SuppressWarnings("rawtypes")
    public WebReturn page(IPage page) {
        this.put(WebConst.PAGE, new PageResult(page));
        return this;
    }

    /**
     * 设置令牌
     */
    public WebReturn token(String tk) {
        this.put(WebConst.TOKEN, tk);
        return this;
    }

    /**
     * 判断是否刷新了令牌，并设置令牌
     */
    public WebReturn checkRefreshToken() {
        return SecurityUtils.refreshToken().map(this::token).orElse(this);
    }

    /**
     * 分页结果类
     */
    public static class PageResult implements Serializable {

        private static final long serialVersionUID = 518262774679135183L;

        //count for all records
        private int count;

        //page size
        private int size;

        //count of all pages
        private int total;

        //current page number
        private int current;

        /**
         * Constructor, for IPage of mybatis-plus
         */
        @SuppressWarnings("rawtypes")
        public PageResult(IPage page) {
            this.count = (int) page.getPages();
            this.size = (int) page.getSize();
            this.current = (int) page.getCurrent();
            this.total = (int) page.getTotal();
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

    }

}
