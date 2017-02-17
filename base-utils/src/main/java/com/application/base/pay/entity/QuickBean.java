package com.application.base.pay.entity;

/**
 * 快捷支付 bean 设置
 */
public class QuickBean {

	public static final String EXT_MAP = "extMap"; 
	public static final String EXT_DATE = "extDate";
	public static final String KEY = "key";
	public static final String VALUE = "value";

	static final String ONEPay = "1";
	
	static final String TWOPay = "2";

	static final String savePciYes = "1";
	
	static final String savePciNo = "0";

	private String phone; //支付手机号
	
	private String validCode; //验证code
	
	private String savePciFlag; //是否存图
	
	private String token; //信令token
	
	private String payBatch; //支付批次

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSavePciFlag() {
		return savePciFlag;
	}

	public void setSavePciFlag(String savePciFlag) {
		this.savePciFlag = savePciFlag;
	}

	public String getPayBatch() {
		return payBatch;
	}

	public void setPayBatch(String payBatch) {
		this.payBatch = payBatch;
	}

}
