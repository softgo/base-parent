package com.application.base.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 业务逻辑异常
 * 
 * @ClassName: BusinessException
 *
 */
public class BusinessException extends RuntimeException {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	protected String exceptionKey;

	public BusinessException() {
        if(this.getLocalizedMessage()!=null){
        	logger.error(this.getLocalizedMessage());
        }
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Exception ex, String message) {
		super(message, ex);
	}

	public BusinessException(Exception ex) {
		super(ex);
		exceptionKey = "SYSTEM_ERROR";
	}

	public BusinessException(DataAccessException ex) {
		setExceptionKey(ex.getExceptionKey());
	}

	public String getExceptionKey() {
		if (StringUtils.isEmpty(exceptionKey))
			exceptionKey = "SYSTEM_ERROR";
		return exceptionKey;
	}

	protected void setExceptionKey(String exceptionKey) {
		this.exceptionKey = exceptionKey;
	}

}
