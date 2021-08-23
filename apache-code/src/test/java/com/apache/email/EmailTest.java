package com.apache.email;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * SimpleEmail 纯文本邮件
 * MultiPartEmail 带附件的邮件
 * HtmlEmail 超文本邮件
 * ImageHtmlEmail 图文混排的超文本邮件
 */
@DisplayName("apache email 测试类")
public class EmailTest {

	private static final String FROM_EMAIL = "3210968849@qq.com";
	private static final String EMAIL_PASSWORD = "ppnxfwjzbhamdeja";
	private static final String TO_EMAIL = "3210968849@qq.com";

	@Test
	public void beforeSimpleEmailTest() throws MessagingException {
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.qq.com");
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");

		Session session = Session.getInstance(prop);
		Transport ts = session.getTransport();
		ts.connect(FROM_EMAIL, EMAIL_PASSWORD);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(FROM_EMAIL);
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO_EMAIL));
		message.setSubject("主题");
		message.setContent("正文", "text/html;charset=utf-8");
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
		System.out.println("发送成功");
	}

	@Test
	public void afterSimpleEmailTest() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.qq.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(FROM_EMAIL, EMAIL_PASSWORD));
		email.setSSLOnConnect(true);
		email.setFrom(FROM_EMAIL);
		email.setSubject("主题");
		email.setMsg("内容");
		email.addTo(TO_EMAIL);
		email.send();
		System.out.println("发送成功");
	}

	@Test
	public void beforeHtmlEmail() throws MessagingException, MalformedURLException {
		Properties prop = new Properties();
		prop.setProperty("mail.host", "smtp.qq.com");
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(prop);
		Transport ts = session.getTransport();
		ts.connect("smtp.qq.com", FROM_EMAIL, EMAIL_PASSWORD);
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(FROM_EMAIL));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO_EMAIL));
		message.setSubject("主题");

		MimeBodyPart text = new MimeBodyPart();
		text.setContent("图片<br><img src='cid:avatar.png'>", "text/html;charset=utf-8");

		// 图片
		MimeBodyPart image = new MimeBodyPart();
		image.setDataHandler(new DataHandler(new URL("https://avatars.githubusercontent.com/u/32542178")));
		image.setContentID("avatar.png");

		// 附件
		MimeBodyPart attachment = new MimeBodyPart();
		attachment.setDataHandler(new DataHandler(new FileDataSource("src/test/resources/csv/iris.csv")));
		attachment.setFileName("iris.csv");

		// 构建
		MimeMultipart part = new MimeMultipart();
		part.addBodyPart(text);
		part.addBodyPart(image);
		part.setSubType("related");

		MimeMultipart part2 = new MimeMultipart();
		part2.addBodyPart(attachment);

		MimeBodyPart content = new MimeBodyPart();
		content.setContent(part);
		part2.addBodyPart(content);
		part2.setSubType("mixed");

		message.setContent(part2);
		message.saveChanges();

		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
		System.out.println("发送成功");
	}
}
