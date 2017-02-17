package com.application.base.core.datasource.api;

import com.application.base.core.datasource.session.DataSession;

/**
 * 获得数据库的数据源 
 */
public interface DataSessionFactory {
	
    DataSession getDaoByDataSourceName(String dataSourceName);
    
}

