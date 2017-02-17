package com.application.base.redis.factory;

import com.application.base.redis.api.MulitiRedisSession;
import com.application.base.redis.api.RedisSession;
import com.application.base.redis.exception.RedisException;

/**
 * @ClassName: RedisSessionFactory
 * @Description:TODO
 */
public interface RedisSessionFactory {

	/**
	 * 获取默认（选取DB0库）的redis会话
	 * @return
	 * @throws RedisException
	 */
	RedisSession getSession() throws RedisException;

	/**
	 * 可选取DB库的redis会话
	 * @return
	 * @throws RedisException
	 */
	MulitiRedisSession getMulitiSession() throws RedisException;
	
}
