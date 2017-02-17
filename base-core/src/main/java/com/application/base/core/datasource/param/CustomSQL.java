package com.application.base.core.datasource.param;

/**
 * 常用的客户构建类
 */
public interface CustomSQL {
	
	CustomSQL cloumn(String column);

	CustomSQL operator(ESQLOperator operator);

	CustomSQL value(Object value);

}
