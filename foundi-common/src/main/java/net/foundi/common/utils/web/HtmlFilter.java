/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;

/**
 * 过滤html标签和属性中的敏感字符
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class HtmlFilter {

    private static final Safelist SAFE_LIST_NONE = Safelist.none();
    private static final Safelist SAFE_LIST = HtmlSafeList.custom();

    // 配置过滤化参数,不对代码进行格式化
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);

    /**
     * 根据自定义规则清理Html
     *
     * @param html 待清理Html字符串
     * @return 清理后Html字符串
     */
    public static String clean(String html) {
        // baseUri不能为空，否则属性中所有相对链接Url会被清理
        return Jsoup.clean(html, "http://xx", SAFE_LIST, OUTPUT_SETTINGS);
    }

    /**
     * 清理所有Html标签，只保留文本
     *
     * @param html 待清理Html字符串
     * @return 清理后Html字符串
     */
    public static String cleanAllTags(String html) {
        // baseUri不能为空，否则属性中所有相对链接Url会被清理
        return Jsoup.clean(html, "http://xx", SAFE_LIST_NONE, OUTPUT_SETTINGS);
    }

    /**
     * 自定义Safelist
     */
    public static class HtmlSafeList extends Safelist {
        /**
         * 获取HtmlSafeList实例
         *
         * @return HtmlSafeList
         */
        public static HtmlSafeList custom() {
            HtmlSafeList htmlSafeList = new HtmlSafeList();
            // code from Safelist.relaxed()
            htmlSafeList
                    .addTags(
                            "a", "b", "blockquote", "br", "caption", "cite", "code", "col",
                            "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
                            "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong",
                            "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
                            "ul")
                    .addAttributes("a", "href", "title")
                    .addAttributes("blockquote", "cite")
                    .addAttributes("col", "span", "width")
                    .addAttributes("colgroup", "span", "width")
                    .addAttributes("img", "align", "alt", "height", "src", "title", "width")
                    .addAttributes("ol", "start", "type")
                    .addAttributes("q", "cite")
                    .addAttributes("table", "summary", "width")
                    .addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width")
                    .addAttributes(
                            "th", "abbr", "axis", "colspan", "rowspan", "scope",
                            "width")
                    .addAttributes("ul", "type")
                    .addProtocols("a", "href", "ftp", "http", "https", "mailto")
                    .addProtocols("blockquote", "cite", "http", "https")
                    .addProtocols("cite", "cite", "http", "https")
                    .addProtocols("img", "src", "http", "https")
                    .addProtocols("q", "cite", "http", "https")
                    // 不转换相对链接url
                    .preserveRelativeLinks(true);
            return htmlSafeList;
        }

        @Override
        protected boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
            // 允许Base64图像的Url
            if ("img".equalsIgnoreCase(tagName) && "src".equalsIgnoreCase(attr.getKey())) {
                String value = attr.getValue();
                if (value.startsWith("data:image/png;base64")
                        || value.startsWith("data:image/jpeg;base64")
                        || value.startsWith("data:image/gif;base64")
                        || value.startsWith("data:image/x-icon;base64")) {
                    return true;
                }
            }
            // 不允许javascript开头的src和 href属性
            if ("src".equalsIgnoreCase(attr.getKey()) || "href".equalsIgnoreCase(attr.getKey())) {
                String value = attr.getValue();
                if (value.toLowerCase().startsWith("javascript")) {
                    return false;
                }
            }
            return super.isSafeAttribute(tagName, el, attr);
        }
    }

}
