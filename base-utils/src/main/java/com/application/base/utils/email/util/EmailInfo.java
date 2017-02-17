package com.application.base.utils.email.util;


/**
 * 载体.
 */
public class EmailInfo {

	private String mailHost;// 邮箱服务器ip/域名
	private String mailPort;// 端口
	private String from;// 来源
	private String user;// 用户名
	private String password;// 登陆密码
	private String protocol;// 协议pop3,smtp,imap
	private String auth; // 是否校验
	private String[] toList;// 目的地址串
	private String[] ccList;// 抄送地址串
	private String subject;// 主题
	private String content;// 内容
	private String[] files; // 文件

	public EmailInfo() {

	}

	public EmailInfo(String mailHost, String mailPort, String from, String user, String password, String protocol,
			String auth, String[] toList, String[] ccList, String subject, String content, String[] files) {
		super();
		this.mailHost = mailHost;
		this.mailPort = mailPort;
		this.from = from;
		this.user = user;
		this.password = password;
		this.protocol = protocol;
		this.auth = auth;
		this.toList = toList;
		this.ccList = ccList;
		this.subject = subject;
		this.content = content;
		this.files = files;
	}



	public String[] getToList() {
		return toList;
	}

	public void setToList(String[] toList) {
		this.toList = toList;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getCcList() {
		return ccList;
	}

	public void setCcList(String[] ccList) {
		this.ccList = ccList;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailPort() {
		return mailPort;
	}

	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}