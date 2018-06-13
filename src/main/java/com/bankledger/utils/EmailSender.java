package com.bankledger.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2017年12月4日 下午7:28:16
 */
public class EmailSender {

	private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

	private static String username;

	private static String password;

	private static Properties getProperties() {

		Properties prop = new Properties();
		// 协议
		prop.setProperty("mail.transport.protocol", PropertyUtil.getProperty("mail.transport.protocol"));
		// 服务器
		prop.setProperty("mail.host", PropertyUtil.getProperty("mail.host"));
		// 端口
		prop.setProperty("mail.smtp.port", PropertyUtil.getProperty("mail.smtp.port"));
		// 开启debug
		prop.setProperty("mail.debug", PropertyUtil.getProperty("mail.debug"));
		// 身份验证
		prop.setProperty("mail.smtp.auth", PropertyUtil.getProperty("mail.smtp.auth"));
		// 使用SSL，企业邮箱必需！
		MailSSLSocketFactory sslSocketFactory = null;
		try {
			sslSocketFactory = new MailSSLSocketFactory();
			sslSocketFactory.setTrustAllHosts(true);
			prop.put("mail.smtp.ssl.enable", PropertyUtil.getProperty("mail.smtp.ssl.enable"));
			prop.put("mail.smtp.ssl.socketFactory", sslSocketFactory);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}

		username = PropertyUtil.getProperty("mail.username");
		password = PropertyUtil.getProperty("mail.password");

		return prop;
	}

	public static void sendEmail(String to, String subject, String context)
			throws UnsupportedEncodingException, MessagingException {

		// 设置环境信息
		Session session = Session.getDefaultInstance(getProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		session.setDebug(true);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username));
		// 收件人
		message.addRecipient(RecipientType.TO, new InternetAddress(to));
		// 主题
		message.setSubject(subject);
		// 内容
		message.setContent(context, "text/html;charset=utf-8");
		// 保存
		message.saveChanges();
		// 发送
		Transport.send(message);
	}

	public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {

		// 模板解析器
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("files/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
		templateResolver.setCacheable(true);
		templateResolver.setCharacterEncoding("UTF-8");
		// 模板引擎
		TemplateEngine template = new TemplateEngine();
		template.setTemplateResolver(templateResolver);

		// 内容
		Context context = new Context();
		context.setVariable("message", "hello word ！");
		// 装载
		String str = template.process("template_1", context);

		System.out.println(str);
		EmailSender.sendEmail("tuyi954456157@qq.com", "我是主题", str);

	}
}
