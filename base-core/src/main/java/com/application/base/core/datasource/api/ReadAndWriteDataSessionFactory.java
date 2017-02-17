package com.application.base.core.datasource.api;

import com.application.base.core.datasource.session.DataSession;

/**
 * 只读数据源的设置.
 */
public interface ReadAndWriteDataSessionFactory extends DataSessionFactory{

    /**
     * 获取读库数据访问session
     * @return
     * DataSession
     *
     */
    DataSession getReadDataSession();

    /**
     * 获取写库数据访问session
     * @return
     * DataSession
     *
     */
    DataSession getWriteDataSession();
    
}
