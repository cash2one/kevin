package com.fuda.dc.lhtz.crawler.index;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuda.dc.lhtz.client.db.DbClient;
import com.fuda.dc.lhtz.crawler.basicinfo.BasicInfoImporter;
import com.fuda.dc.lhtz.crawler.stockcode.StockCodeImporter;
import com.fuda.dc.lhtz.crawler.vo.AdvanceIndex;
import com.fuda.dc.lhtz.crawler.vo.DailyPrice;

public class IndexToDb {
	private static final Logger LOG = LoggerFactory.getLogger(StockCodeImporter.class);
	
	public void importer() {
		List<String> stockCodeList = getAllStockCode();
		for (String stockCode : stockCodeList) {
			List<DailyPrice> dailyPrices = getDailyPrices(stockCode);
			List<AdvanceIndex> advanceIndices = IndexCalculator.calculate(dailyPrices);
			for (AdvanceIndex advanceIndex : advanceIndices) {
				infoToDb(advanceIndex);
			}
		}
	}
	
	public List<DailyPrice> getDailyPrices(String stockCode) {
		List<DailyPrice> dailyPrices = new ArrayList<DailyPrice>();
		String sql = "select * from td_dailyprice where stockCode =" + stockCode;
		
		Connection conn = DbClient.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				DailyPrice dailyPrice = new DailyPrice(rs.getLong("id"), rs.getString("stockCode"), rs.getFloat("openPrice"), 
						rs.getFloat("highPrice"), rs.getFloat("lowPrice"), rs.getFloat("closePrice"), rs.getLong("volume"), 
						rs.getFloat("adjustClosePrice"), rs.getString("date"));
				dailyPrices.add(dailyPrice);
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
		
		return dailyPrices;
	}
	
	private void infoToDb(AdvanceIndex advanceIndex) {
		String insertSql = genInsertSql(advanceIndex);
		
		Connection conn = DbClient.getInstance().getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
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
	
	private String genInsertSql(AdvanceIndex advanceIndex) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append("insert into tb_basic_info(macdEma12, macdEma26, macdDea, macdDiff, macd, mean5, mean10, mean20, mean30, mean60, kdjK, kdjD, kdjJ, obv, rsi) values('");
		insertSql.append(advanceIndex.getMacdEma12());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMacdEma26());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMacdDea());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMacdDiff());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMacd());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMean5());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMean10());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMean20());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMean30());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getMean60());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getKdjK());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getKdjD());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getKdjK());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getObv());
		insertSql.append("', '");
		insertSql.append(advanceIndex.getRsi());
		
		insertSql.append("')");
		
		return insertSql.toString();
	}
	
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
	
	public static void main(String[] args) {
		BasicInfoImporter importer = new BasicInfoImporter();
		importer.importer();
	}
	
}
