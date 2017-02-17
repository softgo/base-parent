package com.application.base.codis.architecture.enumer;

public enum CacheEnvironment {

	USER("user"), PROD("prod"), TRD("trd"), INVT("invt"), PAY("pay"), ACC("acc"), MKT("mkt"), LOAN("loan"), INFRA(
			"infra"), DEFAULT("default");

	private String value;

	private CacheEnvironment(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

	public String toString() {
		return super.toString();
	}

	public String encode(String key) {
		return this.value + ":" + key;
	}

	public String decode(String key) {
		return key.substring(key.indexOf(":") + 1);
	}

	public static CacheEnvironment env(String value) {
		value = value.toLowerCase();
		switch (value) {
		case "user":
			return USER;
		case "prod":
			return PROD;
		case "trd":
			return TRD;
		case "invt":
			return INVT;
		case "pay":
			return PAY;
		case "acc":
			return ACC;
		case "mkt":
			return MKT;
		case "loan":
			return LOAN;
		case "infra":
			return INFRA;
		}
		return DEFAULT;
	}

}
