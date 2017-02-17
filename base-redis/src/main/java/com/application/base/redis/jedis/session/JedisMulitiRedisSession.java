package com.application.base.redis.jedis.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.redis.api.MulitiRedisSession;
import com.application.base.redis.exception.RedisException;

import redis.clients.jedis.JedisPubSub;

/**
 * redis 数据库切换存储数据
 *
 */
public class JedisMulitiRedisSession extends JedisSimpleSession implements MulitiRedisSession {
	
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Redis数据库切换
     * @param dbIndex
     */
    private void changeDB(int dbIndex) {
        try {
            getJedis().select(dbIndex);
        } catch (Exception e) {
            logger.error("[redis错误:{}]", e);
            throw new RedisException(e);
        }
    }

    @Override
    public void setData(int dbIndex, String key, Object value) throws RedisException {
        changeDB(dbIndex);
        super.setData(key, value);

    }

    @Override
    public <T> T getTypeObject(int dbIndex, Class<T> clazz, String key) throws RedisException {
        changeDB(dbIndex);
        return getTypeObject(clazz, key);
    }

    @Override
    public String getData(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return super.getData(key);
    }

    @Override
    public void setData(int dbIndex, String key, Object value, int timeout) throws RedisException {
        changeDB(dbIndex);
        super.setData(key, value, timeout);
    }

    @Override
    public boolean contains(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return super.contains(key);
    }

    @Override
    public long getKeyLastTime(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return super.getKeyLastTime(key);
    }

    @Override
    public long delete(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return super.delete(key);
    }

    @Override
    public long setnx(int dbIndex, String key, Object value) throws RedisException {
        changeDB(dbIndex);
        return super.setnx(key, value);
    }

    @Override
    public long push(int dbIndex, String key, String... value) throws RedisException {
        changeDB(dbIndex);
        return super.push(key, value);
    }

    @Override
    public String pop(int dbIndex, String key) throws RedisException {
        changeDB(dbIndex);
        return super.pop(key);
    }

    @Override
    public long expire(int dbIndex, String key, int seconds) throws RedisException {
        changeDB(dbIndex);
        return super.expire(key, seconds);
    }

    @Override
    public void publish(int dbIndex, String chanel, Object msg) {
        changeDB(dbIndex);
        super.publish(chanel, msg);
    }

    @Override
    public void publish(int dbIndex, String chanel, String msgJson) {
        changeDB(dbIndex);
        super.publish(chanel, msgJson);
    }

    @Override
    public void subscribe(int dbIndex, JedisPubSub jedisPubSub, String... channels) {
        changeDB(dbIndex);
        super.subscribe(jedisPubSub, channels);
    }

	@Override
	public long incrNum(int dbIndex, String key) throws RedisException {
		changeDB(dbIndex);
		return super.incrNum(key);
	}

	@Override
	public long incrByNum(int dbIndex, String key, long index) throws RedisException {
		changeDB(dbIndex);
		return super.incrByNum(key, index);
	}

	@Override
	public long decrNum(int dbIndex, String key) throws RedisException {
		changeDB(dbIndex);
		return super.decrNum(key);
	}

	@Override
	public long decrByNum(int dbIndex, String key, long index) throws RedisException {
		changeDB(dbIndex);
		return super.decrByNum(key, index);
	}
}
