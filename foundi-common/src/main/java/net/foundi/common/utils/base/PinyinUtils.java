/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.base;

import net.foundi.common.utils.lang.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉语拼音工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class PinyinUtils {

    /**
     * 获取汉语拼音
     *
     * @param src 汉字字符串
     * @param upperFirst 是否首字母大写
     * @return 汉语拼音
     */
    public static String getPinYin(String src, Boolean upperFirst) {
        if (StringUtils.isEmpty(src)) {
            return "";
        }
        char[] cs = src.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder sb = new StringBuilder();
        try {
            for (char c : cs) {
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    //is Chinese
                    if (upperFirst) {
                        sb.append(StringUtils.upperFirst(PinyinHelper.toHanyuPinyinStringArray(c, format)[0]));
                    } else {
                        sb.append(PinyinHelper.toHanyuPinyinStringArray(c, format)[0]);
                    }
                } else {
                    //not Chinese
                    if (upperFirst) {
                        sb.append(StringUtils.upperFirst(String.valueOf(c)));
                    } else {
                        sb.append(c);
                    }
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw ExceptionUtils.unchecked(e);
        }
        return sb.toString();
    }

    /**
     * 获取汉语拼音，首字母大写
     *
     * @param src 汉字字符串
     * @return 汉语拼音
     */
    public static String getPinYin(String src) {
       return getPinYin(src, true);
    }

    /**
     * 获取汉语拼音首字母
     *
     * @param src 汉字字符串
     * @return 汉语拼音首字母
     */
    public static String getPinYinHeadChar(String src) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char word = src.charAt(i);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                sb.append(pinyinArray[0].charAt(0));
            } else {
                sb.append(word);
            }
        }
        return sb.toString();
    }

    /**
     * 获取汉语拼音大写首字母
     *
     * @param str 汉字字符串
     * @return 汉语拼音首字母
     */
    public static String getPinYinHeadUpperChar(String str) {
        return getPinYinHeadChar(str).toUpperCase();
    }

    public static void main(String[] args) {
        String cnStr = "心有灵犀, 一点通";
        System.out.println(getPinYin(cnStr, false));
        System.out.println(getPinYin(cnStr));
        System.out.println(getPinYinHeadChar(cnStr));
        System.out.println(getPinYinHeadUpperChar(cnStr));
    }
}
