package com.acme.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.TransportMode;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import ch.qos.logback.classic.Logger;

@Configuration
@EnableCaching
public class CacheConfig {
	
	private Logger logger = (Logger) LoggerFactory.getLogger(CacheConfig.class);
	
	@Autowired
	private CacheManagerProperties properties;
	
	@Bean
	public RedissonClient redisson() {
		
		logger.info("redis type : "+ properties.getRedis().getType());
		
		Config cfg = new Config();
		if ("standalone".equals(properties.getRedis().getType())) {
			cfg.setTransportMode(TransportMode.NIO)
					.useSingleServer()
					.setAddress("redis://" + properties.getRedis().getHost() + ":" + properties.getRedis().getPort())
					.setPingConnectionInterval(1000)
					.setConnectTimeout(30000)
					.setKeepAlive(true)
					.setTcpNoDelay(true)
					.setRetryInterval(3000)
					.setRetryAttempts(1000)
					.setClientName("ocf-cache");
					//.setPassword(properties.getRedis().getPassword());
		} else if ("sentinel".equals(properties.getRedis().getType())) {
			cfg.setTransportMode(TransportMode.NIO)
			.useSentinelServers()
			.setMasterName(properties.getRedis().getMasterName())
			.addSentinelAddress(properties.getRedis().getSentinelAddresses().toArray(new String[properties.getRedis().getSentinelAddresses().size()]))
			.setClientName("ocf-cache")
			.setPingConnectionInterval(10000)
			.setReadMode(ReadMode.SLAVE)
			.setKeepAlive(true)
			.setTcpNoDelay(true)
			.setRetryInterval(30000)
			.setRetryAttempts(10000);
			//.setPassword(properties.getRedis().getPassword());
		}

		cfg.setCodec(new SerializationCodec());
		
		return Redisson.create(cfg);
	}
	

	@Bean
	@Primary
	public CacheManager redissonSpringCacheManager() {
		return new RedissonSpringCacheManager(redisson(), "classpath:/redis-cache-conf.json");
	}

}