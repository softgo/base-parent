package com.application.base.redis.api;

import com.application.base.redis.exception.RedisException;

import redis.clients.jedis.JedisPubSub;

public interface MulitiRedisSession extends RedisSession{

    /**
     * 根据key存入值Value
     * 将Object类型的值转化为JSON字符串存入redis
     * @param key
     * @param value
     */
    void setData(int dbIndex,String key, Object value) throws RedisException;

    /**
     * 根据key取出值，并根据Class类型转化为相应类对象
     * 取出的值为JSON字符串，将字符串转化为对象，使用前请确认存数数据类型
     * 可能会抛出转化失败异常
     * @param clazz
     * @param key
     * @param <T>
     * @return
     */
    <T> T getTypeObject(int dbIndex,Class<T> clazz, String key) throws RedisException;

    /**
     * 根据key获取redis存入的值，String类型
     * 如果存入的是Object对象则取出对象的JSON串
     * @param key
     * @return
     */
    String getData(int dbIndex,String key) throws RedisException;

    /**
     * 根据key将值value存入redis,过期时间为timeout，单位：秒
     * 默认86400秒
     * @param key
     * @param value
     * @param timeout
     */
    void setData(int dbIndex,String key, Object value, int timeout) throws RedisException;

    /**
     * 判断是否存在key
     *
     * @param key
     * @return
     */
    boolean contains(int dbIndex,String key) throws RedisException;

    /**
     * 获得当前键剩余过期时间
     *
     * @param key
     * @return
     */
    long getKeyLastTime(int dbIndex,String key) throws RedisException;

    /**
     * 删除缓存
     * @param key
     */

    long delete(int dbIndex,String key) throws RedisException;


    /**
     * 当且仅当 key 不存在，将 key 的值设为 value ，并返回1；若给定的 key 已经存在，则 SETNX 不做任何动作，并返回0。
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    long setnx(int dbIndex,String key, Object value) throws RedisException;

    /**
     * 队列,左进
     * @param key
     * @param value
     * @return
     * @throws RedisException
     */
    long push(int dbIndex,String key, String... value)throws RedisException;

    /**
     * 队列,右出
     * @param key
     * @return
     * @throws RedisException
     */
    String pop(int dbIndex,String key)throws RedisException;

    /**
     * 设定键有效期,到期时间后键不会在Redis中使用。
     * @param key
     * @param seconds
     * @return
     * @throws RedisException
     */
    long expire(int dbIndex,String key, int seconds)throws RedisException;


    /**
     * 发布消息
     * @param chanel
     * @param msg
     */
    void publish(int dbIndex,String chanel, Object msg);

    /**
     * 发布消息
     * @param chanel
     * @param msgJson
     */
    void publish(int dbIndex,String chanel, String msgJson);


    /**
     * 注册监听
     * @param jedisPubSub
     * @param channels
     */
    void subscribe(int dbIndex,JedisPubSub jedisPubSub, String... channels);


    /**
     * 给指定的 key 增加1
     * @param key
     */
    long incrNum(int dbIndex,String key) throws RedisException;

    
    /**
     * 给指定的key 的值:(index) 增加1
     * @param key
     * @param index
     */
    long incrByNum(int dbIndex,String key, long index) throws RedisException;

    
    /**
     * 给指定的 key 减少 1
     * @param key
     */
    long decrNum(int dbIndex,String key) throws RedisException;
    
    
    /**
     * 给指定的key 的值:(index) 减少1
     * @param key
     * @param index
     */
    long decrByNum(int dbIndex,String key, long index) throws RedisException;
    
    
}
