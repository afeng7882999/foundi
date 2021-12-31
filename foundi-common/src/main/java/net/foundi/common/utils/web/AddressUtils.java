/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.common.utils.web;

import net.foundi.common.utils.lang.JsonUtils;
import net.foundi.common.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * 获取地址工具类
 *
 * @author Afeng (afeng7882999@163.com)
*/
public class AddressUtils {

    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IPUtils.internalIp(ip)) {
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        try {
            Map<String, String> map = JsonUtils.toStringMap(rspStr);
            String region = map.get("region");
            String city = map.get("city");
            address = region + " " + city;
        } catch (IOException e) {
            log.error("解析地理位置异常 {}", ip);
            return address;
        }
        return address;
    }

}
