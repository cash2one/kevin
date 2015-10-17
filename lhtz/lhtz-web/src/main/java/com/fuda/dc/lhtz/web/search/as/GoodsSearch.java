package com.fuda.dc.lhtz.web.search.as;

import java.util.List;

import com.fuda.dc.lhtz.web.search.as.rank.BasicRank;
import com.fuda.dc.lhtz.web.search.as.rank.IAdvanceRank;
import com.fuda.dc.lhtz.web.vo.IcbIndexInfo;


public class GoodsSearch extends AdvanceSearch {

    private final IAdvanceRank rank = new BasicRank();

    @Override
    public List<IcbIndexInfo> rank(List<IcbIndexInfo> icbIndexInfos) {
        return rank.rank(icbIndexInfos);
    }

}