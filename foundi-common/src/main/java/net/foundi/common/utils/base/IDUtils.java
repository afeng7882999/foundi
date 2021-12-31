/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.base;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * ID工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class IDUtils {

    private static final String CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String CODE_NUM = "0123456789";
    private static final SecureRandom RND = new SecureRandom();

    /**
     * 获取UUID
     *
     * @return UUID字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取UUID，不包括 "-".
     *
     * @return UUID字符串
     */
    public static String uuidNoDash() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 根据时间和随机数生成ID
     *
     * @param prefix ID前缀
     * @param suffix ID后缀
     * @return ID
     */
    public static String genMillisId(String prefix, String suffix) {
        StringBuilder sb = new StringBuilder();
        long millis = System.currentTimeMillis();
        Random random = new Random();
        int append = random.nextInt(999);
        return sb.append(prefix)
                .append(millis)
                .append(String.format("%03d", append))
                .append(suffix)
                .toString();
    }

    /**
     * 生成ID，包含字母和数字
     *
     * @param len 长度
     * @return ID
     */
    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(CODE.charAt(RND.nextInt(CODE.length())));
        return sb.toString();
    }

    /**
     * 生成数字ID
     *
     * @param len 长度
     * @return ID
     */
    public static String randomNumber(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(CODE_NUM.charAt(RND.nextInt(CODE_NUM.length())));
        return sb.toString();
    }

}
