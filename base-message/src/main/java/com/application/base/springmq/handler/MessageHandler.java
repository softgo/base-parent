package com.application.base.springmq.handler;

import com.application.base.springmq.msg.CommonMessage;

public class MessageHandler {
	
	/**
	 * 处理信息
	 * 
	 */
    public void handleMessage(CommonMessage message) {  
        try{  
            System.out.println("...." + message);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
	
}
