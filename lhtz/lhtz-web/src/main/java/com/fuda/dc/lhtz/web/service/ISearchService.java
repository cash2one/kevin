package com.fuda.dc.lhtz.web.service;

import java.util.List;

import com.fuda.dc.lhtz.web.vo.IcbIndexInfo;

public interface ISearchService {
    public List<IcbIndexInfo> search(String query);
}
