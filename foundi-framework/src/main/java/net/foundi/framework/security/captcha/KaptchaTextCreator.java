/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.framework.security.captcha;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

/**
 * 验证码文本生成器
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class KaptchaTextCreator extends DefaultTextCreator {
    private static final String[] NUMS = "0,1,2,3,4,5,6,7,8,9,10".split(",");

    @Override
    public String getText() {
        int result = 0;
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        StringBuilder sb = new StringBuilder();
        int type = (int) Math.round(Math.random() * 2);
        if (type == 0) {
            result = x * y;
            sb.append(NUMS[x]);
            sb.append("*");
            sb.append(NUMS[y]);
        }
        else if (type == 1) {
            if (!(x == 0) && y % x == 0) {
                result = y / x;
                sb.append(NUMS[y]);
                sb.append("/");
                sb.append(NUMS[x]);
            }
            else {
                result = x + y;
                sb.append(NUMS[x]);
                sb.append("+");
                sb.append(NUMS[y]);
            }
        }
        else if (type == 2) {
            if (x >= y) {
                result = x - y;
                sb.append(NUMS[x]);
                sb.append("-");
                sb.append(NUMS[y]);
            }
            else {
                result = y - x;
                sb.append(NUMS[y]);
                sb.append("-");
                sb.append(NUMS[x]);
            }
        }
        else {
            result = x + y;
            sb.append(NUMS[x]);
            sb.append("+");
            sb.append(NUMS[y]);
        }
        sb.append("=?@").append(result);
        return sb.toString();
    }
}