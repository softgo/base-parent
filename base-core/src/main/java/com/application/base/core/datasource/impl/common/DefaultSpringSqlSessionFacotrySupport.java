package com.application.base.core.datasource.impl.common;

import com.application.base.core.datasource.api.AbstractSpringSqlSessionFactorySupport;
import com.application.base.core.utils.PropertiesUtils;

/**
 * 获得 SessionFactory 的工具类.
 */
public class DefaultSpringSqlSessionFacotrySupport extends AbstractSpringSqlSessionFactorySupport{
	
    @Override
    protected String getFactoryName(String factoryName) {
    	System.out.println("*********************************************"+factoryName+"*********************************************");
        return PropertiesUtils.getString(factoryName, "sqlSessionFactory");
    }
    
}
