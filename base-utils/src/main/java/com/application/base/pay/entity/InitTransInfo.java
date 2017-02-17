package com.application.base.pay.entity;

/**
 * 初始化交易访问地址
 */
public class InitTransInfo {

	/**
	 * 设置传输交易类型
	 * @param txnType
	 * @return
	 */
	public static TransInfo setTxnType(String txnType) {
		TransInfo transInfo = new TransInfo();
		transInfo.setTxnType(txnType);
		transInfo.setRecordeText_1("TxnMsgContent");
		transInfo.setRecordeText_2("ErrorMsgContent");
		transInfo.setFLAG(true);
		if (txnType.equals("PUR")) {
			transInfo.setPostUrl("/cnp/purchase");
		}
		else if (txnType.equals("INP")) {
			transInfo.setPostUrl("/cnp/installment_purchase");
		}
		else if (txnType.equals("PRE")) {
			transInfo.setPostUrl("/cnp/preauth");
		}
		else if (txnType.equals("CFM")) {
			transInfo.setPostUrl("/cnp/confirm");
		}
		else if (txnType.equals("VTX")) {
			transInfo.setPostUrl("/cnp/void");
		}
		else if (txnType.equals("RFD")) {
			transInfo.setPostUrl("/cnp/refund");
		}
		return transInfo;
	}

	
	/**
	 * 设置非正式交易类型
	 * @param otherType
	 * @return
	 */
	public static TransInfo setOtherType(String otherType) {
		TransInfo transInfo = new TransInfo();
		if (otherType.equals("batch_refund")) {
			transInfo.setRecordeText_1("BatchRefundContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/batch_refund");
		}
		else if (otherType.equals("query_txn")) {
			transInfo.setRecordeText_1("QryTxnMsgContent");
			transInfo.setRecordeText_2("TxnMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/query_txn");
		}
		else if (otherType.equals("query_txn_list")) {
			transInfo.setRecordeText_1("BatchQryTxnMsgContent");
			transInfo.setRecordeText_2("TxnListContent");
			transInfo.setFLAG(false);
			transInfo.setPostUrl("/cnp/query_txn_list");
		}
		else if (otherType.equals("query_confirm_txn_list")) {
			transInfo.setRecordeText_1("QryConfirmListContent");
			transInfo.setRecordeText_2("TxnListContent");
			transInfo.setFLAG(false);
			transInfo.setPostUrl("/cnp/query_confirm_txn_list");
		}
		else if (otherType.equals("query_settlement_txn_list")) {
			transInfo.setRecordeText_1("QrySettlementListContent");
			transInfo.setRecordeText_2("SettleListContent");
			transInfo.setFLAG(false);
			transInfo.setPostUrl("/cnp/query_settlement_txn_list");
		}
		else if (otherType.equals("query_cardinfo")) {
			transInfo.setRecordeText_1("QryCardContent");
			transInfo.setRecordeText_2("CardInfoContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/query_cardinfo");
		}
		else if (otherType.equals("card_validation")) {
			transInfo.setRecordeText_1("SvcCardContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/card_validation");
		}
		else if (otherType.equals("card_phoneno_binding")) {
			transInfo.setRecordeText_1("CardPhoneBindContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/card_phoneno_binding");
		}
		else if (otherType.equals("dp_request")) {
			transInfo.setRecordeText_1("DyPinContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/dp_request");
		}
		else if (otherType.equals("get_confirm_txnlist")) {
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/mcs/get_confirm_txnlist");
		}
		else if (otherType.equals("get_settlement_list")) {
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/mcs/get_settlement_list");
		}
		return transInfo;
	}

	
	/**
	 * 快捷支付交易设置
	 * @param quickType
	 * @return
	 */
	public static TransInfo setQuicklyPay(String quickType) {
		TransInfo transInfo = new TransInfo();
		transInfo.setTxnType(quickType);
		if (quickType.equals("getDynNum")) {
			transInfo.setRecordeText_1("GetDynNumContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/getDynNum");
		}
		else if (quickType.equals("PUR")) {
			transInfo.setRecordeText_1("TxnMsgContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/purchase");
		}
		else if (quickType.equals("pci_store")) {
			transInfo.setRecordeText_1("PciDataContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(false);
			transInfo.setPostUrl("/cnp/pci_store");
		}
		else if (quickType.equals("pci_query")) {
			transInfo.setRecordeText_1("PciQueryContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(false);
			transInfo.setPostUrl("/cnp/pci_query");
		}
		else if (quickType.equals("pci_del")) {
			transInfo.setRecordeText_1("PciDeleteContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(false);
			transInfo.setPostUrl("/cnp/pci_del");
		}
		else if (quickType.equals("ind_auth")) {
			transInfo.setRecordeText_1("indAuthContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/ind_auth");
		}
		else if (quickType.equals("ind_auth_verify")) {
			transInfo.setRecordeText_1("indAuthDynVerifyContent");
			transInfo.setRecordeText_2("ErrorMsgContent");
			transInfo.setFLAG(true);
			transInfo.setPostUrl("/cnp/ind_auth_verify");
		}
		return transInfo;
	}
}
