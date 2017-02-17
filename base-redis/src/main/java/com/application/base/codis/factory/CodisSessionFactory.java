package com.application.base.codis.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.codis.architecture.cache.CacheClient;
import com.application.base.codis.session.CodisSession;
import com.application.base.redis.api.MulitiRedisSession;
import com.application.base.redis.api.RedisSession;
import com.application.base.redis.exception.RedisException;
import com.application.base.redis.factory.RedisSessionFactory;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * codis 工厂
 */
public class CodisSessionFactory implements RedisSessionFactory {
	
    Logger logger = LoggerFactory.getLogger(getClass());

    private Pool<Jedis> pool;

    private CacheClient client;

    @Override
    public RedisSession getSession() throws RedisException {
        RedisSession session;
        try {
            session = (RedisSession) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new
                    Class[]{RedisSession.class}, new CodisSessionFactory.CodisSimpleSessionProxy(new CodisSession()));
        } catch (Exception e) {
            logger.error("{}", e);
            throw new RedisException("获取RedisSession失败");
        }
        return session;
    }

    @Override
    public MulitiRedisSession getMulitiSession() throws RedisException {
        throw new RedisException("unsupport Exception:this class unsupport this method!");
    }

    public CacheClient getClient() {
        return client;
    }

    public void setClient(CacheClient client) {
        this.client = client;
    }

    public Pool<Jedis> getPool() {
        return pool;
    }

    public void setPool(Pool<Jedis> pool) {
        this.pool = pool;
    }

    private class CodisSimpleSessionProxy implements InvocationHandler {
        private CodisSession codisSession;

        public CodisSimpleSessionProxy(CodisSession codisSession) {
            this.codisSession = codisSession;
        }

        //同步获取Jedis链接
        private synchronized Jedis getJedis() {
            logger.debug("获取redis链接");
            Jedis jedis = null;
            try {
                jedis = CodisSessionFactory.this.pool.getResource();
            } catch (Exception e) {
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
                if (codisSession == null) {
                    logger.error("获取codisSession失败");
                    throw new RedisException("获取codisSession失败");
                }

                jedis = getJedis();
                codisSession.setJedis(jedis);
                codisSession.setClient(client);
                return method.invoke(codisSession, args);
            } catch (RuntimeException e) {
                success = false;
                if (jedis != null) {
                    jedis.close();
                }
                logger.error("[Jedis执行失败！异常信息为：{}]", e);
                throw e;
            } finally {
                if (success && jedis != null) {
                    logger.debug("redis 链接关闭");
                    jedis.close();
                }
            }
        }
    }

}