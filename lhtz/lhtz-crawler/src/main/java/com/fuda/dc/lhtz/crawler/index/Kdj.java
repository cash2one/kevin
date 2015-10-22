package com.fuda.dc.lhtz.crawler.index;

import java.util.List;

import com.fuda.dc.lhtz.crawler.vo.DailyPrice;

public class Kdj {
	/**
	 * （Cn－Ln）/（Hn－Ln）×100当日K值=2/3×前一日K值+1/3×当日RSV
	 * 当日K值=2/3×前一日K值+1/3×当日RSV
	 * 当日D值=2/3×前一日D值+1/3×当日K值
	 * 若无前一日K 值与D值，则可分别用50来代替。
	 * J值=3*当日K值-2*当日D值
	 * 
	 * @param dailyPrices
	 * @param n
	 * @return
	 */
	public static double rsv(List<DailyPrice> dailyPrices, int n) {
		double minPrice = Double.MAX_VALUE;
		double maxPrice = Double.MIN_VALUE;
		
		int len = dailyPrices.size() <= n ? dailyPrices.size() : n;
		for (int i = 0; i < len; i++) {
			DailyPrice dailyPrice = dailyPrices.get(i);
			if (minPrice > dailyPrice.getLowPrice()) {
				minPrice = dailyPrice.getLowPrice();
			}
			
			if (maxPrice < dailyPrice.getHighPrice()) {
				maxPrice = dailyPrice.getHighPrice();
			}
		}
		
		double rsv = (dailyPrices.get(len - 1).getClosePrice() - minPrice) / (maxPrice - minPrice);
		return rsv;
	}
	
	public static double k(double kYesterday, double rsv) {
		return kYesterday * 2 / 3 + rsv / 3;
	}
	
	public static double d(double dYesterday, double k) {
		return dYesterday * 2 / 3 + k / 3;
	}
	
	public static double j(double k, double d) {
		return k * 3 - d * 2;
	}
}
