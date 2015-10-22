package com.fuda.dc.lhtz.crawler.index;

public class Rsi {
	/**
	 * 计算rsi:RSI=100-[100/(1+RS)],其中 RS=14天内收市价上涨数之和的平均值/14天内收市价下跌数之和的平均值
	 * 
	 * @param closePrices
	 * @return
	 */
	public static double rsi(double[] closePrices) {
		double plusSum = 0;
		double minusSum = 0;
		int len = closePrices.length <= 15 ? closePrices.length : 15;
		for (int i = 1; i < len; i++) {
			if (closePrices[i] > closePrices[i - 1]) {
				plusSum += closePrices[i] - closePrices[i - 1];
			} else {
				minusSum += closePrices[i -1] - closePrices[i];
			}
		}
		
		if (minusSum == 0.0) {
			return 100;
		}
		
		double rs = plusSum / minusSum;
		return 100 - 100 / (1 + rs);
	}
}
