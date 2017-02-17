package com.application.base.utils.email.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.utils.common.StringDefaultValue;
import com.application.base.utils.message.util.MessageUtil;

/**
 * bruce 发送邮件. 测试OK
 */
public class SendMailUtil {

	public static final Logger logger = LoggerFactory.getLogger(SendMailUtil.class.getName());

	private static SendMailUtil instance = null;

	private SendMailUtil() {
	}

	public static SendMailUtil getInstance() {
		if (instance == null) {
			instance = new SendMailUtil();
		}
		return instance;
	}

	/**
	 * 发邮件初始化的信息(注意设置默认值).
	 * 
	 * @param mailHost
	 * @param mailPort
	 * @param from
	 * @param user
	 * @param password
	 * @param auth
	 * @param protocol
	 * @param toList
	 * @param ccList
	 * @param title
	 * @param content
	 * @param files
	 * @return
	 */
	public static EmailInfo initEmailInfo(String mailHost, String mailPort, String from, String user, String password,
			String auth, String protocol, String[] toList, String[] ccList, String title, String content,
			String[] files) {
		if (StringDefaultValue.isEmpty(mailHost)) {
			mailHost = MessageUtil.getMsgVal("mailHost", "mail.163.com");
		}
		if (StringDefaultValue.isEmpty(mailPort)) {
			mailPort = MessageUtil.getMsgVal("mailPort", "25");
		}
		if (StringDefaultValue.isEmpty(from)) {
			from = MessageUtil.getMsgVal("from", "");
		}
		if (StringDefaultValue.isEmpty(user)) {
			user = MessageUtil.getMsgVal("user", "");
		}
		if (StringDefaultValue.isEmpty(password)) {
			password = MessageUtil.getMsgVal("password", "");
		}
		if (StringDefaultValue.isEmpty(auth)) {
			auth = MessageUtil.getMsgVal("auth", "true");
		}
		if (StringDefaultValue.isEmpty(protocol)) {
			protocol = MessageUtil.getMsgVal("protocol", "smtp");
		}
		if (StringDefaultValue.isEmpty(toList) || toList.length == 0) {
			String lists = MessageUtil.getMsgVal("toList", "");
			if (!StringDefaultValue.isEmpty(lists)) {
				toList = lists.split("#");
			}
		}
		if (StringDefaultValue.isEmpty(ccList) || ccList.length == 0) {
			String lists = MessageUtil.getMsgVal("ccList", "");
			if (!StringDefaultValue.isEmpty(lists)) {
				ccList = lists.split("#");
			}
		}
		if (StringDefaultValue.isEmpty(files)) {
			String lists = MessageUtil.getMsgVal("files", "");
			if (!StringDefaultValue.isEmpty(lists)) {
				files = lists.split("#");
			}
		}

		EmailInfo email = new EmailInfo();
		email.setMailHost(mailHost);
		email.setMailPort(mailPort);
		email.setFrom(from);
		email.setUser(user);
		email.setPassword(password);
		email.setAuth(auth);
		email.setProtocol(protocol);
		email.setToList(toList);
		email.setCcList(ccList);
		email.setSubject(title);
		email.setContent("<meta http-equiv=Content-Type content=text/html;charset=UTF-8>" + content);
		email.setFiles(files);
		return email;
	}

	/**
	 * send messages.
	 * 
	 * @param email
	 */
	public static void sendMails(EmailInfo email) {
		Message msg = null;
		String message = "";
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", email.getAuth());
			props.put("mail.transport.protocol", email.getProtocol() == null ? "smtp" : email.getProtocol());
			props.put("mail.smtp.host", email.getMailHost());
			props.put("mail.smtp.port", email.getMailPort() == null ? "25" : email.getMailPort());
			// 建立会话
			Session session = Session.getInstance(props);
			msg = new MimeMessage(session); // 建立信息
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			msg.setFrom(new InternetAddress(email.getFrom())); // 发件人

			// 发送,
			if (email.getToList() != null && email.getToList().length > 0) {
				InternetAddress[] iaToList = new InternetAddress[email.getToList().length];
				for (int i = 0; i < email.getToList().length; i++) {
					if (!StringDefaultValue.isEmpty(email.getToList()[i])) {
						iaToList[i] = new InternetAddress(email.getToList()[i]);
					}
				}
				msg.setRecipients(Message.RecipientType.TO, iaToList);
			}
			// 抄送
			if (email.getCcList() != null && email.getCcList().length > 0) {
				InternetAddress[] iaToListcs = new InternetAddress[email.getCcList().length];
				for (int i = 0; i < email.getCcList().length; i++) {
					if (!StringDefaultValue.isEmpty(email.getCcList()[i])) {
						iaToListcs[i] = new InternetAddress(email.getCcList()[i]);
					}
				}
				msg.setRecipients(Message.RecipientType.CC, iaToListcs);
			}

			msg.setSentDate(new Date()); // 发送日期
			msg.setSubject(email.getSubject()); // 主题
			msg.setText(email.getContent()); // 内容

			// 显示以html格式的文本内容
			messageBodyPart.setContent(email.getContent(), "text/html;charset=UTF-8");
			multipart.addBodyPart(messageBodyPart);
			// 附件
			if (email.getFiles() != null) {
				for (int i = 0; i < email.getFiles().length; i++) {
					addTach(email.getFiles()[i], multipart);
				}
			}
			msg.setContent(multipart);
			// 邮件服务器进行验证
			Transport tran = session.getTransport("smtp");
			tran.connect(email.getMailHost(), email.getUser(), email.getPassword());
			tran.sendMessage(msg, msg.getAllRecipients()); // 发送
			message = "邮件已经发送成功,邮件标题:" + email.getSubject();
			logger.info(message);
			System.out.println(message);
		}
		catch (Exception e) {
			message = "邮件已经发送失败,邮件标题:" + email.getSubject() + ",error:" + e;
			e.printStackTrace();
			logger.error(message);
			System.out.println(message);
		}
	}

	// 添加一个附件
	private static void addTach(String file, Multipart multipart)
			throws MessagingException, UnsupportedEncodingException {
		MimeBodyPart mailArchieve = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(file);
		mailArchieve.setDataHandler(new DataHandler(fds));
		mailArchieve.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
		multipart.addBodyPart(mailArchieve);
	}

	/**
	 * 获取邮件发送列表
	 * 
	 * @return
	 */
	public static String getMailList(String[] mailArray) {
		StringBuffer toList = new StringBuffer();
		int length = mailArray.length;
		if (mailArray != null && length < 2) {
			toList.append(mailArray[0]);
		}
		else {
			for (int i = 0; i < length; i++) {
				toList.append(mailArray[i]);
				if (i != (length - 1)) {
					toList.append(",");
				}
			}
		}
		return toList.toString();
	}
}

