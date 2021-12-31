/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.admin.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.foundi.admin.system.entity.domain.UserDo;
import net.foundi.admin.system.entity.dto.CaptchaDto;
import net.foundi.admin.system.entity.dto.LoginDto;
import net.foundi.admin.system.entity.dto.RegisterDto;
import net.foundi.admin.system.entity.dto.ResetPasswordDto;
import net.foundi.admin.system.entity.enums.ResetPasswordType;
import net.foundi.admin.system.service.ConfigService;
import net.foundi.admin.system.service.LoginService;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.web.IPUtils;
import net.foundi.framework.entity.validation.PhoneValid;
import net.foundi.framework.entity.validation.UsernameValid;
import net.foundi.framework.log.Log;
import net.foundi.framework.security.AnonymousAccess;
import net.foundi.framework.web.WebReturn;
import net.foundi.framework.web.limit.Limit;
import net.foundi.framework.web.resubmit.Resubmit;
import net.foundi.support.oauth.config.OAuthType;
import net.foundi.support.oauth.qq.QqOAuthService;
import net.foundi.support.oauth.weibo.WeiboOAuthService;
import net.foundi.support.oauth.weixin.WeixinOAuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.util.Map;

/**
 * 登录、注册Controller
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Api(tags = "登录、注册")
@RestController
@Validated
@RequestMapping("/login")
public class LoginController {

    private static final String CLOSE_WINDOW_SCRIPT = "<html><head><script language=\"javascript\">window.close();</script></head><body>成功登录, 请关闭此页面...</body></html>";

    private final WeixinOAuthService weixinOAuthService;
    private final QqOAuthService qqOAuthService;
    private final WeiboOAuthService weiboOAuthService;
    private final LoginService loginService;
    private final ConfigService configService;


    public LoginController(WeixinOAuthService weixinOAuthService,
                           QqOAuthService qqOAuthService,
                           WeiboOAuthService weiboOAuthService,
                           LoginService loginService,
                           ConfigService configService) {
        this.weixinOAuthService = weixinOAuthService;
        this.qqOAuthService = qqOAuthService;
        this.weiboOAuthService = weiboOAuthService;
        this.loginService = loginService;
        this.configService = configService;
    }

    //************************************************************
    //*  账密方式登录
    //************************************************************

    @ApiOperation("使用账号密码登录")
    @PostMapping("")
    @AnonymousAccess
    public WebReturn login(@Validated @RequestBody LoginDto loginDto) {
        String token = loginService.login(loginDto);
        return WebReturn.ok()
                .token(token)
                .message("登录成功，已返回AccessToken");
    }

    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    @AnonymousAccess
    @Resubmit(checkFor = Resubmit.CheckTarget.IP, interval = 2)
    public WebReturn getCaptcha() {
        CaptchaDto captchaDto = loginService.getCaptcha();
        return WebReturn.ok().content(captchaDto);
    }

    @ApiOperation("验证码验证")
    @GetMapping("/captcha-verify")
    @AnonymousAccess
    @Resubmit(checkFor = Resubmit.CheckTarget.IP, interval = 2)
    public WebReturn verifyCaptcha(@RequestParam String uuid, @RequestParam String code) {
        Boolean result = loginService.verifyCaptcha(uuid, code);
        return WebReturn.ok().content(result);
    }

    //************************************************************
    //*  手机验证码方式登录
    //************************************************************

    @ApiOperation("使用手机验证码方式登录")
    @PostMapping("/mobile")
    @AnonymousAccess
    public WebReturn loginByMobile(@RequestParam @PhoneValid String mobile, @RequestParam String code,
                                   @RequestParam String appName) {
        String token = loginService.loginByMobile(mobile, code, appName);
        return WebReturn.ok()
                .token(token)
                .message("登录成功，已返回AccessToken");
    }

    @ApiOperation("发送手机验证码")
    @GetMapping("/mobile-valid")
    @AnonymousAccess
    @Resubmit(lockKey = "#mobile", checkFor = Resubmit.CheckTarget.IP, interval = 20)
    @Limit(checkFor = Limit.CheckTarget.IP, maxRate = 60, duration = 60L, forbiddenTime = 60 * 10L)
    public WebReturn mobileValid(@RequestParam @PhoneValid String mobile) {
        loginService.mobileValid(mobile);
        return WebReturn.ok();
    }

    //************************************************************
    //*  OAuth2 登录
    //************************************************************

    @ApiOperation("获取微信认证url")
    @GetMapping("/weixin-url")
    @AnonymousAccess
    public WebReturn loginByWeixin(@RequestParam String appName, HttpServletRequest req) {
        Map<String, String> result = weixinOAuthService.getAuthzUrlAndState(appName, IPUtils.getIpAddrOrEx(req));
        return WebReturn.ok()
                .content("authzUrl", result.get("authzUrl"))
                .content("state", result.get("state"));
    }

    /**
     * 微信认证回调地址
     */
    @GetMapping("/weixin-callback")
    @AnonymousAccess
    public void weixinCallback(@RequestParam String code, @RequestParam String state) {
        weixinOAuthService.setAuthc(state, code);
    }

    /**
     * 通过微信登录
     */
    @GetMapping("/weixin-login")
    @AnonymousAccess
    public WebReturn weixinCheckLogin(@RequestParam String state) {
        String token = loginService.loginByOAuth(weixinOAuthService, state, OAuthType.WEIXIN);
        return WebReturn.ok()
                .token(token)
                .content(true)
                .message("登录成功，已返回AccessToken");
    }

    /**
     * 微信公众号开发使用
     *
     * @param echostr 返回字符串
     * @return String
     */
    @GetMapping("/weixin-callback2")
    @AnonymousAccess
    public String callback(@RequestParam String echostr) {
        return echostr;
    }

    @ApiOperation("获取QQ认证url")
    @GetMapping("/qq-url")
    @AnonymousAccess
    public WebReturn loginByQQ(@RequestParam String appName, HttpServletRequest req) {
        Map<String, String> result = qqOAuthService.getAuthzUrlAndState(appName, IPUtils.getIpAddrOrEx(req));
        return WebReturn.ok()
                .content("authzUrl", result.get("authzUrl"))
                .content("state", result.get("state"));
    }

    /**
     * QQ认证回调地址
     */
    @GetMapping("/qq-callback")
    @AnonymousAccess
    public String qqCallback(@RequestParam String code, @RequestParam String state) {
        qqOAuthService.setAuthc(state, code);
        return CLOSE_WINDOW_SCRIPT;
    }

    /**
     * QQ登录
     */
    @GetMapping("/qq-login")
    @AnonymousAccess
    public WebReturn qqCheckLogin(@RequestParam String state) {
        String token = loginService.loginByOAuth(qqOAuthService, state, OAuthType.QQ);
        return WebReturn.ok()
                .token(token)
                .content(true)
                .message("登录成功，已返回AccessToken");
    }

    @ApiOperation("获取微博认证url")
    @GetMapping("/weibo-url")
    @AnonymousAccess
    public WebReturn loginByWeibo(@RequestParam String appName, HttpServletRequest req) {
        Map<String, String> result = weiboOAuthService.getAuthzUrlAndState(appName, IPUtils.getIpAddrOrEx(req));
        return WebReturn.ok()
                .content("authzUrl", result.get("authzUrl"))
                .content("state", result.get("state"));
    }

    /**
     * 微博认证回调地址
     */
    @GetMapping("/weibo-callback")
    @AnonymousAccess
    public String weiboCallback(@RequestParam String code, @RequestParam String state) {
        weiboOAuthService.setAuthc(state, code);
        return CLOSE_WINDOW_SCRIPT;
    }

    /**
     * 微博取消认证回调地址
     */
    @GetMapping("/weibo-cancel")
    @AnonymousAccess
    public String weiboCancelCallback(@RequestParam String source, @RequestParam String uid,
                                      @RequestParam String auth_end) {
        return "success";
    }

    /**
     * 微博登录
     */
    @GetMapping("/weibo-login")
    @AnonymousAccess
    public WebReturn weiboCheckLogin(@RequestParam String state) {
        String token = loginService.loginByOAuth(weiboOAuthService, state, OAuthType.WEIBO);
        return WebReturn.ok()
                .token(token)
                .content(true)
                .message("登录成功，已返回AccessToken");
    }

    //************************************************************
    //*  注册
    //************************************************************

    @ApiOperation("注册用户")
    @PostMapping("/register")
    @AnonymousAccess
    @Log(value = "注册用户", param = "#dto.username")
    public WebReturn register(@RequestBody @Validated RegisterDto dto) {
        loginService.register(dto);
        return WebReturn.ok();
    }

    @ApiOperation("检测用户名是否可用")
    @GetMapping("/check-username")
    @AnonymousAccess
    @Resubmit(checkFor = Resubmit.CheckTarget.IP, interval = 2)
    public WebReturn checkUsername(@RequestParam @UsernameValid String username) {
        return WebReturn.ok()
                .content(loginService.checkUsername(username));
    }

    @ApiOperation("当前用户检测邮箱是否可用")
    @GetMapping("/check-email")
    @AnonymousAccess
    @Resubmit(checkFor = Resubmit.CheckTarget.IP, interval = 2)
    public WebReturn checkEmail(@RequestParam @Email String email) {
        return WebReturn.ok()
                .content(loginService.checkEmail(email));
    }

    @ApiOperation("获取网站用户协议与隐私政策")
    @GetMapping("/agreement")
    @AnonymousAccess
    @Resubmit(checkFor = Resubmit.CheckTarget.IP, interval = 2)
    public WebReturn getAgreement() {
        return WebReturn.ok()
                .content("userAgreement", configService.getConfigFromResource("agreement/agreement.html"))
                .content("userPrivacy", configService.getConfigFromResource("agreement/privacy.html"));
    }

    //************************************************************
    //*  重置密码
    //************************************************************

    @ApiOperation("检测是否是注册用户")
    @GetMapping("/check-exist")
    @AnonymousAccess
    @Resubmit(checkFor = Resubmit.CheckTarget.IP, interval = 2)
    public WebReturn checkExistByUsername(@RequestParam @UsernameValid String username) {
        UserDo aDo = loginService.getUserByUsername(username);
        if (aDo != null) {
            return WebReturn.ok()
                    .content("exist", true)
                    .content("mobile", StringUtils.briefMobile(aDo.getMobile()))
                    .content("email", StringUtils.briefEmail(aDo.getEmail()));
        } else {
            return WebReturn.ok()
                    .content("exist", false);
        }
    }

    @ApiOperation("发送重置密码的验证码")
    @GetMapping("/reset-valid")
    @AnonymousAccess
    @Resubmit(checkFor = Resubmit.CheckTarget.IP, lockKey = "#username", interval = 20)
    @Log(value = "发送重置密码的验证码", param = "#username + ',' + #type")
    public WebReturn resetPasswordValid(@RequestParam @UsernameValid String username, @RequestParam String type) {
        loginService.resetPasswordValid(username, ResetPasswordType.byKey(type));
        return WebReturn.ok();
    }

    @ApiOperation("重置密码")
    @PutMapping("/reset-password")
    @AnonymousAccess
    @Resubmit(checkFor = Resubmit.CheckTarget.IP, lockKey = "#dto.username", interval = 20)
    @Log(value = "重置密码", param = "#dto.username")
    public WebReturn resetPassword(@RequestBody @Validated ResetPasswordDto dto) {
        loginService.resetPassword(dto);
        return WebReturn.ok();
    }

}
