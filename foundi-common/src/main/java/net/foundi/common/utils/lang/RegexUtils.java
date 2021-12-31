/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.lang;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class RegexUtils {

    // 空行
    public static final String REG_SPACE_LINE = "\\n\\s*\\r";

    // 首部、尾部空格
    public static final String REG_SPACE_ENDS = "^\\s*|\\s*$";

    // HTML
    public static final String REG_HTML_SECTION = "<(\\S*?)[^>]*>.*?</\\1>|<.*? />";

    // Email
    public static final String REG_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    // 座机号
    public static final String REG_TELEPHONE = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";

    // 邮政编码
    public static final String REG_ZIP_CODE = "[1-9]\\d{5}(?!\\d)";

    // 身份证号
    public static final String REG_ID_CARD = "\\d{15}|\\d{18}";

    // 手机号
    public static final String REG_MOBILE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    // http、https、ftp的url地址
    public static final String REG_URL = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)" +
            "(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\\\/])+(\\\\?{0,1}(([A-Za-z0-9-~]+\\\\={0,1})([A-Za-z0-9-~]*)\\\\&{0,1})*)$";

    // IP地址
    public static final String REG_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    // 合法账号名：字母开头，包含5-16个字符，仅包含字母、数字和下划线
    public static final String REG_LEGAL_ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";

    // 固定格式的日期："yyyy-mm-dd"
    public static final String REG_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|" +
            "(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|" +
            "(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";

    /**
     * 判断字符串是否仅包含数字
     *
     * @param str 判断的字符串
     * @return true：仅包含数字
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.hasValue(str)) {
            return Pattern.compile("^[0-9]+$").matcher(str).matches();
        }
        return false;
    }

    /**
     * 判断字符串是否仅包含字母
     *
     * @param str 判断的字符串
     * @return true：仅包含字母
     */
    public static boolean isABC(String str) {
        if (StringUtils.hasValue(str)) {
            return Pattern.compile("^[a-z|A-Z]+$").matcher(str).matches();
        }
        return false;
    }

    /**
     * 判断Email地址是否合法
     *
     * @param str 判断的字符串
     * @return true：合法
     */
    public static boolean isEmail(String str) {
        if (str == null || str.length() < 1 || str.length() > 256) {
            return false;
        }
        return Pattern.compile(REG_EMAIL).matcher(str).matches();
    }

    /**
     * 判断Email地址是否合法
     *
     * @param str 判断的字符串
     * @return true：合法
     */
    public static boolean isIp(String str) {
        if (str == null || str.length() < 7 || str.length() > 15) {
            return false;
        }
        return Pattern.compile(REG_IP).matcher(str).matches();
    }

    /**
     * 判断密码强度
     * 不可用：长度小于8，或只包含1种字符
     * 弱：长度大于8，包含2种字符
     * 中：长度大于8，包含3种字符
     * 强：长度大于8，包含4种字符
     * 字符种类：数字、大写字母、小写字母、特殊字符
     *
     * @return 0-1（不可用）、2（弱）、3（中）、4（强）
     */
    public static int passwordStrength(String password) {
        String[] regexArray = new String[]{"(?=.*[A-Z]).{8,}", "(?=.*[a-z]).{8,}", "(?=.*[0-9]).{8,}",
                "(?=.*['\\+`<>\\?/\\\\\"\\[\\]\\{\\}\\|:;\\,\\.~!@#\\$%^\\&\\*\\(\\)_-]).{8,}"};
        int strength = 0;
        for (String regex : regexArray) {
            strength += password.matches(regex) ? 1 : 0;
        }
        return strength;
    }

    /**
     * 将正则表达式加转义符
     *
     * @param original 原表达式
     * @return 加转义符后的表达式
     */
    public static String EscapeRegex(String original) {
        if (StringUtils.isEmpty(original)) {
            return original;
        }
        return original.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }
}
