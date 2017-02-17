package com.application.base.core.datasource.api;

import org.apache.ibatis.session.SqlSessionFactory;
/**
 * 获得 sessionFactory 对象
 */
public interface SqlSessionFactorySupport {
	
	/**
	 * 通过指定的种子,获得 SqlSessionFactory 对象。
	 * @param seed
	 * @return
	 */
    SqlSessionFactory getSqlSessionFacotry(String seed);
    
}
