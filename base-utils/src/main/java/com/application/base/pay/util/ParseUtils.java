package com.application.base.pay.util;

import java.util.HashMap;

import com.application.base.pay.entity.TransInfo;

public class ParseUtils {

	public static HashMap parseXML(String resXml, TransInfo transInfo) {
		HashMap returnRespXml = null;
		ParseXMLUtils pxu = ParseXMLUtils.initParseXMLUtil();
		if (resXml != null) {
			if (transInfo.isFLAG()) {
				returnRespXml = pxu.returnXMLData(pxu.parseXML(resXml), transInfo.getRecordeText_1(),
						transInfo.getRecordeText_2());
			}
			else {
				returnRespXml = pxu.returnXMLDataList(pxu.parseXML(resXml), transInfo.getRecordeText_1(),
						transInfo.getRecordeText_2());
			}
		}
		return returnRespXml;

	}

}
