/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.monitor.service.impl;

import net.foundi.admin.monitor.config.DataSourceProperties;
import net.foundi.admin.monitor.service.DataSourceService;
import net.foundi.common.exception.BusinessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据源监控信息（ Druid ）Service
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    public static final String[] DATA_SOURCE_INFO_TYPES = {"basic", "datasource", "sql", "wall", "webapp", "weburi", "spring", "reset-all"};

    private final RestTemplate restTemplate;
    private final DataSourceProperties dataSourceProperties;

    public DataSourceServiceImpl(RestTemplate restTemplate, DataSourceProperties dataSourceProperties) {
        this.restTemplate = restTemplate;
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    public Map<String, String> getInfoByType(List<String> types) {
        return null;
    }

    @Override
    public String getFullSqlById(String id) {
        return null;
    }

    @Override
    public String getWebUriDetailById(String id) {
        return null;
    }

    private String getUrl(String append) {
        return "http://localhost:" + dataSourceProperties.getServerPort() + "/druid/" + append;
    }

    private String loginDruidAndGetCookie() {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("loginUsername", dataSourceProperties.getDruidLoginUsername());
        map.add("loginPassword", dataSourceProperties.getDruidLoginPassword());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(getUrl("login"), request, String.class);
        return Objects.requireNonNull(response.getHeaders().get("Set-Cookie")).get(0);
    }

    private String getJsonByType(String type) {
        HttpHeaders headers = new HttpHeaders();
        List<String> cookies = new ArrayList<>();
        try {
            cookies.add("XXL_JOB_LOGIN_IDENTITY=6333303830376536353837616465323835626137616465396638383162336437; Path=/; HttpOnly");
            headers.put(HttpHeaders.COOKIE, cookies);
            return restTemplate.getForObject(getUrl(type + ".json"), String.class);

        } catch (Exception e) {
            throw new BusinessException("未能获取数据源监控信息", e);
        }
    }
}
