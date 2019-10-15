package com.acme.redis.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="ocf.cache")
public class CacheManagerProperties {
	
	RedisConfiguration redis = new RedisConfiguration();

	public RedisConfiguration getRedis() {
		return redis;
	}

	public void setRedis(RedisConfiguration redis) {
		this.redis = redis;
	}
	
	@Configuration
	@ConfigurationProperties(prefix="ocf.cache.redis")
	public static class RedisConfiguration {

		private String type = "standalone";

		private String host;

		private int port;

		private String password = null;
		
		private String masterName= "mymaster";

		private List<String> sentinelAddresses = new ArrayList<>();
		
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getMasterName() {
			return masterName;
		}
		public void setMasterName(String masterName) {
			this.masterName = masterName;
		}
		public List<String> getSentinelAddresses() {
			return sentinelAddresses;
		}
		public void setSentinelAddresses(List<String> sentinelAddresses) {
			this.sentinelAddresses = sentinelAddresses;
		}
	}
}
