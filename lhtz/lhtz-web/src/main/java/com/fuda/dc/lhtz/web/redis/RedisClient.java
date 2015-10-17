package com.fuda.dc.lhtz.web.redis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.web.util.PropertiesReader;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * redis client，使用连接池+分布式
 * 
 * @author kevin
 * @date 2015.09.10
 */
public class RedisClient {
	
	private static final Logger LOG = LoggerFactory.getLogger(RedisClient.class);
	
	private static final String REDIS_CONFIG_FILE = "redis";
	
	private static final String REDIS_ADDRS = "redis.servers";
	private static final String REDIS_MAX_IDLE_KEY = "redis.maxIdle";
	private static final String REDIS_MAX_TOTAL_KEY = "redis.maxTotal";
	private static final String REDIS_MAX_WAIT_MILLIS_KEY = "redis.maxWaitMillis";
	private static final int REDIS_MAX_IDLE_DEFAULT_VALUE = 10;
	private static final int REDIS_MAX_TOTAL_DEFAULT_VALUE = 30;
	private static final int REDIS_MAX_WAIT_MILLIS_DEFAULT_VALUE = 3000;
	
	private ShardedJedisPool pool;
	
	/**
	 * 获取单例的实例
	 * 
	 * @return
	 */
    public static RedisClient getInstance() {
        return RedisClientInstance.INSTANCE;
    }
    
    /**
     * 内部类持有实例，实现延迟加载 
     */
    private static class RedisClientInstance {
    	public static final RedisClient INSTANCE = new RedisClient();
    }

    private RedisClient() {
        try {
            connect();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
	
    /**
     * 连接redis集群
     */
    public void connect() {
    	// 生成多机连接信息列表
    	List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
    	String servers = PropertiesReader.getValue(REDIS_CONFIG_FILE, REDIS_ADDRS);
    	String[] addrs = servers.split(",");
    	for (String addr : addrs) {
    		String[] items = addr.split(":");
    		try {
    			shards.add(new JedisShardInfo(items[0], Integer.valueOf(items[1])));
    		} catch (Exception e) {
    			LOG.error("ip and port config error, please check! addr: {}", addr);
    		}
    	}
    	
    	// 生成连接池配置信息
    	JedisPoolConfig config = new JedisPoolConfig();
    	config.setMaxIdle(PropertiesReader.getIntValue(REDIS_CONFIG_FILE, REDIS_MAX_IDLE_KEY, REDIS_MAX_IDLE_DEFAULT_VALUE));
    	config.setMaxTotal(PropertiesReader.getIntValue(REDIS_CONFIG_FILE, REDIS_MAX_TOTAL_KEY, REDIS_MAX_TOTAL_DEFAULT_VALUE));
    	config.setMaxWaitMillis(PropertiesReader.getIntValue(REDIS_CONFIG_FILE, REDIS_MAX_WAIT_MILLIS_KEY, REDIS_MAX_WAIT_MILLIS_DEFAULT_VALUE));

    	// 在应用初始化的时候生成连接池
    	pool = new ShardedJedisPool(config, shards);
    }
    
    public byte[] get(byte[] key) {
    	if (pool == null) {
    		// throw new IOException("[RedisClient.java] connect to memcachedServers failed.");
    		return null;
    	}

    	ShardedJedis client = pool.getResource();
    	try {
    		return client.get(key);
    	} catch(Exception e) {
    		LOG.error("", e);
    	} finally {
			if (client != null) {
				client.close();
			}
		}
    	return null;
    }
    
    public String get(String key) {
    	if (pool == null) {
    		// throw new IOException("[RedisClient.java] connect to memcachedServers failed.");
    		return null;
    	}

    	ShardedJedis client = pool.getResource();
    	try {
    		return client.get(key);
    	} catch(Exception e) {
    		LOG.error("", e);
    	} finally {
			if (client != null) {
				client.close();
			}
		}
    	return null;
    }
    
    public String set(byte[] key, byte[] value) {
    	if (pool == null) {
    		return null;
    	}

    	ShardedJedis client = pool.getResource();
    	try {
    		return client.set(key, value);
    	} catch(Exception e) {
    		LOG.error("", e);
    	} finally {
			if (client != null) {
				client.close();
			}
		}
    	return null;
    }
    
    public String set(String key, String value) {
    	if (pool == null) {
    		return null;
    	}

    	ShardedJedis client = pool.getResource();
    	try {
    		return client.set(key, value);
    	} catch(Exception e) {
    		LOG.error("", e);
    	} finally {
			if (client != null) {
				client.close();
			}
		}
    	return null;
    }
    
    
    /**
     * 断开连接
     */
    public void close() {
    	if (pool != null) {
    		pool.destroy();
    	}
    }
    
    public static void main(String[] args) {
    	RedisClient.getInstance().set("name", "minmin");
    	String value = RedisClient.getInstance().get("name");
    	System.out.print("value: " + value);
    }

}
