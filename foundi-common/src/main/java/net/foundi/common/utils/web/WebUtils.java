/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.base.ExceptionUtils;
import net.foundi.common.utils.lang.JsonUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpringWebUtils;

/**
 * web工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class WebUtils {

    public static final String DEFAULT_JSON_CONTENT_TYPE = "application/json;charset=utf-8";

    /**
     * 返回字符串到客户端
     *
     * @param response HttpServletResponse对象
     * @param str 返回的字符串
     */
    public static void returnStr(HttpServletResponse response, String str)
    {
        //response.setStatus(HttpStatus.OK.value());
        response.setContentType(DEFAULT_JSON_CONTENT_TYPE);
        response.setCharacterEncoding(FoundiConst.DEFAULT_CHARSET);
        try (OutputStream out = response.getOutputStream()) {
            out.write(str.getBytes(FoundiConst.DEFAULT_CHARSET));
            out.flush();
        } catch (IOException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 返回JSON到客户端
     *
     * @param response HttpServletResponse对象
     * @param json JSON
     */
    public static void returnJson(HttpServletResponse response, String json) {
        returnStr(response, json);
    }

    /**
     * 返回对象到客户端
     *
     * @param response HttpServletResponse对象
     * @param result 返回对象
     */
    public static void returnObj(HttpServletResponse response, Object result) {
        try {
            returnJson(response, JsonUtils.fromObject(result));
        } catch (JsonProcessingException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 获取HttpServletRequest
     *
     * @return HttpServletRequest对象
     */
    public static HttpServletRequest getRequest(){
        return SpringWebUtils.getRequestFromContext();
    }

    /**
     * 获取HttpServletResponse
     *
     * @return HttpServletResponse对象
     */
    public static HttpServletResponse getResponse() {
        return SpringWebUtils.getResponseFromContext();
    }

    /**
     * 编码URL
     *
     * @param value 未编码的URL
     * @return 编码后的URL
     */
    public static String urlEncode(String value) {
        if (StringUtils.isEmpty(value)) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, FoundiConst.DEFAULT_CHARSET);
            return encoded.replace("+", "%20").replace("*", "%2A")
                    .replace("~", "%7E").replace("/", "%2F");
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 解码URL
     *
     * @param value 未解码的URL
     * @return 解码后的URL
     */
    public static String urlDecode(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        try {
            return URLDecoder.decode(value, FoundiConst.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 提取URL的参数至Map中
     *
     * @param paramStr URL参数部分
     * @return 参数键值对
     */
    public static Map<String, String> paramsToMap(String paramStr) {
        Map<String, String> map = new HashMap<>();
        String[] params = paramStr.split("&");
        for (String param : params) {
            String[] kv = param.split("=");
            map.put(kv[0], kv[1]);
        }
        return map;
    }

    /**
     * 是否是Ajax异步请求
     *
     * @param request HttpServletRequest对象
     * @return true：是Ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json"))
        {
            return true;
        }
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"))
        {
            return true;
        }
        String uri = request.getRequestURI();
        if (StringUtils.inIgnoreCase(uri, ".json", ".xml"))
        {
            return true;
        }
        String ajax = request.getParameter("__ajax");
        return StringUtils.inIgnoreCase(ajax, "json", "xml");
    }

}
