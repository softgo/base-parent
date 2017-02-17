package com.application.base.redis.jedis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.redis.api.RedisSession;
import com.application.base.redis.exception.DistributedLockException;
import com.application.base.redis.exception.RedisException;
import com.application.base.redis.factory.RedisSessionFactory;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现.
 */
public class DelegateDistributedLock implements DistributedLock{

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private RedisSessionFactory factory;

    @Override
    public void lock(String uniqueKey) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = factory.getSession();
            do {
                logger.debug("lock key: " + uniqueKey);
                Long i = session.setnx(uniqueKey, uniqueKey);
                if (i == SUCCESS) {
                    session.expire(uniqueKey, DEFAULT_SINGLE_EXPIRE_TIME);
                    logger.debug("get lock, key: " + uniqueKey + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
                    return;
                } else {
                    String desc = session.getData(uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                Thread.sleep(300);
            } while (true);
        } catch (RedisException e) {
            throw e;
        } catch (Exception e) {
           throw new DistributedLockException(e);
        }
    }

    @Override
    public boolean tryLock(String uniqueKey) throws DistributedLockException, RedisException {
        return tryLock(uniqueKey, 0L, null);
    }

    @Override
    public boolean tryLock(String uniqueKey, long timeout, TimeUnit unit) throws DistributedLockException, RedisException {
        RedisSession session = null;
        try {
            session = factory.getSession();
            long nano = System.nanoTime();
            do {
                logger.debug("try lock key: " + uniqueKey);
                Long i = session.setnx(uniqueKey, uniqueKey);
                if (i == SUCCESS) {
                    session.expire(uniqueKey, DEFAULT_SINGLE_EXPIRE_TIME);
                    logger.debug("get lock, key: " + uniqueKey + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
                    return Boolean.TRUE;
                } else { // 存在锁
                    String desc = session.getData(uniqueKey);
                    logger.debug("key: " + uniqueKey + " locked by another business：" + desc);
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(300);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (RedisException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DistributedLockException(e);
        }
    }

    @Override
    public void unLock(String uniqueKey) throws DistributedLockException, RedisException {
        try {
            factory.getSession().delete(uniqueKey);
        } catch (RedisException e) {
           throw e;
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new DistributedLockException(e);
        }
    }

    public RedisSessionFactory getFactory() throws DistributedLockException, RedisException {
        return factory;
    }

    public void setFactory(RedisSessionFactory factory) throws DistributedLockException, RedisException {
        this.factory = factory;
    }

}
