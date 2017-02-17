package com.application.base.core.datasource.api;

import com.application.base.core.datasource.session.CacheDataSession;
import com.application.base.redis.factory.RedisSessionFactory;

/**
 * 读写数据源的设置
 */
public interface CacheReadAndWriteDataSessionFactory extends ReadAndWriteDataSessionFactory{

    /**
     * 获取缓存工厂
     * @return
     */
    RedisSessionFactory getRedisSessionFactory();
    
    /**
     * 获取读库缓存数据访问session
     * @return
     * DataSession
     *
     */
    CacheDataSession getCacheReadDataSession();

    /**
     * 获取写库缓存数据访问session
     * @return
     * DataSession
     *
     */
    CacheDataSession getCacheWriteDataSession();
    
}
