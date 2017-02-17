package com.application.base.core.datasource.impl.cache;

import org.springframework.stereotype.Service;

import com.application.base.core.datasource.api.CacheReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.impl.common.DefaultReadAndWriteDataSessionFactory;
import com.application.base.core.datasource.session.CacheDataSession;
import com.application.base.redis.factory.RedisSessionFactory;

/**
 * 默认的带有缓存功能的读写分离CacheDataSession工厂,
 * 用于获取带有缓存功能的数据库访问会话。
 * 单数据源操作数据库，实现读写分离
 */
@Service
public class DefaultCacheReadAndWriteDataSessionFactory extends DefaultReadAndWriteDataSessionFactory implements CacheReadAndWriteDataSessionFactory {

    private RedisSessionFactory redisSessionFactory;

    private CacheDataSession cacheReadDataSession;
    
    private CacheDataSession cacheWriteDataSession;

    public CacheDataSession getCacheReadDataSession() {
        if (cacheReadDataSession != null){
        	return cacheReadDataSession;
        }
        cacheReadDataSession = new DefaultCacheDataSession(getSupport().getSqlSessionFacotry(getReadDataSource()),redisSessionFactory);
        return cacheReadDataSession;
    }
    
    public CacheDataSession getCacheWriteDataSession() {
        if (cacheWriteDataSession != null){
            return cacheWriteDataSession;
        }
        cacheWriteDataSession = new DefaultCacheDataSession(getSupport().getSqlSessionFacotry(getWriteDataSource()),redisSessionFactory);
        return cacheWriteDataSession;
    }

    
    public RedisSessionFactory getRedisSessionFactory() {
        return redisSessionFactory;
    }

    public void setRedisSessionFactory(RedisSessionFactory redisSessionFactory) {
        this.redisSessionFactory = redisSessionFactory;
    }
}
