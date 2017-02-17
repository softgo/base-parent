package com.application.base.activemq.topic.customer;

import java.util.ArrayList;
import java.util.List;

import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.base.activemq.topic.common.ActiveMQTopicCommonUtil;

@Service
public class ActiveMQTopicMessageCustomer {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMQTopicMessageCustomer.class);
	// 消息池工厂
	@Autowired
	private ActiveMQTopicCommonUtil topicActiveMQ;
	
	/**
	 * 从队列中获取消息
	 * @param topicName:目标队列获取.
	 * @return TextMessage
	 */
	public List<TextMessage> receiveMessage(String topicName) {
		List<TextMessage> messages = new ArrayList<TextMessage>();
		try {
			MessageConsumer consumer = topicActiveMQ.getMessageConsumer(topicName);
			if (null!=consumer) {
				while (true) {
					// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
					TextMessage message = (TextMessage) consumer.receive(10000);
					if (null != message) {
						messages.add(message);	
					}
					else {
						break;
					}
				}
			}
		}
		catch (Exception e) {
			logger.error("{}", "获取消息失败了", e);
		}
		return messages;
	}
}
