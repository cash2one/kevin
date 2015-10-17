package com.fuda.dc.lhtz.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fuda.dc.lhtz.web.search.client.ElasticSearchClient;
import com.fuda.dc.lhtz.web.search.pool.ElasticSearchPool;
import com.fuda.dc.lhtz.web.service.ISearchService;
import com.fuda.dc.lhtz.web.vo.IcbIndexInfo;

@Service
public class SearchService implements ISearchService {

    @Autowired
    private ElasticSearchPool pool;

    @Override
    public List<IcbIndexInfo> search(String query) {
        List<IcbIndexInfo> icbIndexInfos = new ArrayList<IcbIndexInfo>();
        ElasticSearchClient client = null;

        try {
            client = pool.borrowObject();
            List<String> searchResults = client.search(query);
            for (String result : searchResults) {
                IcbIndexInfo icbIndexInfo = (IcbIndexInfo) JSON.parseObject(result, IcbIndexInfo.class);
                System.out.println(result + ":" + icbIndexInfo.getRegNum());
                if (icbIndexInfo != null) {
                    icbIndexInfos.add(icbIndexInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.returnObject(client);
        }
        return icbIndexInfos;
    }

}
