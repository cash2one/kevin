package com.fuda.dc.lhtz.crawler.vo;

import java.util.Date;

public class BasicInfo {
	private long id;
	private float pe;
	private float pb;
	private long netAsset;
	private float perNetAsset;
	private long revenue;
	private long netProfit;
	private long totalShares;
	private float earningsPerShare;
	private long totalMarketValue; 
	private long circulationMarketValue;
	private long circulationShares;
	private Date inTime;
	private Date updateTime; 
	private String stockCode;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getPe() {
		return pe;
	}
	public void setPe(float pe) {
		this.pe = pe;
	}
	public float getPb() {
		return pb;
	}
	public void setPb(float pb) {
		this.pb = pb;
	}
	public long getNetAsset() {
		return netAsset;
	}
	public void setNetAsset(long netAsset) {
		this.netAsset = netAsset;
	}
	public float getPerNetAsset() {
		return perNetAsset;
	}
	public void setPerNetAsset(float perNetAsset) {
		this.perNetAsset = perNetAsset;
	}
	public long getRevenue() {
		return revenue;
	}
	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}
	public long getNetProfit() {
		return netProfit;
	}
	public void setNetProfit(long netProfit) {
		this.netProfit = netProfit;
	}
	public long getTotalShares() {
		return totalShares;
	}
	public void setTotalShares(long totalShares) {
		this.totalShares = totalShares;
	}
	public float getEarningsPerShare() {
		return earningsPerShare;
	}
	public void setEarningsPerShare(float earningsPerShare) {
		this.earningsPerShare = earningsPerShare;
	}
	public long getTotalMarketValue() {
		return totalMarketValue;
	}
	public void setTotalMarketValue(long totalMarketValue) {
		this.totalMarketValue = totalMarketValue;
	}
	public long getCirculationMarketValue() {
		return circulationMarketValue;
	}
	public void setCirculationMarketValue(long circulationMarketValue) {
		this.circulationMarketValue = circulationMarketValue;
	}
	public long getCirculationShares() {
		return circulationShares;
	}
	public void setCirculationShares(long circulationShares) {
		this.circulationShares = circulationShares;
	}
	public Date getInTime() {
		return inTime;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	
}