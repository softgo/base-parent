package com.application.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.utils.common.StringDefaultValue;

/**
 * logback 的日志记录器.
 * @author 草原孤狼
 */
public class LOGBackUtils{
	
	 /** 
     * 错误输入日志 
     */  
    public static final Logger logger = LoggerFactory.getLogger("");  
  
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     */
    @SuppressWarnings("rawtypes")
	public static void logTrace(Class claz,String method,String line,String message) { 
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.trace("Class:"+claz.getName()+",message:"+message);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.trace("Class:"+claz.getName()+",line:"+line+",message:"+message);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.trace("Class:"+claz.getName()+",method:"+method+",message:"+message);
		}else {
			logger.trace("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message);
		}
    }  
    
    
  
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     * @param e
     */
    @SuppressWarnings("rawtypes")
    public static void logTrace(Class claz,String method,String line,String message,Throwable e) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.trace("Class:"+claz.getName()+",message:"+message,"exception:"+e);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.trace("Class:"+claz.getName()+",line:"+line+",message:"+message,"exception:"+e);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.trace("Class:"+claz.getName()+",method:"+method+",message:"+message,"exception:"+e);
		}else {
			logger.trace("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message,"exception:"+e);
		}
    } 

    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     */
    @SuppressWarnings("rawtypes")
	public static void logDebug(Class claz,String method,String line,String message) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.debug("Class:"+claz.getName()+",message:"+message);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.debug("Class:"+claz.getName()+",line:"+line+",message:"+message);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.debug("Class:"+claz.getName()+",method:"+method+",message:"+message);
		}else {
			logger.debug("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message);
		}
    }  
  
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     * @param e
     */
    @SuppressWarnings("rawtypes")
    public static void logDebug(Class claz,String method,String line,String message,Throwable e) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.debug("Class:"+claz.getName()+",message:"+message,"exception:"+e);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.debug("Class:"+claz.getName()+",line:"+line+",message:"+message,"exception:"+e);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.debug("Class:"+claz.getName()+",method:"+method+",message:"+message,"exception:"+e);
		}else {
			logger.debug("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message,"exception:"+e);
		}
    }  
  
    
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     */
    @SuppressWarnings("rawtypes")
	public static void logInfo(Class claz,String method,String line,String message) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.info("Class:"+claz.getName()+",message:"+message);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.info("Class:"+claz.getName()+",line:"+line+",message:"+message);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.info("Class:"+claz.getName()+",method:"+method+",message:"+message);
		}else {
			logger.info("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message);
		}
    }  
  
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     * @param e
     */
    @SuppressWarnings("rawtypes")
    public static void logInfo(Class claz,String method,String line,String message,Throwable e) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.info("Class:"+claz.getName()+",message:"+message,"exception:"+e);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.info("Class:"+claz.getName()+",line:"+line+",message:"+message,"exception:"+e);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.info("Class:"+claz.getName()+",method:"+method+",message:"+message,"exception:"+e);
		}else {
			logger.info("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message,"exception:"+e);
		}
    }  
  
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     */
    @SuppressWarnings("rawtypes")
	public static void logError(Class claz,String method,String line,String message) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.error("Class:"+claz.getName()+",message:"+message);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.error("Class:"+claz.getName()+",line:"+line+",message:"+message);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.error("Class:"+claz.getName()+",method:"+method+",message:"+message);
		}else {
			logger.error("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message);
		}
    }  
  
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     * @param e
     */
    @SuppressWarnings("rawtypes")
    public static void logError(Class claz,String method,String line,String message,Throwable e) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.error("Class:"+claz.getName()+",message:"+message,"exception:"+e);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.error("Class:"+claz.getName()+",line:"+line+",message:"+message,"exception:"+e);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.error("Class:"+claz.getName()+",method:"+method+",message:"+message,"exception:"+e);
		}else {
			logger.error("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message,"exception:"+e);
		}
    }  
    
    
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     */
    @SuppressWarnings("rawtypes")
	public static void logWarn(Class claz,String method,String line,String message) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.warn("Class:"+claz.getName()+",message:"+message);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.warn("Class:"+claz.getName()+",line:"+line+",message:"+message);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.warn("Class:"+claz.getName()+",method:"+method+",message:"+message);
		}else {
			logger.warn("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message);
		}
    }  
  
    /**
     * 在哪个类的,哪个方法,哪一行,答应的Message.
     * @param claz
     * @param method
     * @param line
     * @param message
     * @param e
     */
    @SuppressWarnings("rawtypes")
    public static void logWarn(Class claz,String method,String line,String message,Throwable e) {  
    	if (StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.warn("Class:"+claz.getName()+",message:"+message,"exception:"+e);
		}else if (StringDefaultValue.isEmpty(method) && !StringDefaultValue.isEmpty(line)) {
			logger.warn("Class:"+claz.getName()+",line:"+line+",message:"+message,"exception:"+e);
		}else if (!StringDefaultValue.isEmpty(method) && StringDefaultValue.isEmpty(line)) {
			logger.warn("Class:"+claz.getName()+",method:"+method+",message:"+message,"exception:"+e);
		}else {
			logger.warn("Class:"+claz.getName()+",method:"+method+",line:"+line+",message:"+message,"exception:"+e);
		}
    }  
    
}
