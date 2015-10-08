package com.fuda.dc.lhtz.client.memcache;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.crawler.util.PropertiesReader;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * spy memcached client 
 * 
 * @author liukai
 * @date 2015.09.10
 * @version 1.0
 */
public class SpymemcachedClient {

    private static final Logger LOG = LoggerFactory.getLogger(SpymemcachedClient.class);

    /**
     * memcached配置文件名
     */
    private static final String MEM_CONFIG_FILE = "memcache.properties"; // key前缀
    private static final String MEM_EXPIRE_KEY = "memcache.expire";
    private static final String MEM_SERVERS_KEY = "memcache.servers";
    private static final int MEM_EXPIRE_DEFAULT_VALUE = 3;
    
    /**
     * 过期时间，单位为秒，默认为3秒
     */
    public static final int EXPIRE = PropertiesReader.getIntValue(MEM_CONFIG_FILE, MEM_EXPIRE_KEY, MEM_EXPIRE_DEFAULT_VALUE); 
    private MemcachedClient memClient;
    private final String memcachedServers;


    public static SpymemcachedClient getInstance() {
        return SpymemcachedClientInstance.INSTANCE;
    }
    
    private static class SpymemcachedClientInstance {
    	public static final SpymemcachedClient INSTANCE = new SpymemcachedClient();
    }

    private SpymemcachedClient() {
        // load from configfile, 格式如下ip1:port1,ip2:port2
        memcachedServers = PropertiesReader.getValue(MEM_CONFIG_FILE, MEM_SERVERS_KEY); 

        try {
            connect();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 建立memcached连接
     * 
     * @throws IOException
     */
    private void connect() throws IOException {
        if (StringUtils.isEmpty(memcachedServers) || memClient != null) {
            LOG.error("[SpymemcachedManager] memcachedServers={} is null");
            return;
        }

        String[] servers = memcachedServers.split(",");
        StringBuilder buf = new StringBuilder();
        for (String server : servers) {
            buf.append(server).append(" ");
        }

        memClient = new MemcachedClient(AddrUtil.getAddresses(memcachedServers));
        LOG.info("[SpymemcachedManager] connect to memcachedServers={}", memcachedServers);
    }

    /**
     * 将key的value保存到memcached中
     * 
     * @param key       数据的key
     * @param value     数据的value 
     * @param expire    数据的过期时间, seconds
     * @return
     * @throws IOException 
     */
    public boolean set(String key, Object value, int expire) throws IOException {
        if (memClient == null) {
            throw new IOException("[SpymemcachedManager] connect to memcachedServers failed.");
        }
        OperationFuture<Boolean> result = memClient.set(key, expire, value);
        return result.isDone();
    }

    /**
     * 按照key从memcached中获取value
     * 
     * @param key   数据的key
     * @return      数据的value
     * @throws IOException 
     */
    public Object get(String key) throws IOException {
        if (memClient == null) {
            throw new IOException("[SpymemcachedManager] connect to memcachedServers failed.");
        }
        return memClient.get(key);
    }

    /**
     * 批量按照key从memcached中获取value
     * 
     * @param key   数据的key
     * @return      数据的value
     * @throws IOException 
     */
    public Map<String, Object> mget(String... keys) throws IOException {
        if (memClient == null) {
            throw new IOException("[SpymemcachedManager] connect to memcachedServers failed.");
        }
        return memClient.getBulk(keys);
    }

    /**
     * 批量按照key从memcached中获取value
     * 
     * @param key   数据的key
     * @return      数据的value
     * @throws IOException 
     */
    public Map<String, Object> mget(List<String> keys) throws IOException {
        if (memClient == null) {
            throw new IOException("[SpymemcachedManager] connect to memcachedServers failed.");
        }
        return memClient.getBulk(keys);
    }

    /**
     * 递减指定key的value值
     * 
     * @param key   数据的key
     * @param by    每次递减的值
     * @param defaultValue  数据的初始value
     * @param expire    数据的过期时间, seconds
     * @return      递减后的结果
     * @throws IOException 
     */
    public long decr(String key, int by, long defaultValue, int expire) throws IOException {
        if (memClient == null) {
            throw new IOException("[SpymemcachedManager] connect to memcachedServers failed.");
        }
        return memClient.decr(key, by, defaultValue, expire);
    }

    /**
     * 递减指定key的value值
     * 
     * @param key   数据的key
     * @param by    每次递减的值
     * @return      递减后的结果
     * @throws IOException 
     */
    public long decr(String key, int by) throws IOException {
        if (memClient == null) {
            throw new IOException("[SpymemcachedManager] connect to memcachedServers failed.");
        }
        return memClient.decr(key, by);
    }

    /**
     * 关闭memcached连接
     */
    public void shutdown() {
        LOG.info("[SpymemcachedManager] shutdown the connected, memcachedServers={}", memcachedServers);
        if (memClient == null) {
            return;
        }
        memClient.shutdown();
    }

}
