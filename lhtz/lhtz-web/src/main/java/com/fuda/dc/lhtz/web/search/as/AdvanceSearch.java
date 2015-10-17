package com.fuda.dc.lhtz.web.search.as;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.mapper.ObjectMappers;
import org.elasticsearch.index.mapper.object.ObjectMapper;

import com.alibaba.fastjson.JSON;
import com.fuda.dc.lhtz.web.search.client.ElasticSearchClient;
import com.fuda.dc.lhtz.web.search.pool.ElasticSearchPool;
import com.fuda.dc.lhtz.web.vo.IcbIndexInfo;

public abstract class AdvanceSearch {

    private final ElasticSearchPool pool = new ElasticSearchPool();

    public List<IcbIndexInfo> search(String query) {
        List<IcbIndexInfo> icbIndexInfos = new ArrayList<IcbIndexInfo>();
        ElasticSearchClient client = null;
        try {
            client = pool.borrowObject();
            List<String> searchResults = client.search(query);
            for (String result : searchResults) {
                IcbIndexInfo icbIndexInfo = (IcbIndexInfo) JSON.parseObject(result, IcbIndexInfo.class);
                if (icbIndexInfo != null) {
                    icbIndexInfos.add(icbIndexInfo);
                }
            }

            if (icbIndexInfos.size() >= 2) {
                icbIndexInfos = rank(icbIndexInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnObject(client);
        }
        return icbIndexInfos;
    }

    protected abstract List<IcbIndexInfo> rank(List<IcbIndexInfo> icbIndexInfos);

}
