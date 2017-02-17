package com.application.base.utils.message.test;

import com.application.base.utils.common.JSONUtils;
import com.application.base.utils.message.constant.MsgType;
import com.application.base.utils.message.core.impl.SmsMsgSender;

public class SmsMsgTest {

	public static void main(String[] args) {
		SmsMsgSender sender = new SmsMsgSender();
		System.out.println(JSONUtils.toJson(sender.sendMsg("你好", new String[]{"18810056855"}, MsgType.WLWX)));
	}
}
