/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.xss;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.web.HtmlUtils;
import net.foundi.framework.web.exception.XssFilterException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * XSS过滤处理
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 仅处理 application/json 请求
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return super.getInputStream();
        }

        try {
            // 为空，直接返回
            String json = IOUtils.toString(super.getInputStream(), FoundiConst.DEFAULT_CHARSET);
            if (StringUtils.isBlank(json)) {
                return super.getInputStream();
            }
            // xss过滤
            json = removeXss(json);
            final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes(FoundiConst.DEFAULT_CHARSET));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                @Override
                public int read() {
                    return bis.read();
                }
            };
        } catch (Exception e) {
            throw new XssFilterException("运行XSS Filter出错", e);
        }
    }

    @Override
    public String getParameter(String name) {
        try {
            String value = super.getParameter(removeXss(name));
            if (StringUtils.hasValue(value)) {
                value = removeXss(value);
            }
            return value;
        } catch (Exception e) {
            throw new XssFilterException("运行XSS Filter出错", e);
        }
    }

    @Override
    public String[] getParameterValues(String name) {
        try {
            String[] parameters = super.getParameterValues(name);
            if (parameters == null || parameters.length == 0) {
                return null;
            }
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = removeXss(parameters[i]);
            }
            return parameters;
        } catch (Exception e) {
            throw new XssFilterException("运行XSS Filter出错", e);
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        try {
            Map<String, String[]> parameters = super.getParameterMap();
            for (String key : parameters.keySet()) {
                String[] values = parameters.get(key);
                for (int i = 0; i < values.length; i++) {
                    values[i] = removeXss(values[i]);
                }
                map.put(key, values);
            }
            return map;
        } catch (Exception e) {
            throw new XssFilterException("运行XSS Filter出错", e);
        }
    }

    @Override
    public String getHeader(String name) {
        try {
            String value = super.getHeader(removeXss(name));
            if (StringUtils.hasValue(value)) {
                value = removeXss(value);
            }
            return value;
        } catch (Exception e) {
            throw new XssFilterException("运行XSS Filter出错", e);
        }
    }

    /**
     * 清理Html内容，移除Xss
     *
     * @param html 输入Html
     * @return 清理后的Html
     */
    private String removeXss(String html) {
        return HtmlUtils.clean(html);
    }

}
