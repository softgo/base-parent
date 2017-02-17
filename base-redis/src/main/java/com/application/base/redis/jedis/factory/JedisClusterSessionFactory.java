package com.application.base.redis.jedis.factory;

import com.application.base.redis.api.MulitiRedisSession;
import com.application.base.redis.api.RedisSession;
import com.application.base.redis.exception.RedisException;
import com.application.base.redis.factory.RedisSessionFactory;

/**
 * @ClassName: JedisClusterSessionFactory
 * @Description:TODO
 */
public class JedisClusterSessionFactory implements RedisSessionFactory {
	
    public RedisSession getSession()  throws RedisException {
    	//集群redis 的时候,再去实现这个方法.
    	return null;
    }

    @Override
    public MulitiRedisSession getMulitiSession() throws RedisException {
    	//集群redis 的时候,再去实现这个方法.
    	return null;
    }
    
}
