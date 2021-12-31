/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.sms.service;

import net.foundi.common.constant.FoundiConst;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.lang.AssertUtils;
import net.foundi.support.sms.config.SmsProperties;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 华为云SMS发送服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class HuaweiSmsService implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(HuaweiSmsService.class);

    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";

    private final SmsProperties properties;

    //APP接入地址+接口访问URI
    private final String url;
    //APP_Key
    private final String appKey;
    //APP_Secret
    private final String appSecret;

    public HuaweiSmsService(SmsProperties properties) {
        this.properties = properties;
        this.url = properties.get("url");
        this.appKey = properties.get("appKey");
        this.appSecret = properties.get("appSecret");
    }

    @Override
    public void sendCode(String mobile, String scene, String code) {
        Map<String, String> sc = properties.getScene(scene);
        if (sc == null
                || !sc.containsKey("sender")
                || !sc.containsKey("templateId")
                || !sc.containsKey("signature")) {
            logger.warn("SMS：发送短信失败: 请配置foundi.sms.huawei.scenes");
            throw new BusinessException("发送短信失败");
        }

        String sender = sc.get("sender");
        String templateId = sc.get("templateId");
        String signature = sc.get("signature");
        String templateParam = "[\"" + code + "\"]";
        send(mobile, templateParam, sender, templateId, signature);
        logger.info("SMS：发送短信成功，mobile：{}，code：{}，scene：{}", mobile, code, scene);
    }

    /**
     * 发送短信
     * @param mobile 短信接收人号码，必填,全局号码格式(包含国家码),示例:+8615123456789,多个号码之间用英文逗号分隔
     * @param templateParam 模板变量
     *                      使用无变量模板时请赋空值 String templateParas = "";
     *                      单变量模板示例:模板内容为"您的验证码是${NUM_6}"时,templateParas可填写为"[\"369751\"]"
     *                      双变量模板示例:模板内容为"您有${NUM_2}件快递请到${TXT_32}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
     *                      查看更多模板格式规范:常见问题>业务规则>短信模板内容审核标准
     * @param sender 国内短信签名通道号或国际/港澳台短信通道号
     * @param templateId 模板ID
     * @param signature 签名名称
     *                  条件必填,国内短信关注,当templateId指定的模板类型为通用模板时生效且必填,必须是已审核通过的,与模板类型一致的签名名称
     *                  国际/港澳台短信不用关注该参数
     */
    private void send(String mobile, String templateParam, String sender, String templateId, String signature) {
        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "";

        try {
            //请求Body,不携带签名名称时,signature请填null
            String body = buildRequestBody(sender, mobile, templateId, templateParam, statusCallBack, signature);

            //请求Headers中的X-WSSE参数值
            String wsseHeader = buildWsseHeader(appKey, appSecret);

            CloseableHttpClient client = HttpClients.custom()
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                            (x509CertChain, authType) -> true).build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();

            HttpResponse response = client.execute(RequestBuilder.create("POST")//请求方法POST
                    .setUri(url)
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                    .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                    .addHeader("X-WSSE", wsseHeader)
                    .setEntity(new StringEntity(body)).build());

            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() == 200) {
                logger.info("SMS：华为云发送短信成功，mobile：{}，signName：{}，templateCode：{}，templateParam：{}",
                        mobile, signature, templateId, templateParam);
            } else {
                throw new BusinessException("发送短信失败", status.toString());
            }

        } catch (Exception e) {
            logger.warn("SMS：华为云发送短信失败，response：{}，mobile：{}，signature：{}，templateId：{}，templateParam：{}",
                    e.getMessage(), mobile, signature, templateId, templateParam);
            throw new BusinessException("发送短信失败", e.getMessage(), e);
        }
    }

    /**
     * 构造请求Body体
     */
    private String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallbackUrl, String signature) {
        AssertUtils.paramAnyEmpty(sender, receiver, templateId)
                .ex(() -> new BusinessException("发送短信失败",
                        "buildRequestBody(): sender, receiver or templateId is null."));
        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();
        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        if (null != templateParas && !templateParas.isEmpty()) {
            keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        }
        if (null != statusCallbackUrl && !statusCallbackUrl.isEmpty()) {
            keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));
        }
        if (null != signature && !signature.isEmpty()) {
            keyValues.add(new BasicNameValuePair("signature", signature));
        }

        return URLEncodedUtils.format(keyValues, FoundiConst.DEFAULT_CHARSET);
    }

    /**
     * 构造X-WSSE参数值
     */
    private String buildWsseHeader(String appKey, String appSecret) {
        AssertUtils.paramAnyEmpty(appKey, appSecret)
                .ex(() -> new BusinessException("发送短信失败", "appKey或appSecret为空"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = LocalDateTime.now().format(formatter);
        String nonce = UUID.randomUUID().toString().replace("-", ""); //Nonce

        byte[] passwordDigest = DigestUtils.sha256(nonce + time + appSecret);
        String hexDigest = Hex.encodeHexString(passwordDigest);
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(hexDigest.getBytes()); //PasswordDigest

        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }
}
