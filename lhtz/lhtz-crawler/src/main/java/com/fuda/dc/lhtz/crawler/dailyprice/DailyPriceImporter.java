package com.fuda.dc.lhtz.crawler.dailyprice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.client.db.DbClient;
import com.fuda.dc.lhtz.crawler.stockcode.StockCodeImporter;
import com.fuda.dc.lhtz.crawler.util.StringUtil;
import com.fuda.dc.lhtz.crawler.vo.DailyPrice;

public class DailyPriceImporter {
	private static final Logger LOG = LoggerFactory.getLogger(StockCodeImporter.class);
	
	private static final String DOWNLOAD_PATH = "D:\\my work\\stock\\javacode\\lhtz\\lhtz-crawler\\daily table";
	
	private static final int BATCH_INSERT_NUM = 1024;
	
	private YahooDailyPriceTableDownloader downloader = new YahooDailyPriceTableDownloader(DOWNLOAD_PATH);
	
	/**
	 * 下载每日股票信息,存入数据库
	 */
	public void importer() {
		List<String> stockCodeList = getAllStockCode();
		downloader.downloadAll(stockCodeList);
		infoToDb(DOWNLOAD_PATH);
	}
	
	/**
	 * 获取所有股票代码
	 * 
	 * @return
	 */
	public List<String> getAllStockCode() {
		List<String> stockCodeList = new ArrayList<String>();
		String sql = "select stockCode from td_name_code_map";
		
		Connection conn = DbClient.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				stockCodeList.add(rs.getString("stockCode"));
				System.out.println(rs.getString("stockCode"));
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOG.error(e.getMessage());
				}
			}
		}
		
		return stockCodeList;
	}
	
	/**
	 * 股票代码及名称入库操作
	 * 
	 * @param stockKeyPairs
	 */
	private void infoToDb(String loadPath) {
		File dir = new File(loadPath);
		for (String fileName : dir.list()) {
			oneTableToDb(loadPath, fileName, true);
			System.out.println("finish one table to db, table name: " + fileName);
		}
	}

	/**
	 * 将下载的一只股票所有天交易信息表(excel)转存入数据库
	 * 
	 * @param loadPath
	 * @param fileName
	 */
	private void oneTableToDb(String loadPath, String fileName, boolean isBatchInsert) {
		List<DailyPrice> dailyPrices = loadDailyPriceFile(loadPath, fileName);
		
		if (isBatchInsert) {
			List<DailyPrice> dailyPriceList = new ArrayList<DailyPrice>(BATCH_INSERT_NUM);
			int batchSize = dailyPrices.size() / BATCH_INSERT_NUM;
			for (int i = 0; i < batchSize; i++) {
				int start = i * BATCH_INSERT_NUM;
				int end = (i + 1) * BATCH_INSERT_NUM;
				for (int j = start; j < end; j++) {
					dailyPriceList.add(dailyPrices.get(j));
				}
				batchInfoToDb(dailyPriceList);
				dailyPriceList.clear();
			}
			
			int lastStart = batchSize * BATCH_INSERT_NUM;
			for (int i = lastStart; i < dailyPrices.size(); i++) {
				dailyPriceList.add(dailyPrices.get(i));
			}
			batchInfoToDb(dailyPriceList);
		} else {
			for (DailyPrice dailyPrice : dailyPrices) {
				oneInfoToDb(dailyPrice);
			}
		}
		 
	}
	
	/**
	 * 读取csv文件中一只股票的每日股票交易信息
	 * 
	 * @param fileName
	 * @return
	 */
	private List<DailyPrice> loadDailyPriceFile(String loadPath, String fileName) {
		List<DailyPrice> dailyPrices = new ArrayList<DailyPrice>();
		String stockCode = getStockCodeFromFileName(fileName);
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(StringUtil.implode(loadPath, File.separator, fileName)));
			br.readLine();
			
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] items = line.split(",");
				if (items.length != 7) {
					LOG.warn("daily price data format is wrong, format: {}", line);
					continue;
				}
				DailyPrice dailyPrice = new DailyPrice();
				dailyPrice.setStockCode(stockCode);
				dailyPrice.setDate(items[0]);
				dailyPrice.setOpenPrice(Float.valueOf(items[1]));
				dailyPrice.setHighPrice(Float.valueOf(items[2]));
				dailyPrice.setLowPrice(Float.valueOf(items[3]));
				dailyPrice.setClosePrice(Float.valueOf(items[4]));
				dailyPrice.setVolume(Long.valueOf(items[5]));
				dailyPrice.setAdjustClosePrice(Float.valueOf(items[6]));
				
				dailyPrices.add(dailyPrice);
			}
		} catch (FileNotFoundException e) {
			LOG.error("file not found! file: {}", fileName);
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
				}
			}
		}

		return dailyPrices;
	}
	
	/**
	 * 从csv文件名中获取股票代码
	 * 
	 * @param fileName
	 * @return
	 */
	private String getStockCodeFromFileName(String fileName) {
		if (!fileName.endsWith(".csv")) {
			return "";
		}
		String[] items = fileName.split("\\.");
		return items[0];
	}
	
	/**
	 * 将股票一天交易信息存入数据库，如果数据库有该条记录则不插入
	 */
	private void oneInfoToDb(DailyPrice dailyPrice) {
		String selectSql = genSelectSql(dailyPrice.getStockCode(), dailyPrice.getDate().toString());
		String insertSql = genInsertSql(dailyPrice);
		
		Connection conn = DbClient.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSql.toString());
			if (rs.next()) {
				return;
			}
			stmt.executeUpdate(insertSql.toString());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOG.error(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * 将股票多天交易信息存入数据库，如果数据库有该条记录则不插入
	 */
	private void batchInfoToDb(List<DailyPrice> dailyPrices) {
		String batchInsertSql = genBatchInsertSql(dailyPrices);
		
		Connection conn = DbClient.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(batchInsertSql.toString());
			System.out.println(batchInsertSql.toString());
		} catch (SQLException e) {
			LOG.error("batch insert failed!, sql: {}", batchInsertSql.toString());
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOG.error(e.getMessage());
				}
			}
		}
	}
	
	
	private String genSelectSql(String stockCode, String date) {
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select count(*) as cnt from tb_price_daily where stockCode ='");
		selectSql.append(stockCode);
		selectSql.append("' and date='");
		selectSql.append(date);
		selectSql.append("'");
		return selectSql.toString();
	}
	
	private String genInsertSql(DailyPrice dailyPrice) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into tb_daily_price(stockCode, openPrice, highPrice, lowPrice, closePrice, volume, adjustClosePrice, date) values('");
		insertSql.append(dailyPrice.getStockCode());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getOpenPrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getHighPrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getLowPrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getClosePrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getVolume());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getAdjustClosePrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getDate());
		insertSql.append("')");
		
		return insertSql.toString();
	}

	
	private String genBatchInsertSql(List<DailyPrice> dailyPrices) {
		if (dailyPrices == null || dailyPrices.size() == 0) {
			return "";
		}
		
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into tb_daily_price(stockCode, openPrice, highPrice, lowPrice, closePrice, volume, adjustClosePrice, date) values('");
		int length = dailyPrices.size() - 1;
		for(int i = 0; i < length; i++) {
			DailyPrice dailyPrice = dailyPrices.get(i);
			insertSql.append(dailyPrice.getStockCode());
			insertSql.append("', '");
			insertSql.append(dailyPrice.getOpenPrice());
			insertSql.append("', '");
			insertSql.append(dailyPrice.getHighPrice());
			insertSql.append("', '");
			insertSql.append(dailyPrice.getLowPrice());
			insertSql.append("', '");
			insertSql.append(dailyPrice.getClosePrice());
			insertSql.append("', '");
			insertSql.append(dailyPrice.getVolume());
			insertSql.append("', '");
			insertSql.append(dailyPrice.getAdjustClosePrice());
			insertSql.append("', '");
			insertSql.append(dailyPrice.getDate());
			insertSql.append("'), ('");
		}
		DailyPrice dailyPrice = dailyPrices.get(length);
		insertSql.append(dailyPrice.getStockCode());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getOpenPrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getHighPrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getLowPrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getClosePrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getVolume());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getAdjustClosePrice());
		insertSql.append("', '");
		insertSql.append(dailyPrice.getDate());
		insertSql.append("')");
		
		return insertSql.toString();
	}
	
	public static void main(String[] args) {
		DailyPriceImporter importer = new DailyPriceImporter();
		importer.importer();
	}
}
