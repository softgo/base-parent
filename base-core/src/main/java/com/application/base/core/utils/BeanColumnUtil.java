package com.application.base.core.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.utils.common.StringDefaultValue;

/**
 * bean对象的操作类
 */
public class BeanColumnUtil {

	private static Logger logger = LoggerFactory.getLogger(BeanColumnUtil.class.getName());
	
	/**
	 * 字符校验.
	 * @param object 哪个bean对象.
	 * @param columns 校验哪些列.
	 * @param attachments 附件list校验.
	 */
	public static boolean validateEmpty(Object object, List<String> columns, List<?> attachments){
		if (attachments==null || attachments.size()==0) {
			return true;
		}else{
			return validateEmpty(object, columns);
		}
	} 
	
	/**
	 * 获取数据
	 * @param data
	 * @param filterNams
	 * @return
	 */
	public static boolean validateEmpty(Object data, List<String> filterNames) {
		try {
			boolean result = false;
			Class<?> cls = data.getClass();
			Field[] fields = cls.getDeclaredFields();
			String name = null;
			for (Field field : fields) {
				name = field.getName();
				if (filterNames.contains(name)) {
					field.setAccessible(true);
					String val = field.get(data).toString();
					if (StringDefaultValue.isEmpty(val)) {
						result = true;
					}
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("非空验证失败了",e);
			return true;
		}
	}
	
	
	/**
	 * 获取传入的两个对象的value是否相同.
	 * 
	 * @param newData
	 * @param oldData
	 * @param filterNams
	 * @return
	 */
	public static List<?> diffValus(Object newData, Object oldData, List<String> filterNames) {
		List<?> resultData = new ArrayList<>();
		Map<String, String> newMap = getValus(newData, filterNames);
		Map<String, String> oldMap = getValus(oldData, filterNames);
		for (String column : filterNames) {
			String ndata = newMap.get(column);
			ndata = dealDecimal(ndata);
			String odata = oldMap.get(column);
			odata = dealDecimal(odata);
			if (!ndata.equals(odata)) {
				Object data = new Object();
				
				//TODO //两个类的字段的比较方式实现.
				
			}
		}
		return resultData;
	}

	/**
	 * 获取数据
	 * 
	 * @param data
	 * @param filterNams
	 * @return
	 */
	public static Map<String, String> getValus(Object data, List<String> filterNames) {
		try {
			Class<?> cls = data.getClass();
			Field[] fields = cls.getDeclaredFields();
			Map<String, String> dataMap = new HashMap<String, String>();
			String name = null;
			for (Field field : fields) {
				name = field.getName();
				if (filterNames.contains(name)) {
					field.setAccessible(true);
					String val = field.get(data).toString();
					if (!StringDefaultValue.isEmpty(val)) {
						dataMap.put(name, val);
					}
				}
			}
			return dataMap;
		} catch (Exception e) {
			logger.error("获取信息异常了.");
			return null;
		}
	}
	
	/**
	 * 获取数据
	 * 
	 * @param data
	 * @param coloums
	 * @return
	 */
	public static Map<String, Object> getBeanValus(Object data, List<String> coloums) {
		try {
			Class<?> cls = data.getClass();
			Field[] fields = cls.getDeclaredFields();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			String name = null;
			for (Field field : fields) {
				name = field.getName();
				if (coloums.contains(name)) {
					field.setAccessible(true);
					Object val = field.get(data);
					dataMap.put(name, val);
				}
			}
			return dataMap;
		} catch (Exception e) {
			logger.error("获取信息异常了.");
			return null;
		}
	}
	
	/**
	 * 处理,"0"
	 * @param value
	 * @return
	 */
	private static String dealDecimal(String value) {
		if (StringDefaultValue.isEmpty(value)) {
			return "";
		}
		if (value.indexOf(".") > 0) {
			// 正则表达
			value = value.replaceAll("0+?$", "");
			value = value.replaceAll("[.]$", "");
		}
		return value;
	}
	
}
