package com.application.base.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据访问异常
 * 
 * @ClassName: DataAccessException
 *
 */
public class DataAccessException extends BusinessException {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessException() {
		setExceptionKey("DATA_ACCESS_ERROR");
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Exception ex) {
		super(ex);
		ex.printStackTrace();
		logger.error("DataAccessException:{}", ex.getMessage());
	}
}
