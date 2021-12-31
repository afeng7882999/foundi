/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.web;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.file.FileTypeUtils;
import net.foundi.common.utils.file.FileUtils;
import net.foundi.common.utils.lang.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 文件上传、下载工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class MultipartUtils {

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension        扩展名
     * @param allowedExtension 允许的扩展名数组
     * @return true：MIME类型允许
     */
    public static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String type = file.getContentType();
        if (StringUtils.isEmpty(extension) && type != null) {
            extension = FileTypeUtils.getExtension(type);
        }
        return extension;
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    /**
     * 下载文件
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param file     File
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file,
                                    boolean deleteOnExit)
            throws IOException {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

        try (FileInputStream fis = new FileInputStream(file)) {
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } finally {
            if (deleteOnExit) {
                file.deleteOnExit();
            }
        }
    }

    /**
     * 字节作为文件下载
     *
     * @param response HttpServletResponse
     * @param bytes 字节数组
     * @param filename 文件名
     */
    public static void downloadBytes(HttpServletResponse response, byte[] bytes, String filename)
            throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.addHeader("Content-Length", String.valueOf(bytes.length));
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(bytes, response.getOutputStream());
    }

    /**
     * 导出Excel
     *
     * @param list     需要导出的数据
     * @param response HttpServletResponse
     */
    public static void downloadExcel(List<Map<String, Object>> list, HttpServletResponse response) throws IOException {
        String tempPath = FileUtils.getTempDir("") + IDUtils.uuidNoDash() + ".xlsx";
        File file = new File(tempPath);
        BigExcelWriter writer = ExcelUtil.getBigWriter(file);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=file.xlsx");
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out, true);
        }
        file.deleteOnExit();
    }
}