package com.application.base.core.constant;

import com.application.base.core.result.ResultData;

/**
 * 常量数据类
 * 
 * @ClassName: Constants
 */
public class Constants extends com.application.base.utils.common.Constants {

	@SuppressWarnings("rawtypes")
	public static ResultData ERROR = new ResultData(APICALL.API_CALL_SYS_ERROR, "系统错误");
	@SuppressWarnings("rawtypes")
	public static ResultData NOT_FOUND = new ResultData(APICALL.API_CALL_NOT_FOUND, "接口不存在");

}
