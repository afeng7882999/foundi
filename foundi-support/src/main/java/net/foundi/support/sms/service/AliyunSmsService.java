/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.sms.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.lang.AssertUtils;
import net.foundi.support.sms.config.SmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 阿里云SMS发送服务
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class AliyunSmsService implements SmsService {

	private static Logger logger = LoggerFactory.getLogger(AliyunSmsService.class);

	private SmsProperties properties;

	// 短信API产品名称（短信产品名固定，无需修改）
	private static final String product = "Dysmsapi";
	// 短信API产品域名（接口地址固定，无需修改）
	private static final String domain = "dysmsapi.aliyuncs.com";
	private static String accessKeyId;
	private static String accessKeySecret;
	
	public AliyunSmsService(SmsProperties properties) {
		this.properties = properties;
		accessKeyId = properties.get("accessKeyId");
		accessKeySecret = properties.get("accessKeySecret");
	}

	public void sendCode(String mobile, String scene, String code) {
		Map<String, String> sc = properties.getScene(scene);
		if (sc == null || !sc.containsKey("signName") || !sc.containsKey("templateCode")) {
			logger.warn("SMS：发送短信失败：请配置foundi.sms.aliyun.scenes");
			throw new BusinessException("发送短信失败");
		}
		
		String signName = sc.get("signName");
		String templateCode = sc.get("templateCode");
		String templateParam = "{\"code\":\"" + code + "\"}";
		send(mobile, signName, templateCode, templateParam);
		logger.info("SMS：发送短信成功，mobile：{}，code：{}，scene：{}", mobile, code, scene);
	}

	/**
	 * @param phoneNumbers 待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
	 * @param signName 短信签名-可在短信控制台中找到
	 * @param templateCode 短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
	 * @param templateParam 模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为"{\"name\":\"Tom\", \"code\":\"123\"}"
	 */
	private static void send(String phoneNumbers, String signName, String templateCode, String templateParam) {
		AssertUtils.paramAnyEmpty(accessKeyId, accessKeySecret, phoneNumbers, signName, templateCode, templateParam)
				.ex(() -> new BusinessException("发送短信失败"));
		try {
			// 设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			// 初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
			request.setPhoneNumbers(phoneNumbers);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			// 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
			request.setTemplateCode(templateCode);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			request.setTemplateParam(templateParam);
			// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			// request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse res = acsClient.getAcsResponse(request);
			if (res.getCode() != null && res.getCode().equals("OK")) {
				logger.info("SMS：阿里云发送短信成功，code：{}，message：{}，requestId：{}，bizId：{}",
						res.getCode(), res.getMessage(), res.getRequestId(), res.getBizId());
			} else {
				String msg = "code:" + res.getCode() + ",message:" + res.getMessage() + ",requestId:" +
						res.getRequestId() + ",bizId:" + res.getBizId();
				throw new BusinessException("发送短信失败", msg);
			}
		} catch (Exception e) {
			logger.warn("SMS：阿里云发送短信失败：{}，mobile：{}，signName：{}，templateCode：{}，templateParam：{}",
					e.getMessage(), phoneNumbers, signName, templateCode, templateParam);
			throw new BusinessException("发送短信失败", e);
		}
	}

}
