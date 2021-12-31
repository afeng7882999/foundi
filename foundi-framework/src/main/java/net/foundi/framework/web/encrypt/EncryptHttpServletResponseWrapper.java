/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.web.encrypt;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.crypto.AES;
import net.foundi.framework.web.config.WebConst;
import net.foundi.framework.web.exception.EncryptFilterException;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * Http返回数据加密
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class EncryptHttpServletResponseWrapper extends HttpServletResponseWrapper {

    // 加密密钥
    private final byte[] encryptKey;

    private final ByteArrayOutputStream responseBytes = new ByteArrayOutputStream();

    private PrintWriter printWriter;

    private final HttpServletResponse response;

    public EncryptHttpServletResponseWrapper(HttpServletResponse response, byte[] encryptKey) {
        super(response);
        this.encryptKey = encryptKey;
        this.response = response;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        ByteArrayOutputStream responseBytes = this.responseBytes;
        return new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                responseBytes.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        try {
            this.printWriter = new PrintWriter(new OutputStreamWriter(this.responseBytes, FoundiConst.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return this.printWriter;
    }

    /**
     * 加密Http响应
     */
    public void encrypt() {
        try {
            if (this.printWriter != null) {
                this.printWriter.close();
            } else {
                this.responseBytes.flush();
            }

            byte[] bytes = this.responseBytes.toByteArray();
            byte[] encrypt = AES.encrypt2(bytes, this.encryptKey);

            // 写入response
            this.response.getOutputStream().write(encrypt);
            this.response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, WebConst.ENCRYPT_HEADER);
            this.response.addHeader(WebConst.ENCRYPT_HEADER, "true");

        } catch (Exception e) {
            throw new EncryptFilterException("Encrypt Filter response加密出错", e);
        }
    }

}
