/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.oauth;

import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.crypto.BASE64;
import net.foundi.common.utils.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * OAuth第三方登录服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface OAuthService {

    Map<String, String> getAuthzUrlAndState(String appName, String ip);

    Map<String, String> getAccessTokenAndOpenId(String code, String state);

    OAuthUser getOAuthUser(String token, String openId);

    void setAuthc(String state, String code);

    String checkAuthc(String state);

    void clearAuthc(String state);

    default String compactState(String appName, String ip) {
        return BASE64.encodeUrl(appName + "_" + ip + "_" + IDUtils.randomString(6));
    }

    default List<String> extractState(String state) {
        state = BASE64.decodeUrl(state);
        if (StringUtils.isEmpty(state)) {
            return null;
        }
        List<String> result = StringUtils.str2List(state, "_");
        return result.size() == 3 ? result : null;
    }
}
