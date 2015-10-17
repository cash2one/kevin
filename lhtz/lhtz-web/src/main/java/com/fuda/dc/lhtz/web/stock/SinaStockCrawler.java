package com.fuda.dc.lhtz.web.stock;

import java.util.HashMap;
import java.util.Map;

import com.fuda.dc.lhtz.web.util.HttpClient;

public class SinaStockCrawler implements IStockCrawler {

    public static final String URL_PREFIX = "http://hq.sinajs.cn/list=";

    @Override
    public Map<String, String> getRealStockData(String code) {
        String url = URL_PREFIX + code;
        String data = HttpClient.doGet(url);
        if (data == null || data.length() == 0) {
            return null;
        } else {
            String[] items = data.split(",");
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", items[0]);
            map.put("open", items[1]);
            map.put("close", items[2]);
            map.put("current", items[3]);
            map.put("high", items[4]);
            map.put("low", items[5]);
            map.put("quantity", items[8]);
            map.put("exchange", items[9]);
            map.put("date", items[30]);
            map.put("time", items[31]);
            return map;
        }
    }
}