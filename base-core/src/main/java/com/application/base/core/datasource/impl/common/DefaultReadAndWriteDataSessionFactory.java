package com.application.base.core.datasource.impl.common;

import com.application.base.core.datasource.api.ReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.api.SqlSessionFactorySupport;
import com.application.base.core.datasource.session.DataSession;

/**
 * 读写dataSessionFactory实现
 * @ClassName:  DefaultReadAndWriteDataSessionFactory   
 */
public class DefaultReadAndWriteDataSessionFactory implements ReadAndWriteDataSessionFactory {

	private SqlSessionFactorySupport support;

	private String defaultDataSource;
	
	private String readDataSource;
	
	private String writeDataSource;

	private DataSession readDataSession;
	
	private DataSession writeDataSession;

	
	public DataSession getDaoByDataSourceName(String dataSourceName) {
		DataSession singleDataSession = new DefaultDataSession(support.getSqlSessionFacotry(dataSourceName));
		return singleDataSession;
	}

	
	public DataSession getReadDataSession() {
		if (readDataSession != null)
			return readDataSession;
		readDataSession = new DefaultDataSession(support.getSqlSessionFacotry(readDataSource));
		return readDataSession;
	}

	
	public DataSession getWriteDataSession() {
		if (writeDataSession != null)
			return writeDataSession;
		writeDataSession = new DefaultDataSession(support.getSqlSessionFacotry(writeDataSource));
		return writeDataSession;
	}

	public SqlSessionFactorySupport getSupport() {
		return support;
	}

	public void setSupport(SqlSessionFactorySupport support) {
		this.support = support;
	}

	public String getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(String defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	public String getReadDataSource() {
		return readDataSource;
	}

	public void setReadDataSource(String readDataSource) {
		this.readDataSource = readDataSource;
	}

	public String getWriteDataSource() {
		return writeDataSource;
	}

	public void setWriteDataSource(String writeDataSource) {
		this.writeDataSource = writeDataSource;
	}

}
