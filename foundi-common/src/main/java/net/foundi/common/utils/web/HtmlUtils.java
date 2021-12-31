/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.web;

import net.foundi.common.utils.lang.StringUtils;

/**
 * HTML转义和反转义工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class HtmlUtils
{
    public static final String RE_HTML_MARK = "(<[^<]*?>)|(<[\\s]*?/[^<]*?>)|(<[^<]*?/[\\s]*?>)";

    private static final char[][] TEXT = new char[64][];

    static {
        for (int i = 0; i < 64; i++) {
            TEXT[i] = new char[] { (char) i };
        }
        TEXT['\''] = "&#039;".toCharArray(); // 单引号
        TEXT['"'] = "&#34;".toCharArray(); // 单引号
        TEXT['&'] = "&#38;".toCharArray(); // &符
        TEXT['<'] = "&#60;".toCharArray(); // 小于号
        TEXT['>'] = "&#62;".toCharArray(); // 大于号
    }

    /**
     * 转义文本中的HTML字符为安全的字符
     * 
     * @param text 被转义的文本
     * @return 转义后的文本
     */
    public static String escape(String text) {
        return encode(text);
    }

    /**
     * 还原被转义的HTML特殊字符
     * 
     * @param content 包含转义符的HTML内容
     * @return 转换后的字符串
     */
    public static String unescape(String content) {
        return decode(content);
    }

    /**
     * Escape编码
     * 
     * @param text 被编码的文本
     * @return 编码后的字符
     */
    private static String encode(String text) {
        int len;
        if ((text == null) || ((len = text.length()) == 0)) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder(len + (len >> 2));
        char c;
        for (int i = 0; i < len; i++) {
            c = text.charAt(i);
            if (c < 64) {
                sb.append(TEXT[c]);
            }
            else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Escape解码
     * 
     * @param content 被转义的内容
     * @return 解码后的字符串
     */
    public static String decode(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        StringBuilder sb = new StringBuilder(content.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < content.length()) {
            pos = content.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (content.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(content.substring(pos + 2, pos + 6), 16);
                    sb.append(ch);
                    lastPos = pos + 6;
                }
                else {
                    ch = (char) Integer.parseInt(content.substring(pos + 1, pos + 3), 16);
                    sb.append(ch);
                    lastPos = pos + 3;
                }
            }
            else {
                if (pos == -1) {
                    sb.append(content.substring(lastPos));
                    lastPos = content.length();
                }
                else {
                    sb.append(content.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 根据自定义规则清理Html
     *
     * @param html 待清理Html字符串
     * @return 清理后Html字符串
     */
    public static String clean(String html) {
       return HtmlFilter.clean(html);
    }

    /**
     * 清理所有Html标签，只保留文本
     *
     * @param html 待清理Html字符串
     * @return 清理后Html字符串
     */
    public static String cleanAllTags(String html) {
        return HtmlFilter.cleanAllTags(html);
    }

}
