package com.fuda.dc.lhtz.crawler.vo;

public class LonghuInfo {
	/**
	 * 股票代码
	 */
	private String stockCode;
	/**
	 * 股票名称
	 */
	private String stockName;
	/**
	 * 涨跌幅
	 */
	private float upDownRange;
	/**
	 * 龙虎榜成交额(万)
	 */
	private float turnover;
	/**
	 * 买入额(万)
	 */
	private float buyAmount;
	/**
	 * 买入额占总成交比例
	 */
	private float buyRatio;
	/**
	 * 卖出额(万)
	 */
	private float sellAmount;
	/**
	 * 卖出额占总成交比例
	 */
	private float sellRatio;
	/**
	 * 上榜原因
	 */
	private String onBoardReason;
	
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public float getUpDownRange() {
		return upDownRange;
	}
	public void setUpDownRange(float upDownRange) {
		this.upDownRange = upDownRange;
	}
	public float getTurnover() {
		return turnover;
	}
	public void setTurnover(float turnover) {
		this.turnover = turnover;
	}
	public float getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(float buyAmount) {
		this.buyAmount = buyAmount;
	}
	public float getBuyRatio() {
		return buyRatio;
	}
	public void setBuyRatio(float buyRatio) {
		this.buyRatio = buyRatio;
	}
	public float getSellAmount() {
		return sellAmount;
	}
	public void setSellAmount(float sellAmount) {
		this.sellAmount = sellAmount;
	}
	public float getSellRatio() {
		return sellRatio;
	}
	public void setSellRatio(float sellRatio) {
		this.sellRatio = sellRatio;
	}
	public String getOnBoardReason() {
		return onBoardReason;
	}
	public void setOnBoardReason(String onBoardReason) {
		this.onBoardReason = onBoardReason;
	}
	
}
