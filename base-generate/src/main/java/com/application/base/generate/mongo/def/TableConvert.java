package com.application.base.generate.mongo.def;

/**
 * 
 * 表的转换判断.
 * 
 * @author bruce.
 * 
 */

public class TableConvert {

	public static String getNullAble(String nullable) {
		if (("YES".equals(nullable)) || ("yes".equals(nullable)) || ("y".equals(nullable)) || ("Y".equals(nullable))) {
			return "Y";
		}
		
		if (("NO".equals(nullable)) || ("N".equals(nullable)) || ("no".equals(nullable)) || ("n".equals(nullable))) {
			return "N";
		}
		
		return null;
	}

}
