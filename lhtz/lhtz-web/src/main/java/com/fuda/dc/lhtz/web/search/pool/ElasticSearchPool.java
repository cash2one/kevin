package com.fuda.dc.lhtz.web.search.pool;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.fuda.dc.lhtz.web.search.client.ElasticSearchClient;

public class ElasticSearchPool {
    private static final int MAX_POOL_SIZE = 10;
    private static final int DEFAULT_POOL_SIZE = 5;
    private final ObjectPool<ElasticSearchClient> pool;

    public ElasticSearchPool() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(MAX_POOL_SIZE);
        config.setBlockWhenExhausted(true);
        this.pool = new GenericObjectPool<ElasticSearchClient>(new ElasticSearchObjectFactory(), config);

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                pool.addObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ElasticSearchClient borrowObject() throws Exception {
        return pool.borrowObject();
    }

    public void returnObject(ElasticSearchClient client) {
        try {
            pool.returnObject(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getActiveNum() {
        return pool.getNumActive();
    }

    public void close() {
        pool.close();
    }
}
