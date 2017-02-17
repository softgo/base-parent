package com.application.base.core.datasource.session;

import com.application.base.redis.api.MulitiRedisSession;
import com.application.base.redis.api.RedisSession;

/**
 * redis 缓存Session,所有的缓存信息都放在这个 redis 中.
 */
public interface CacheDataSession extends DataSession{
	
	//默认从redis 的 db0 开始放数据.
    RedisSession getRedisSession();
    
    //指定存放到那个 redis db 库.
    MulitiRedisSession getMulitiRedisSession();
    
}
