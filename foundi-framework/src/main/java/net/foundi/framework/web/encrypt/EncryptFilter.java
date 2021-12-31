/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.encrypt;

import net.foundi.common.utils.crypto.RSA;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.web.config.WebConst;
import net.foundi.framework.web.exception.EncryptFilterException;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Http请求数据加密Filter
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class EncryptFilter implements Filter {

    // 加密链接
    public Map<String, String> includes = new HashMap<>();

    // RSA私钥
    private String rsaPrivateKey = "";

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig config) {
        rsaPrivateKey = config.getInitParameter("rsaPrivateKey");

        String includeParams = config.getInitParameter("urls");
        if (StringUtils.hasValue(includeParams)) {
            this.includes = StringUtils.str2List(includeParams)
                    .stream()
                    .filter(StringUtils::hasValue)
                    .map(p -> StringUtils.str2List(p, ":"))
                    .collect(Collectors.toMap(pl -> pl.get(0), pl -> pl.size() == 2 ? pl.get(1) : ""));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        // 是否在url列表中
        if (!matchUrlAndMethod(req)) {
            chain.doFilter(req, rep);
            return;
        }

        // 获取加密key
        String headerVal = req.getHeader(WebConst.ENCRYPT_HEADER);
        if (StringUtils.isEmpty(headerVal) || StringUtils.isFalse(headerVal)) {
            String url = req.getServletPath();
            throw new EncryptFilterException("Encrypt Filter出错，'" + url + "'需加密访问");
        }

        // 加密密钥、IV
        byte[] keyWithIV = RSA.decryptByPrivateKey2(headerVal, rsaPrivateKey);

        // request解密，response加密
        DecryptHttpServletRequestWrapper decryptReq = new DecryptHttpServletRequestWrapper(req, keyWithIV);
        EncryptHttpServletResponseWrapper encryptRep = new EncryptHttpServletResponseWrapper(rep, keyWithIV);
        chain.doFilter(decryptReq, encryptRep);
        encryptRep.encrypt();
    }

    /**
     * 判断Http请求url与method是否匹配
     *
     * @param request HttpServletRequest
     * @return 匹配，返回true
     */
    private boolean matchUrlAndMethod(HttpServletRequest request) {
        String url = request.getServletPath();
        String method = request.getMethod();

        // OPTIONS不拦截
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return false;
        }

        for (Map.Entry<String, String> entry : this.includes.entrySet()) {
            // url与method都匹配，返回true
            if (antPathMatcher.match(entry.getKey(), url)) {
                if (StringUtils.isEmpty(entry.getValue())) {
                    return true;
                }
                return StringUtils.str2List(entry.getValue(), "\\|")
                        .stream()
                        .filter(StringUtils::hasValue)
                        .anyMatch(m -> m.equalsIgnoreCase(method));
            }
        }

        return false;
    }

    @Override
    public void destroy() {
    }
}
