package com.application.base.pay.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class ParseXMLUtils {

	public static ParseXMLUtils initParseXMLUtil() {
		return new ParseXMLUtils();
	}

	private HashMap parseXMLNode(HashMap hm, Element e) {
		Element child = null;

		for (Iterator childs = e.getChildren().iterator(); childs.hasNext();) {
			child = (Element) childs.next();
			hm.put(child.getName(), child.getValue());
		}
		return hm;
	}

	private HashMap parseXMLNodeList(HashMap hm, Element e, String flag2) {

		Element child = null;
		Element child_txn = null;
		HashMap HM_txn = null;
		List list = null;

		for (Iterator childs = e.getChildren().iterator(); childs.hasNext();) {
			child = (Element) childs.next();
			if (flag2.equals(child.getName())) {
				list = new ArrayList();

				for (Iterator childs2 = child.getChildren().iterator(); childs2.hasNext();) {
					HM_txn = new HashMap();
					child_txn = (Element) childs2.next();
					HM_txn = parseXMLNode(HM_txn, child_txn);
					list.add(HM_txn);
				}
				hm.put("Txn", list);
			}
			else {
				hm.put(child.getName(), child.getValue());
			}
		}
		return hm;

	}

	public Element parseXML(String resXml) {

		SAXBuilder sb = new SAXBuilder();
		StringReader read = new StringReader(resXml);
		InputSource inSource = new InputSource(read);
		Document doc = null;
		Element root = null;
		try {
			doc = sb.build(inSource);
		}
		catch (JDOMException e1) {
			e1.printStackTrace();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}

		if (doc != null) {
			root = doc.getRootElement();
		}
		else {
			System.out.println("root");
		}
		return root;

	}

	public HashMap returnXMLData(Element root, String flag1, String flag2) {

		HashMap xmlData = null;
		Element child = null;

		if (root != null) {
			xmlData = new HashMap();
			String childName = "";

			for (Iterator childs = root.getChildren().iterator(); childs.hasNext();) {
				child = (Element) childs.next();
				childName = (String) child.getName();

				if (flag1.equals(childName)) {
					xmlData = parseXMLNode(xmlData, child);
				}
				else if (flag2.equals(childName)) {
					xmlData = parseXMLNode(xmlData, child);
				}
				else if ("ErrorMsgContent".equals(childName)) {
					xmlData = parseXMLNode(xmlData, child);
				}
				else {
					xmlData.put(childName, child.getValue());
				}
			}
		}
		return xmlData;

	}

	public HashMap returnXMLDataList(Element root, String flag1, String flag2) {

		HashMap xmlData = null;
		Element child = null;

		if (root != null) {
			xmlData = new HashMap();
			String childName = "";

			for (Iterator childs1 = root.getChildren().iterator(); childs1.hasNext();) {
				child = (Element) childs1.next();
				childName = (String) child.getName();
				if (flag1.equals(childName)) {
					xmlData = parseXMLNodeList(xmlData, child, flag2);
				}
				else if (flag2.equals(childName)) {
					xmlData = parseXMLNodeList(xmlData, child, "");
				}
				else if ("ErrorMsgContent".equals(childName)) {
					xmlData = parseXMLNode(xmlData, child);
				}
				else {
					xmlData.put(childName, child.getValue());
				}
			}
		}
		return xmlData;

	}

	public static void main(String... args) {
		String resXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><MasMessage xmlns=\"http://www.99bill.com/mas_cnp_merchant_interface\"><version>1.0</version><QryCardContent><cardNo>6214830107352543</cardNo><txnType>PUR</txnType></QryCardContent><CardInfoContent><cardNo>6214830107352543</cardNo><cardOrg>CU</cardOrg><cardType>0002</cardType><issuer>招商银行</issuer><validFlag>1</validFlag><validateType>000</validateType><bankId>CMB</bankId></CardInfoContent></MasMessage>";
		ParseXMLUtils pxu = ParseXMLUtils.initParseXMLUtil();
		Element root = pxu.parseXML(resXml);
		HashMap hashMap = pxu.returnXMLDataList(root, "QryCardContent", "CardInfoContent");

		System.err.println(hashMap.toString());
	}

}
