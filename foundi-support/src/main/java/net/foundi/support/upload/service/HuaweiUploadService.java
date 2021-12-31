/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.service;

import com.obs.services.ObsClient;
import com.obs.services.model.DeleteObjectsRequest;
import com.obs.services.model.DeleteObjectsResult;
import com.obs.services.model.PutObjectResult;
import net.foundi.common.utils.file.FileUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.exception.BusinessException;
import net.foundi.support.upload.config.OssProperties;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 华为云OBS上传服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class HuaweiUploadService implements UploadService {

    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String endpoint;

    private ObsClient obsClient;

    public HuaweiUploadService(OssProperties props) {
        this.accessKeyId = props.get("accessKeyId");
        this.accessKeySecret = props.get("accessKeySecret");
        this.bucketName = props.get("bucketName");
        this.endpoint = props.get("endpoint");
        obsClient = new ObsClient(this.accessKeyId, this.accessKeySecret, this.endpoint);
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
        try {
            PutObjectResult result = obsClient.putObject(this.bucketName, key,
                    new ByteArrayInputStream(uploadBytes));
            String uploadUrl = result.getObjectUrl().replaceAll("%2F", "/");
            return uploadUrl;
        } catch (Exception e) {
            throw new BusinessException("华为云OBS文件上传出错", e);
        } finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                fileWithParentPaths.forEach(request::addKeyAndVersion);
            } else {
                filePaths.forEach(request::addKeyAndVersion);
            }
            request.setQuiet(true);
            DeleteObjectsResult result = obsClient.deleteObjects(request);
            return result.getDeletedObjectResults().stream()
                    .filter(d -> d.isDeleteMarker())
                    .map(d -> d.getObjectKey())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new BusinessException("华为云OBS文件删除出错", e);
        } finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        if (StringUtils.startWith(fileUrl, "http://")) {
            fileUrl = fileUrl.substring(8);
        } else if (StringUtils.startWith(fileUrl, "https://")) {
            fileUrl = fileUrl.substring(9);
        }

        String key = fileUrl.substring(fileUrl.indexOf("/") + 1);
        return key;
    }
}
