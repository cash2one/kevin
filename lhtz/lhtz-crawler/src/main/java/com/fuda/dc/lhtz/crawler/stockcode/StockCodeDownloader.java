package com.fuda.dc.lhtz.crawler.stockcode;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.multi.MultiButtonUI;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fuda.dc.lhtz.client.http.HttpClient;
import com.fuda.dc.lhtz.crawler.util.KeyPair;

/**
 * 下载并解析网页中的股票代码和名称
 * 
 * @author kevin
 *
 */
public class StockCodeDownloader {
	
	/**
	 * 下载股票代码、名称网页
	 * 
	 * @param url 下载地址
	 * @return
	 */
	public List<KeyPair<String, String>> download(String url) {
		String page = HttpClient.doGet(url);
		List<KeyPair<String, String>> stockKeyPairs = parse(page);
		return stockKeyPairs;
	}
	
	/**
	 * 解析网页获取股票代码、名称k-v对列表
	 * 
	 * @param page 股票代码、名称网页
	 * @return
	 */
	private List<KeyPair<String, String>> parse(String page) {
		List<KeyPair<String, String>> stockKeyPairs = new ArrayList<KeyPair<String,String>>();
		Document document = Jsoup.parse(page);
		Elements elements = document.getElementById("quotesearch").getElementsByTag("ul");
		for (Element element : elements) {
			Elements lis = element.getElementsByTag("li");
            for (Element li : lis) {
            	Elements as = li.getElementsByTag("a");
            	Element a = as.get(as.size() - 1);
                String href = a.attr("href");
                if (href == null || "".equals(href)) {
                	continue;
                }
                String text = a.text().trim();
                KeyPair<String, String> keyPair = genKeyPair(text);
                if (filter(keyPair)) { 
                	stockKeyPairs.add(keyPair);
                }
            }
		}
		
		return stockKeyPairs;
	}
	
	/**
	 * 股票代码、名称字符串解析成k-v对
	 * 
	 * @param keyPairStr
	 * @return
	 */
	private KeyPair<String, String> genKeyPair(String keyPairStr) {
		String[] items = keyPairStr.split("\\(");
		if (items.length != 2) {
			return null;
		}
		
		if (!items[1].endsWith(")")) {
			return null;
		}
		
		String value = items[1].substring(0, items[1].length() - 1);
		return new KeyPair<String, String>(items[0], value);
	}
	
	/**
	 * 过滤出错误或不要的股票代码、名称
	 * 
	 * @param keyPair
	 * @return true-正确；false-错误
	 */
	private boolean filter(KeyPair<String, String> keyPair) {
		String stockCode = keyPair.getValue();
		if (StringUtils.isEmpty(stockCode) || stockCode.length() != 6) {
			return false;
		}
		
		for (int i = 0; i < 6; i++) {
			char number = stockCode.charAt(i);
			if (number > '9' || number < '0') {
				return false;
			}
		}
		
		if (!stockCode.startsWith("0") && !stockCode.startsWith("3") && !stockCode.startsWith("6") ) {
			return false;
		}
		
		return true;
	}

}
