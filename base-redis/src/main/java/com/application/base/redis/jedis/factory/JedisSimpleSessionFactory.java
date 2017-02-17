package com.application.base.redis.jedis.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.redis.api.MulitiRedisSession;
import com.application.base.redis.api.RedisSession;
import com.application.base.redis.exception.RedisException;
import com.application.base.redis.factory.RedisSessionFactory;
import com.application.base.redis.jedis.session.JedisMulitiRedisSession;
import com.application.base.redis.jedis.session.JedisSimpleSession;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

/**
 * @ClassName: JedisSessionFactory
 * @Description:TODO
 */
public class JedisSimpleSessionFactory implements RedisSessionFactory {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	private Pool<Jedis> pool;

	public JedisSimpleSessionFactory(Pool<Jedis> pool) {
		this.pool = pool;
	}

	public JedisSimpleSessionFactory() {
	}

   @Override
    public RedisSession getSession() {
        RedisSession session = null;
        try {
            session = (RedisSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{RedisSession.class}, new JedisSimpleSessionProxy(new JedisSimpleSession()));
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return session;
    }

    @Override
    public MulitiRedisSession getMulitiSession() throws RedisException {
        MulitiRedisSession session = null;
        try {
            session = (MulitiRedisSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{MulitiRedisSession.class}, new JedisMultiSessionProxy(new JedisMulitiRedisSession()));
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return session;
    }

	public Pool<Jedis> getPool() {
		return pool;
	}

	public void setPool(Pool<Jedis> pool) {
		this.pool = pool;
	}

	private class JedisSimpleSessionProxy implements InvocationHandler {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		private JedisSimpleSession jedisSession;

		public JedisSimpleSessionProxy(JedisSimpleSession jedisSession) {
			this.jedisSession = jedisSession;
		}

		// 同步获取Jedis链接
		private synchronized Jedis getJedis() {
			logger.debug("获取redis链接");
			Jedis jedis = null;
			try {
				jedis = JedisSimpleSessionFactory.this.pool.getResource();
			}
			catch (Exception e) {
				logger.error("获取redis链接错误,{}", e);
				throw new RedisException(e);
			}
			return jedis;
		}

		/**
		 * Redis方法的代理实现
		 *
		 * @param proxy
		 * @param method
		 * @param args
		 * @return
		 * @throws Throwable
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Jedis jedis = null;
			boolean success = true;
			try {
				if (pool == null) {
					logger.error("获取Jedi连接池失败");
					throw new RedisException("获取Jedi连接池失败");
				}
				jedis = getJedis();
				jedisSession.setJedis(jedis);
				return method.invoke(jedisSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (jedis != null) {
					jedis.close();
				}
				logger.error("[Jedis执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && jedis != null) {
					logger.debug("redis 链接关闭");
					jedis.close();
				}
			}
		}
	}

	private class JedisMultiSessionProxy implements InvocationHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		private JedisMulitiRedisSession jedisSession;

		public JedisMultiSessionProxy(JedisMulitiRedisSession jedisSession) {
			this.jedisSession = jedisSession;
		}

		// 同步获取Jedis链接
		private synchronized Jedis getJedis() {
			logger.debug("获取redis链接");
			Jedis jedis = null;
			try {
				jedis = JedisSimpleSessionFactory.this.pool.getResource();
			}
			catch (Exception e) {
				logger.error("获取redis链接错误,{}", e);
				throw new RedisException(e);
			}
			return jedis;
		}

		/**
		 * Redis方法的代理实现
		 *
		 * @param proxy
		 * @param method
		 * @param args
		 * @return
		 * @throws Throwable
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Jedis jedis = null;
			boolean success = true;
			try {
				if (pool == null) {
					logger.error("获取Jedi连接池失败");
					throw new RedisException("获取Jedi连接池失败");
				}
				jedis = getJedis();
				jedisSession.setJedis(jedis);
				return method.invoke(jedisSession, args);
			}
			catch (RuntimeException e) {
				success = false;
				if (jedis != null) {
					jedis.close();
				}
				logger.error("[Jedis执行失败！异常信息为：{}]", e);
				throw e;
			}
			finally {
				if (success && jedis != null) {
					logger.debug("redis 链接关闭");
					jedis.close();
				}
			}
		}
	}
}
