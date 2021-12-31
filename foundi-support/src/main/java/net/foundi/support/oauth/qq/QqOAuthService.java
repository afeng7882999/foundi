/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.oauth.qq;

import net.foundi.common.utils.lang.JsonUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.enums.Gender;
import net.foundi.support.oauth.OAuthService;
import net.foundi.support.oauth.OAuthUser;
import org.springframework.cache.Cache;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QQ第三方登录服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class QqOAuthService implements OAuthService {

    private static final String AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s&scope=%s";
    private static final String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
    private static final String OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String USER_INFO_URL = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";

    private QqOAuthProperties properties;
    private RestTemplate restTemplate;
    private Cache cache;

    public QqOAuthService(QqOAuthProperties props, RestTemplate restTemplate, Cache cache) {
        this.properties = props;
        this.restTemplate = restTemplate;
        this.cache = cache;
    }

    /**
     * 返回一个认证URL，以及用户标识
     *
     * @param appName app终端名称
     * @param ip      用户IP
     * @return 包含2个元素的Map：state、authzUrl
     */
    public Map<String, String> getAuthzUrlAndState(String appName, String ip) {
        Map<String, String> map = new HashMap<>();
        String state = compactState(appName, ip);
        map.put("state", state);
        this.setAuthc(state, "");
        String authzUrl = String.format(AUTHORIZE_URL, properties.getAppId(), properties.getCallbackUrl(), state,
                properties.getScope());
        map.put("authzUrl", authzUrl);
        return map;
    }

    /**
     * 获取 access token 和 openId
     *
     * @param code  认证码
     * @param state 用户标识
     * @return 包含4个元素的Map：accessToken、openId、appName、ip
     */
    public Map<String, String> getAccessTokenAndOpenId(String code, String state) {
        Map<String, String> map = new HashMap<>();
        String tokenUrl = String.format(ACCESS_TOKEN_URL, properties.getAppId(), properties.getAppKey(), code,
                properties.getCallbackUrl());
        try {
            //get access token
            String tokenRes = restTemplate.getForObject(tokenUrl, String.class);
            if (tokenRes == null || !tokenRes.contains("access_token")) {
                throw new BusinessException("QQ用户的Token为空");
            }
            String accessToken = StringUtils.subStringFirst(tokenRes, "(?<=access_token=)\\w*(?=&)");

            //get openId
            String openIdUrl = String.format(OPEN_ID_URL, accessToken);
            String openIdRes = restTemplate.getForObject(openIdUrl, String.class);
            if (openIdRes == null || !openIdRes.contains("openid")) {
                throw new BusinessException("获取QQ用户OpenId失败");
            }
            String openId = StringUtils.subStringFirst(openIdRes, "(?<=\"openid\":\")\\w*(?=\"})");

            //extract state
            List<String> stateList = extractState(state);
            if (stateList == null) {
                throw new BusinessException("QQ用户的state无效, 请重新登录");
            }

            map.put("accessToken", accessToken);
            map.put("openId", openId);
            map.put("appName", stateList.get(0));
            map.put("ip", stateList.get(1));
            return map;
        } catch (Exception e) {
            throw new BusinessException("获取QQ用户OpenId失败");
        }
    }

    /**
     * 获取用户信息
     *
     * @param qqToken 令牌
     * @param openId  openId
     * @return OAuthUser对象
     */
    public OAuthUser getOAuthUser(String qqToken, String openId) {
        String url = String.format(USER_INFO_URL, qqToken, properties.getAppId(), openId);
        try {
            String res = restTemplate.getForObject(url, String.class);
            Map<String, Object> json = JsonUtils.toMap(res);
            if ((Integer) json.get("ret") == 0) {
                OAuthUser user = new OAuthUser();
                user.setNickName((String) json.get("nickname"));
                user.setOpenid(openId);
                String img = (String) json.get("figureurl_qq");
                if (StringUtils.hasValue(img)) {
                    user.setAvatar(img);
                }
                String gender = (String) json.get("gender");
                user.setGender("男".equals(gender) ? Gender.MALE.key() : Gender.FEMALE.key());
                return user;
            } else {
                throw new BusinessException("获取QQ用户信息失败", json.get("msg"));
            }
        } catch (Exception e) {
            throw new BusinessException("获取QQ用户信息失败", e);
        }
    }

    /**
     * 缓存用户认证码
     *
     * @param state 用户标识
     * @param code  用户认证码
     */
    public void setAuthc(String state, String code) {
        String stateKey = properties.getCacheKey() + state;
        cache.put(stateKey, code);
    }

    /**
     * 判断用户是否登录，返回认证码
     *
     * @param state 用户标识
     * @return 未登录返回null，已登录返回认证码
     */
    public String checkAuthc(String state) {
        String stateKey = properties.getCacheKey() + state;
        Cache.ValueWrapper valueWrapper = cache.get(stateKey);
        if (valueWrapper != null) {
            return (String) valueWrapper.get();
        }
        return null;
    }

    /**
     * 清除用户认证码
     *
     * @param state 用户标识
     */
    public void clearAuthc(String state) {
        String stateKey = properties.getCacheKey() + state;
        cache.evict(stateKey);
    }

}
