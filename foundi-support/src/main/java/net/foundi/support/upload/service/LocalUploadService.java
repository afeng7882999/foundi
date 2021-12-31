/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.service;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.file.FileUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpringWebUtils;
import net.foundi.support.upload.config.OssProperties;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 本地文件上传服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class LocalUploadService implements UploadService {

    private String localUploadDir;

    public LocalUploadService(OssProperties props) {
        this.localUploadDir = props.get("uploadDir");
    }

    /**
     * 上传文件
     *
     * @param uploadBytes 字节数组
     * @param filePath    上传到OSS的文件路径
     * @return 上传完成后OSS的文件Url
     */
    @Override
    public String upload(byte[] uploadBytes, String filePath) {
        String localDir = getLocalDir();
        Path path = Paths.get(filePath);
        String filename = FileUtils.getName(path);
        String pathName = FileUtils.getParent(path).toString();
        try {
            Path dir = Paths.get(localDir + pathName);
            Files.createDirectories(dir);
            Path file = Paths.get(localDir + pathName + "/" + filename);
            Files.write(file, uploadBytes);
            return FoundiConst.LOCAL_UPLOAD_URL + pathName + "/" + filename;
        } catch (IOException e) {
            throw new BusinessException("本地文件上传出错", e);
        }
    }

    /**
     * 删除文件
     *
     * @param filePaths OSS的文件路径
     * @return true：删除成功
     */
    @Override
    public boolean delete(String... filePaths) {
        return Stream.of(filePaths).allMatch(f -> this.deleteOne(f, true));
    }

    /**
     * 删除单个文件
     *
     * @param filePath           OSS的文件路径
     * @param deleteEmptyFolders 是否删除空文件夹
     * @return true：删除成功
     */
    private boolean deleteOne(String filePath, boolean deleteEmptyFolders) {
        if (!filePath.startsWith(localUploadDir)) {
            return false;
        }
        //the file
        filePath = getLocalDir() + filePath.substring(FoundiConst.LOCAL_UPLOAD_URL.length());
        Path file = Paths.get(filePath);
        try {
            // delete file
            if (Files.exists(file)) {
                Files.delete(file);
            }
            //delete parent directories which is empty
            if (deleteEmptyFolders) {
                Path tmp = file.getParent();
                while (tmp.toString().length() > getLocalDir().length()) {
                    if (Files.exists(tmp) && Files.isDirectory(tmp)) {
                        try {
                            Files.delete(tmp);
                        } catch (DirectoryNotEmptyException e) {
                            break;
                        }
                    }
                    tmp = tmp.getParent();
                }
            }
        } catch (IOException e) {
            throw new BusinessException("本地文件删除出错", e);
        }

        return true;
    }

    /**
     * 获取OSS文件key
     *
     * @param fileUrl OSS的文件Url
     * @return key
     */
    @Override
    public String getKey(String fileUrl) {
        return fileUrl;
    }

    /**
     * 获取上传文件的本地目录
     *
     * @return 本地目录
     */
    private String getLocalDir() {
        String localPath = localUploadDir;
        if (StringUtils.isBlank(localPath)) {
            localPath = SpringWebUtils.getRequestFromContext().getServletContext().getRealPath("/") + "upload";
        }
        if (!localPath.endsWith("/")) {
            localPath += "/";
        }
        return localPath;
    }

}
