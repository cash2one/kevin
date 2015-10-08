package com.fuda.dc.lhtz.crawler.board;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fuda.dc.lhtz.client.http.HttpClient;
import com.fuda.dc.lhtz.crawler.util.StringUtil;
import com.fuda.dc.lhtz.crawler.vo.LonghuInfo;

public class LonghuBoardDownloader {
	
	/**
	 * 下载虎龙信息榜上的龙虎信息
	 * 
	 * @param url
	 * @return
	 */
	public List<LonghuInfo> download(String url) {
		String page = HttpClient.doGet(url);
		List<LonghuInfo> longhuInfos = parse(page);
		return longhuInfos;
	}
	
	/**
	 * 解析网页中龙虎信息榜上的龙虎信息
	 * 
	 * @param page
	 * @return
	 */
	public List<LonghuInfo> parse(String page) {
		List<LonghuInfo> longhuInfos = new ArrayList<LonghuInfo>();
		Document document = Jsoup.parse(page);
		Elements tbodys = document.getElementById("dt_1").getElementsByTag("tbody");
		Elements trs = tbodys.get(0).getElementsByTag("tr");
		for (Element tr : trs) {
			Elements tds = tr.getElementsByTag("td");
			if (tds.size() != 11) {
				continue;
			}

            LonghuInfo longhuInfo = genLonghuInfo(tds);
            longhuInfos.add(longhuInfo);
		}
		
		return longhuInfos;
	}

	/**
	 * 生成龙虎信息类
	 * 
	 * @param tds
	 * @return
	 */
	private LonghuInfo genLonghuInfo(Elements tds) {
        LonghuInfo longhuInfo = new LonghuInfo();
        String stockCode = tds.get(1).text().toString();
        longhuInfo.setStockCode(stockCode);
        String stockName = tds.get(2).text().toString();
        longhuInfo.setStockName(stockName);
        longhuInfo.setUpDownRange(StringUtil.strToFloat(tds.get(4).text().toString(), true));
        longhuInfo.setTurnover(StringUtil.strToFloat(tds.get(5).text().toString()));
        longhuInfo.setBuyAmount(StringUtil.strToFloat(tds.get(6).text().toString()));
        longhuInfo.setBuyRatio(StringUtil.strToFloat(tds.get(7).text().toString(), true));
        longhuInfo.setSellAmount((StringUtil.strToFloat(tds.get(8).text().toString())));
        longhuInfo.setSellRatio((StringUtil.strToFloat(tds.get(9).text().toString(), true)));
        longhuInfo.setOnBoardReason(tds.get(10).text().toString());
		return longhuInfo;
	}
	
}
