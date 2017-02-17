package com.application.base.redis.jedis.lock;

import java.util.concurrent.TimeUnit;

import com.application.base.redis.exception.DistributedLockException;
import com.application.base.redis.exception.RedisException;

/**
 * 分布式锁
 */
public interface DistributedLock {

    /**
     * 默认超时时间
     */
    int DEFAULT_SINGLE_EXPIRE_TIME = 3;
    
    /**
     * 获取锁成功
     */
    int SUCCESS = 1;
    
    /**
     * 根据uniqueKey构建分布式锁,对key进行锁定,循环获取锁,直到获取到锁为止
     * @param uniqueKey
     */
    void lock(String uniqueKey) throws DistributedLockException,RedisException;
    

    /**
     * 根据uniqueKey构建分布式锁,如果锁可用   立即返回true，  否则返回false
     * @param uniqueKey
     * @return
     */
    boolean tryLock(String uniqueKey) throws DistributedLockException,RedisException;
    

    /**
     * 在给定时间内获取锁,如果获取锁则返回true,否则返回false
     * @param uniqueKey
     * @param timeout
     * @param unit
     * @return
     */
    boolean tryLock(String uniqueKey, long timeout, TimeUnit unit) throws DistributedLockException,RedisException;
    

    /**
     * 释放锁
     * @param uniqueKey
     */
    void unLock(String uniqueKey) throws DistributedLockException,RedisException;

}
