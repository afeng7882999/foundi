/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.foundi.admin.system.dao.OAuthUserDao;
import net.foundi.admin.system.entity.domain.OAuthUserDo;
import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.service.OAuthUserService;
import net.foundi.admin.system.service.RoleService;
import net.foundi.admin.system.service.UserService;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.log.LogUtils;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.security.enums.AccountStatus;
import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.service.BaseServiceImpl;
import net.foundi.support.oauth.OAuthService;
import net.foundi.support.oauth.OAuthUser;
import net.foundi.support.oauth.config.OAuthType;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OAuth用户Service
 *
 * @author Afeng
 */
@Service
public class OAuthUserServiceImpl extends BaseServiceImpl<OAuthUserDao, OAuthUserDo> implements OAuthUserService {


    private final UserService userService;
    private final RoleService roleService;

    public OAuthUserServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public OAuthUserDo getByUserIdAndType(Long userId, OAuthType oAuthType) {
        return this.baseMapper.selectOne(new QueryWrapper<OAuthUserDo>().lambda()
                .eq(OAuthUserDo::getUserId, userId)
                .eq(OAuthUserDo::getOAuthTypeDict, oAuthType.key()));
    }

    @Override
    public List<OAuthUserDo> getByUserId(Long userId) {
        return this.baseMapper.selectList(new QueryWrapper<OAuthUserDo>().lambda()
                .eq(OAuthUserDo::getUserId, userId));
    }

    @Override
    public UserDo getBindUser(String openId, OAuthType oAuthType) {
        OAuthUserDo oAuthUser = this.baseMapper.selectOne(new QueryWrapper<OAuthUserDo>().lambda()
                .eq(OAuthUserDo::getOpenId, openId).eq(OAuthUserDo::getOAuthTypeDict, oAuthType.key()));
        if (oAuthUser == null || oAuthUser.getUserId() == null) {
            return null;
        }
        return userService.getById(oAuthUser.getUserId());
    }

    @Override
    public UserDo bindToUser(Long userId, String openId, OAuthType oAuthType, OAuthUser oAuthUser) {
        String type = oAuthType.key();

        UserDo result;
        if (userId != null) {
            //check exist
            OAuthUserDo exist = this.baseMapper.selectOne(new QueryWrapper<OAuthUserDo>().lambda()
                    .eq(OAuthUserDo::getOAuthTypeDict, type)
                    .eq(OAuthUserDo::getOpenId, openId)
                    .ne(OAuthUserDo::getUserId, userId));
            if (exist != null) {
                throw new BusinessException("OAuth认证信息已经被其他用户占用");
            }
            //delete old one
            this.baseMapper.delete(new QueryWrapper<OAuthUserDo>().lambda()
                    .eq(OAuthUserDo::getUserId, userId).eq(OAuthUserDo::getOAuthTypeDict, type));
            //get bind user
            result = userService.getById(userId);
        } else {
            //delete old one
            this.baseMapper.delete(new QueryWrapper<OAuthUserDo>().lambda()
                    .eq(OAuthUserDo::getOpenId, openId).eq(OAuthUserDo::getOAuthTypeDict, type));
            //create new user
            UserDo user = new UserDo();
            user.setUsername(oAuthUser.getNickName() + "_" + IDUtils.randomString(6));
            user.setAvatar(oAuthUser.getAvatar());
            user.setName(oAuthUser.getNickName());
            user.setGenderDict(oAuthUser.getGender());
            user.setStatusDict(AccountStatus.NORMAL.key());
            //default role
            RoleDo role = roleService.getOne(new QueryWrapper<RoleDo>().lambda().eq(RoleDo::getLabel, "user"));
            user.setRoleList(Collections.singletonList(role));
            result = userService.saveAndGet(user);
        }

        //create OAuth user
        OAuthUserDo oAuth = new OAuthUserDo();
        oAuth.setUserId(result.getId());
        oAuth.setOAuthTypeDict(type);
        oAuth.setAccount(oAuthUser.getNickName());
        oAuth.setAvatar(oAuthUser.getAvatar());
        oAuth.setNickName(oAuthUser.getNickName());
        oAuth.setOpenId(oAuthUser.getOpenid());
        oAuth.setGenderDict(oAuthUser.getGender());
        this.baseMapper.insert(oAuth);

        return result;
    }

    @Override
    public void unbindFromUser(Long userId, OAuthType... oAuthTypes) {
        LambdaQueryWrapper<OAuthUserDo> wrapper = new QueryWrapper<OAuthUserDo>().lambda();
        wrapper = wrapper.eq(OAuthUserDo::getUserId, userId);
        for (OAuthType type : oAuthTypes) {
            wrapper = wrapper.eq(OAuthUserDo::getOAuthTypeDict, type.key());
        }
        this.baseMapper.delete(wrapper);
    }

    @Override
    public boolean currentBindOAuth(OAuthService service, String state, OAuthType oAuthType) {
        String code = service.checkAuthc(state);
        if (StringUtils.isEmpty(code)) {
            return false;
        }
        //clear flag
        service.clearAuthc(state);

        //get user info from OAuth server
        Map<String, String> result = service.getAccessTokenAndOpenId(code, state);
        OAuthUser oAuthUser = service.getOAuthUser(result.get("accessToken"), result.get("openId"));

        //bind to current user
        UserContext current = SecurityUtils.getCurrentUserOrEx();
        Long userId = current.getId();
        this.bindToUser(userId, result.get("openId"), oAuthType, oAuthUser);

        // Success and log
        Map<String, String> params = new HashMap<>();
        params.put("id", userId.toString());
        params.put("username", current.getUsername());
        params.put("nickname", oAuthUser.getNickName());
        params.put("oauthType", oAuthType.val());
        params.put("openId", oAuthUser.getOpenid());
        String method = this.getClass().getName() + ".currentBindOAuth()";
        LogUtils.logOperation("当前用户绑定" + oAuthType.val(), method, params.toString(), "true", null);

        return true;
    }

}