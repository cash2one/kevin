package com.fuda.dc.lhtz.crawler.index;

/**
 * 计算MA MA=N日内的收盘价之和÷N
 * （2）计算标准差MD MD=平方根N日的（C－MA）的两次方之和除以N
 * （3）计算MB、UP、DN线 MB=（N－1）日的MA UP=MB+k×MD DN=MB－k×MD
 * 
 * @author aa
 *
 */
public class Boll {
	public static double mb(double[] closePrices) {
		double sum = 0;
		for (int i = 0; i < closePrices.length; i++) {
			sum += closePrices[i];
		}
		return sum / closePrices.length;
	}
	
	public static double md(double[] closePrices, double mb) {
		double sum = 0;
		for (int i = 0; i < closePrices.length; i++) {
			sum += (closePrices[i] - mb) * (closePrices[i] - mb);
		}
		return Math.sqrt(sum / closePrices.length);
	}
	
	public static double up(double mb, double md, double k) {
		return mb + md * k;
	}
	
	public static double up(double mb, double md) {
		return mb + md * 2;
	}
	
	public static double dn(double mb, double md, double k) {
		return mb - md * k;
	}
	
	public static double dn(double mb, double md) {
		return mb - md * 2;
	}
}
