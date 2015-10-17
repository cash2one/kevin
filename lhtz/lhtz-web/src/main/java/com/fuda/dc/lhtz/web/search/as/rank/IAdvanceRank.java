package com.fuda.dc.lhtz.web.search.as.rank;

import java.util.List;

import com.fuda.dc.lhtz.web.vo.IcbIndexInfo;

public interface IAdvanceRank {
    public List<IcbIndexInfo> rank(List<IcbIndexInfo> icbIndexInfos);
}
