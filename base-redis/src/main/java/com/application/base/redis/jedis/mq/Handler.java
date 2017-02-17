package com.application.base.redis.jedis.mq;

@FunctionalInterface
public interface Handler {
	
    /**
     * 处理消息
     * @param msg
     */
    void handle(Message msg);
    
}

