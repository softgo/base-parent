package com.application.base.codis.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.codis.architecture.cache.CacheClient;
import com.application.base.redis.api.RedisSession;
import com.application.base.redis.exception.RedisException;
import com.application.base.redis.jedis.session.JedisSimpleSession;
import com.application.base.utils.common.StringDefaultValue;

import redis.clients.jedis.JedisPubSub;

import java.util.Objects;

/**
 * codis 缓存
 *
 */
public class CodisSession extends JedisSimpleSession implements RedisSession {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private CacheClient client;

	public CodisSession() {
	}

	public CodisSession(CacheClient client) {
		this.client = client;
	}

	@Override
	public void setData(String key, Object value) throws RedisException {
		super.setData(key, value);
	}

	@Override
	public <T> T getTypeObject(Class<T> clazz, String key) throws RedisException {
		return super.getTypeObject(clazz, key);
	}

	@Override
	public String getData(String key) throws RedisException {
		String objStr;
		try {
			Object o = client.get(key);
			if (o == null) return null;
			objStr = Objects.toString(o);
			logger.debug("[codis操作，根据key:{},获得:{}]", key, objStr);
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
		return objStr;
	}

	@Override
	public void setData(String key, Object value, int timeout) throws RedisException {
		try {
			if (key == null) {
				logger.info("[codis操作，存入key:{},key:{}为空！]", key, value);
				throw new RedisException("存入键为空!");
			}
			if (value == null) {
				logger.info("[codis操作，存入key:{},value:{}为空！]", key, Objects.toString(value));
				throw new RedisException("存入值为空!");
			}
			if (timeout == 0) timeout = DEFAULT_TIMEOUT;
			client.setExpire(key, timeout, StringDefaultValue.StringValue(value));
			logger.info("[codis操作，存入key:{},value:{}]", key, StringDefaultValue.StringValue(value));
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public boolean contains(String key) throws RedisException {
		try {
			return client.exists(key);
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public long getKeyLastTime(String key) throws RedisException {
		try {
			if (key == null) {
				logger.info("[codis操作，key:{}为空！]", key);
				throw new RedisException("存入键为空!");
			}
			long timeout = client.ttl(key);
			logger.info("codis操作，key:{},剩余超时时间为：{}", key, timeout);
			return timeout;
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public long delete(String key) throws RedisException {
		try {
			return client.del(new String[]{key});
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public void flushAll() throws RedisException {
		throw new RedisException("codis操作，unsupport method!");
	}

	@Override
	public long setnx(String key, Object value) throws RedisException {
		try {
			if (key == null) {
				logger.info("[codis操作，key:{}为空！]", key);
				throw new RedisException("存入键为空!");
			}
			if (value == null) {
				logger.info("[codis操作，存入key:{},value:{}为空！]", key, Objects.toString(value));
				throw new RedisException("存入值为空!");
			}
			long result = client.setnx(key, value.toString());
			return result;
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public long push(String key, String... value) throws RedisException {
		try {
			if (key == null) {
				logger.info("[codis操作，存入队列key:{},key:{}为空！]", key, value);
				throw new RedisException("存入键为空!");
			}
			if (value == null) {
				logger.info("[codis操作，存入队列key:{},value:{}为空！]", key, value);
				throw new RedisException("存入值为空!");
			}
			long result = client.lpush(key, value);
			logger.debug("[codis操作，存入队列key:{},value:{}]", key, Objects.toString(value));
			return result;
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public String pop(String key) throws RedisException {
		try {
			String o = client.rpop(key);
			if (o == null) return null;
			logger.debug("[codis操作，根据key:{},获得:{}]", key, o);
			return o;
		}
		catch (Exception e) {
			logger.error("[codis操作，redis错误:{}]", e);
			throw new RedisException(e);
		}
	}

	@Override
	public long expire(String key, int seconds) throws RedisException {
		if (key == null) {
			logger.info("[codis操作，key:{}为空！]", key);
			throw new RedisException("存入键为空!");
		}
		if (seconds <= 0) {
			logger.info("[codis操作，超时时间应为大于零的整数,输入值为{}！]", seconds);
			throw new RedisException("存入值为空!");
		}
		Boolean result = client.expire(key, seconds);
		return result ? 1L : 0;
	}

	@Override
	public void publish(String chanel, Object msg) {
		super.publish(chanel, msg);
	}

	@Override
	public void publish(String chanel, String msgJson) {
		super.publish(chanel, msgJson);
	}

	@Override
	public void subscribe(JedisPubSub jedisPubSub, String... channels) {
		super.subscribe(jedisPubSub, channels);
	}

	public CacheClient getClient() {
		return client;
	}

	public void setClient(CacheClient client) {
		this.client = client;
	}
}
