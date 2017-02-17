package com.application.base.utils.message.core.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.utils.message.config.SYMessageConfig;
import com.application.base.utils.message.config.WLWXMessageConfig;
import com.application.base.utils.message.constant.MsgType;
import com.application.base.utils.message.core.SmsMsgService;

/**
 * 消息的实现 
 */
public class SmsMsgSender implements SmsMsgService {

	private Logger logger = LoggerFactory.getLogger(SmsMsgSender.class.getName()); 
	
	@Override
	public Map<String, Object> getBalance(MsgType type) {
		try {
			if (type==MsgType.SY) {
				SYMessageConfig config = SYMessageConfig.getMsgInstance();
				return config.toCheckBalance();
			}
			if (type==MsgType.WLWX) {
				WLWXMessageConfig config = WLWXMessageConfig.getMsgInstance();
				return config.toCheckBalance();
			}
		}
		catch (Exception e) {
			logger.error("发送短信异常",e);
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@Override
	public Map<String, Object> sendMsg(String content, String[] phones, MsgType type) {
		logger.info("要发送的短信的内容是:"+content);
		try {
			if (type==MsgType.SY) {
				SYMessageConfig config = SYMessageConfig.getMsgInstance();
				config.initConfig(phones);
				config.setContent(content);
				return config.toSendMsg();
			}
			if (type==MsgType.WLWX) {
				WLWXMessageConfig config = WLWXMessageConfig.getMsgInstance();
				config.initConfig(phones);
				config.setContent(content);
				return config.toSendMsg();
			}
		}
		catch (Exception e) {
			logger.error("发送短信异常",e);
			e.printStackTrace();
			return null;
		}
		return null;
	}

	
}
