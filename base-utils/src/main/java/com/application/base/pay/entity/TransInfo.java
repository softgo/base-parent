package com.application.base.pay.entity;

/**
 * 交易信息提示
 */
public class TransInfo {

	private String postUrl;
	private boolean FLAG;
	private String recordeText_1;
	private String recordeText_2;

	private String txnType;

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public boolean isFLAG() {
		return FLAG;
	}

	public void setFLAG(boolean flag) {
		FLAG = flag;
	}

	public String getRecordeText_1() {
		return recordeText_1;
	}

	public void setRecordeText_1(String recordeText_1) {
		this.recordeText_1 = recordeText_1;
	}

	public String getRecordeText_2() {
		return recordeText_2;
	}

	public void setRecordeText_2(String recordeText_2) {
		this.recordeText_2 = recordeText_2;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

}
