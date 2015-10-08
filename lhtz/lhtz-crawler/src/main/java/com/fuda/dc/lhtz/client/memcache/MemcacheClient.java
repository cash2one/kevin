package com.fuda.dc.lhtz.client.memcache;

import com.fuda.dc.lhtz.crawler.util.PropertiesReader;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * java memcached client
 * 
 * @author kevin
 * @date 2015.09.10
 */
public class MemcacheClient {  
	
    private static final String MEM_CONFIG_FILE = "memcache.properties"; // key前缀
    private static final String MEM_SERVERS_KEY = "memcache.servers";
    private static final String MEM_INIT_CONN_KEY = "memcache.initConn";
    private static final int MEM_INIT_CONN_DEFAULT_VALUE = 3;
    
    private static MemCachedClient cachedClient;  
  
    private MemcacheClient() {  
        init();
    }  
    
    /**
     * 初始化memcache client线程池配置
     */
    private void init() {
    	cachedClient = new MemCachedClient();  
        //获取连接池实例  
        SockIOPool pool = SockIOPool.getInstance();  
  
        //设置缓存服务器地址，可以设置多个实现分布式缓存  
        String servers = PropertiesReader.getValue(MEM_CONFIG_FILE, MEM_SERVERS_KEY);
        pool.setServers(new String[]{servers});  
          
        //设置初始连接5  
        pool.setInitConn(PropertiesReader.getIntValue(MEM_CONFIG_FILE, MEM_INIT_CONN_KEY, MEM_INIT_CONN_DEFAULT_VALUE));  
        //设置最小连接5  
        pool.setMinConn(5);  
        //设置最大连接250  
        pool.setMaxConn(250);  
        //设置每个连接最大空闲时间3个小时  
        pool.setMaxIdle(1000 * 60 * 60 * 3);  
  
        pool.setMaintSleep(30);  
  
        pool.setNagle(false);  
        pool.setSocketTO(3000);  
        pool.setSocketConnectTO(0);  
        pool.initialize();  
    }
    
    /** 
     * 获取缓存管理器唯一实例 
     */  
    public static MemcacheClient getInstance() {  
        return MemcachedClientInstance.INSTANCE;  
    } 
    
    private static class MemcachedClientInstance {
    	public static final MemcacheClient INSTANCE = new MemcacheClient();
    }
  
    public void add(String key, Object value) {  
        cachedClient.set(key, value);  
    }  
  
    public void add(String key, Object value, int milliseconds) {  
        cachedClient.set(key, value, milliseconds);  
    }  
  
    public void remove(String key) {  
        cachedClient.delete(key);  
    }  
  
    public void update(String key, Object value, int milliseconds) {  
        cachedClient.replace(key, value, milliseconds);  
    }  
  
    public void update(String key, Object value) {  
        cachedClient.replace(key, value);  
    }  
      
    public Object get(String key) {  
        return cachedClient.get(key);  
    }  
  
}  
