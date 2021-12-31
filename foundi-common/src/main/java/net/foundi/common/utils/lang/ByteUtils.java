/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.lang;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.base.ExceptionUtils;

import java.io.UnsupportedEncodingException;

/**
 * Byte工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class ByteUtils {

    /**
     * 字节数组转换为16位字符串
     * 如：[35, 43, 109, -120] -> "232b6d88"
     *
     * @param bytes 字节数组
     * @return 16位字符串
     */
    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        int i;
        for (i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10)
                sb.append("0");
            sb.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return sb.toString();
    }

    /**
     * 16位字符串转换为字节数组
     * 如："232b6d88" -> [35, 43, 109, -120].
     *
     * @param hexStr 16位字符串
     * @return 字节数组
     */
    public static byte[] hexStrToBytes(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] bytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            bytes[i] = (byte) (high * 16 + low);
        }
        return bytes;
    }

    /**
     * 字节数组中取4个字节转换为32位整数
     *
     * @param bytes  字节数组
     * @param offset 偏移量
     * @return int(32 - bit)
     */
    public static int bytesToInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xff) << 24) | ((bytes[offset + 1] & 0xff) << 16) |
                ((bytes[offset + 2] & 0xff) << 8) | ((bytes[offset + 3] & 0xff));
    }

    /**
     * 32位整数转换为4个字节，放入字节数组中
     *
     * @param bits   32位整数
     * @param bytes  字节数组
     * @param offset 偏移量
     */
    public static void intToBytes(int bits, byte[] bytes, int offset) {
        bytes[offset + 3] = (byte) bits;
        bytes[offset + 2] = (byte) (bits >> 8);
        bytes[offset + 1] = (byte) (bits >> 16);
        bytes[offset] = (byte) (bits >> 24);
    }

    /**
     * 字节数组中取8个字节转换为64位长整型
     *
     * @param bytes  字节数组
     * @param offset 偏移量
     * @return long(64 - bit)
     */
    public static long bytesToLong(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xffL) << 56) | ((bytes[offset + 1] & 0xffL) << 48) |
                ((bytes[offset + 2] & 0xffL) << 40) | ((bytes[offset + 3] & 0xffL) << 32) |
                ((bytes[offset + 4] & 0xffL) << 24) | ((bytes[offset + 5] & 0xffL) << 16) |
                ((bytes[offset + 6] & 0xffL) << 8) | ((bytes[offset + 7] & 0xffL));
    }

    /**
     * 64位长整型转换为8个字节，放入字节数组中
     *
     * @param bits   64位长整型
     * @param bytes  字节数组
     * @param offset 偏移量
     */
    public static void longToBytes(long bits, byte[] bytes, int offset) {
        bytes[offset + 7] = (byte) bits;
        bytes[offset + 6] = (byte) (bits >> 8);
        bytes[offset + 5] = (byte) (bits >> 16);
        bytes[offset + 4] = (byte) (bits >> 24);
        bytes[offset + 3] = (byte) (bits >> 32);
        bytes[offset + 2] = (byte) (bits >> 40);
        bytes[offset + 1] = (byte) (bits >> 48);
        bytes[offset] = (byte) (bits >> 56);
    }

    /**
     * 判断字节数组是否为空
     *
     * @param bytes 字节数组
     * @return true：为空
     */
    public static boolean isEmpty(byte[] bytes) {
        return (bytes == null || bytes.length == 0);
    }


    /**
     * 以系统默认编码获取字节数组
     *
     * @param strDefaultEncoding 默认编码的字符串
     * @return 字节数组
     */
    public static byte[] getBytes(String strDefaultEncoding) {
        try {
            return strDefaultEncoding.getBytes(FoundiConst.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 获取字节长度字符串
     *
     * @param size 字节大小
     * @return 长度字符串
     */
    public static String sizeToStr(long size) {
        if (size >= FoundiConst.GB) {
            return String.format("%.1f GB", (float) size / FoundiConst.GB);
        }
        if (size >= FoundiConst.MB) {
            float f = (float) size / FoundiConst.MB;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        }
        if (size >= FoundiConst.KB) {
            float f = (float) size / FoundiConst.KB;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        }
        return String.format("%d B", size);
    }

}
