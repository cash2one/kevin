package com.fuda.dc.lhtz.crawler.index;

public class Fomula {
	/**
	 * 求平均值
	 * 
	 * @param arr
	 * @return
	 */
	public static double mean(double[] arr) {
		double sum = 0.0;
		for (double x : arr) {
			sum += x;
		}
		return sum / arr.length;
	}
	
}
