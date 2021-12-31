/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.encrypt;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.crypto.AES;
import net.foundi.common.utils.lang.JsonUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.web.config.WebConst;
import net.foundi.framework.web.exception.EncryptFilterException;
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
 * Http请求数据解密
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class DecryptHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] encryptKey;

    private Map<String, String[]> params = new LinkedHashMap<>();

    private byte[] body;

    public DecryptHttpServletRequestWrapper(HttpServletRequest request, byte[] encryptKey) {
        super(request);
        this.encryptKey = encryptKey;
        try {
            decryptParams(request);
            decryptBody(request);
        } catch (IOException e) {
            throw new EncryptFilterException("Encrypt Filter request解密出错", e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 仅处理 application/json 请求
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return super.getInputStream();
        }

        // 为空，直接返回
        if (body == null) {
            return super.getInputStream();
        }

        // 返回ServletInputStream
        final ByteArrayInputStream bis = new ByteArrayInputStream(this.body);
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
    }

    @Override
    public String getParameter(String name) {
        String[] values = this.params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.params.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.params;
    }

    /**
     * 解密Http请求参数
     *
     * @param request HttpServletRequest
     */
    private void decryptParams(HttpServletRequest request) throws IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        String[] encParams = parameters.get(WebConst.ENCRYPT_PARAMS_KEY);

        if (encParams == null) {
            this.params = request.getParameterMap();
            return;
        }

        // 参数需要解密
        if (encParams.length == 1 && StringUtils.hasValue(encParams[0])) {
            String jsonParam = decrypt(encParams[0]);
            Map<String, String> paramMap = JsonUtils.toStringMap(jsonParam);
            paramMap.forEach((k, v) -> this.params.put(k, new String[]{v}));
            return;
        }

        this.params = request.getParameterMap();
    }

    /**
     * 解密Http请求body
     *
     * @param request HttpServletRequest
     */
    private void decryptBody(HttpServletRequest request) throws IOException {
        // 为空，直接返回
        String encBody = IOUtils.toString(request.getInputStream(), FoundiConst.DEFAULT_CHARSET);
        if (StringUtils.isBlank(encBody)) {
            return;
        }

        // 解密request body
        if (encBody.startsWith("\"")) {
            // body去除双引号
            encBody = encBody.substring(1, encBody.length() - 1);
        }
        String json = decrypt(encBody);
        this.body = json.getBytes(FoundiConst.DEFAULT_CHARSET);
    }

    /**
     * 解密数据
     *
     * @param encryptedValue 加密后的数据
     * @return 解密后的数据
     */
    private String decrypt(String encryptedValue) {
        return AES.decrypt2(encryptedValue, this.encryptKey);
    }
}
