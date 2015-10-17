package com.fuda.dc.lhtz.web.search.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.fuda.dc.lhtz.web.search.client.ElasticSearchClient;

public class ElasticSearchObjectFactory extends BasePooledObjectFactory<ElasticSearchClient> {

    @Override
    public ElasticSearchClient create() throws Exception {
        return new ElasticSearchClient();
    }

    @Override
    public PooledObject<ElasticSearchClient> wrap(ElasticSearchClient client) {
        return new DefaultPooledObject<ElasticSearchClient>(client);
    }

}
