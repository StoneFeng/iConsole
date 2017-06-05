package com.singland.console.test;

//import java.io.UnsupportedEncodingException;
//import java.util.Properties;
//
//import javax.mail.Address;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import org.testng.annotations.Test;

public class JavaMailTest {
	
	@Test
	public void sendMailTest() throws Exception {
//		Properties props = new Properties();
//		props.setProperty("mail.debug", "true");
//		props.setProperty("mail.smtp.auth", "false");
//		props.setProperty("mail.host", "smtptest.huawei.com");
//		props.setProperty("mail.transport.protocol", "smtp");
//		Session session = Session.getInstance(props);
//		Message msg = new MimeMessage(session);
//		msg.setSubject("JavaMail测试");
//		msg.setText("这是一封由JavaMail发送的邮件！");
//		msg.setFrom(new InternetAddress("fengxinbo@huawei.com"));
//		Transport transport = session.getTransport();
//		transport.connect("fengxinbo", "javamail");
//		transport.sendMessage(msg, new Address[] {new InternetAddress("fengxinbo@huawei.com")});
//		transport.close();
		
		
//		Session session= Session.getDefaultInstance(props);
//		MimeMessage message = new MimeMessage(session);
//		message.setFrom(new InternetAddress("fengxinbo@huawei.com", "Stone Feng", "UTF-8"));
//		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("fengxinbo@huawei.com", "fengxinbo", "UTF-8"));
//		message.setSubject("TEST邮件主题", "UTF-8");
		
	}

}
