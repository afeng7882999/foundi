/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.upload.config;

/**
 * 文件上传服务接口
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface UploadPropertyService {

    /**
     * 由数据库获取激活的OSS配置参数
     *
     * @param ossConfigKey OSS配置键值
     * @return OssProperties对象
     */
    OssProperties getEnabledOssConfig(String ossConfigKey);

}
