package com.application.base.core.datasource.api;

import org.apache.ibatis.session.SqlSessionFactory;

import com.application.base.core.utils.SpringContextHolder;

/**
 * Sqlsessionfactory提供者SpringContext的抽象(模板)实现，
 * 
 * 所有的Spring的sqlsessionFacotry实现都由此类派生,模板类提供生成工厂前后方法定制。
 *
 * @ClassName: AbstractSqlSessionFactorySupport
 *
 */
public abstract class AbstractSpringSqlSessionFactorySupport implements SqlSessionFactorySupport {

	public SqlSessionFactory getSqlSessionFacotry(String seed) {
        String sqlSessionFactoryBeanName = getFactoryName(seed);
        return SpringContextHolder.getBean(sqlSessionFactoryBeanName, SqlSessionFactory.class);
    }
	
    /**
     *
     * @Title: getFactoryName
     * @Description: 该方法返回springContext中sqlSessionFactoryBean的名字，该方法的覆盖实现需提供Bean名称产生策略。
     * @param: @return
     * @return: String
     * @throws
     */
    protected abstract String getFactoryName(String factoryName);
    
}
