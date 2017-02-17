package com.application.base.utils.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * UUID生产器
 * @ClassName:  UUIDProvider   
 */
public class UUIDProvider {

	static String[] numbers = new String[]{"0","1","2","3","4","5","6","7","8","9"};
	
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	//返回数字.
	public static String number(String uuid) {
		List<String> nums = Arrays.asList(numbers);
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < uuid.length(); i++) {
			 String tmp = uuid.charAt(i)+"";
			 if (nums.contains(tmp)) {
				buffer.append(tmp);
			}
		}
		return buffer.toString();
	}
	
	//返回字母.
	public static String letter(String uuid) {
		List<String> nums = Arrays.asList(numbers);
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < uuid.length(); i++) {
			 String tmp = uuid.charAt(i)+"";
			 if (!nums.contains(tmp)) {
				buffer.append(tmp);
			}
		}
		return buffer.toString();
	}

	/**
	 * 获得uuid的组合.
	 * @return
	 */
	public static Map<String, Object> getResult() {
		Map<String, Object> result = new HashMap<String, Object>();
		String uuid = uuid();
		result.put("uuid", uuid);
		result.put("number", number(uuid));
		result.put("letter", letter(uuid));
		return result;
	}
	
	
	
    public static void main(String[] args) {
    	Map<String, Object> result = getResult();
    	System.out.println(result.get("uuid"));
    	System.out.println(result.get("number"));
    	System.out.println(result.get("letter"));
    }
}
