package com.application.base.core.constant;

/**
 * 公共消息定义
 */
public class CommonResultConstant {

	/**
	 * 通用的 message 设置.
	 */
	public interface CommonResult {
		String SYSTEM_SUCCESS = "SYSTEM_SUCCESS";  // 操作成功
		String SYSTEM_ERROR = "SYSTEM_ERROR";  // 系统错误
		String INTERFACE_NOT_FOUND = "INTERFACE_NOT_FOUND"; // 接口不存在
		String PARAMS_EMPTY = "PARAMS_EMPTY";  // 参数不完整,请检查参数
		String VALIDATED_ERROR = "VALIDATED_ERROR"; // 参数格式不符合要求,请检查参数
		String GET_RESULT_IS_NULL = "GET_RESULT_IS_NULL"; // 查询获得的结果为空
		String OPERATE_ACTION_IS_FAILD = "OPERATE_ACTION_IS_FAILD"; // CRUD操作失败
		String GET_RESULT_COUNT_FAILD = "GET_RESULT_COUNT_FAILD";  // 查询总个数失败
	}
	
}
