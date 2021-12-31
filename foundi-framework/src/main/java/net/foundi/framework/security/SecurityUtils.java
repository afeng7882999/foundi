/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.common.utils.spring.SpringAppUtils;
import net.foundi.framework.security.config.CaptchaProperties;
import net.foundi.framework.security.config.SecurityConst;
import net.foundi.framework.security.exception.SecureException;
import net.foundi.framework.security.model.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Security 工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class SecurityUtils {

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static Optional<UserContext> currentUser() {
        try {
            Authentication authc = SecurityContextHolder.getContext().getAuthentication();
            return Optional.ofNullable(authc).map(a -> {
                if (authc.getPrincipal() instanceof UserContext) {
                    return (UserContext) authc.getPrincipal();
                }
                return null;
            });
        } catch (Exception e) {
            throw new SecureException("获取用户信息异常");
        }
    }

    /**
     * 获取当前用户，获取不到则抛出异常
     *
     * @return 当前用户
     */
    public static UserContext getCurrentUserOrEx() {
        return currentUser().orElseThrow(() -> new SecureException("获取用户信息异常，当前用户不存在"));
    }

    /**
     * 获取刷新后的JWT
     *
     * @return JWT
     */
    public static Optional<String> refreshToken() {
        return currentUser().map(UserContext::getRefreshToken);
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 前端传回密码字符串
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        // 生成BCrypt加密的密码字符
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param password     真实密码
     * @param truePassword 加密后字符
     * @return true：相同
     */
    public static boolean passwordMatches(String password, String truePassword) {
        // 比对BCrypt加密的密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, truePassword);
    }

    /**
     * 是否为超级管理员
     *
     * @param userId 用户ID
     * @return true：为超级管理员
     */
    public static boolean isAdmin(Long userId) {
        return SecurityConst.SUPER_ADMIN_ID.equals(userId);
    }

    /**
     * 获取所有声明为 @AnonymousAccess Url
     *
     * @return url集合
     */
    public static Set<String> anonymousUrls() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap =
                SpringAppUtils.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess && null != infoEntry.getKey().getPatternsCondition()) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
        }
        return anonymousUrls;
    }

    /**
     * 生成验证码
     *
     * @param captchaType 验证码类型
     * @return Captcha对象
     */
    public static Captcha getCaptcha(CaptchaProperties.CaptchaType captchaType) {
        DefaultKaptcha defaultKaptcha = SpringAppUtils.getBean(DefaultKaptcha.class);
        Captcha captcha = new Captcha();
        captcha.setId(IDUtils.uuidNoDash());
        BufferedImage image;

        // generate captcha
        if (CaptchaProperties.CaptchaType.MATH.equals(captchaType)) {
            // Math
            String capText = defaultKaptcha.createText();
            String capStr = capText.substring(0, capText.lastIndexOf("@"));
            String code = capText.substring(capText.lastIndexOf("@") + 1);
            captcha.setCode(code);
            image = defaultKaptcha.createImage(capStr);
        } else {
            // Char
            String code = defaultKaptcha.createText();
            captcha.setCode(code);
            image = defaultKaptcha.createImage(code);
        }

        // output
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
            captcha.setImg(os.toByteArray());
            return captcha;
        } catch (IOException e) {
            throw new BusinessException(StringUtils.withPrefix("获取验证码出错"));
        }
    }

    /**
     * 验证码
     */
    public static class Captcha {
        // 验证码ID
        private String id;

        // 验证码
        private String code;

        // 验证码图片
        private byte[] img;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public byte[] getImg() {
            return img;
        }

        public void setImg(byte[] img) {
            this.img = img;
        }
    }

    public static void main(String... args) {
        // 生成BCrypt加密的密码字符
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encoded = passwordEncoder.encode("123456");
        System.out.println(encoded);
    }

}
