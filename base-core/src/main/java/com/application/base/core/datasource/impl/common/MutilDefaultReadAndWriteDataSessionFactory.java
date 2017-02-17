package com.application.base.core.datasource.impl.common;


import org.springframework.util.StringUtils;

import com.application.base.core.datasource.api.ReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.api.SqlSessionFactorySupport;
import com.application.base.core.datasource.session.DataSession;
import com.application.base.utils.common.StringDefaultValue;

/**
 * 读写dataSessionFactory实现
 * @ClassName: MutilDefaultReadAndWriteDataSessionFactory   
 *
 */
public class MutilDefaultReadAndWriteDataSessionFactory implements ReadAndWriteDataSessionFactory {

	private SqlSessionFactorySupport support; //数据源配置的类.

	private String factoryTag = ""; //数据的设计从A,B,C,D,E,F... 

	private String defaultDataSource; //默认的数据源设置.
	
	private DataSession readDataSession;
	
	private DataSession writeDataSession;

	
	public DataSession getDaoByDataSourceName(String dataSourceName) {
		System.out.println("=============================="+dataSourceName+"==============================");
		DataSession singleDataSession = new DefaultDataSession(support.getSqlSessionFacotry(dataSourceName));
		return singleDataSession;
	}
	
	public DataSession getReadDataSession() {
		if (readDataSession != null){
			return readDataSession;
		}else{
			if (StringUtils.isEmpty(getReadDataSource())) {
				readDataSession = new DefaultDataSession(support.getSqlSessionFacotry(getDefaultDataSource()));
			}else{
				readDataSession = new DefaultDataSession(support.getSqlSessionFacotry(getReadDataSource()));
			}
			return readDataSession;
		}
	}
	
	public DataSession getWriteDataSession() {
		if (writeDataSession != null){
			return writeDataSession;
		}else{
			if (StringUtils.isEmpty(getWriteDataSource())) {
				writeDataSession = new DefaultDataSession(support.getSqlSessionFacotry(getDefaultDataSource()));
			}else{
				writeDataSession = new DefaultDataSession(support.getSqlSessionFacotry(getWriteDataSource()));
			}
			return writeDataSession;
		}
	}

	public SqlSessionFactorySupport getSupport() {
		return support;
	}

	public void setSupport(SqlSessionFactorySupport support) {
		this.support = support;
	}

	public String getReadDataSource() {
		if (StringUtils.isEmpty(getFactoryTag())) {
			return getDefaultDataSource();
		}
		return "sqlSessionFactory"+getFactoryTag().toUpperCase()+"read";
	}

	public String getWriteDataSource() {
		if (StringUtils.isEmpty(getFactoryTag())) {
			return getDefaultDataSource();
		}
		return "sqlSessionFactory"+getFactoryTag().toUpperCase()+"write";
	}

    public String getFactoryTag() {
		return factoryTag;
	}
    
    /**
     * 设置:
     * 增删改查操作时候使用的数据源.
     * 如果没有手动设置,就使用默认的数据源.
     * @param factoryTag
     */
	public String setFactoryTag(String factoryTag) {
		if (StringUtils.isEmpty(factoryTag)) {
			this.factoryTag = "";
		}else{
			this.factoryTag = factoryTag.toUpperCase();
		}
		return factoryTag;
	}
	
	/**
	 * 设置默认的数据源.
	 * @return
	 */
	public String getDefaultDataSource() {
		//default setting for one datasource
		if (StringDefaultValue.isEmpty(defaultDataSource)) {
			return "sqlSessionFactoryDefault";  
		}else{
			return defaultDataSource;
		}
	}

	public void setDefaultDataSource(String defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

}
