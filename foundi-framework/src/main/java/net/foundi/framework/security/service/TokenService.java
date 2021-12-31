/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.lang.ByteUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.cache.lock.DistributedLock;
import net.foundi.framework.cache.lock.RedisLock;
import net.foundi.framework.cache.redis.RedisCache;
import net.foundi.framework.security.config.SecurityConst;
import net.foundi.framework.security.config.SecurityProperties;
import net.foundi.framework.security.exception.SecureException;
import net.foundi.framework.security.model.UserContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 令牌处理服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class TokenService {

    public static final long MILLISECONDS_PER_HOUR = 60 * 60 * 1000L;

    private final SecurityProperties properties;

    private final RedisCache redisCache;

    public TokenService(SecurityProperties properties, RedisCache redisCache) {
        this.properties = properties;
        this.redisCache = redisCache;
    }

    /**
     * 获取用户信息
     *
     * @param request HttpServletRequest对象
     * @return UserContext，用户信息
     */
    public UserContext getLoginUser(HttpServletRequest request) {
        // 由Http请求获取JWT
        String bearerToken = request.getHeader(SecurityConst.REQUEST_AUTHC_HEADER);
        if (StringUtils.isEmpty(bearerToken)) {
            return null;
        }
        if (!bearerToken.startsWith(SecurityConst.REQUEST_TOKEN_PREFIX)) {
            return null;
        }
        String rawToken = bearerToken.substring(SecurityConst.REQUEST_TOKEN_PREFIX.length());
        if (StringUtils.isEmpty(rawToken)) {
            return null;
        }

        // 解析JWT
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(ByteUtils.getBytes(properties.getTokenSecret())))
                .build()
                .parseClaimsJws(rawToken)
                .getBody();
        String token = (String) claims.get(SecurityConst.USER_KEY_OF_CLAIM);

        // 由缓存获取用户信息
        UserContext userContext = redisCache.getObject(tokenKeyOfRedis(token));
        System.out.println(StringUtils.withPrefix("获取用户" + userContext));
        return userContext;
    }

    /**
     * 修改用户信息，不重新生成令牌
     *
     * @param loginUser UserContext，用户信息
     */
    public void setLoginUser(UserContext loginUser) {
        if (loginUser != null && StringUtils.hasValue(loginUser.getToken())) {
            loginUser.setLoginTime(System.currentTimeMillis());
            loginUser.setExpireTime(loginUser.getLoginTime() + properties.getTokenExpiration());
            String userKey = tokenKeyOfRedis(loginUser.getToken());
            redisCache.setObject(userKey, loginUser, properties.getTokenExpiration().longValue(), TimeUnit.HOURS);
        }
    }

    /**
     * 删除用户身份信息
     *
     * @param token 用户令牌
     */
    public void delLoginUser(String token) {
        if (StringUtils.hasValue(token)) {
            redisCache.deleteObject(tokenKeyOfRedis(token));
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 加密后的令牌
     */
    public String createToken(UserContext loginUser) {
        // 生成令牌
        String token = IDUtils.uuidNoDash();
        loginUser.setToken(token);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + properties.getTokenExpiration() * MILLISECONDS_PER_HOUR);

        String userKey = tokenKeyOfRedis(loginUser.getToken());
        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConst.USER_KEY_OF_CLAIM, token);
        // 缓存令牌
        redisCache.setObject(userKey, loginUser, properties.getTokenExpiration().longValue(), TimeUnit.HOURS);
        // 令牌加密
        return Jwts.builder()
                .setClaims(claims)
                .signWith(Keys.hmacShaKeyFor(ByteUtils.getBytes(properties.getTokenSecret())), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 验证令牌有效期，刷新令牌
     *
     * @param loginUser 用户信息
     */
    public void refreshTokenIfNeeded(UserContext loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= properties.getTokenRefreshPeriod() * MILLISECONDS_PER_HOUR) {
            // 刷新令牌
            try {
                // 加锁，防止多请求重复刷新
                DistributedLock lock = RedisLock.create(SecurityConst.TOKEN_LOCK_PREFIX_REDIS + loginUser.getToken());
                lock.tryLock(SecurityConst.TOKEN_LOCK_TIME, TimeUnit.MILLISECONDS);
                if (lock.isLocked()) {
                    String jwt = createToken(loginUser);
                    // 记录刷新的JWT，随后返回客户端
                    loginUser.setRefreshToken(jwt);
                    // 旧令牌准备弃用
                    redisCache.setTtl(tokenKeyOfRedis(loginUser.getToken()), SecurityConst.OLD_TOKEN_EXPIRATION,
                            TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                throw new SecureException("加锁异常，刷新用户令牌出错");
            }
        }
    }

    /**
     * 获取特定令牌在redis中的key
     *
     * @param token 令牌
     * @return key值
     */
    private String tokenKeyOfRedis(String token) {
        return SecurityConst.TOKEN_KEY_PREFIX_REDIS + token;
    }
}
