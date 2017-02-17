package com.application.base.pay.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.application.base.pay.QuickBillService;
import com.application.base.pay.entity.InitTransInfo;
import com.application.base.pay.entity.QuickBean;
import com.application.base.pay.entity.TransInfo;
import com.application.base.pay.util.QuickBillXmlHandler;
import com.application.base.pay.util.SendTR1;

public class QuickBillServiceImpl implements QuickBillService {

	/**
	 * 获取短信验证码(绑定并支付时)
	 */
	@Override
	public Map<String, Object> getDynByExpense( Map<String, Object> param) {
		/*
		 * 1、设置交易方式
		 */
		TransInfo transInfo = InitTransInfo.setQuicklyPay("getDynNum");
		/*
		 * 2、组装请求信息TR1参数
		 */
		StringBuffer reqXml = null;
		reqXml = SendTR1.appendParam(reqXml, "customerId", param.get("99billUserCode").toString(), transInfo); //快钱用户的code.
		reqXml = SendTR1.appendParam(reqXml, "externalRefNumber", param.get("orderNo").toString(), transInfo); //处理的订单号.
		reqXml = SendTR1.appendParam(reqXml, "cardHolderName", param.get("realName").toString(), transInfo); //交易用户的真实姓名
		reqXml = SendTR1.appendParam(reqXml, "idType", "0", transInfo); //默认值
		reqXml = SendTR1.appendParam(reqXml, "cardHolderId", param.get("idCardNo").toString(), transInfo);  //交易用户的真实身份证号
		reqXml = SendTR1.appendParam(reqXml, "pan", param.get("bankCardNo").toString(), transInfo);  //交易用户的真实银行卡号
		reqXml = SendTR1.appendParam(reqXml, "phoneNO", param.get("mobilePhone").toString(), transInfo);  //交易用户的银行手机号
		reqXml = SendTR1.appendParam(reqXml, "amount", param.get("moneyAmount").toString(), transInfo);  //交易金额
		reqXml = SendTR1.appendParam(reqXml, "", "", transInfo);
		/*
		 * 3、发送TR1报文并获取回调信息TR2 进行解析
		 */
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		//获取支付的返回信息
		return QuickBillXmlHandler.responseCodeHandle(respXml);
	}

	/**
	 * 绑卡并支付:
	 */
	public Map<String, Object> doPay(Map<String, Object> param) {
		Map<String, Object> resultData = new HashMap<String, Object>();
		/*
		 * 1、设置交易方式
		 */
		TransInfo transInfo = InitTransInfo.setQuicklyPay("PUR");
		QuickBean quickBean = new QuickBean();
		quickBean.setPhone(param.get("mobilePhone").toString());
		quickBean.setToken(param.get("token").toString());
		quickBean.setValidCode(param.get("validCode").toString());
		quickBean.setPayBatch("1"); //支付批次
		quickBean.setSavePciFlag("1"); //图片
		/*
		 * 2、组装请求信息TR1参数
		 */
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "cardNo", param.get("bankCardNo").toString(), transInfo); //交易银行卡号
		reqXml = SendTR1.transParams(reqXml, "storableCardNo",param.get("storableCardNo").toString(), transInfo); //storablePan
		reqXml = SendTR1.transParams(reqXml, "amount",param.get("moneyAmount").toString(), transInfo); //交易金额
		reqXml = SendTR1.transParams(reqXml, "externalRefNumber",param.get("orderNo").toString(), transInfo); //交易订单号
		reqXml = SendTR1.transParams(reqXml, "customerId",param.get("99billUserCode").toString(), transInfo); //快钱账户userCode
		reqXml = SendTR1.transParams(reqXml, "cardHolderName",param.get("realName").toString(), transInfo); //交易真实名称
		reqXml = SendTR1.transParams(reqXml, "cardHolderId",param.get("idCardNo").toString(),transInfo); //身份证号
		reqXml = SendTR1.transParams(reqXml, "spFlag", "QuickPay", transInfo);
		reqXml = SendTR1.transParams(reqXml, "idType", "0", transInfo);
		reqXml = SendTR1.appendQuickPay(reqXml, quickBean);
		reqXml = SendTR1.transParams(reqXml, "", "", transInfo);
		/*
		 * 3、发送TR1报文并获取回调信息TR2 进行解析
		 */
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		//处理返回结果
		return QuickBillXmlHandler.responseCodeHandle(respXml);
	}

	/**
	 *  快捷支付
	 */
	public Map<String, Object> quickPay(Map<String, Object> param) {
		Map<String, Object> resultData = new HashMap<String, Object>();
		/*
		 * 1、设置交易方式
		 */
		QuickBean quickBean = new QuickBean();
		quickBean.setPhone("");
		quickBean.setToken("");
		quickBean.setValidCode("");
		quickBean.setPayBatch("2");
		quickBean.setSavePciFlag("0");
		TransInfo transInfo = InitTransInfo.setQuicklyPay("PUR");
		/*
		 * 2、组装请求信息TR1参数
		 */
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "storableCardNo", param.get("storableCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "externalRefNumber", param.get("orderNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "customerId", param.get("99billUserCode").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "spFlag", "QPay02", transInfo);
		reqXml = SendTR1.transParams(reqXml, "amount", param.get("moneyAmount").toString(), transInfo);
		reqXml = SendTR1.appendQuickPay(reqXml, quickBean);
		reqXml = SendTR1.transParams(reqXml, "", "", transInfo);
		/*
		 * 3、发送TR1报文并获取回调信息TR2 进行解析
		 */
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		resultData = QuickBillXmlHandler.responseCodeHandle(respXml);
		return resultData;
	}

	/**
	 * 单独绑卡时获取验证码
	 */
	public Map<String, Object> getDynByBind(Map<String, Object> param) {
		// 设置交易方式
		TransInfo transInfo = InitTransInfo.setQuicklyPay("ind_auth");
		// 组装请求信息TR1参数
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "customerId", param.get("99billUserCode").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "externalRefNumber", param.get("orderNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "cardHolderName", param.get("realName").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "idType", "0", transInfo);
		reqXml = SendTR1.transParams(reqXml, "cardHolderId", param.get("idCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "pan", param.get("bankCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "phoneNO", param.get("mobilePhone").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "", "", transInfo);
		// TR2接收的数据，向支付平台发送请求，并接受返回信息
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		return QuickBillXmlHandler.responseCodeHandle(respXml);
	}

	/**
	 * 绑卡设置
	 * @return
	 */
	public Map<String, Object> bindCard(Map<String, Object> param) {
		/*
		 * 1、组装提交快钱接口参数
		 */
		TransInfo transInfo = InitTransInfo.setQuicklyPay("ind_auth_verify");
		// 下面开始进行组合
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "customerId", param.get("99billUserCode").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "cardHolderName", param.get("realName").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "idType", "0", transInfo);
		reqXml = SendTR1.transParams(reqXml, "cardHolderId", param.get("idCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "pan", param.get("bankCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "phoneNO", param.get("mobilePhone").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "validCode", param.get("validCode").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "externalRefNumber", param.get("orderNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "token", param.get("token").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "", "", transInfo);
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		return QuickBillXmlHandler.responseCodeHandle(respXml);
	}

	/**
	 * 查询卡信息
	 * @param bankCardno
	 * @return
	 */
	private Map<String, Object> queryCardInfo(String bankCardno) {
		/*
		 * 1、组装提交快钱接口参数
		 */
		TransInfo transInfo = InitTransInfo.setOtherType("query_cardinfo");
		String txnType = "PUR";
		// 下面开始进行组合
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "cardNo", bankCardno, transInfo);
		reqXml = SendTR1.transParams(reqXml, "txnType", txnType, transInfo);
		reqXml = SendTR1.transParams(reqXml, "", "", transInfo);
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		return respXml;
	}

	/**
	 * 银行卡号
	 * @param bankCardno
	 * @return
	 */
	private String getBankidByCardno(String bankCardno) {
		Map<String, Object> respXml = queryCardInfo(bankCardno);
		if (respXml == null) {
			// 银行通讯异常
			return "-1";
		}
		if (respXml.get("errorCode") != null) {
			// 卡号不正确
			return "-2";
		}
		if (respXml.get("bankId") == null) {
			return null;
		}
		String bankid = respXml.get("bankId").toString();
		return bankid;
	}

	/**
	 * 动态支付
	 */
	public Map<String, Object> getDynamicCodeByRecharge(Map<String, Object> param) {
		/*
		 * 1、设置交易方式
		 */
		TransInfo transInfo = InitTransInfo.setQuicklyPay("getDynNum");
		/*
		 * 2、组装请求信息TR1参数
		 */
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "customerId", param.get("99billUserCode").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "externalRefNumber", param.get("orderNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "cardHolderName", param.get("realName").toString(), transInfo);
		reqXml = SendTR1.appendParam(reqXml, "idType", "0", transInfo);
		reqXml = SendTR1.transParams(reqXml, "cardHolderId", param.get("idCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "pan", param.get("bankCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "phoneNO", param.get("mobilePhone").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "amount", param.get("moneyAmount").toString(), transInfo);
		reqXml = SendTR1.appendParam(reqXml, "", "", transInfo);
		/*
		 * 3、发送TR1报文并获取回调信息TR2 进行解析
		 */
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		return QuickBillXmlHandler.responseCodeHandle(respXml);
	}

	
	/**
	 * 第一次支付
	 * @return
	 */
	public Map<String, Object> firstRecharge(Map<String, Object> param) {
		Map<String, Object> ressultData = new HashMap<String, Object>();
		/*
		 * 1、设置交易方式
		 */
		TransInfo transInfo = InitTransInfo.setQuicklyPay("PUR");
		QuickBean quickBean = new QuickBean();
		quickBean.setPhone(param.get("mobilePhone").toString());
		quickBean.setToken(param.get("token").toString());
		quickBean.setValidCode(param.get("validCode").toString());
		quickBean.setPayBatch("1");
		quickBean.setSavePciFlag("1");
		/*
		 * 2、组装请求信息TR1参数
		 */
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "cardNo", param.get("bankCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "storableCardNo",  param.get("storableCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "amount", param.get("moneyAmount").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "externalRefNumber", param.get("orderNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "customerId", param.get("99billUserCode").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "cardHolderName", param.get("realName").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "cardHolderId", param.get("idCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "spFlag", "QuickPay", transInfo);
		reqXml = SendTR1.transParams(reqXml, "idType", "0", transInfo);
		reqXml = SendTR1.appendQuickPay(reqXml, quickBean);
		reqXml = SendTR1.transParams(reqXml, "", "", transInfo);
		/*
		 * 3、发送TR1报文并获取回调信息TR2 进行解析
		 */
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		ressultData = QuickBillXmlHandler.responseCodeHandle(respXml);
		return ressultData;
	}

	/**
	 * 快捷支付
	 * @param quickRechargeViewModel
	 * @return
	 */
	public Map<String, Object> quickRecharge(Map<String, Object> param) {
		Map<String, Object> resultData = new HashMap<String, Object>();
		/*
		 * 1、设置交易方式
		 */
		QuickBean quickBean = new QuickBean();
		quickBean.setPhone("");
		quickBean.setToken("");
		quickBean.setValidCode("");
		quickBean.setPayBatch("2");
		quickBean.setSavePciFlag("0");
		TransInfo transInfo = InitTransInfo.setQuicklyPay("PUR");
		/*
		 * 2、组装请求信息TR1参数
		 */
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "storableCardNo",param.get("storableCardNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "externalRefNumber", param.get("orderNo").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "customerId", param.get("99billUserCode").toString(), transInfo);
		reqXml = SendTR1.transParams(reqXml, "spFlag", "QPay02", transInfo);
		reqXml = SendTR1.transParams(reqXml, "amount", param.get("moneyAmount").toString(), transInfo);
		reqXml = SendTR1.appendQuickPay(reqXml, quickBean);
		reqXml = SendTR1.transParams(reqXml, "", "", transInfo);
		/*
		 * 3、发送TR1报文并获取回调信息TR2 进行解析
		 */
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		String responseCode = respXml.get("responseCode") + ""; // 00 成功.
		if (StringUtils.isNotEmpty(responseCode)) {
			// 判断是 "68" 还是 "C0" 返回码.
			if (responseCode.equalsIgnoreCase("68") || responseCode.equalsIgnoreCase("C0")) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("status", "5");// 1-初始流水 2-成功流水 3-失败流水 4-重复流水,5-处理中流水
				params.put("userCode", param.get("userCode").toString());
				params.put("orderId", param.get("orderNo").toString());
				// 返回.
				HashMap<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("responseCode", responseCode);
				resultMap.put("responseMsg", "交易进行中"); // 拿快钱的responseMsg.
				return resultMap;
			}
		}
		resultData = QuickBillXmlHandler.responseCodeHandle(respXml);
		return resultData;
	}

	/**
	 * 移除快钱和银行卡之间的关系
	 */
	public Map<String, Object> removeBindCard(Map<String, Object> param) {
		TransInfo transInfo = InitTransInfo.setQuicklyPay("pci_del");
		// 下面开始进行组合
		StringBuffer reqXml = null;
		reqXml = SendTR1.appendParam(reqXml, "customerId", param.get("99billUserCode").toString(), transInfo);
		reqXml = SendTR1.appendParam(reqXml, "storablePan", param.get("storablePan").toString(), transInfo);
		reqXml = SendTR1.appendParam(reqXml, "pan",param.get("bankCardNo").toString(), transInfo);
		reqXml = SendTR1.appendParam(reqXml, "bankId",param.get("bankCardName").toString(), transInfo);
		reqXml = SendTR1.appendParam(reqXml, "", "", transInfo);
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		if (respXml == null) {
			return null;
		}
		return respXml;
	}
	
	/***
	 * 根据订单号检查快钱订单状态
	 */
	public Map<String, Object> checkOrderStatus(String orderNo) {
		/*
		 * 1、组装提交快钱接口参数
		 */
		TransInfo transInfo = InitTransInfo.setOtherType("query_txn");
		String txnType = "PUR";
		// 下面开始进行组合
		StringBuffer reqXml = null;
		reqXml = SendTR1.transParams(reqXml, "externalRefNumber", orderNo, transInfo);
		reqXml = SendTR1.transParams(reqXml, "txnType", txnType, transInfo);
		reqXml = SendTR1.transParams(reqXml, "", "", transInfo);
		HashMap respXml = SendTR1.sendTR1(reqXml.toString(), transInfo);
		return respXml;
	}
	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		QuickBillServiceImpl service = new QuickBillServiceImpl();
		String orderId = "NN2015092500202457812321287";
		System.err.println(service.checkOrderStatus(orderId));
		;
	}

	

}
