package com.fuda.dc.lhtz.crawler.index;

public class Obv {
	/**
	 * 计算obv：今日OBV=昨天OBV+sgn×今天的成交量其中sgn是符号的意思，sgn可能是+1，也可能是-1，这由下式决定。Sgn=+1 今收盘价≥昨收盘价,Sgn=―1 今收盘价《昨收盘价
	 * 
	 * @param obvYesterday
	 * @param openPrice
	 * @param closePrice
	 * @return
	 */
	public static double obv(double obvYesterday, double openPrice, double closePrice, long volume) {
		if (closePrice > openPrice) {
			return obvYesterday + volume;
		} else if (closePrice > openPrice) {
			return obvYesterday - volume;
		} else {
			return obvYesterday;
		}
	}
}
