package com.fuda.dc.lhtz.crawler.basicinfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.client.db.DbClient;
import com.fuda.dc.lhtz.crawler.stockcode.StockCodeImporter;
import com.fuda.dc.lhtz.crawler.vo.BasicInfo;
import com.fuda.dc.lhtz.crawler.vo.DailyPrice;

public class BasicInfoImporter {
	private static final Logger LOG = LoggerFactory.getLogger(StockCodeImporter.class);
	
	private DfcfwBasicInfoDownloader dfcfwDownloader = new DfcfwBasicInfoDownloader();
	
	private ThsBasicInfoDownloader thsDownloader = new ThsBasicInfoDownloader();
	
	/**
	 * 下载每日股票信息,存入数据库
	 */
	public void importer() {
		List<String> stockCodeList = getAllStockCode();
		for (String stockCode : stockCodeList) {
			BasicInfo basicInfo = thsDownloader.download(stockCode);
			if (basicInfo == null) {
				continue;
			}
			basicInfo.setInTime(new Date());
			basicInfo.setUpdateTime(new Date());
			basicInfo.setStockCode(stockCode);
			// BasicInfo basicInfo2 = dfcfwDownloader.download(stockCode);
			
			// basicInfo2.setPe(basicInfo2.getPe());
			// basicInfo2.setPb(basicInfo2.getPb());
			infoToDb(basicInfo);
		}
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
	 * 将股票基本信息存入数据库，如果数据库有该条记录则不插入
	 */
	private void infoToDb(BasicInfo basicInfo) {
		String selectSql = genSelectSql(basicInfo.getStockCode());
		String insertSql = genInsertSql(basicInfo);
		
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
	
	private String genSelectSql(String stockCode) {
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("select count(*) as cnt from tb_price_daily where stockCode ='");
		selectSql.append(stockCode);
		selectSql.append("'");
		return selectSql.toString();
	}
	
	private String genInsertSql(BasicInfo basicInfo) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into tb_basic_info(pe, pb, netAsset, perNetAsset, revenue, netProfit, totalShares, earningsPerShare, totalMarketValue,circulationMarketValue, circulationShares, inTime, updateTime, stockCode) values('");
		insertSql.append(basicInfo.getPe());
		insertSql.append("', '");
		insertSql.append(basicInfo.getPb());
		insertSql.append("', '");
		insertSql.append(basicInfo.getNetAsset());
		insertSql.append("', '");
		insertSql.append(basicInfo.getPerNetAsset());
		insertSql.append("', '");
		insertSql.append(basicInfo.getRevenue());
		insertSql.append("', '");
		insertSql.append(basicInfo.getNetProfit());
		insertSql.append("', '");
		insertSql.append(basicInfo.getTotalShares());
		insertSql.append("', '");
		insertSql.append(basicInfo.getEarningsPerShare());
		insertSql.append("', '");
		insertSql.append(basicInfo.getTotalMarketValue());
		insertSql.append("', '");
		insertSql.append(basicInfo.getCirculationMarketValue());
		insertSql.append("', '");
		insertSql.append(basicInfo.getCirculationShares());
		insertSql.append("', '");
		insertSql.append(basicInfo.getInTime());
		insertSql.append("', '");
		insertSql.append(basicInfo.getUpdateTime());
		insertSql.append("', '");
		insertSql.append(basicInfo.getStockCode());
		insertSql.append("')");
		
		return insertSql.toString();
	}
	
	public static void main(String[] args) {
		BasicInfoImporter importer = new BasicInfoImporter();
		importer.importer();
	}
}
