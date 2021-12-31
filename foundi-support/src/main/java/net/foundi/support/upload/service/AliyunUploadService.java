/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import net.foundi.common.utils.file.FileUtils;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.exception.BusinessException;
import net.foundi.support.upload.config.OssProperties;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 阿里云OSS上传服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class AliyunUploadService implements UploadService {

    private final String accessKeyId;
    private final String accessKeySecret;
    private final String bucketName;
    private final String endpoint;

    private final OSS ossClient;

    public AliyunUploadService(OssProperties properties) {
        this.accessKeyId = properties.get("accessKeyId");
        this.accessKeySecret = properties.get("accessKeySecret");
        this.bucketName = properties.get("bucketName");
        this.endpoint = properties.get("endpoint");
        ossClient = new OSSClientBuilder().build(this.endpoint, this.accessKeyId, this.accessKeySecret);
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
        String key = filePath;
        return upload(bucketName, key, new ByteArrayInputStream(uploadBytes));
    }

    private String upload(String bucketName, String key, InputStream input) {
        try {
            ossClient.putObject(bucketName, key, input);
            LocalDateTime expiration = DateUtils.offset(LocalDateTime.now(), 10, ChronoUnit.YEARS);
            String url = ossClient.generatePresignedUrl(bucketName, key, DateUtils.localToDate(expiration)).toString();
            return url;
        } catch (Exception e) {
            throw new BusinessException("阿里云OSS文件上传出错", e);
        } finally {
            ossClient.shutdown();
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
        List<String> deleted = deleteMulti(Arrays.asList(filePaths), true);
        return deleted.size() == filePaths.length;
    }

    /**
     * 删除多个文件
     *
     * @param filePaths          OSS的文件路径
     * @param deleteEmptyFolders 是否删除空文件夹
     * @return 已经删除的文件
     */
    private List<String> deleteMulti(List<String> filePaths, boolean deleteEmptyFolders) {
        try {
            DeleteObjectsRequest request = new DeleteObjectsRequest(this.bucketName);
            if (deleteEmptyFolders) {
                //TRY to deleting all empty parent folders.
                //The folder not empty will not be deleted according to the rules of OSS server.
                ArrayList<String> fileWithParentPaths = new ArrayList<>();
                filePaths.forEach(f -> {
                    fileWithParentPaths.add(f);
                    fileWithParentPaths.addAll(FileUtils.getParentPaths(f));
                });
                request.setKeys(fileWithParentPaths);
            } else {
                request.setKeys(filePaths);
            }
            request.setQuiet(true);
            DeleteObjectsResult result = ossClient.deleteObjects(request);
            return result.getDeletedObjects();
        } catch (Exception e) {
            throw new BusinessException("阿里云OSS文件删除出错", e);
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 获取OSS文件key
     *
     * @param fileUrl OSS的文件Url
     * @return key
     */
    @Override
    public String getKey(String fileUrl) {
        String ep = this.endpoint;
        if (StringUtils.startWith(ep, "http://")) {
            ep = ep.substring(8);
        } else if (StringUtils.startWith(ep, "https://")) {
            ep = ep.substring(9);
        }
        String key = fileUrl.substring(fileUrl.indexOf(ep) + ep.length() + 1, fileUrl.indexOf("?"));
        return key;
    }

}
