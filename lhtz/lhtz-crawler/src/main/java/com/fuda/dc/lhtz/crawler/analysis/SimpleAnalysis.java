package com.fuda.dc.lhtz.crawler.analysis;

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
import com.fuda.dc.lhtz.crawler.vo.DailyPrice;

public class SimpleAnalysis {
	
	private static final Logger LOG = LoggerFactory.getLogger(StockCodeImporter.class);
	
	public List<String> findFallMuchStock(String startDate, double ratio) {
		List<String> fallMuchCodeList = new ArrayList<String>();
		
		List<String> stockCodeList = getAllStockCode();
		for (String stockCode : stockCodeList) {
			List<DailyPrice> dailyPrices = getDailyPrices(stockCode);
			for (DailyPrice dailyPrice : dailyPrices) {
				if (isFallMuchStock(dailyPrices, startDate, ratio)) {
					fallMuchCodeList.add(dailyPrice.getStockCode());
				}
			}
		}
		
		return fallMuchCodeList;
	}

	private boolean isFallMuchStock(List<DailyPrice> dailyPrices, String startDate, double ratio) {
		double maxPrice = Double.MIN_VALUE;
		double minPrice = Double.MAX_VALUE;
		for (DailyPrice dailyPrice : dailyPrices) {
			if (dailyPrice.getDate().compareTo(startDate) > 0) {
				if (maxPrice < dailyPrice.getClosePrice()) {
					maxPrice = dailyPrice.getClosePrice();
				}
				
				if (minPrice > dailyPrice.getClosePrice()) {
					minPrice = dailyPrice.getAdjustClosePrice();
				}
			}
		}
		
		if (maxPrice == Double.MIN_VALUE) {
			return false;
		}
		
		if (minPrice > (1 - ratio) * maxPrice) {
			return false;
		}
		
		return true;
	}
	
	private List<DailyPrice> getDailyPrices(String stockCode) {
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

	
	private List<String> getAllStockCode() {
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
		SimpleAnalysis simpleAnalysis = new SimpleAnalysis();
		List<String> fallMachStockList = simpleAnalysis.findFallMuchStock("2015-06-01", 0.5);
		for (String code : fallMachStockList) {
			System.out.println(code);
		}
	}

}
