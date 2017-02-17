package com.application.base.redis.jedis.session;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.redis.api.RedisSession;
import com.application.base.redis.exception.RedisException;
import com.application.base.utils.common.JSONUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @ClassName: JedisSimpleSession
 * @Description:TODO
 */
public class JedisSimpleSession implements RedisSession {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    protected static final int DEFAULT_TIMEOUT = 60*60*24 ;
    
    /** 默认的 {@code JSON} 完整日期/时间字段的格式化模式。 */
    //被 装配到 Spring 工厂
    private Jedis jedis;

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
    
    public <T> T getTypeObject(Class<T> clazz, String key) throws RedisException {
        T t = null;
        String objStr = getData(key);
        logger.debug("[根据key:{},获得:{}]",key,objStr);
        try {
            t = JSONUtils.fromJson(objStr,clazz);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
        return t;
    }

   
    public String getData(String key) throws RedisException {
        String objStr;
        try {
            Object o = getJedis().get(key);
            if(o == null)
                return null;
            objStr = Objects.toString(o);
            logger.debug("[根据key:{},获得:{}]",key,objStr);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
        return objStr;
    }
   
    
    public void setData(String key, Object value) throws RedisException {
        try {
            if(key == null){
                logger.info("[存入key:{},key:{}为空！]",key,value);
                throw new RedisException("存入键为空!");
            }
            if (value == null) {
                logger.info("[存入key:{},value:{}为空！]",key,value);
                throw new RedisException("存入值为空!");
            }
            setData(key, value.toString(),DEFAULT_TIMEOUT);
            logger.debug("[存入key:{},value:{}]" ,key,Objects.toString(value));
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    public void setData(String key, Object value, int timeout)  throws RedisException{
        try {
            if(key == null){
                logger.info("[存入key:{},key:{}为空！]",key,value);
                throw new RedisException("存入键为空!");
            }
            if (value == null) {
                logger.info("[存入key:{},value:{}为空！]",key,Objects.toString(value));
                throw new RedisException("存入值为空!");
            }
            if (timeout == 0)
                timeout = DEFAULT_TIMEOUT;
            getJedis().setex(key, timeout, value.toString());
            logger.info("[存入key:{},value:{}]" ,key,Objects.toString(value));
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    public boolean contains(String key) throws RedisException {
        try {
            return getJedis().exists(key);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
   
    public long getKeyLastTime(String key)  throws RedisException{
        try {
            if(key == null){
                logger.info("[key:{}为空！]",key);
                throw new RedisException("存入键为空!");
            }
            long timeout = getJedis().ttl(key);
            logger.info("key:{},剩余超时时间为：{}",key,timeout);
            return timeout;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
	
	public long delete(String key)  throws RedisException{
        try {
            return this.getJedis().del(key);
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }

   
    public void flushAll() throws RedisException {
        try {
        	this.getJedis().flushDB(); //Delete currently selected DB ...
            this.getJedis().flushAll(); //Delete all the keys of all the existing databases ...
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }


    @Override
    public long setnx(String key, Object value) throws RedisException {
        try {
            if(key == null){
                logger.info("[key:{}为空！]",key);
                throw new RedisException("存入键为空!");
            }
            if (value == null) {
                logger.info("[存入key:{},value:{}为空！]",key,Objects.toString(value));
                throw new RedisException("存入值为空!");
            }
            long result = getJedis().setnx(key,value.toString());
            return result;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }

    @Override
    public long push(String key,String ...value) throws RedisException {
        try {
            if(key == null){
                logger.info("[存入队列key:{},key:{}为空！]",key,value);
                throw new RedisException("存入键为空!");
            }
            if (value == null) {
                logger.info("[存入队列key:{},value:{}为空！]",key,value);
                throw new RedisException("存入值为空!");
            }
            long result =  getJedis().lpush(key,value);
            logger.debug("[存入队列key:{},value:{}]" ,key,Objects.toString(value));
            return result;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }

    @Override
    public String pop(String key) throws RedisException {
        try {
            String o = getJedis().rpop(key);
            if(o == null)
                return null;
            logger.debug("[根据key:{},获得:{}]",key,o);
            return o;
        } catch (Exception e) {
            logger.error("[redis错误:{}]",e);
            throw new RedisException(e);
        }
    }
    
    @Override
    public long expire(String key, int seconds) throws RedisException {
        if (key == null) {
            logger.info("[key:{}为空！]", key);
            throw new RedisException("存入键为空!");
        }
        if (seconds <= 0) {
            logger.info("[超时时间应为大于零的整数,输入值为{}！]", seconds);
            throw new RedisException("存入值为空!");
        }
        long result = getJedis().expire(key, seconds);
        return result;
    }
    
    /**
     * 发布消息
     */
    @Override
    public void publish(String chanel, Object msg) {
    	if (chanel == null) {
            logger.info("[chanel:{}为空！]", chanel);
            throw new RedisException("chanel为空!");
        }
    	if (msg == null) {
            logger.info("[msg:{}为空！]", msg);
            throw new RedisException("发送msg为空!");
        }
        String msgJson =null;
        try {
        	msgJson = JSONUtils.toJson(msg);
		}
		catch (Exception e) {
            logger.error("[ Object 转换成 Json 失败！]", msg);
		}
        publish(chanel,msgJson);
    }

    /**
     * 发布消息
     */
    @Override
    public void publish(String chanel, String msgJson) {
    	if (chanel == null) {
            logger.info("[chanel:{}为空！]", chanel);
            throw new RedisException("chanel为空!");
        }
    	if (msgJson == null) {
            logger.info("[msgJson:{}为空！]", msgJson);
            throw new RedisException("发送msgJson为空!");
        }
    	getJedis().publish(chanel, msgJson);
    }

    /**
     * 注册监听
     */
    @Override
    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
    	if (jedisPubSub == null) {
            logger.info("[jedisPubSub:{}为空！]", jedisPubSub);
            throw new RedisException("jedisPubSub为空!");
        }
    	if (channels == null) {
            logger.info("[channels:{}为空！]", channels+"");
            throw new RedisException("发送channels为空!");
        }
    	getJedis().subscribe(jedisPubSub, channels);
    }

	@Override
	public long incrNum(String key) throws RedisException {
		if (key == null) {
            logger.info("[key:{}为空！]", key);
            throw new RedisException("存入键为空!");
        }
        long result = getJedis().incr(key);
        return result;
	}

	@Override
	public long incrByNum(String key, long index) throws RedisException {
		if (key == null) {
            logger.info("[key:{}为空！]", key);
            throw new RedisException("存入键为空!");
        }
        long result = getJedis().incrBy(key, index);
        return result;
	}

	@Override
	public long decrNum(String key) throws RedisException {
		if (key == null) {
            logger.info("[key:{}为空！]", key);
            throw new RedisException("存入键为空!");
        }
        long result = getJedis().decr(key);
        return result;
	}

	@Override
	public long decrByNum(String key, long index) throws RedisException {
		if (key == null) {
            logger.info("[key:{}为空！]", key);
            throw new RedisException("存入键为空!");
        }
        long result = getJedis().decrBy(key, index);
        return result;
	}
    
}
