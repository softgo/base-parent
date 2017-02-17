package com.application.base.generate.javabase.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.generate.javabase.constant.CommonConstant;

import java.io.InputStream;
import java.util.Properties;

/**
 * 数据库信息
 */
public class DBInfoConfig {
	
	private static Logger loggger = LoggerFactory.getLogger(DBInfoConfig.class);
	
	private static String FILE_PATH = CommonConstant.GENERATE_FILE_PATH;
	
	private static String URL = "mysql.url";
	
	private static String USERNAME = "mysql.username";
	
	private static String PASSWORD = "mysql.password";
	
    private static String DRIVER = "mysql.driverClassName";
	
	public static String URL_VALUE;
	
	public static String USERNAME_VALUE;
	
	public static String PASSWORD_VALUE;
	
    public static String DRIVER_URL;
    
	public static Properties props = new Properties();
	
	static {
		try {
            InputStream in = DBInfoConfig.class.getResourceAsStream(FILE_PATH);
            
            props.load(in);
            
            URL_VALUE = props.getProperty(URL);
            
			USERNAME_VALUE = props.getProperty(USERNAME);
			
			PASSWORD_VALUE = props.getProperty(PASSWORD);
			
            DRIVER_URL = props.getProperty(DRIVER);
            
		} catch (Exception e) {
			if (loggger.isErrorEnabled()) {
				loggger.error("Read jdbc.properties file error!", e);
			}
		}
	}

	public static String getConfig(String name) {
		return props.getProperty(name);
	}
}