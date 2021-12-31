/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.base;

import net.foundi.common.utils.crypto.BASE64;
import net.foundi.common.utils.lang.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 图像工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class ImageUtils {

    /**
     * 获取图片BASE64编码
     *
     * @param path 图像文件路径
     * @return BASE64编码
     */
    public static String base64(String path) throws IOException {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        byte[] img = Files.readAllBytes(Paths.get(path));
        return BASE64.encodeUrl(img);
    }

    /**
     * 将BASE64编码解析为图像
     *
     * @param path   图像文件
     * @param base64 BASE64编码
     */
    public static void fromBase64(String path, String base64) throws IOException {
        if (StringUtils.hasValue(path) && StringUtils.hasValue(base64)) {
            Files.write(Paths.get(path), BASE64.decodeUrlToBytes(base64), StandardOpenOption.CREATE);
        }
    }

}
