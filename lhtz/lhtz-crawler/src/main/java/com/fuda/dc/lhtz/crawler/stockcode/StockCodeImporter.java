package com.fuda.dc.lhtz.crawler.stockcode;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.client.db.DbClient;
import com.fuda.dc.lhtz.crawler.util.KeyPair;

/**
 * 从网页上获取股票代码、名称，导入数据库
 * 
 * @author liukai
 * @date 2015.09.03
 */
public class StockCodeImporter {
	private static final Logger LOG = LoggerFactory.getLogger(StockCodeImporter.class);
	
	private static final String CODE_URL = "http://quote.eastmoney.com/stocklist.html#sh";
	
	private StockCodeDownloader downloader = new StockCodeDownloader();
	
	/**
	 * 下载股票代码及名称,存入数据库
	 */
	public void importer() {
		List<KeyPair<String, String>> stockKeyPairs = downloader.download(CODE_URL);
		infoToDb(stockKeyPairs);
	}
	
	/**
	 * 股票代码及名称入库操作
	 * 
	 * @param stockKeyPairs
	 */
	private void infoToDb(List<KeyPair<String, String>> stockKeyPairs) {
		Connection conn = DbClient.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			return;
		}
		
		for (KeyPair<String, String> keyPair : stockKeyPairs) {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into td_name_code_map(stockCode, stockName) values('");
			sql.append(keyPair.getValue());
			sql.append("', '");
			sql.append(keyPair.getKey());
			sql.append("')");
			// System.out.println(sql.toString());
			
			try {
				stmt.executeUpdate(sql.toString());
			} catch (SQLException e) {
				LOG.error("Insert data failed! data: {}", keyPair.toString());
				e.printStackTrace();
			}
		}
		
		try {
			stmt.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		StockCodeImporter importer = new StockCodeImporter();
		importer.importer();
	}
	
}
