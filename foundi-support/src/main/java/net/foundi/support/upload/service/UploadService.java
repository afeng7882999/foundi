/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.service;

/**
 * OSS文件上传服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface UploadService {

    /**
     * 上传文件
     *
     * @param uploadBytes 字节数组
     * @param filePath    上传到OSS的文件路径
     * @return 上传完成后OSS的文件Url
     */
    String upload(byte[] uploadBytes, String filePath);

    /**
     * 删除文件
     *
     * @param filePaths OSS的文件路径
     * @return true：删除成功
     */
    boolean delete(String... filePaths);

    /**
     * 获取OSS文件key
     *
     * @param fileUrl OSS的文件Url
     * @return key
     */
    String getKey(String fileUrl);

}
