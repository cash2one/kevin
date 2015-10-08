package com.fuda.dc.lhtz.crawler.basicinfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.client.http.HttpClient;
import com.fuda.dc.lhtz.crawler.util.StringUtil;
import com.fuda.dc.lhtz.crawler.vo.BasicInfo;
import com.fuda.dc.lhtz.crawler.vo.LonghuInfo;


public class ThsBasicInfoDownloader {
	private static final Logger LOG = LoggerFactory.getLogger(ThsBasicInfoDownloader.class);
	
	private static final String URL_PREFIX = "http://f10.eastmoney.com/f10_v2/FinanceAnalysis.aspx?code=sz300059";
	
	private static Set<String> keySet = new HashSet<String>();
	
	static {
		keySet.add("总股本");
		keySet.add("收益");
		keySet.add("净资产");
		keySet.add("市净率");
		keySet.add("净利润");
		keySet.add("总值");
		keySet.add("流通股");
		keySet.add("流值");
	}
	
	public ThsBasicInfoDownloader() {
	}
	
	/**
	 * 是否需要重新下载
	 * 
	 * @param stockCode
	 * @return
	 */
	private boolean isNeedDownLoad(String stockCode) {
		return true;
	}
	
	public BasicInfo download(String stockCode) {
		String urlSuffix = genUrlSuffix(stockCode);
		if ("".equals(urlSuffix)) {
			LOG.info("stock code is not start with 0 or 3 or 6, code: {}", stockCode);
			return null;
		}
		
		String url = URL_PREFIX.replace("stockCode", stockCode);
		String page = HttpClient.doGet(url, "GBK");
		writeToFile("D:/local/test.html", page);
		BasicInfo basicInfo = parse(page);
		return basicInfo;
	}
	
	/**
	 * 根据股票代码第一位生成请求url后缀
	 * 
	 * @param code
	 * @return
	 */
	private String genUrlSuffix(String code) {
		if (code == null || "".equals(code)) {
			return "";
		}
		
		char firstCode = code.charAt(0);
		switch (firstCode) {
		case '0':
			return "sz";
		case '3':
			return "sz";
		case '6':
			return "ss";
		default:
			return "";
		}
	}
	
	/**
	 * 解析网页中的股票基本信息
	 * 
	 * @param page
	 * @return
	 */
	private BasicInfo parse(String page) {
		BasicInfo basicInfo = new BasicInfo();
		Document document = Jsoup.parse(page);
		Elements uls = document.getElementsByClass("new_trading fl");
		if (uls.size() != 1) {
			return null;
		}
		
		Elements lis = uls.get(0).getElementsByTag("li");
		Element li3 = lis.get(3);
		Elements spans = li3.getElementsByTag("span");
		String pb = spans.get(2).text().toString();
		basicInfo.setPb(StringUtil.strToFloat(pb));
		Element li4 = lis.get(3);
		spans = li4.getElementsByTag("span");
		String pe = spans.get(2).text().toString();
		basicInfo.setPe(StringUtil.strToFloat(pe));
		
		return basicInfo;
	}
	
	private void writeToFile(String fileName, String text) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			fw.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
