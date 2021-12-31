/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.crypto;

import net.foundi.common.constant.FoundiConst;

import java.io.UnsupportedEncodingException;

/**
 * BASE64编码算法
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class BASE64 {

    private static final String ENCODE_EXCEPTION = "当前运行环境不支持BASE64的" + FoundiConst.DEFAULT_CHARSET + "编码";
    private static final String NO_INTACT_EXCEPTION = "字符串不是BASE64编码，解码失败";

    /**
     * 字符串进行BASE64编码
     *
     * @param original 未编码字符串
     * @return Base64编码字符串
     */
    public static String encode(String original) {
        try {
            return java.util.Base64.getEncoder().encodeToString(original.getBytes(FoundiConst.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new CryptoException(ENCODE_EXCEPTION, e);
        }
    }

    /**
     * 字节数组进行BASE64编码
     *
     * @param originalBytes 字节数组
     * @return Base64编码字符串
     */
    public static String encode(byte[] originalBytes) {
        return java.util.Base64.getEncoder().encodeToString(originalBytes);
    }

    /**
     * BASE64字符串解码
     *
     * @param encodedString 编码字符串
     * @return 解码的字符串
     */
    public static String decode(String encodedString) {
        try {
            byte[] encodedBytes = encodedString.getBytes(FoundiConst.DEFAULT_CHARSET);
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(encodedBytes);
            return new String(decodedBytes, FoundiConst.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new CryptoException(ENCODE_EXCEPTION, e);
        } catch (IllegalArgumentException e) {
            throw new CryptoException(NO_INTACT_EXCEPTION, e);
        }
    }

    /**
     * BASE64编码的byte数组解码
     *
     * @param encodedBytes 编码的byte数组
     * @return 解码的byte数组
     */
    public static byte[] decode(byte[] encodedBytes) {
        try {
            return java.util.Base64.getDecoder().decode(encodedBytes);
        } catch (IllegalArgumentException e) {
            throw new CryptoException(NO_INTACT_EXCEPTION, e);
        }
    }

    /**
     * BASE64字符串解码，解码为字节数组
     *
     * @param encodedString 编码字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeToBytes(String encodedString) {
        try {
            return java.util.Base64.getDecoder().decode(encodedString.getBytes(FoundiConst.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new CryptoException(ENCODE_EXCEPTION, e);
        } catch (IllegalArgumentException e) {
            throw new CryptoException(NO_INTACT_EXCEPTION, e);
        }
    }

    /**
     * 字符串进行BASE64编码，url友好
     *
     * @param original 未编码字符串
     * @return Base64编码字符串
     */
    public static String encodeUrl(String original) {
        try {
            return java.util.Base64.getUrlEncoder().encodeToString(original.getBytes(FoundiConst.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new CryptoException(ENCODE_EXCEPTION, e);
        }
    }

    /**
     * 字符串进行BASE64编码，url友好
     *
     * @param original 未编码字符串
     * @return Base64编码字符数组
     */
    public static byte[] encodeUrlToBytes(byte[] original) {
        return java.util.Base64.getUrlEncoder().encode(original);
    }

    /**
     * 字节数组进行BASE64编码，url友好
     *
     * @param originalBytes 字节数组
     * @return Base64编码字符串
     */
    public static String encodeUrl(byte[] originalBytes) {
        return java.util.Base64.getUrlEncoder().encodeToString(originalBytes);
    }

    /**
     * 对url友好的BASE64字符串解码
     *
     * @param encodedSafeString 编码字符串
     * @return 解码后的字符串
     */
    public static String decodeUrl(String encodedSafeString) {
        try {
            byte[] encodeBytes = java.util.Base64.getUrlDecoder().decode(encodedSafeString.getBytes(FoundiConst.DEFAULT_CHARSET));
            return new String(encodeBytes, FoundiConst.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new CryptoException(ENCODE_EXCEPTION, e);
        } catch (IllegalArgumentException e) {
            throw new CryptoException(NO_INTACT_EXCEPTION, e);
        }
    }

    /**
     * 对url友好的BASE64字符串解码，解码为字节数组
     *
     * @param encodedSafeString 编码字符串
     * @return 解码后的字节数组
     */
    public static byte[] decodeUrlToBytes(String encodedSafeString) {
        try {
            return java.util.Base64.getUrlDecoder().decode(encodedSafeString.getBytes(FoundiConst.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            throw new CryptoException(ENCODE_EXCEPTION, e);
        } catch (IllegalArgumentException e) {
            throw new CryptoException(NO_INTACT_EXCEPTION, e);
        }
    }

    /**
     * 对url友好的BASE64字符串解码，解码为字节数组
     *
     * @param encodedSafeBytes 编码字符数组
     * @return 解码后的字节数组
     */
    public static byte[] decodeUrlToBytes(byte[] encodedSafeBytes) {
        try {
            return java.util.Base64.getUrlDecoder().decode(encodedSafeBytes);
        } catch (IllegalArgumentException e) {
            throw new CryptoException(NO_INTACT_EXCEPTION, e);
        }
    }

    public static void main(String[] args) {
        System.out.println(encodeUrl("admin:weixin:admin"));
    }

}
