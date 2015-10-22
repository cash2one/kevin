package com.fuda.dc.lhtz.crawler.index;

public class Macd {
	/**
	 * 计算26日移动平均线EMA2:当日EMA(12)=前一日EMA(12)×11/13＋当日收盘价×2/13 
	 * 
	 * @param ema12Yesterday
	 * @param closePrice
	 * @return
	 */
	public static double ema12(double ema12Yesterday, double closePrice) {
		return ema12Yesterday * 11 /13 + closePrice * 2 / 13;
	}
	
	/**
	 * 计算12日移动平均线EMA1:当日EMA(26)=前一日EMA(26)×25/27＋当日收盘价×2/27 
	 * 
	 * @param ema12Yesterday
	 * @param closePrice
	 * @return
	 */
	public static double ema26(double ema12Yesterday, double closePrice) {
		return ema12Yesterday * 25 / 27 + closePrice * 2 / 27;
	}
	
	/**
	 * 计算离差值(DIFF):DIFF=当日EMA(12)－当日EMA(26) 
	 * 
	 * @param ema12
	 * @param ema26
	 * @return
	 */
	public static double diff(double ema12, double ema26) {
		return ema12 - ema26;
	}
	
	/**
	 * 计算9日离差平均值DEA:当日DEA=前一日DEA×8/10＋当日DIFF×2/10
	 * 
	 * @param deaYesterday
	 * @param diff
	 * @return
	 */
	public static double dea(double deaYesterday, double diff) {
		return deaYesterday * 8 / 10 + diff * 2 / 10;
	}

	/**
	 * 计算MACD:MACD=2×(DIFF－DEA) 
	 * 
	 * @param diff
	 * @param dea
	 * @return
	 */
	public static double macd(double diff, double dea) {
		return 2 * (diff - dea);
	}
}
