package com.application.base.redis.jedis.mq;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;

	private String chanel; //渠道

    private int index;  //索引

	private String msg;  //消息

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }
	
    public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}