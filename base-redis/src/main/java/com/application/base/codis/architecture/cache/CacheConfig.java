package com.application.base.codis.architecture.cache;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.application.base.codis.architecture.enumer.CacheEnvironment;
import com.application.base.codis.architecture.util.PropertiesLoader;

import redis.clients.jedis.JedisPoolConfig;

public final class CacheConfig {

	private static final String DEFAULT_FILE_PATH = "/JAVA_FILES/cacheclient-service/conf/app-codis.properties";
	private static final String DEFAULT_CLASS_PATH = "conf/app-codis.properties";
	private static final Integer DEFAULT_MAX_TOTAL = Integer.valueOf(1000);

	private static final Integer DEFAULT_MAX_IDLE = Integer.valueOf(1000);

	private static final Integer DEFAULT_MIN_IDLE = Integer.valueOf(0);

	private static final Integer DEFAULT_MAXWAIT_MILLIS = Integer.valueOf(20000);
	private static final String DEFAULT_ZKADDRESSANDPORT = "172.16.4.51:2181,172.16.4.52:2181,172.16.4.53:2181";
	private static final String DEFAULT_ZKPROXYDIR = "/zk/codis/db_nonobank/proxy";
	private static final Integer DEFAULT_ZKSESSIONTIMEOUTMS = Integer.valueOf(30000);

	private static final String DEFAULT_ENVIRONMENT = CacheEnvironment.DEFAULT.value();
	private static final boolean DEFAULT_DEBUG = false;
	private int maxTotal = DEFAULT_MAX_TOTAL.intValue();

	private int maxIdle = DEFAULT_MAX_IDLE.intValue();

	private int minIdle = DEFAULT_MIN_IDLE.intValue();

	private int maxWaitMillis = DEFAULT_MAXWAIT_MILLIS.intValue();

	private String zkAddressAndPort = "172.16.4.51:2181,172.16.4.52:2181,172.16.4.53:2181";

	private int zkSessionTimeOutMs = DEFAULT_ZKSESSIONTIMEOUTMS.intValue();

	private String zkProxyDir = "/zk/codis/db_nonobank/proxy";

	private boolean debug = false;

	private String envrionment = DEFAULT_ENVIRONMENT;

	private static Logger log = LoggerFactory.getLogger(CacheConfig.class);

	private boolean tryInit() {
		Properties pros = PropertiesLoader.load("/JAVA_FILES/cacheclient-service/conf/app-codis.properties", true);
		if (pros == null) {
			pros = PropertiesLoader.load("conf/app-codis.properties", false);
		}
		if (pros != null) {
			try {
				this.maxIdle = Integer.valueOf(pros.getProperty("codis.pool.maxIdle")).intValue();
				this.maxTotal = Integer.valueOf(pros.getProperty("codis.pool.maxTotal")).intValue();
				this.minIdle = Integer.valueOf(pros.getProperty("codis.pool.minIdle")).intValue();
				this.maxWaitMillis = Integer.valueOf(pros.getProperty("codis.pool.maxWaitMillis")).intValue();
				this.zkAddressAndPort = pros.getProperty("codis.zk.zkAddressAndPort");
				this.zkSessionTimeOutMs = Integer.valueOf(pros.getProperty("codis.zk.zkSessionTimeOutMs")).intValue();
				this.zkProxyDir = pros.getProperty("codis.zk.zkProxyDir");
				this.debug = Boolean.getBoolean(pros.getProperty("codis.debug"));
				this.envrionment = pros.getProperty("codis.envrionment");

				return true;
			}
			catch (Exception e) {
				e.printStackTrace();
				log.info("properties file load error! cann`t init cache client!|properties file path:{} or {}",
						"conf/app-codis.properties", "/JAVA_FILES/cacheclient-service/conf/app-codis.properties");
				log.info("trying to init cache client by default values!");
			}
		}
		return false;
	}

	protected JedisPoolConfig CacheConfig2JedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setTestWhileIdle(true);
		jedisPoolConfig.setMinEvictableIdleTimeMillis(60000L);
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000L);
		jedisPoolConfig.setNumTestsPerEvictionRun(-1);
		jedisPoolConfig.setMaxIdle(this.maxIdle);
		jedisPoolConfig.setMaxTotal(this.maxTotal);
		jedisPoolConfig.setMinIdle(this.minIdle);
		jedisPoolConfig.setMaxWaitMillis(this.maxWaitMillis);

		return jedisPoolConfig;
	}

	public int getMaxTotal() {
		return this.maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxIdle() {
		return this.maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return this.minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxWaitMillis() {
		return this.maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public String getZkAddressAndPort() {
		return this.zkAddressAndPort;
	}

	public void setZkAddressAndPort(String zkAddressAndPort) {
		this.zkAddressAndPort = zkAddressAndPort;
	}

	public int getZkSessionTimeOutMs() {
		return this.zkSessionTimeOutMs;
	}

	public void setZkSessionTimeOutMs(int zkSessionTimeOutMs) {
		this.zkSessionTimeOutMs = zkSessionTimeOutMs;
	}

	public String getZkProxyDir() {
		return this.zkProxyDir;
	}

	public void setZkProxyDir(String zkProxyDir) {
		this.zkProxyDir = zkProxyDir;
	}

	public boolean getDebug() {
		return this.debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getEnvrionment() {
		return this.envrionment;
	}

	public void setEnvrionment(String envrionment) {
		this.envrionment = envrionment;
	}
}
