/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.service;

import eu.bitwalker.useragentutils.UserAgent;
import net.foundi.common.constant.FoundiConst;
import net.foundi.common.utils.crypto.RSA;
import net.foundi.common.utils.lang.DateUtils;
import net.foundi.common.utils.web.AddressUtils;
import net.foundi.common.utils.web.IPUtils;
import net.foundi.framework.cache.redis.RedisCache;
import net.foundi.framework.log.AsyncLogFactory;
import net.foundi.framework.security.captcha.CaptchaService;
import net.foundi.framework.security.config.SecurityConst;
import net.foundi.framework.security.config.SecurityProperties;
import net.foundi.framework.security.enums.AuthcType;
import net.foundi.framework.security.enums.LoginType;
import net.foundi.framework.security.exception.LoginRetryLimitException;
import net.foundi.framework.security.exception.SecureException;
import net.foundi.framework.security.model.ExternalAuthcToken;
import net.foundi.framework.security.model.UserContext;
import net.foundi.framework.thread.AsyncManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * 用户登录服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class AuthcService {
    private final SecurityProperties securityProperties;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final CaptchaService captchaService;
    private final RedisCache redisCache;

    public AuthcService(SecurityProperties securityProperties,
                        TokenService tokenService,
                        AuthenticationManager authenticationManager,
                        CaptchaService captchaService,
                        RedisCache redisCache) {
        this.securityProperties = securityProperties;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.captchaService = captchaService;
        this.redisCache = redisCache;
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param uuid     验证码ID
     * @return 返回用户的TOKEN
     */
    public String login(String username, String password, String appName, String uuid, HttpServletRequest req) {

        // 密码解密
        String passDecrypt = RSA.decryptByPrivateKey(password, securityProperties.getRsaPrivateKey());

        // 验证码
        String key = getLoginRetryKey(username, req);
        Integer retry = redisCache.getObject(key);
        if (retry != null && retry > securityProperties.getLoginRetryLimit()) {
            // 登录次数达到限定值
            if (!captchaService.checkVerified(uuid)) {
                throw new LoginRetryLimitException("继续登录，请输入验证码");
            }
        }

        // 用户验证
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            Authentication authc = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    passDecrypt));
            UserContext userHasLogin = (UserContext) authc.getPrincipal();
            userHasLogin.setAuthcType(AuthcType.PASSWORD);
            userHasLogin.setAppName(appName);
            // 用户token
            String tk = successAndIssueToken(userHasLogin, req);
            clearLoginRetryTimes(username, req);
            return tk;

        } catch (Exception e) {
            String msg;
            if (e instanceof BadCredentialsException) {
                msg = "用户不存在/密码错误";
            } else {
                msg = e.getMessage();
            }

            // 判断登录次数
            Integer times = increaseLoginRetryTimes(username, req);
            if (times > securityProperties.getLoginRetryLimit()) {
                String msgRetry = msg + "，登录重试超过" + securityProperties.getLoginRetryLimit() + "次";
                asyncLogLogin(username, msgRetry, AuthcType.PASSWORD, FoundiConst.FAIL);
                throw new SecureException(msg);
            }

            asyncLogLogin(username, msg, AuthcType.PASSWORD, FoundiConst.FAIL);
            throw new SecureException(msg);
        }
    }

    /**
     * 登录验证
     * 适用短信、微信等在外部已经验证过的认证
     * 使用这个方法登录，Spring Security没有做任何实际认证处理
     *
     * @param user UserContext
     * @return 返回用户的TOKEN
     */
    public String loginAfterAuthc(UserContext user, HttpServletRequest req) {
        try {
            ExternalAuthcToken token = new ExternalAuthcToken(user, user.getAuthcType());
            Authentication authc = authenticationManager.authenticate(token);
            // 用户token
            String tk = successAndIssueToken((UserContext) authc.getPrincipal(), req);
            clearLoginRetryTimes(user.getUsername(), req);
            return tk;
        } catch (Exception e) {
            asyncLogLogin(user.getUsername(), e.getMessage(), user.getAuthcType(), FoundiConst.FAIL);
            throw new SecureException(e.getMessage());
        }
    }

    /**
     * 登录成功，发放Token
     *
     * @param user UserContext对象
     * @param req  HttpServletRequest对象
     * @return 返回用户的TOKEN
     */
    private String successAndIssueToken(UserContext user, HttpServletRequest req) {
        // 验证通过
        String msg = "登录成功";
        asyncLogLogin(user.getUsername(), msg, user.getAuthcType(), FoundiConst.SUCCESS);
        // 终端信息
        UserAgent userAgent = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));
        String ip = IPUtils.getIpAddrOrEx(req);
        user.setIp(ip);
        user.setLocation(AddressUtils.getRealAddressByIP(ip));
        user.setBrowser(userAgent.getBrowser().getName());
        user.setOs(userAgent.getOperatingSystem().getName());

        // 生成token
        return tokenService.createToken(user);
    }

    /**
     * 异步记录登录日志
     */
    private void asyncLogLogin(String username, String message, AuthcType authcType, String status) {
        AsyncManager.getInstance().execute(AsyncLogFactory.logLogin(username, message, authcType, LoginType.LOGIN.key(),
                status, DateUtils.now()));
    }

    private static final String LOGIN_RETRY_TIMES_SCRIPT = "if redis.call('SETNX',KEYS[1],1)==1 then " +
            "redis.call('EXPIRE',KEYS[1],ARGV[1]); " +
            "return 1; " +
            "else " +
            "local times=redis.call('INCRBY',KEYS[1],1); " +
            "return times+1; " +
            "end;";

    /**
     * 登录重试次数增加
     *
     * @param username 用户名
     * @param req      HttpServletRequest
     * @return 返回登录重试次数
     */
    private Integer increaseLoginRetryTimes(String username, HttpServletRequest req) {
        String key = getLoginRetryKey(username, req);
        // 缓存登录重试次数+1，并返回总数
        Long times = redisCache.execute(LOGIN_RETRY_TIMES_SCRIPT, Collections.singletonList(key),
                securityProperties.getLoginRetryLimitTime());
        return times.intValue();
    }

    /**
     * 清除登录重试次数
     *
     * @param username 用户名
     * @param req      HttpServletRequest
     */
    private void clearLoginRetryTimes(String username, HttpServletRequest req) {
        String key = getLoginRetryKey(username, req);
        redisCache.deleteObject(key);
    }

    /**
     * 获取登录重试次数缓存的key
     *
     * @param username 用户名
     * @param req      HttpServletRequest
     * @return 缓存的key
     */
    private String getLoginRetryKey(String username, HttpServletRequest req) {
        // 检测同一IP下，同一用户名的重试次数
        String ip = IPUtils.getIpAddrOrEx(req);
        return SecurityConst.LOGIN_TRY_TIMES_PREFIX_REDIS + ip + ":" + username;
    }
}
