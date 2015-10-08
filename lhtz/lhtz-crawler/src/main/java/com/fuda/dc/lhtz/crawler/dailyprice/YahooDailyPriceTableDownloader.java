package com.fuda.dc.lhtz.crawler.dailyprice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.client.http.HttpClient;
import com.fuda.dc.lhtz.crawler.util.StringUtil;


public class YahooDailyPriceTableDownloader {
	private static final Logger LOG = LoggerFactory.getLogger(YahooDailyPriceTableDownloader.class);
	
	private static final String URL_PREFIX = "http://table.finance.yahoo.com/table.csv?s=";
	
	private String downloadPath;
	
	public YahooDailyPriceTableDownloader() {
		this("D:\\my work\\stock\\javacode\\lhtz\\lhtz-crawler\\daily table");
	}
	
	public YahooDailyPriceTableDownloader(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	
	public void downloadAll(List<String> stockCodeList) {
		for (String stockCode : stockCodeList) {
			if (isNeedDownLoad(stockCode)) {
				downloadByStockCode(stockCode);
				System.out.println("download stock:" + stockCode);
			}
		}
	}
	
	/**
	 * 是否需要重新下载
	 * 
	 * @param stockCode
	 * @return
	 */
	private boolean isNeedDownLoad(String stockCode) {
		String fileName = StringUtil.implode(downloadPath, File.separator, stockCode, ".csv");
		File file = new File(fileName);
		if (file.exists() && file.length() > 0) {
			return false;
		}
		
		return true;
	}
	
	public void downloadByStockCode(String stockCode) {
		String urlSuffix = genUrlSuffix(stockCode);
		if ("".equals(urlSuffix)) {
			LOG.info("stock code is not start with 0 or 3 or 6, code: {}", stockCode);
			return;
		}
		String url = StringUtil.implode(URL_PREFIX, stockCode, ".", genUrlSuffix(stockCode)); 
		String page = HttpClient.doGet(url);
		writeToFile(page, stockCode);
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
	 * 将http下载流转化成Excel文件
	 * 
	 * @param text
	 * @param stockCode
	 */
	private void writeToFile(String text, String stockCode) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(StringUtil.implode(downloadPath, File.separator, stockCode, ".csv"));
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
