package com.fuda.dc.lhtz.crawler.board;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.client.db.DbClient;
import com.fuda.dc.lhtz.crawler.stockcode.StockCodeImporter;
import com.fuda.dc.lhtz.crawler.vo.LonghuInfo;

public class LonghuBoardImporter {
	private static final Logger LOG = LoggerFactory.getLogger(StockCodeImporter.class);
	/**
	 * 最近一天龙虎榜url
	 */
	private static final String TODAY_URL = "http://data.eastmoney.com/stock/tradedetail.html";
	/**
	 * 历史龙虎榜url
	 */
	private static final String HISTORY_URL_PREFIX = "http://data.eastmoney.com/stock/lhb/date.html";
	
	private LonghuBoardDownloader downloader = new LonghuBoardDownloader();
	
	/**
	 * 下载最近一天龙虎榜上龙虎信息,存入数据库
	 */
	public void importer() {
		Date dt = new Date();   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    String date = sdf.format(dt); 
	    
		List<LonghuInfo> longhuInfos = downloader.download(TODAY_URL);
		infoToDb(longhuInfos, date);
	}
	
	/**
	 * 下载输入日期的龙虎榜上龙虎信息,存入数据库
	 * 
	 * @param date
	 */
	public void importer(String date) {
		String url = HISTORY_URL_PREFIX.replace("date", date);
		List<LonghuInfo> longhuInfos = downloader.download(url);
		infoToDb(longhuInfos, date);
	}
	
	/**
	 * 股票代码及名称入库操作
	 * 
	 * @param stockKeyPairs
	 */
	private void infoToDb(List<LonghuInfo> longhuInfos, String date) {
		Connection conn = DbClient.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			return;
		}
		
		for (LonghuInfo longhuInfo : longhuInfos) {
			String insertSql = genInsertSql(longhuInfo, date);
			// System.out.println(insertSql);
			try {
				stmt.executeUpdate(insertSql);
			} catch (SQLException e) {
				LOG.error("Insert data failed! data: {}", longhuInfo.toString());
				e.printStackTrace();
			}
		}
		
		try {
			stmt.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * 生成插入sql
	 * 
	 * @param longhuInfo
	 * @param date
	 * @return
	 */
	private String genInsertSql(LonghuInfo longhuInfo, String date) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into tb_longhu_board(stockCode, stockName, upDownRange, turnover, buyAmount, buyRatio, sellAmount, sellRatio, onBoardReason, date) values('");
		sb.append(longhuInfo.getStockCode());
		sb.append("','");
		sb.append(longhuInfo.getStockName());
		sb.append("','");
		sb.append(longhuInfo.getUpDownRange());
		sb.append("','");
		sb.append(longhuInfo.getTurnover());
		sb.append("','");
		sb.append(longhuInfo.getBuyAmount());
		sb.append("','");
		sb.append(longhuInfo.getBuyRatio());
		sb.append("','");
		sb.append(longhuInfo.getSellAmount());
		sb.append("','");
		sb.append(longhuInfo.getSellRatio());
		sb.append("','");
		sb.append(longhuInfo.getOnBoardReason());
		sb.append("','");
		sb.append(date);
	    sb.append("')");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		LonghuBoardImporter importer = new LonghuBoardImporter();
		importer.importer("2015-09-07");
	}
}
