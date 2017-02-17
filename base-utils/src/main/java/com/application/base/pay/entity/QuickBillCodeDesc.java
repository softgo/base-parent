package com.application.base.pay.entity;

/**
 * 快钱返回码的消息提示
 * 
 */
public class QuickBillCodeDesc {
	
	/**
	 * 处理返回的消息
	 * @param responseCode
	 * @param responseMsg
	 * @return
	 */
	public static String transResponseCode( String responseCode, String responseMsg ) {
		if ( responseCode == null || responseCode.equals( "" ) ) {
			return responseMsg;
		}
		if ( responseCode.equals( "G0" ) ) {
			responseMsg = "超出该银行卡单笔金额上限";
		} else if ( responseCode.equals( "L9" ) ) {
			responseMsg = "银行卡号输入错误";
		} else if ( responseCode.equals( "21016" ) ) {
			responseMsg = "认证信息不匹配";
		} else if ( responseCode.equals( "21000" ) ) {
			try {
				responseMsg = responseMsg.split( "-" )[1].trim();
			} catch ( Exception e ) {
				responseMsg = "鉴权失败";
			}
		} else if ( responseCode.equals( "CC" ) ) {
			responseMsg = "预留手机号输入错误";
		} else if ( responseCode.equals( "OG" ) ) {
			responseMsg = "单笔金额或日限额超过上限";
		} else if ( responseCode.equals( "OR" ) ) {
			responseMsg = "短时间内请勿重复提交";
		} else if ( responseCode.equals( "TA" ) ) {
			responseMsg = "操作失败,请确认输入信息";
		} else if ( responseCode.equals( "01" ) ) {
			responseMsg = "请核对卡信息后重新输入";
		} else if ( responseCode.equals( "61" ) ) {
			responseMsg = "超出取款转账金额限制";
		} else if ( responseCode.equals( "51" ) ) {
			responseMsg = "卡内余额不足";
		}
		return responseMsg;
	}
}
