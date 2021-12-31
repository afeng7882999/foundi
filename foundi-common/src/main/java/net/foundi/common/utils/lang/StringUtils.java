/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.lang;

import net.foundi.common.constant.FoundiConst;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字符串工具类
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class StringUtils extends org.springframework.util.StringUtils  {

    // 分隔符
    public static final String SEPARATOR = ",";

    // 下划线分隔符
    public static final String UNDERLINE = "_";

    // 空字符串
    public static final String EMPTY = "";

    // 空占位符
    public static final String EMPTY_PLACEHOLDER = "{}";

    // 转义斜杠
    public static final char BACKSLASH = '\\';

    // 占位符起始字符
    public static final char PLACEHOLDER_START = '{';

    /**
     * 判断一个字符串是否为空串
     *
     * @param str string
     * @return true：为空字符串
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals(EMPTY);
    }

    /**
     * 判断一个字符串是否为空串，考虑只有空格的情况
     *
     * @param str String
     * @return true：为空字符串或只有空格
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().equals(EMPTY);
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串
     */
    public static boolean hasValue(String str) {
        return !isEmpty(str);
    }

    /**
     * 多个字符串中是否有空字符串
     *
     * @param strs 字符串数组
     * @return true：有空字符串
     */
    public static boolean anyEmpty(String... strs) {
        if (strs == null) {
            return true;
        }
        for (String s : strs) {
            if (isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * XML中字符串标记是否表示为true
     *
     * @param str boolean字符串标记
     * @return true：boolean字符串标记表示为true
     */
    public static boolean isTrue(String str) {
        if (str == null) {
            return false;
        }
        return str.equalsIgnoreCase("true") ||
                str.equalsIgnoreCase("1") ||
                str.equalsIgnoreCase("enabled") ||
                str.equalsIgnoreCase("y") ||
                str.equalsIgnoreCase("yes") ||
                str.equalsIgnoreCase("on");
    }

    /**
     * XML中字符串标记是否表示为false
     *
     * @param str boolean字符串标记
     * @return true：boolean字符串标记表示为false
     */
    public static boolean isFalse(String str) {
        if (str == null) {
            return true;
        }
        return str.equalsIgnoreCase("false") ||
                str.equalsIgnoreCase("0") ||
                str.equalsIgnoreCase("disabled") ||
                str.equalsIgnoreCase("n") ||
                str.equalsIgnoreCase("no") ||
                str.equalsIgnoreCase("off");
    }

    /**
     * 字符串列表拼接为字符串，使用","分隔符
     *
     * @param stringList 字符串数组
     * @return 拼接的字符串
     */
    public static String strList2Str(List<String> stringList) {
        return String.join(SEPARATOR, stringList);
    }

    /**
     * 字符串列表拼接为字符串，指定分隔符
     *
     * @param stringList 字符串数组
     * @param split      分隔符
     * @return 拼接的字符串
     */
    public static String strList2Str(List<String> stringList, String split) {
        return String.join(split, stringList);
    }

    /**
     * 将使用","分隔的字符串转换为字符串列表
     * 同时去掉首尾空白
     *
     * @param commaString 使用","分隔的字符串
     * @return 字符串数组
     */
    public static List<String> str2List(String commaString) {
        return str2List(commaString, SEPARATOR);
    }

    /**
     * 将使用特定分隔的字符串转换为字符串数组
     * 同时去掉首尾空白
     *
     * @param original String 待分隔的字符串
     * @param split    分隔符
     * @return 字符串列表
     */
    public static List<String> str2List(String original, String split) {
        return Stream.of(original.split(split)).map(String::trim).collect(Collectors.toList());
    }

    /**
     * 列表拼接为字符串，指定分隔符
     *
     * @param list  Object列表
     * @param split 指定分隔符
     * @return 拼接的字符串
     */
    public static <T> String list2Str(List<T> list, String split) {
        return list.stream().map(Object::toString).collect(Collectors.joining(SEPARATOR));
    }

    /**
     * 字符串数组拼接为字符串，指定分隔符
     *
     * @param split   分隔符
     * @param subStrs 字符串数组
     * @return 拼接的字符串
     */
    public static String joinStrings(String split, String... subStrs) {
        StringBuilder sb = new StringBuilder();
        for (String s : subStrs) {
            sb.append(s).append(split);
        }
        if (sb.length() >= split.length()) {
            sb.delete(sb.length() - split.length(), sb.length());
        }
        return sb.toString();
    }

    /**
     * 下划线命名法转换为驼峰命名法，首字母大写
     *
     * @param snakeCaseStr 下划线命名的字符串
     * @return 驼峰命名的字符串
     */
    public static String snakeToCamel(String snakeCaseStr) {
        return snakeToCamel(snakeCaseStr, true);
    }

    /**
     * 下划线命名法转换为驼峰命名法
     *
     * @param snakeCaseStr 下划线命名的字符串
     * @param upperFirst   首字母大写/小写
     * @return 驼峰命名的字符串
     */
    public static String snakeToCamel(String snakeCaseStr, boolean upperFirst) {
        return splitToCamel(snakeCaseStr, '_' , upperFirst);
    }

    /**
     * 给定分隔符字符串转换为驼峰命名法
     *
     * @param str 字符串
     * @param split 分隔符
     * @param upperFirst   首字母大写/小写
     * @return 驼峰命名的字符串
     */
    public static String splitToCamel(String str, char split, boolean upperFirst) {
        if (isEmpty(str)) {
            return EMPTY;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == split) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(str.charAt(i)));
                }
            } else if (i == 0 && upperFirst) {
                sb.append(Character.toUpperCase(str.charAt(i)));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰命名法转换为下划线命名法
     *
     * @param camelCaseStr 驼峰命名的字符串
     * @return 下划线命名的字符串
     */
    public static String camelToSnake(String camelCaseStr) {
        return camelToSnake(camelCaseStr, "_");
    }

    /**
     * 驼峰命名法转换为split分隔的字符串
     *
     * @param split 分隔符
     * @param camelCaseStr 驼峰命名的字符串
     * @return split分隔的字符串的字符串
     */
    public static String camelToSnake(String camelCaseStr, String split) {
        if (isEmpty(camelCaseStr)) {
            return EMPTY;
        }
        int length = camelCaseStr.length();
        StringBuilder sb = new StringBuilder(length);
        sb.append(Character.toLowerCase(camelCaseStr.charAt(0)));
        for (int i = 1; i < length; i++) {
            char c = camelCaseStr.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(split);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字符串数组是否包含某字符串，不考虑大小写
     *
     * @param str  验证字符串
     * @param strs 字符串数组
     * @return 包含则返回true
     */
    public static boolean inIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(s.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断使用split分隔的字符串，能否分隔出sub字符串
     *
     * @param str   包含分隔符的字符串
     * @param sub   sub string 检测的字符串
     * @param split split 分隔符
     * @return true：包含
     */
    public static boolean contain(String str, String sub, String split) {
        if (hasValue(str) && hasValue(sub)) {
            str = str.replaceAll("\\s*", EMPTY);
            String[] sl = str.split(split);
            for (String s : sl) {
                if (s.equals(sub.trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断使用","分隔符的字符串，能否分隔出sub字符串
     *
     * @param str 包含分隔符的字符串
     * @param sub sub string 检测的字符串
     * @return true：包含
     */
    public static boolean contain(String str, String sub) {
        return contain(str, sub, SEPARATOR);
    }

    /**
     * 找到符合正则表达式的第一个部分
     *
     * @param str   被检测的字符串
     * @param regex 正则表达式
     * @return 被找出的子字符串
     */
    public static String subStringFirst(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 找到符合正则表达式的所有部分
     *
     * @param str   被检测的字符串
     * @param regex 正则表达式
     * @return 被找出的子字符串列表
     */
    public static List<String> subString(String str, String regex) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    /**
     * 清除回车、空格、制表符
     *
     * @param str 原字符串
     * @return 处理后的字符串
     */
    public static String trimBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll(EMPTY);
        }
        return str;
    }

    /**
     * 清除回车、空格、制表符、标点
     *
     * @param str 原字符串
     * @return 处理后的字符串
     */
    public static String trimPunctuation(String str) {
        if (isEmpty(str)) {
            return EMPTY;
        }
        return str.replaceAll("[\\pP\\pS\\pZ]", EMPTY);
    }

    /**
     * 判断字符串的前缀
     *
     * @param str    被检测字符串
     * @param prefix 前缀
     * @return true：前缀为prefix
     */
    public static boolean startWith(String str, String prefix) {
        if (str.length() < prefix.length())
            return false;
        return str.substring(0, prefix.length()).equalsIgnoreCase(prefix);
    }

    /**
     * 去掉指定前缀
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 处理后的字符串
     */
    public static String removePrefix(String str, String prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str;
        }
        if (str.startsWith(prefix)) {
            return substring(str, prefix.length(), str.length());
        }
        return str;
    }

    /**
     * 获取前缀
     *
     * @param str   字符串
     * @param split 前缀的分隔符
     * @return 前缀
     */
    public static String getPrefix(String str, String split) {
        if (isEmpty(str) || isEmpty(split)) {
            return str;
        }

        return str2List(str, split).get(0);
    }

    /**
     * 获取前缀
     *
     * @param str 字符串
     * @return 前缀
     */
    public static String getPrefix(String str) {
        if (isEmpty(str)) {
            return str;
        }

        return str2List(str, UNDERLINE).get(0);
    }

    /**
     * 首字母小写
     *
     * @param original 原字符串
     * @return 处理后的字符串
     */
    public static String lowerFirst(String original) {
        return Character.toLowerCase(original.charAt(0)) + original.substring(1);
    }

    /**
     * 首字母大写
     *
     * @param original 原字符串
     * @return 处理后的字符串
     */
    public static String upperFirst(String original) {
        return Character.toUpperCase(original.charAt(0)) + original.substring(1);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return EMPTY;
        }
        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return EMPTY;
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 简化email地址，如：afeng78829999@163.com -> af***@163.com
     *
     * @param email 原字符串
     * @return 简化后的字符串
     */
    public static String briefEmail(String email) {
        if (isBlank(email)) {
            return EMPTY;
        }
        return email.substring(0, 2) + "***" + email.substring(email.indexOf("@"));
    }

    /**
     * 简化手机号码，如：17730271886 -> 177*****886
     *
     * @param mobile 原字符串
     * @return 简化后的字符串
     */
    public static String briefMobile(String mobile) {
        if (isBlank(mobile)) {
            return EMPTY;
        }
        return mobile.substring(0, 3) + "*****" + mobile.substring(8);
    }

    /**
     * 字符串加方括号
     *
     * @param msg 对象
     * @return 处理的字符串
     */
    public static String withBlock(Object msg) {
        return msg != null ? "[" + msg.toString() + "]" : EMPTY;
    }

    /**
     * 日志字符串加前缀
     *
     * @param msg 对象
     * @return 处理的字符串
     */
    public static String withPrefix(String msg) {
        return msg != null ? FoundiConst.GLOBAL_LOG_PREFIX + msg : EMPTY;
    }

    /**
     * 格式化字符串
     * 此方法只是简单将占位符 {} 按照顺序替换为参数
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可
     * 例：
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b
     *
     * @param strPattern 字符串模板
     * @param argArray   参数列表
     * @return 结果
     */
    public static String format(final String strPattern, final Object... argArray) {
        if (StringUtils.isEmpty(strPattern)) {
            return strPattern;
        }
        int strPatternLength = strPattern.length();

        // 初始化定义好的长度以获得更好的性能
        StringBuilder sb = new StringBuilder(strPatternLength + 50);

        int handledPos = 0;
        // 占位符所在位置
        int placeIdx;
        for (int argIdx = 0; argIdx < argArray.length; argIdx++) {
            placeIdx = strPattern.indexOf(EMPTY_PLACEHOLDER, handledPos);
            if (placeIdx == -1) {
                if (handledPos == 0) {
                    return strPattern;
                } else {
                    // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                    sb.append(strPattern, handledPos, strPatternLength);
                    return sb.toString();
                }
            } else {
                if (placeIdx > 0 && strPattern.charAt(placeIdx - 1) == BACKSLASH) {
                    if (placeIdx > 1 && strPattern.charAt(placeIdx - 2) == BACKSLASH) {
                        // 转义符之前还有一个转义符，占位符依旧有效
                        sb.append(strPattern, handledPos, placeIdx - 1);
                        sb.append(ConvertUtils.utf8Str(argArray[argIdx]));
                        handledPos = placeIdx + 2;
                    } else {
                        // 占位符被转义
                        argIdx--;
                        sb.append(strPattern, handledPos, placeIdx - 1);
                        sb.append(PLACEHOLDER_START);
                        handledPos = placeIdx + 1;
                    }
                } else {
                    // 正常占位符
                    sb.append(strPattern, handledPos, placeIdx);
                    sb.append(ConvertUtils.utf8Str(argArray[argIdx]));
                    handledPos = placeIdx + 2;
                }
            }
        }
        // 加入最后一个占位符后所有的字符
        sb.append(strPattern, handledPos, strPattern.length());

        return sb.toString();
    }

}