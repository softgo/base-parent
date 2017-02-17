package com.application.base.core.datasource.dao;

/**
 * 获得传入的类名
 */
public abstract class ClassName<T> {
	
	 public String getClassName(T t){
		 return t.getClass().getName();
	 }
}
