/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.service;

import net.foundi.support.upload.config.OssProperties;
import net.foundi.support.upload.config.OssProvider;
import net.foundi.support.upload.config.UploadPropertyService;

/**
 * OSS文件上传服务工厂类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class UploadServiceFactory {

    private UploadPropertyService uploadPropertyService;

    public UploadServiceFactory(UploadPropertyService uploadPropertyService) {
        this.uploadPropertyService = uploadPropertyService;
    }

    /**
     * 新建一个OSS文件上传服务
     *
     * @return UploadService 对象
     */
    public UploadService build(String ossConfigKey) {
        OssProperties os = this.uploadPropertyService.getEnabledOssConfig(ossConfigKey);
        OssProvider ot = os.getProvider();

        if (OssProvider.ALIYUN.equals(ot)) {
            return new AliyunUploadService(os);
        }

        if (OssProvider.HUAWEI.equals(ot)) {
            return new HuaweiUploadService(os);
        }

        if (OssProvider.QINIU.equals(ot)) {
            return new QiNiuUploadService(os);
        }

        return new LocalUploadService(os);
    }
}
