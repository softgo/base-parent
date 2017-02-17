package com.application.base.core.exception;

public class CommonException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public CommonException() {
	}
	
	public CommonException(String exceptionKey) {
		setExceptionKey(exceptionKey);
	}
}
