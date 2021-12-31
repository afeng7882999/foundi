/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.oauth.weixin;

import net.foundi.common.enums.Gender;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.lang.JsonUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.support.oauth.OAuthService;
import net.foundi.support.oauth.OAuthUser;
import org.springframework.cache.Cache;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信第三方登录服务
 *
 * @author Afeng (afeng7882999@163.com)
 * @since 2019/7/19
 */
public class WeixinOAuthService implements OAuthService {

    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    private static final String ACCESS_TOKEN_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    private WeixinOAuthProperties properties;
    private RestTemplate restTemplate;
    private Cache cache;

    public WeixinOAuthService(WeixinOAuthProperties props, RestTemplate restTemplate, Cache cache) {
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
        String authzUrl = String.format(AUTHORIZE_URL, properties.getAppId(), properties.getCallbackUrl(), properties.getScope(),
                state);
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
        String tokenUrl = String.format(ACCESS_TOKEN_OPENID_URL, properties.getAppId(), properties.getAppSecret(),
                code);

        try {
            String tokenRes = restTemplate.getForObject(tokenUrl, String.class);
            if (tokenRes != null && tokenRes.contains("access_token")) {
                Map<String, String> tokenMap = JsonUtils.toStringMap(tokenRes);
                map.put("accessToken", tokenMap.get("access_token"));
                map.put("openId", tokenMap.get("openid"));
                List<String> stateList = extractState(state);

                if (stateList == null) {
                    throw new BusinessException("微信用户的state无效, 请重新登录");
                }

                map.put("appName", stateList.get(0));
                map.put("ip", stateList.get(1));

            } else {
                throw new BusinessException("微信用户的Token为空");
            }
            return map;
        } catch (Exception e) {
            throw new BusinessException("获取微信用户OpenId失败");
        }
    }

    /**
     * 获取用户信息
     *
     * @param weixinToken 令牌
     * @param openId  openId
     * @return OAuthUser对象
     */
    public OAuthUser getOAuthUser(String weixinToken, String openId) {
        String url = String.format(USER_INFO_URL, weixinToken, openId);
        try {
            String res = restTemplate.getForObject(url, String.class);
            Map<String, Object> json = JsonUtils.toMap(res);
            if (json.get("errcode") == null) {
                OAuthUser user = new OAuthUser();
                user.setNickName((String) json.get("nickname"));
                user.setOpenid(openId);
                String img = (String) json.get("headimgurl");
                if (StringUtils.hasValue(img)) {
                    user.setAvatar(img);
                }
                Integer sex = (Integer) json.get("sex");
                user.setGender(sex == 0 ? Gender.MALE.key() : Gender.FEMALE.key());
                return user;
            } else {
                throw new BusinessException("获取微信用户信息失败", json.get("errmsg"));
            }
        } catch (Exception e) {
            throw new BusinessException("获取微信用户信息失败", e);
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
