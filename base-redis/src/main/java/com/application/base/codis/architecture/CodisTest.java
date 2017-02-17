package com.application.base.codis.architecture;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.Jedis;

public class CodisTest {

	public static void main(String[] args) {
		AtomicInteger count = new AtomicInteger(0);
		Jedis jedis = new Jedis("127.0.0.1", 6579);
		Set<String> sets = jedis.keys("*.lock.cache.key");
		for (String key : sets) {
			if (Integer.valueOf(jedis.get(key)).intValue() >= 5) {
				count.addAndGet(1);
			}
		}
		System.out.println(count.get());
	}
}
