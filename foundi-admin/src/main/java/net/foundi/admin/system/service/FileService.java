/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.FileDo;
import net.foundi.framework.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件上传Service
 *
 * @author Afeng
 */
public interface FileService extends BaseService<FileDo> {

    /**
     * 上传文件
     *
     * @param files 文件数组
     * @return 文件地址列表
     */
    List<String> upload(MultipartFile[] files, String ossConfigKey);

    /**
     * 删除文件
     *
     * @param id           文件ID
     */
    void remove(Long id);

    /**
     * 批量删除文件
     *
     * @param ids          文件ID列表
     */
    void removeBatch(List<Long> ids);
}