package com.apache.email;

import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.DataSourceClassPathResolver;
import org.apache.commons.mail.resolver.DataSourceCompositeResolver;
import org.apache.commons.mail.resolver.DataSourceFileResolver;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
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
        // cid:的值要和后面设置的content-id相同
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

    @Test
    public void afterHtmlEmail() throws EmailException, MalformedURLException {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.qq.com");
        email.setAuthenticator(new DefaultAuthenticator(FROM_EMAIL, EMAIL_PASSWORD));
        email.setFrom(FROM_EMAIL);
        email.addTo(TO_EMAIL);
        email.setSubject("主题");

        // 图片
        String cid = email.embed(new URL("https://avatars.githubusercontent.com/u/32542178"), "cid");
        email.setCharset(EmailConstants.UTF_8);
        email.setMsg("<html>头像: <br/><img src=\"cid:" + cid + "\"></html>");
        email.setTextMsg("邮件不支持html格式");

        // 附件
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("src/test/resources/csv/iris.csv");
        email.attach(attachment);

        email.send();
    }

    @Test
    public void afterImageHtmlEmail() throws MalformedURLException, EmailException {
        ImageHtmlEmail email = new ImageHtmlEmail();
        File file = new File("");
        URL url = new URL("");
        String path = "";

        // 发送多张来源不同的图片
        DataSourceResolver r1 = new DataSourceFileResolver(file);
        DataSourceResolver r2 = new DataSourceUrlResolver(url);
        DataSourceResolver r3 = new DataSourceClassPathResolver(path);
        DataSourceResolver[] resolvers = {r1, r2, r3};

        DataSourceResolver resolver = new DataSourceCompositeResolver(resolvers);
        email.setDataSourceResolver(resolver);
        email.setTextMsg("图片1:<br>"
                + "<img src=\"1.png\">"
                + "<img src=\"2.png\">"
                + "<img src=\"3.png\">");

    }
}
