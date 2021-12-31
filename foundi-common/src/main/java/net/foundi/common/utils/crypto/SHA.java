/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.crypto;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.lang.StringUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA摘要算法
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class SHA {

    // 默认摘要算法
    private static final String DEFAULT_DIGEST_ALGORITHM = "SHA-256";

    // 默认迭代次数
    private static final int DEFAULT_ITERATION = 16;

    /**
     * 生成字符串信息摘要，BASE64编码
     *
     * @param input 原文
     * @param salt  加盐
     * @return SHA摘要字符串
     */
    public static String digest(String input, String salt) {
        return BASE64.encode(digest(DEFAULT_DIGEST_ALGORITHM, input, salt, DEFAULT_ITERATION));
    }

    /**
     * 生成字符串信息摘要，BASE64编码
     *
     * @param input     原文
     * @param salt      加盐
     * @param iteration 迭代次数
     * @return SHA摘要字符串
     */
    public static String digest(String input, String salt, int iteration) {
        return BASE64.encode(digest(DEFAULT_DIGEST_ALGORITHM, input, salt, iteration));
    }

    /**
     * 生成字符串信息摘要，BASE64编码
     *
     * @param input     原文
     * @param salt      加盐
     * @param iteration 迭代次数
     * @return SHA散列后的摘要，字节数组
     */
    public static byte[] digest(String algorithm, String input, String salt, int iteration) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);

            if (StringUtils.hasValue(salt)) {
                md.reset();
                md.update(salt.getBytes(FoundiConst.DEFAULT_CHARSET));
            }

            byte[] hashed = md.digest(input.getBytes(FoundiConst.DEFAULT_CHARSET));
            int it = iteration - 1;

            for (int i = 0; i < it; ++i) {
                md.reset();
                hashed = md.digest(hashed);
            }

            return hashed;

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            String msg = "生成 '" + algorithm + "' 的SHA摘要失败";
            throw new CryptoException(msg, e);
        }
    }

    /**
     * 生成文件信息摘要，BASE64编码
     *
     * @param file 需处理的文件
     * @param salt 加盐
     * @return SHA摘要字符串
     */
    public static String digest(File file, String salt) throws IOException {
        return BASE64.encode(digest(DEFAULT_DIGEST_ALGORITHM, file));
    }

    /**
     * 生成文件信息摘要
     *
     * @param file      需处理的文件
     * @param algorithm 指定摘要算法
     * @return 摘要，字节数组
     */
    public static byte[] digest(String algorithm, File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            return digest(algorithm, is);
        }
    }

    /**
     * 生成输入流的摘要
     *
     * @param algorithm 指定摘要算法
     * @param is        InputStream对象
     * @return 摘要，字节数组
     */
    public static byte[] digest(String algorithm, InputStream is) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] buffer = new byte[4096];
            int read;
            while ((read = is.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            String msg = "当前运行环境不支持'" + algorithm + "'摘要算法";
            throw new CryptoException(msg, e);
        }
    }
}
