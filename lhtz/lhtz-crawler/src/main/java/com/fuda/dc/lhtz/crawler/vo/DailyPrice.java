package com.fuda.dc.lhtz.crawler.vo;

/**
 * 股票每日成交数据（k线）
 * 
 * @author aa
 *
 */
public class DailyPrice {
	private long id;
	private String stockCode;
	private float openPrice;
	private float highPrice;
	private float lowPrice;
	private float closePrice;
	private long volume;
	private float adjustClosePrice;
	private String date;
	
	public DailyPrice() {
	}
	
	public DailyPrice(long id, String stockCode, float openPrice, float highPrice, float lowPrice,
			float closePrice, long volume, float adjustClosePrice, String date ) {
		this.id = id;
		this.stockCode = stockCode;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closePrice = closePrice;
		this.volume = volume;
		this.adjustClosePrice = adjustClosePrice;
		this.date = date;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public float getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(float openPrice) {
		this.openPrice = openPrice;
	}
	public float getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(float highPrice) {
		this.highPrice = highPrice;
	}
	public float getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(float lowPrice) {
		this.lowPrice = lowPrice;
	}
	public float getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(float closePrice) {
		this.closePrice = closePrice;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}
	public float getAdjustClosePrice() {
		return adjustClosePrice;
	}
	public void setAdjustClosePrice(float adjustClosePrice) {
		this.adjustClosePrice = adjustClosePrice;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
