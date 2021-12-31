/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.file.FileUtils;
import net.foundi.support.upload.config.OssProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 七牛云OSS上传服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class QiNiuUploadService implements UploadService {

    private UploadManager uploadManager;
    private BucketManager bucketManager;
    private Auth auth;
    private String token;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String accessUrl;

    public QiNiuUploadService(OssProperties props) {
        this.accessKey = props.get("accessKey");
        this.secretKey = props.get("secretKey");
        this.bucket = props.get("bucket");
        this.accessUrl = props.get("accessUrl");
        Configuration cfg = new Configuration(Region.autoRegion());
        this.auth = Auth.create(this.accessKey, this.secretKey);
        this.token = auth.uploadToken(this.bucket);
        this.uploadManager = new UploadManager(cfg);
        this.bucketManager = new BucketManager(this.auth, cfg);
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
            this.uploadManager.put(uploadBytes, key, token);
            return this.accessUrl + filePath;
        } catch (QiniuException e) {
            throw new BusinessException("七牛OSS文件上传出错", e);
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
        List<String> result = deleteMulti(filePaths, true);
        return result.size() == filePaths.length;
    }

    /**
     * 删除多个文件
     *
     * @param filePaths          OSS的文件路径
     * @param deleteEmptyFolders 是否删除空文件夹
     * @return 已经删除的文件
     */
    private List<String> deleteMulti(String[] filePaths, boolean deleteEmptyFolders) {
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            if (deleteEmptyFolders) {
                //TRY to deleting all empty parent folders.
                //The folder not empty will not be deleted according to the rules of OSS server.
                ArrayList<String> fileWithParentPaths = new ArrayList<>();
                Stream.of(filePaths).forEach(f -> {
                    fileWithParentPaths.add(f);
                    fileWithParentPaths.addAll(FileUtils.getParentPaths(f));
                });
                batchOperations.addDeleteOp(this.bucket, fileWithParentPaths.toArray(new String[0]));
            } else {
                batchOperations.addDeleteOp(this.bucket, filePaths);
            }
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            return IntStream.of(0, batchStatusList.length)
                    .filter(i -> batchStatusList[i].code == 200)
                    .mapToObj(i -> filePaths[i])
                    .collect(Collectors.toList());
        } catch (QiniuException e) {
            throw new BusinessException("七牛OSS文件删除出错", e);
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
        String key = fileUrl.substring(this.accessUrl.length());
        return key;
    }

}
