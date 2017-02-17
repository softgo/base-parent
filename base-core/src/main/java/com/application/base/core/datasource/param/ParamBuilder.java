package com.application.base.core.datasource.param;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 返回作为参数用途的Map对象，同时提供常用参数名静态变量
 * @ClassName:  ParamBuilder   
 */
public class ParamBuilder {

	private static ParamBuilder instance;

	private ParamBuilder() {
	}

	public static ParamBuilder getInstance(){
		if (instance == null){
			instance = new ParamBuilder();
		}
		return instance;
	}

	public Param getParam() {
		return new DefaultParam();
	}

	public static NVPair nv(String key, Object value) {
		return new NVPair(key, value);
	}

}

class DefaultParam implements Param {
	private Map<String, Object> param;

	public DefaultParam() {
		param = new HashMap<String, Object>();
	}

	
	public Map<String, Object> get() {
		return param;
	}

	
	public Param add(NVPair nvPair) {
		param.put(nvPair.getKey(), nvPair.getValue());
		return this;
	}

	
	public Param add(NVPair... pair) {
		for (NVPair nvPair : pair) {
			param.put(nvPair.getKey(), nvPair.getValue());
		}
		return this;
	}

	
	public Param add(Map<String, Object> params) {
		Set<String> keys = params.keySet();
		for (String key : keys) {
			param.put(key, params.get(key));
		}
		return this;
	}

	
	public Param clean() {
		param.clear();
		return this;
	}

}

class NVPair {
	private String key;
	private Object value;

	public NVPair(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
