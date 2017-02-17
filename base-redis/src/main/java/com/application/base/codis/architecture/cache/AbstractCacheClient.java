package com.application.base.codis.architecture.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.application.base.codis.architecture.enumer.ListPosition;

import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

public abstract interface AbstractCacheClient {

	public abstract Boolean set(String paramStr1, String paramStr2);

	public abstract String get(String paramStr);

	public abstract Long setnx(String paramStr1, String paramStr2);

	public abstract String getSet(String paramStr1, String paramStr2);

	public abstract Boolean exists(String paramStr);

	public abstract Boolean expire(String paramStr, int paramInt);

	public abstract Long expireAt(String paramStr, long paramLong);

	public abstract Long append(String paramStr1, String paramStr2);

	public abstract Long decr(String paramStr);

	public abstract Long decrBy(String paramStr, long paramLong);

	public abstract Long incr(String paramStr);

	public abstract Long incrBy(String paramStr, long paramLong);

	public abstract Long del(String[] paramArrayOfString);

	public abstract String getrange(String paramStr, long paramLong1, long paramLong2);

	public abstract String hget(String paramStr1, String paramStr2);

	public abstract Long hdel(String paramStr, String[] paramArrayOfString);

	public abstract Boolean hexists(String paramStr1, String paramStr2);

	public abstract Map<String, String> hgetAll(String paramStr);

	public abstract Long hincrBy(String paramStr1, String paramStr2, long paramLong);

	public abstract Set<String> hkeys(String paramStr);

	public abstract Long hlen(String paramStr);

	public abstract List<String> hmget(String paramStr, String[] paramArrayOfString);

	public abstract String hmset(String paramStr, Map<String, String> paramMap);

	public abstract Long hset(String paramStr1, String paramStr2, String paramStr3);

	public abstract Long hsetnx(String paramStr1, String paramStr2, String paramStr3);

	public abstract List<String> hvals(String paramStr);

	public abstract String lindex(String paramStr, long paramLong);

	public abstract Long linsert(String paramStr1, ListPosition paramListPosition, String paramStr2, String paramStr3);

	public abstract List<String> mget(String[] paramArrayOfString);

	public abstract Boolean mset(String[] paramArrayOfString);

	public abstract Long llen(String paramStr);

	public abstract String lpop(String paramStr);

	public abstract Long lpush(String paramStr, String[] paramArrayOfString);

	public abstract Long lpushx(String paramStr, String[] paramArrayOfString);

	public abstract List<String> lrange(String paramStr, long paramLong1, long paramLong2);

	public abstract Long lrem(String paramStr1, long paramLong, String paramStr2);

	public abstract String ltrim(String paramStr, long paramLong1, long paramLong2);

	public abstract String lset(String paramStr1, long paramLong, String paramStr2);

	public abstract String rpop(String paramStr);

	public abstract Long rpush(String paramStr, String[] paramArrayOfString);

	public abstract Long rpushx(String paramStr1, String paramStr2);

	public abstract Long sadd(String paramStr, String[] paramArrayOfString);

	public abstract Long scard(String paramStr);

	public abstract Set<String> smembers(String paramStr);

	public abstract String setExpire(String paramStr1, int paramInt, String paramStr2);

	public abstract String spop(String paramStr);

	public abstract Long setrange(String paramStr1, long paramLong, String paramStr2);

	public abstract Boolean sismember(String paramStr1, String paramStr2);

	public abstract Long strlen(String paramStr);

	public abstract Long ttl(String paramStr);

	public abstract List<String> sort(String paramStr);

	public abstract List<String> sort(String paramStr, SortingParams paramSortingParams);

	public abstract Long sort(String paramStr1, SortingParams paramSortingParams, String paramStr2);

	public abstract Long sort(String paramStr1, String paramStr2);

	public abstract String srandmember(String paramStr);

	public abstract List<String> srandmember(String paramStr, int paramInt);

	public abstract Long srem(String paramStr, String[] paramArrayOfString);

	public abstract String substr(String paramStr, int paramInt1, int paramInt2);

	public abstract String type(String paramStr);

	public abstract Long zadd(String paramStr1, double paramDouble, String paramStr2);

	public abstract Long zadd(String paramStr, Map<String, Double> paramMap);

	public abstract Long zcard(String paramStr);

	public abstract Long zcount(String paramStr, double paramDouble1, double paramDouble2);

	public abstract Long zcount(String paramStr1, String paramStr2, String paramStr3);

	public abstract Double zincrby(String paramStr1, double paramDouble, String paramStr2);

	public abstract Set<String> zrange(String paramStr, long paramLong1, long paramLong2);

	public abstract Set<String> zrangeByScore(String paramStr, double paramDouble1, double paramDouble2);

	public abstract Set<String> zrangeByScore(String paramStr, double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2);

	public abstract Set<String> zrangeByScore(String paramStr1, String paramStr2, String paramStr3);

	public abstract Set<String> zrangeByScore(String paramStr1, String paramStr2, String paramStr3, int paramInt1,
			int paramInt2);

	public abstract Set<Tuple> zrangeByScoreWithScores(String paramStr, double paramDouble1, double paramDouble2);

	public abstract Set<Tuple> zrangeByScoreWithScores(String paramStr, double paramDouble1, double paramDouble2,
			int paramInt1, int paramInt2);

	public abstract Set<Tuple> zrangeByScoreWithScores(String paramStr1, String paramStr2, String paramStr3);

	public abstract Set<Tuple> zrangeByScoreWithScores(String paramStr1, String paramStr2, String paramStr3,
			int paramInt1, int paramInt2);

	public abstract Set<Tuple> zrangeWithScores(String paramStr, long paramLong1, long paramLong2);

	public abstract Long zrank(String paramStr1, String paramStr2);

	public abstract Long zrem(String paramStr, String[] paramArrayOfString);

	public abstract Long zremrangeByRank(String paramStr, long paramLong1, long paramLong2);

	public abstract Long zremrangeByScore(String paramStr, double paramDouble1, double paramDouble2);

	public abstract Long zremrangeByScore(String paramStr1, String paramStr2, String paramStr3);

	public abstract Set<String> zrevrange(String paramStr, long paramLong1, long paramLong2);

	public abstract Set<String> zrevrangeByScore(String paramStr, double paramDouble1, double paramDouble2);

	public abstract Set<String> zrevrangeByScore(String paramStr, double paramDouble1, double paramDouble2,
			int paramInt1, int paramInt2);

	public abstract Set<String> zrevrangeByScore(String paramStr1, String paramStr2, String paramStr3);

	public abstract Set<String> zrevrangeByScore(String paramStr1, String paramStr2, String paramStr3, int paramInt1,
			int paramInt2);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramStr, double paramDouble1, double paramDouble2);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramStr, double paramDouble1, double paramDouble2,
			int paramInt1, int paramInt2);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramStr1, String paramStr2, String paramStr3);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramStr1, String paramStr2, String paramStr3,
			int paramInt1, int paramInt2);

	public abstract Set<Tuple> zrevrangeWithScores(String paramStr, long paramLong1, long paramLong2);

	public abstract Long zrevrank(String paramStr1, String paramStr2);

	public abstract Double zscore(String paramStr1, String paramStr2);
}
