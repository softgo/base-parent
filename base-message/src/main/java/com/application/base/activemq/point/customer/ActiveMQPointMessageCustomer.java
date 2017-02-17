package com.application.base.activemq.point.customer;

import java.util.ArrayList;
import java.util.List;

import javax.jms.QueueReceiver;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.base.activemq.point.common.ActiveMQPointCommonUtil;

@Service
public class ActiveMQPointMessageCustomer {

	private static final Logger logger = LoggerFactory.getLogger(ActiveMQPointMessageCustomer.class);
	// 消息池工厂
	@Autowired
	private ActiveMQPointCommonUtil pointActiveMQ;
	
	/**
	 * 从队列中获取消息
	 * @param queueName:目标队列获取.
	 * @return TextMessage
	 */
	public List<TextMessage> receiveMessage(String queueName) {
		List<TextMessage> messages = new ArrayList<TextMessage>();
		try {
			QueueReceiver queueReceiver = pointActiveMQ.getQueueReceiver(queueName);
			if (null!=queueReceiver) {
				while (true) {
					// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
					TextMessage message = (TextMessage) queueReceiver.receive(10000);
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
