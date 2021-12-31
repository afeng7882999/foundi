/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.base.IDUtils;
import net.foundi.common.utils.lang.StringUtils;
import net.foundi.framework.cache.redis.RedisCache;
import net.foundi.framework.security.config.CaptchaProperties;
import net.foundi.framework.security.config.SecurityConst;
import net.foundi.framework.security.exception.CaptchaException;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
@Component
public class CaptchaService {

    // 查找验证码验证完成记录后，此记录过期时间
    public final static Long CHECKED_VERIFIED_KEY_TTL =  200L;

    // 验证码验证lua脚本
    private final static String VERIFY_COMMAND_SCRIPT = "if (redis.call('EXISTS',KEYS[1])~=1) then " +
            "return nil; " +
            "end; " +
            "if (redis.call('GET',KEYS[1])==ARGV[1]) then " +
            "redis.call('DEL',KEYS[1]); " +
            "redis.call('SETEX',KEYS[2],ARGV[2],1); " +
            "return 1; " +
            "else " +
            "return 0; " +
            "end;";

    // 验证码验证完成记录lua脚本
    private final static String CHECK_VERIFIED_COMMAND_SCRIPT = "return redis.call('PEXPIRE',KEYS[1],ARGV[1])";

    private final CaptchaProperties captchaProperties;
    private final DefaultKaptcha defaultKaptcha;
    private final RedisCache redisCache;

    public CaptchaService(CaptchaProperties captchaProperties, DefaultKaptcha defaultKaptcha, RedisCache redisCache) {
        this.captchaProperties = captchaProperties;
        this.defaultKaptcha = defaultKaptcha;
        this.redisCache = redisCache;
    }

    /**
     * 生成验证码并缓存
     *
     * @return Captcha对象
     */
    public Captcha generate() {
        // generate captcha
        Captcha captcha = this.generateCaptcha();
        // cache
        String verifyKey = SecurityConst.CAPTCHA_PREFIX_REDIS + captcha.getId();
        this.redisCache.setObject(verifyKey, captcha.getCode(), this.captchaProperties.getExpiration(), TimeUnit.SECONDS);
        return captcha;
    }

    /**
     * 验证码验证
     *
     * @param id   验证码ID
     * @param code 验证码
     * @return 验证正确返回true，否则返回false
     * @throws CaptchaException 验证码过期异常
     */
    public Boolean verify(String id, String code) throws CaptchaException {
        if (StringUtils.anyEmpty(id, code)) {
            return null;
        }
        // get and verify captcha from redis, then delete the captcha key and
        // set a new key to indicate the captcha has been verified
        String redisCodeKey = SecurityConst.CAPTCHA_PREFIX_REDIS + id;
        String captchaTokenKey = SecurityConst.CAPTCHA_VERIFIED_PREFIX_REDIS + id;
        Long result = redisCache.execute(VERIFY_COMMAND_SCRIPT, Arrays.asList(redisCodeKey, captchaTokenKey), code,
                captchaProperties.getExpiration());
        if (result == null) {
            throw new CaptchaException("验证码已失效，请刷新重试");
        }
        if (result != 1L) {
            throw new CaptchaException("验证码错误");
        }
        return true;
    }

    /**
     * 检测验证码是否验证通过
     *
     * @param id 验证码ID
     * @return 已经验证过返回true，否则返回false
     */
    public Boolean checkVerified(String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        // set TTL of the key indicates captcha verified, to a short duration
        String captchaTokenKey = SecurityConst.CAPTCHA_VERIFIED_PREFIX_REDIS + id;
        Long result = redisCache.execute(CHECK_VERIFIED_COMMAND_SCRIPT, Collections.singletonList(captchaTokenKey),
                CHECKED_VERIFIED_KEY_TTL);
        return result == 1L;
    }

    /**
     * 获取缓存的验证码
     *
     * @param id 验证码ID
     * @return 验证码，可能为null
     */
    public String getFromCache(String id) {
        String redisCodeKey = SecurityConst.CAPTCHA_PREFIX_REDIS + id;
        String captcha = redisCache.getObject(redisCodeKey);
        redisCache.deleteObject(redisCodeKey);

        return captcha;
    }

    /**
     * 生成验证码
     *
     * @return Captcha对象
     */
    public Captcha generateCaptcha() {
        Captcha captcha = new Captcha();
        captcha.setId(IDUtils.uuidNoDash());
        BufferedImage image;

        // generate captcha
        if (CaptchaProperties.CaptchaType.MATH.equals(this.captchaProperties.getType())) {
            // Math
            String capText = this.defaultKaptcha.createText();
            String capStr = capText.substring(0, capText.lastIndexOf("@"));
            String code = capText.substring(capText.lastIndexOf("@") + 1);
            captcha.setCode(code);
            captcha.setExtra("请输入计算结果");
            image = this.defaultKaptcha.createImage(capStr);
        } else {
            // Char
            String code = this.defaultKaptcha.createText();
            captcha.setCode(code);
            image = this.defaultKaptcha.createImage(code);
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
     * 验证码POJO
     */
    public static class Captcha {
        // 验证码ID
        private String id;

        // 验证码
        private String code;

        // 附加信息
        private String extra;

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

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public byte[] getImg() {
            return img;
        }

        public void setImg(byte[] img) {
            this.img = img;
        }
    }
}
