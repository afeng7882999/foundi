/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.foundi.admin.system.entity.domain.RoleDo;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.entity.dto.CaptchaDto;
import net.foundi.admin.system.entity.dto.LoginDto;
import net.foundi.admin.system.entity.dto.RegisterDto;
import net.foundi.admin.system.entity.dto.ResetPasswordDto;
import net.foundi.admin.system.entity.enums.ResetPasswordType;
import net.foundi.admin.system.service.*;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.base.ThreadUtils;
import net.foundi.common.utils.crypto.BASE64;
import net.foundi.common.utils.lang.AssertUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpringAppUtils;
import net.foundi.common.utils.spring.SpringWebUtils;
import net.foundi.framework.security.SecurityUtils;
import net.foundi.framework.security.captcha.CaptchaService;
import net.foundi.framework.security.enums.AccountStatus;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.security.exception.CaptchaException;
import net.foundi.framework.security.service.AuthcService;
import net.foundi.support.email.EMailValidator;
import net.foundi.support.oauth.OAuthService;
import net.foundi.support.oauth.OAuthUser;
import net.foundi.support.oauth.config.OAuthType;
import net.foundi.support.sms.service.SmsValidator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录、注册Service实现
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final AuthcService authcService;
    private final UserService userService;
    private final RoleService roleService;
    private final MenuService menuService;
    private final OAuthUserService oAuthUserService;
    private final SmsValidator smsValidator;
    private final EMailValidator eMailValidator;
    private final CaptchaService captchaService;

    public LoginServiceImpl(AuthcService authcService,
                            UserService userService,
                            RoleService roleService,
                            MenuService menuService, OAuthUserService oAuthUserService,
                            SmsValidator smsValidator,
                            EMailValidator eMailValidator,
                            CaptchaService captchaService) {
        this.authcService = authcService;
        this.userService = userService;
        this.roleService = roleService;
        this.menuService = menuService;
        this.oAuthUserService = oAuthUserService;
        this.smsValidator = smsValidator;
        this.eMailValidator = eMailValidator;
        this.captchaService = captchaService;
    }


    @Override
    public String login(LoginDto loginDto) {
        return authcService.login(loginDto.getUsername(), loginDto.getPassword(), loginDto.getAppName(),
                loginDto.getUuid(), SpringWebUtils.getRequestFromContext());
    }

    @Override
    public Boolean verifyCaptcha(String captchaId, String code) {
        try {
            return this.captchaService.verify(captchaId, code);
        } catch (CaptchaException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void mobileValid(String mobile) {
        UserDo aDo = userService.getOne(new QueryWrapper<UserDo>().lambda().eq(UserDo::getMobile, mobile));
        if (aDo != null) {
            smsValidator.sendCode(aDo.getMobile());
            return;
        }
        throw new BusinessException("此手机号码还未注册");
    }

    @Override
    public String loginByMobile(String mobile, String code, String appName) {
        UserDo aDo = userService.getByMobile(mobile);
        if (aDo != null) {
            //validate sms code
            smsValidator.verifyCode(mobile, code);
            //if valid, just login
            List<Long> roleIds = aDo.getRoleList().stream().map(RoleDo::getId).collect(Collectors.toList());
            List<String> perms = menuService.getPermsByRoleIds(roleIds);
            //send back tokens
            return authcService.loginAfterAuthc(userService.getUserContext(aDo, perms, AuthcType.MOBILE, appName),
                    SpringWebUtils.getRequestFromContext());
        } else {
            throw new BusinessException("账号不存在或密码错误");
        }
    }

    @Override
    public String loginByOAuth(OAuthService authService, String state, OAuthType oAuthType) {
        AssertUtils.getNull(oAuthType).ex(() -> new BusinessException("登录失败，OAuth2登录类型为空"));

        String code = authService.checkAuthc(state);
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException("登录失败，状态码不存在");
        }

        //clear flag
        authService.clearAuthc(state);

        //has login, get access token from weixin or others
        Map<String, String> result = authService.getAccessTokenAndOpenId(code, state);
        String accessToken = result.get("accessToken");
        String openId = result.get("openId");
        String appName = result.get("appName");
        // String ip = result.get("ip");

        //get user info from weixin or others
        OAuthUser oAuthUser = authService.getOAuthUser(accessToken, openId);

        UserDo user = oAuthUserService.getBindUser(oAuthUser.getOpenid(), oAuthType);
        if (user == null) {
            //register a new one
            user = oAuthUserService.bindToUser(null, openId, oAuthType, oAuthUser);
        }

        //if state is valid and user is exist, just login
        List<Long> roleIds = user.getRoleList().stream().map(RoleDo::getId).collect(Collectors.toList());
        List<String> perms = menuService.getPermsByRoleIds(roleIds);
        AuthcType authcType = AuthcType.byVal(oAuthType.val());
        return authcService.loginAfterAuthc(userService.getUserContext(user, perms, authcType, appName),
                SpringWebUtils.getRequestFromContext());
    }

    @Override
    public void register(RegisterDto registerDto) {
        // use 'user' role by default
        RoleDo role = roleService.getOne(new QueryWrapper<RoleDo>().lambda().eq(RoleDo::getLabel, "user"));
        if (role == null) {
            throw new IllegalArgumentException("未配置角色\"user\"");
        }
        UserDo aDo = new UserDo();
        aDo.setUsername(registerDto.getUsername());
        aDo.setEmail(registerDto.getEmail());
        aDo.setPassword(registerDto.getPassword());
        aDo.setRoleList(Collections.singletonList(role));
        aDo.setStatusDict(AccountStatus.NORMAL.key());
        userService.saveAndGet(aDo);
    }

    @Override
    public Boolean checkUsername(String username) {
        UserDo aDo = userService.getOne(new QueryWrapper<UserDo>().lambda()
                .eq(UserDo::getUsername, username));
        return aDo == null;
    }

    @Override
    public Boolean checkEmail(String email) {
        UserDo aDo = userService.getOne(new QueryWrapper<UserDo>().lambda()
                .eq(UserDo::getEmail, email));
        return aDo == null;
    }

    @Override
    public UserDo getUserByUsername(String username) {
        return userService.getOne(new QueryWrapper<UserDo>().lambda().eq(UserDo::getUsername, username));
    }

    @Override
    public void resetPasswordValid(String username, ResetPasswordType type) {
        UserDo aDo = userService.getOne(new QueryWrapper<UserDo>().lambda()
                .eq(UserDo::getUsername, username));

        if (aDo != null && ResetPasswordType.EMAIL.equals(type)) {
            String email = aDo.getEmail();

            // send email asynchronously
            ThreadPoolTaskExecutor executor = SpringAppUtils.getBean("threadPoolTaskExecutor");
            executor.execute(() -> {
                try {
                    eMailValidator.sendCode(email);
                } catch (Exception e) {
                    ThreadUtils.printException(e);
                }
            });

            return;
        }

        if (aDo != null && ResetPasswordType.MOBILE.equals(type)) {
            String mobile = aDo.getMobile();
            smsValidator.sendCode(mobile);
            return;
        }

        throw new BusinessException("参数无效");
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        UserDo aDo = userService.getOne(new QueryWrapper<UserDo>().lambda()
                .eq(UserDo::getUsername, resetPasswordDto.getUsername()));

        if (aDo != null) {
            if (ResetPasswordType.EMAIL.key().equals(resetPasswordDto.getType())) {
                eMailValidator.verifyCode(aDo.getEmail(), resetPasswordDto.getCode());
            } else if (ResetPasswordType.MOBILE.key().equals(resetPasswordDto.getType())) {
                smsValidator.verifyCode(aDo.getMobile(), resetPasswordDto.getCode());
            }
            String password = SecurityUtils.encryptPassword(resetPasswordDto.getPassword());
            UserDo newDo = new UserDo();
            newDo.setId(aDo.getId());
            newDo.setPassword(password);
            userService.updateById(newDo);
            return;
        }

        throw new BusinessException("参数无效");
    }

    @Override
    public CaptchaDto getCaptcha() {
        // generate captcha
        CaptchaService.Captcha captcha = this.captchaService.generate();
        CaptchaDto captchaDto = new CaptchaDto();
        captchaDto.setUuid(captcha.getId());
        captchaDto.setExtra(captcha.getExtra());
        captchaDto.setImg(BASE64.encode(captcha.getImg()));

        return captchaDto;
    }
}
