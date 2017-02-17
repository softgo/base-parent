package com.application.base.activemq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息监听器
 */
public class TopicMessageListener implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(TopicMessageListener.class.getName());  
	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
	        try {
	        	logger.info(" TopicMessageListener 监听到了文本消息:" + tm.getText());
	            //do something ...
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
		}
	}
}
