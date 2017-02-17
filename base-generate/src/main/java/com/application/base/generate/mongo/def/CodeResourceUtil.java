package com.application.base.generate.mongo.def;

import java.util.ResourceBundle;

/**
 * 
 * 获得数据资源
 * 
 * @author bruce.
 * 
 */

public class CodeResourceUtil {

	private static final ResourceBundle bundle = ResourceBundle.getBundle("config/mongo_generate");
	
	public static String DIVER_NAME = "com.mongdb.jdbc.Driver";

	public static String URL = "127.0.0.1:27017";

	public static String USERNAME = "admin";

	public static String PASSWORD = "admin";

	public static String DATABASE_NAME = "data";

	public static String DATABASE_TYPE = "mongo";

	public static String web_root_package = "WebRoot";

	public static String source_root_package = "src";

	public static String bussiPackage = "sun";

	public static String bussiPackageUrl = "sun";

	public static String entity_package = "entity";

	public static String page_package = "page";

	public static String ENTITY_URL;

	public static String PAGE_URL;

	public static String ENTITY_URL_INX;

	public static String PAGE_URL_INX;

	public static String TEMPLATEPATH;

	public static String CODEPATH;

	public static String JSPPATH;

	public static String JAVABASE_GENERATE_TABLE_ID;

	public static String JAVABASE_GENERATE_UI_FILTER_FIELDS;

	public static String SYSTEM_ENCODING;

	/**
	 * 初始化数字操作。 
	 */
	static {
		
		DIVER_NAME = getDIVER_NAME();
		
		URL = getURL();
		
		USERNAME = getUSERNAME();
		
		PASSWORD = getPASSWORD();
		
		DATABASE_NAME = getDATABASE_NAME();

		SYSTEM_ENCODING = getSYSTEM_ENCODING();
		
		TEMPLATEPATH = getTEMPLATEPATH();
		
		source_root_package = getSourceRootPackage();
		
		web_root_package = getWebRootPackage();
		
		bussiPackage = getBussiPackage();
		
		bussiPackageUrl = bussiPackage.replace(".", "/");

		JAVABASE_GENERATE_TABLE_ID = getJavaBase_generate_table_id();

		source_root_package = source_root_package.replace(".", "/");
		
		web_root_package = web_root_package.replace(".", "/");

		ENTITY_URL = source_root_package + "/" + bussiPackageUrl + "/"+ entity_package + "/";

		PAGE_URL = source_root_package + "/" + bussiPackageUrl + "/"+ page_package + "/";

		ENTITY_URL_INX = bussiPackage + "." + entity_package + ".";

		PAGE_URL_INX = bussiPackage + "." + page_package + ".";

		CODEPATH = source_root_package + "/" + bussiPackageUrl + "/";

		JSPPATH = web_root_package + "/" + bussiPackageUrl + "/";
	}

	private void ResourceUtil() {}

	public static final String getDIVER_NAME() {
		return bundle.getString("mongo.diver_name");
	}

	public static final String getURL() {
		return bundle.getString("mongo.url");
	}

	public static final String getUSERNAME() {
		return bundle.getString("mongo.username");
	}

	public static final String getPASSWORD() {
		return bundle.getString("mongo.password");
	}

	public static final String getDATABASE_NAME() {
		return bundle.getString("mongo.database_name");
	}

	private static String getBussiPackage() {
		return bundle.getString("bussi_package");
	}

	public static final String getEntityPackage() {
		return bundle.getString("entity_package");
	}

	public static final String getPagePackage() {
		return bundle.getString("page_package");
	}

	public static final String getTEMPLATEPATH() {
		return bundle.getString("templatepath");
	}

	public static final String getSourceRootPackage() {
		return bundle.getString("source_root_package");
	}

	public static final String getWebRootPackage() {
		return bundle.getString("webroot_package");
	}

	public static final String getSYSTEM_ENCODING() {
		return bundle.getString("system_encoding");
	}

	public static final String getJavaBase_generate_table_id() {
		return bundle.getString("mongo_generate_table_id");
	}
	
}
