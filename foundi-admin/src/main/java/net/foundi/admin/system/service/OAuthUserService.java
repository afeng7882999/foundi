/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.OAuthUserDo;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.framework.service.BaseService;
import net.foundi.support.oauth.OAuthService;
import net.foundi.support.oauth.OAuthUser;
import net.foundi.support.oauth.config.OAuthType;

import java.util.List;

/**
 * OAuth用户Service
 *
 * @author Afeng
 */
public interface OAuthUserService extends BaseService<OAuthUserDo> {

    /**
     * 获取特定用户的OAuth信息
     *
     * @param userId    用户ID
     * @param oAuthType 认证类型
     * @return OAuthUserDo对象
     */
    OAuthUserDo getByUserIdAndType(Long userId, OAuthType oAuthType);

    /**
     * 获取特定用户的OAuth信息
     *
     * @param userId 用户ID
     * @return OAuthUserDo对象列表
     */
    List<OAuthUserDo> getByUserId(Long userId);

    /**
     * 获取openId绑定的用户
     *
     * @param openId    OpenId
     * @param oAuthType 认证类型
     * @return UserDo对象
     */
    UserDo getBindUser(String openId, OAuthType oAuthType);

    /**
     * 将OAuth信息绑定至特定用户
     *
     * @param userId    用户ID
     * @param openId    openId
     * @param oAuthType 认证类型
     * @param oAuthUser OAuthUser对象
     * @return UserDo对象
     */
    UserDo bindToUser(Long userId, String openId, OAuthType oAuthType, OAuthUser oAuthUser);

    /**
     * 删除特定用户的OAuth信息
     *
     * @param userId     用户ID
     * @param oAuthTypes 多个认证类型
     */
    void unbindFromUser(Long userId, OAuthType... oAuthTypes);

    /**
     * 将OAuth信息绑定到当前用户
     *
     * @param service   OAuthService对象
     * @param state     认证标志
     * @param oAuthTypes AuthcType
     * @return boolean
     */
    boolean currentBindOAuth(OAuthService service, String state, OAuthType oAuthTypes);

}