/*
 * Copyright (c) 2019-2021 Afeng & tianfeng All Rights Reserved
 * (email:afeng7882999@163.com, qq:7882999).
 */

package net.foundi.support.email;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.foundi.common.constant.FoundiConst;
import net.foundi.common.exception.BusinessException;
import net.foundi.common.utils.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Email发送
 *
 * @author Afeng (afeng7882999@163.com)
 */
public class EMailSender {

    private static final Logger logger = LoggerFactory.getLogger(EMailSender.class);

    private JavaMailSender javaMailSender;

    private String defaultFrom;

    public EMailSender(JavaMailSender javaMailSender, String from) {
        this.javaMailSender = javaMailSender;
        this.defaultFrom = from;
    }

    /**
     * 发送纯文本邮件
     *
     * @param from    发送地址
     * @param to      接收地址
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void send(String from, String to, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);
        try {
            javaMailSender.send(msg);
            if (logger.isDebugEnabled()) {
                logger.debug(StringUtils.withPrefix("EMAIL：邮件发送成功,from:{}, to:{}"), from, to);
            }
        } catch (Exception e) {
            throw new BusinessException("邮件发送失败", "", e);
        }
    }

    /**
     * 发送纯文本邮件，使用默认发送地址
     *
     * @param to      发送地址
     * @param subject 邮件标题
     * @param content 邮件内容
     */
    public void send(String to, String subject, String content) {
        this.send(this.defaultFrom, to, subject, content);
    }

    /**
     * 发送带附件邮件
     *
     * @param from        发送地址
     * @param to          接收地址
     * @param subject     邮件标题
     * @param content     邮件内容
     * @param attachments 文件列表
     */
    public void send(String from, String to, String subject, String content, List<File> attachments) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true, FoundiConst.DEFAULT_CHARSET);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            for (File file : attachments) {
                helper.addAttachment(file.getName(), file);
            }
            javaMailSender.send(message);
            if (logger.isDebugEnabled()) {
                logger.debug(StringUtils.withPrefix("EMAIL：邮件发送成功,from:{}, to:{}"), from, to);
            }
        } catch (Exception e) {
            throw new BusinessException("邮件发送失败", "", e);
        }
    }

    /**
     * 发送带附件邮件，使用默认发送地址
     *
     * @param to          接收地址
     * @param subject     邮件标题
     * @param content     邮件内容
     * @param attachments 文件列表
     */
    public void send(String to, String subject, String content, List<File> attachments) {
        this.send(this.defaultFrom, to, subject, content, attachments);
    }

    /**
     * 发送验证邮件
     *
     * @param from   发送地址
     * @param to     接收地址
     * @param params 模板参数
     * @param type   验证方式
     */
    public void send(String from, String to, Map<String, String> params, ValidType type) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, FoundiConst.DEFAULT_CHARSET);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("验证" + type.message());
            helper.setText(templateOutput(type.name().toLowerCase() + ".ftl", params), true);
            javaMailSender.send(message);
            if (logger.isDebugEnabled()) {
                logger.debug(StringUtils.withPrefix("EMAIL：邮件发送成功,from:{}, to:{}"), from, to);
            }
        } catch (Exception e) {
            throw new BusinessException("邮件发送失败", "", e);
        }
    }

    /**
     * 发送验证邮件，使用默认发送地址
     *
     * @param to     接收地址
     * @param params 模板参数
     * @param type   验证方式
     */
    public void send(String to, Map<String, String> params, ValidType type) {
        this.send(this.defaultFrom, to, params, type);
    }

    /**
     * 由freemarker模板生成邮件内容
     *
     * @param templateName 模板名称
     * @param params       模板参数
     * @return 邮件内容
     */
    private String templateOutput(String templateName, Map<String, String> params)
            throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassForTemplateLoading(this.getClass(), "/email");
        StringWriter writer = new StringWriter();
        Template temp = cfg.getTemplate(templateName, FoundiConst.DEFAULT_CHARSET);
        temp.process(params, writer);
        return writer.toString();
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String getDefaultFrom() {
        return defaultFrom;
    }

    public void setDefaultFrom(String defaultFrom) {
        this.defaultFrom = defaultFrom;
    }
}
