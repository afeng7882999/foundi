/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.oauth.weibo;

import net.foundi.common.enums.Gender;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.exception.BusinessException;
import net.foundi.support.oauth.OAuthService;
import net.foundi.support.oauth.OAuthUser;
import org.springframework.cache.Cache;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微博QQ第三方登录服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class WeiboOAuthService implements OAuthService {

    private static final String AUTHORIZE_URL = "https://api.weibo.com/oauth2/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s";
    private static final String ACCESS_TOKEN_URL = "https://api.weibo.com/oauth2/access_token";
    private static final String USER_INFO_URL = "https://api.weibo.com/2/users/show.json?access_token=%s&uid=%s";

    private WeiboOAuthProperties properties;
    private RestTemplate restTemplate;
    private Cache cache;

    public WeiboOAuthService(WeiboOAuthProperties props, RestTemplate restTemplate, Cache cache) {
        this.properties = props;
        this.restTemplate = restTemplate;
        this.cache = cache;
    }

    /**
     * 返回一个认证URL，以及认证标识
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
        String authzUrl = String.format(AUTHORIZE_URL, properties.getAppKey(), properties.getCallbackUrl(), state);
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
    @SuppressWarnings("unchecked")
    public Map<String, String> getAccessTokenAndOpenId(String code, String state) {
        Map<String, String> map = new HashMap<>();
        try {
            //get access token
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", properties.getAppKey());
            params.add("client_secret", properties.getAppSecret());
            params.add("grant_type", "authorization_code");
            params.add("code", code);
            params.add("redirect_uri", properties.getCallbackUrl());
            Map<String, Object> tokenMap = restTemplate.postForObject(ACCESS_TOKEN_URL, params, Map.class);
            if (tokenMap == null || !tokenMap.containsKey("access_token")) {
                throw new BusinessException("微博用户的Token为空");
            }
            map.put("accessToken", (String) tokenMap.get("access_token"));
            map.put("openId", (String) tokenMap.get("uid"));

            //extract state
            List<String> stateList = extractState(state);
            if (stateList == null) {
                throw new BusinessException("微博用户的state无效, 请重新登录");
            }
            map.put("appName", stateList.get(0));
            map.put("ip", stateList.get(1));

            return map;
        } catch (Exception e) {
            throw new BusinessException("获取微博用户OpenId失败");
        }
    }

    /**
     * 获取用户信息
     *
     * @param weiboToken 令牌
     * @param openId  openId
     * @return OAuthUser对象
     */
    @SuppressWarnings("unchecked")
    public OAuthUser getOAuthUser(String weiboToken, String openId) {
        String url = String.format(USER_INFO_URL, weiboToken, openId);
        try {
            Map<String, Object> res = restTemplate.getForObject(url, Map.class);
            if (res != null) {
                OAuthUser user = new OAuthUser();
                String name = (String) res.get("name");
                user.setNickName(StringUtils.isEmpty(name) ? (String) res.get("screen_name") : name);
                user.setOpenid(openId);
                String img = (String) res.get("avatar_hd");
                if (StringUtils.hasValue(img)) {
                    user.setAvatar(img);
                }
                String gender = (String) res.get("gender");
                user.setGender("m".equals(gender) ? Gender.MALE.key() : Gender.FEMALE.key());
                return user;
            } else {
                throw new BusinessException("获取微博用户信息失败");
            }
        } catch (Exception e) {
            throw new BusinessException("获取微博用户信息失败", e);
        }
    }

    /**
     * 缓存用户认证码
     *
     * @param state 用户标识
     * @param code  用户认证码
     */
    public void setAuthc(String state, String code) {
        String stateKey = properties.getCacheKey() + ":" + state;
        cache.put(stateKey, code);
    }

    /**
     * 判断用户是否登录，返回认证码
     *
     * @param state 用户标识
     * @return 未登录返回null，已登录返回认证码
     */
    public String checkAuthc(String state) {
        String stateKey = properties.getCacheKey() + ":" + state;
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
        String stateKey = properties.getCacheKey() + ":" + state;
        cache.evict(stateKey);
    }

}
