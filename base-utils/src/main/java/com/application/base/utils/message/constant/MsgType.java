package com.application.base.utils.message.constant;

/**
 * 短信类型.
 */
public enum MsgType {
    
    SY("SY"), //示远短信.
    
    WLWX("WLWX"); //未来无线短信.
    
	private String type; //类型

	MsgType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
