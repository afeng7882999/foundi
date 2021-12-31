/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.sms.service;

/**
 * SMS发送服务接口
 *
 * @author Afeng (afeng7882999@163.com)
 */
public interface SmsService {

    void sendCode(String mobile, String scene, String code);
}
