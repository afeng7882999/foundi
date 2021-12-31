/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.file;

import java.util.Arrays;

/**
 * 文件类型工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class FileTypeUtils {
    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";

    public static final String[] IMG_FILES = {"bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx",
            "svg", "psd", "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf"};

    public static final String[] DOC_FILES = {"txt", "doc", "docx", "xls", "htm", "html", "jsp", "rtf", "wpd", "pdf",
            "ppt"};

    public static final String[] VIDEO_FILES = {"mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb",
            "webm"};

    public static final String[] AUDIO_FILES = {"mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf",
            "ape", "mid", "ogg", "m4a", "vqf"};

    public static final String[] THUMB_IMG_FILES = {"bmp", "jpg", "jpeg", "png", "tiff", "gif", "svg"};


    /**
     * 通过扩展名获取文件类型
     *
     * @param fileName 文件名
     * @return 类型字典枚举
     */
    public static FileType getByName(String fileName) {
        if (fileName == null) {
            return FileType.UNKNOWN;
        }

        String ext = FileUtils.getExtName(fileName);
        if (Arrays.asList(IMG_FILES).contains(ext)) {
            return FileType.IMAGE;
        } else if (Arrays.asList(DOC_FILES).contains(ext)) {
            return FileType.DOCUMENT;
        } else if (Arrays.asList(AUDIO_FILES).contains(ext)) {
            return FileType.AUDIO;
        } else if (Arrays.asList(VIDEO_FILES).contains(ext)) {
            return FileType.VIDEO;
        } else {
            return FileType.UNKNOWN;
        }
    }

    /**
     * 通过HTTP编码格式获取扩展名
     *
     * @param code 编码格式
     * @return 扩展名
     */
    public static String getExtension(String code) {
        switch (code) {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            default:
                return "";
        }
    }

}
