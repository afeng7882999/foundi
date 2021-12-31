/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import cn.hutool.core.io.resource.ClassPathResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.foundi.admin.system.dao.ConfigDao;
import net.foundi.admin.system.entity.domain.ConfigDo;
import net.foundi.admin.system.entity.enums.ConfigType;
import net.foundi.admin.system.service.ConfigService;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.lang.AssertUtils;
import net.foundi.common.utils.lang.JsonUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.service.BaseServiceImpl;
import net.foundi.support.sms.config.SmsProperties;
import net.foundi.support.sms.config.SmsPropertyService;
import net.foundi.support.sms.config.SmsServiceProvider;
import net.foundi.support.upload.config.OssProperties;
import net.foundi.support.upload.config.OssProvider;
import net.foundi.support.upload.config.UploadPropertyService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统配置Service
 *
 * @author Afeng
 */
@Service
public class ConfigServiceImpl extends BaseServiceImpl<ConfigDao, ConfigDo> implements ConfigService,
        UploadPropertyService, SmsPropertyService {

    @Override
    public String getEnabledByKey(String key) {
        try {
            ConfigDo aDo = baseMapper.selectOne(new QueryWrapper<ConfigDo>().lambda()
                    .eq(ConfigDo::getConfigKey, key)
                    .eq(ConfigDo::getEnabled, true));
            AssertUtils.getNull(aDo).ex(() -> new BusinessException(
                    StringUtils.format("获取配置：{}出错", key)));
            return aDo.getConfigValue();
        } catch (Exception e) {
            throw new BusinessException(StringUtils.format("获取配置：{}出错", key));
        }
    }

    @Override
    public OssProperties getEnabledOssConfig(String ossConfigKey) {
        OssProperties properties = new OssProperties();
        try {
            ConfigDo aDo = baseMapper.selectOne(new QueryWrapper<ConfigDo>().lambda()
                    .eq(ConfigDo::getConfigKey, ossConfigKey)
                    .eq(ConfigDo::getEnabled, true));
            AssertUtils.getNull(aDo).ex(() -> new BusinessException(
                    StringUtils.format("获取OSS配置：{}出错", ossConfigKey)));

            // 所有配置项
            Map<String, String> map = JsonUtils.toStringMap(aDo.getConfigValue());

            // Provider
            if (ConfigType.OSS_ALIYUN.key().equals(aDo.getConfigTypeDict())) {
                properties.setProvider(OssProvider.ALIYUN);
            } else if (ConfigType.OSS_HUAWEI.key().equals(aDo.getConfigTypeDict())) {
                properties.setProvider(OssProvider.HUAWEI);
            } else if (ConfigType.OSS_QINIU.key().equals(aDo.getConfigTypeDict())) {
                properties.setProvider(OssProvider.QINIU);
            } else {
                properties.setProvider(OssProvider.LOCAL);
            }
            properties.setProperties(map);

            return properties;
        } catch (IOException e) {
            throw new BusinessException(StringUtils.format("获取OSS配置：{}出错", ossConfigKey));
        }
    }

    @Override
    public SmsProperties getEnabledSmsConfig() {
        SmsProperties properties = new SmsProperties();
        try {
            ConfigDo aDo = baseMapper.selectOne(new QueryWrapper<ConfigDo>().lambda()
                    .in(ConfigDo::getConfigTypeDict, ConfigType.SMS_ALIYUN.key(), ConfigType.SMS_HUAWEI.key())
                    .eq(ConfigDo::getEnabled, true));
            AssertUtils.getNull(aDo).ex(() -> new BusinessException("获取SMS配置出错"));

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(aDo.getConfigValue());
            root.fields().forEachRemaining(f -> {
                if ("scenes".equals(f.getKey())) {
                    // scene配置
                    setSmsPropertyScenes(properties, f.getValue());
                } else {
                    // 其他配置项
                    properties.add(f.getKey(), f.getValue().asText());
                }
            });

            // Provider
            if (ConfigType.SMS_ALIYUN.key().equals(aDo.getConfigTypeDict())) {
                properties.setProvider(SmsServiceProvider.ALIYUN);
            } else {
                properties.setProvider(SmsServiceProvider.HUAWEI);
            }

            return properties;
        } catch (IOException e) {
            throw new BusinessException("获取SMS配置出错");
        }
    }

    @Override
    @Cacheable(value = "config_resource", key = "#path")
    public String getConfigFromResource(String path) {
        return new ClassPathResource(path).readUtf8Str();
    }

    /**
     * 解析Json中的scene字段，并为SmsProperties赋值
     *
     * @param properties SmsProperties
     * @param node JsonNode
     */
    private void setSmsPropertyScenes(SmsProperties properties, JsonNode node) {
        List<String> scenes = new ArrayList<>();
        node.fieldNames().forEachRemaining(scenes::add);
        for (String scene: scenes) {
            node.get(scene).fields().forEachRemaining(f -> {
                properties.addScene(scene, f.getKey(), f.getValue().asText());
            });
        }
    }
}