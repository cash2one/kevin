package com.fuda.dc.lhtz.web.stock;

import java.util.Map;

public interface IStockCrawler {
    public Map<String, String> getRealStockData(String code);
}
