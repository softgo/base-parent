package com.application.base.core.result;

import java.io.Serializable;

/**
 * 各个独立系统返回的结果数据对象，将返回结构封装为Result对象，转化为JSON返回
 * @ClassName:  ResultData   
 *
 */
public class ResultData<T> implements Serializable {
	
	/**   
	 * @Fields serialVersionUID : TODO  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 结果返回码
	 */
	private String code;
	/**
	 * 结果返回信息
	 */
	private String message;
	/**
	 * 结果返回内容
	 */
	private T result;

	public ResultData() {
		// TODO Auto-generated constructor stub
	}
	public ResultData(String code,String message) {
		setCode(code);
		setMessage(message);
	}
	public ResultData(ResultInfo info) {
		setCode(info.getCode());
		setMessage(info.getMessage());
	}

	public ResultData(ResultInfo info, T result) {
		this(info);
		setResult(result);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public void setResultInfo(ResultInfo info) {
		setCode(info.getCode());
		setMessage(info.getMessage());
	}
}
