/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.service;

import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.entity.dto.CaptchaDto;
import net.foundi.admin.system.entity.dto.LoginDto;
import net.foundi.admin.system.entity.dto.RegisterDto;
import net.foundi.admin.system.entity.dto.ResetPasswordDto;
import net.foundi.admin.system.entity.enums.ResetPasswordType;
import net.foundi.support.oauth.OAuthService;
import net.foundi.support.oauth.config.OAuthType;

/**
 * 登录、注册Service
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface LoginService {

    /**
     * 登录，账密方式
     *
     * @param loginDto LoginDto对象
     * @return token
     */
    String login(LoginDto loginDto);

    /**
     * 验证Captcha
     *
     * @param captchaId 验证码ID
     * @param code      验证码
     * @return Boolean
     */
    Boolean verifyCaptcha(String captchaId, String code);

    /**
     * 发送手机验证码
     *
     * @param mobile 手机号
     */
    void mobileValid(String mobile);

    /**
     * 登录，手机验证码方式
     *
     * @param mobile  手机号
     * @param code    验证码
     * @param appName 终端名称
     * @return token
     */
    String loginByMobile(String mobile, String code, String appName);

    /**
     * 登录，OAuth2
     *
     * @param authService OAuthService对象
     * @param state       状态码
     * @param oAuthType   OAuth2类型
     * @return token
     */
    String loginByOAuth(OAuthService authService, String state, OAuthType oAuthType);

    /**
     * 注册
     *
     * @param registerDto RegisterDto对象
     */
    void register(RegisterDto registerDto);

    /**
     * 判断用户名是否已经被使用
     *
     * @param username 用户名
     * @return Boolean
     */
    Boolean checkUsername(String username);

    /**
     * 判断Email是否已经被使用
     *
     * @param email Email地址
     * @return Boolean
     */
    Boolean checkEmail(String email);

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return UserDo对象
     */
    UserDo getUserByUsername(String username);

    /**
     * 发送重置密码的验证码
     *
     * @param username 用户名
     * @param type     手机、邮箱方式，mobile、email
     */
    void resetPasswordValid(String username, ResetPasswordType type);

    /**
     * 重置密码
     *
     * @param resetPasswordDto ResetPasswordDto对象
     */
    void resetPassword(ResetPasswordDto resetPasswordDto);

    /**
     * 获取Captcha
     *
     * @return CaptchaDto对象
     */
    CaptchaDto getCaptcha();
}
