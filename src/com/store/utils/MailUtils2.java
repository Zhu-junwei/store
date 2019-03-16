package com.store.utils;

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
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils2 {

	private static final String FROM_EMAIL = "1286630326@qq.com";
	private static final String FROM_PASS = "stthdvtdvfcxjfdd";

	// email:邮件发给谁; subject:主题; emailMsg:邮件的内容
	public static void sendMail(String email, String subject, String emailMsg)
			throws AddressException, MessagingException {

		// 1、创建一个程序与邮件服务器会话对象 Session
		Properties props = new Properties();

//		localSend(props);
		remoteSend(props);
		
		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(FROM_PASS, FROM_EMAIL);// 本地发送 密码 ，邮箱
				return new PasswordAuthentication(FROM_EMAIL,FROM_PASS);// 远程发送  邮箱，密码 
			}
		};

		// mail.jar包中的Session。和web中的session不一样。
		Session session = Session.getInstance(props, auth);

		// 2、创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(FROM_EMAIL)); // 设置发送者。 (根据需求修改)

		message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

		message.setSubject(subject);// 邮件的主题

		message.setContent(emailMsg, "text/html;charset=utf-8");

		// 3、创建 Transport用于将邮件发送
		Transport.send(message);
	}

	private static void remoteSend(Properties props) {
		props.setProperty("mail.transport.protocol", "SMTP");// 发邮件的协议
//		props.setProperty("mail.host", "localhost"); //发送邮件的服务器地址。(localhost根据需求修改)
		props.setProperty("mail.host", "smtp.qq.com"); // 发送邮件的服务器地址。(localhost根据需求修改)
		props.setProperty("mail.smtp.auth", "true"); // 指定验证为true
	}

	@SuppressWarnings("unused")
	private static void localSend(Properties props) {}
}