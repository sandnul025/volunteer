package com.bankledger.utils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmailUtils {

	private static String account;// 登录用户名
	private static String pass; // 登录密码
	private static String from; // 发件地址
	private static String host; // 服务器地址
	private static String port; // 端口
	private static String protocol; // 协议
	public static String path;

	private String to;

	// 用户名密码验证，需要实现抽象类Authenticator的抽象方法PasswordAuthentication
	static class MyAuthenricator extends Authenticator {
		String u = null;
		String p = null;

		public MyAuthenricator(String u, String p) {
			this.u = u;
			this.p = p;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(u, p);
		}
	}

	public SendEmailUtils(String to) {

		account = PropertyUtil.getProperty("mail.username");
		pass = PropertyUtil.getProperty("mail.password");
		from = PropertyUtil.getProperty("mail.username");
		host = PropertyUtil.getProperty("mail.host");
		port = PropertyUtil.getProperty("mail.smtp.port");
		protocol = PropertyUtil.getProperty("mail.transport.protocol");
		this.to = to;// 收件人
	}

	public void send(String code) throws AddressException, MessagingException, GeneralSecurityException, UnsupportedEncodingException {

		Properties prop = new Properties();
		// 协议
		prop.setProperty("mail.transport.protocol", protocol);
		// 服务器
		prop.setProperty("mail.smtp.host", host);
		// 端口
		prop.setProperty("mail.smtp.port", port);
		// 使用smtp身份验证
		prop.setProperty("mail.smtp.auth", "true");
		// 使用SSL，企业邮箱必需！
		// 开启安全协议
		MailSSLSocketFactory sf = null;
		
		sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);

		Session session = Session.getInstance(prop, new MyAuthenricator(account, pass));
		session.setDebug(true);

		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(from, "博客邮箱验证码"));
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		mimeMessage.setSubject("博客验证码");
		mimeMessage.setSentDate(new Date());
		mimeMessage.setContent("<html><body><p>您的验证码是：" + code + "</a></body></html>",
				"text/html;charset=utf-8");
		mimeMessage.saveChanges();

		Transport.send(mimeMessage);
	}

	public static void test4() throws Exception {

		SendEmailUtils s = new SendEmailUtils("tuyi954456157@163.com");
		s.send("123456");
	}

	public static void main(String[] args) throws Exception {
		test4();
	}

}