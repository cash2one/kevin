package com.fuda.dc.lhtz.web.search.as.rank;

import java.util.List;

import com.fuda.dc.lhtz.web.vo.IcbIndexInfo;

public class SpecialRank implements IAdvanceRank {

    @Override
    public List<IcbIndexInfo> rank(List<IcbIndexInfo> icbIndexInfos) {
        //TODO special rank
        return icbIndexInfos;
    }

}
