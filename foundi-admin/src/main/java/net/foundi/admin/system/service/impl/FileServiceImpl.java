/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import net.foundi.admin.system.dao.FileDao;
import net.foundi.admin.system.entity.domain.FileDo;
import net.foundi.admin.system.service.FileService;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.file.FileTypeUtils;
import net.foundi.common.utils.file.FileUtils;
import net.foundi.framework.service.BaseServiceImpl;
import net.foundi.support.upload.service.UploadService;
import net.foundi.support.upload.service.UploadServiceFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* 文件上传Service
*
* @author Afeng
*/
@Service
public class FileServiceImpl extends BaseServiceImpl<FileDao, FileDo> implements FileService {


    private final UploadServiceFactory uploadServiceFactory;

    public FileServiceImpl(UploadServiceFactory uploadServiceFactory) {
        this.uploadServiceFactory = uploadServiceFactory;
    }

    @Override
    public List<String> upload(MultipartFile[] files, String ossConfigKey) {
        List<String> urlList = new ArrayList<>();
        UploadService uploadService = uploadServiceFactory.build(ossConfigKey);
        for (MultipartFile file : files) {
            String filename = getRandomFilename(file.getOriginalFilename());
            String url = "";
            try {
                url = uploadService.upload(file.getBytes(), FileUtils.generatePathNameByTime() + "/" + filename);
            } catch (Exception e) {
                throw new BusinessException("上传文件出错", e);
            }

            FileDo aDo = new FileDo();
            aDo.setName(filename);
            aDo.setOss(ossConfigKey);
            aDo.setTypeDict(FileTypeUtils.getByName(filename).key());
            aDo.setUrl(url);
            this.save(aDo);
            urlList.add(url);
        }
        return urlList;
    }

    /**
     * 文件名中加入随机字符串
     *
     * @param path 原文件路径
     * @return 生成的文件名
     */
    private String getRandomFilename(String path) {
        String filename = FileUtils.getName(path);
        return FileUtils.getBaseName(filename) + "_fd" +
                IDUtils.randomNumber(6) + "." +
                FileUtils.getExtName(filename);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        try {
            FileDo aDo = baseMapper.selectById(id);
            baseMapper.deleteById(id);

            if (aDo == null) {
                return;
            }
            String ossConfigKey = aDo.getOss();
            UploadService uploadService = uploadServiceFactory.build(ossConfigKey);
            uploadService.delete(uploadService.getKey(aDo.getUrl()));
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBatch(List<Long> ids) {
        try {
            List<FileDo> dos = baseMapper.selectBatchIds(ids);
            baseMapper.deleteBatchIds(ids);

            List<String> ossConfigKeys = dos.stream().map(FileDo::getOss).distinct().collect(Collectors.toList());
            for (String ossConfigKey : ossConfigKeys) {
                UploadService uploadService = uploadServiceFactory.build(ossConfigKey);
                String[] urls = dos.stream()
                        .filter(f -> ossConfigKey.equals(f.getOss()))
                        .map(FileDo::getUrl)
                        .map(uploadService::getKey)
                        .toArray(String[]::new);
                uploadService.delete(urls);
            }
        } catch (Exception e) {
            throw new BusinessException("删除项目出错", e);
        }
    }

}