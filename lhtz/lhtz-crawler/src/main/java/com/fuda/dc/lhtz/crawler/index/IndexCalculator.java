package com.fuda.dc.lhtz.crawler.index;

import java.util.ArrayList;
import java.util.List;

import com.fuda.dc.lhtz.crawler.vo.AdanceIndex;
import com.fuda.dc.lhtz.crawler.vo.DailyPrice;

public class IndexCalculator {

	/**
	 * 通过当日股票交易数据与昨日各项高级指标计算当日高级指标
	 * 
	 * @param dailyPrice
	 * @param yesAdanceIndex
	 * @return
	 */
	public AdanceIndex calculate(DailyPrice dailyPrice, AdanceIndex yesAdanceIndex) {
		AdanceIndex adanceIndex = new AdanceIndex();
		double[] macdParams = calcMacd(dailyPrice, yesAdanceIndex);
		adanceIndex.setEma12(macdParams[0]);
		adanceIndex.setEma26(macdParams[1]);
		adanceIndex.setDiff(macdParams[2]);
		adanceIndex.setDea(macdParams[3]);
		adanceIndex.setMacd(macdParams[4]);
		
		return adanceIndex;
	}
	
	/**
	 * 通过股票所有交易日交易数据计算各项高级指标
	 * 
	 * @param dailyPrices
	 * @return
	 */
	public List<AdanceIndex> calculate(List<DailyPrice> dailyPrices) {
		List<AdanceIndex> adanceIndexs = new ArrayList<AdanceIndex>();
		List<double[]> macdList = calcMacd(dailyPrices);
		
		for (int i = 0; i < dailyPrices.size(); i++) {
			AdanceIndex adanceIndex = new AdanceIndex();
			double[] macdParams = macdList.get(i);
			adanceIndex.setEma12(macdParams[0]);
			adanceIndex.setEma26(macdParams[1]);
			adanceIndex.setDiff(macdParams[2]);
			adanceIndex.setDea(macdParams[3]);
			adanceIndex.setMacd(macdParams[4]);
			adanceIndexs.add(adanceIndex);
		}
		
		return adanceIndexs;
	}
	
	/**
	 * 通过股票所有交易日交易数据计算macd各项指标
	 * 
	 * @param dailyPrices
	 * @return
	 */
	public List<double[]> calcMacd(List<DailyPrice> dailyPrices) {
		List<double[]> macdList = new ArrayList<double[]>();
		DailyPrice dailyPrice = dailyPrices.get(0);
		double ema12 = dailyPrice.getClosePrice();
		double ema26 = dailyPrice.getClosePrice();
		double diff = Macd.diff(ema12, ema26);
		double dea = Macd.dea(dailyPrice.getClosePrice(), diff);
		double macd = Macd.macd(diff, dea);
		macdList.add(new double[] {ema12, ema26, diff, dea, macd});
		
		for (int i = 1; i < dailyPrices.size(); i++) {
			double[] params = macdList.get(i - 1);
			double[] macdParams = calcMacd(dailyPrice, params[0], params[1], params[3]);
			macdList.add(macdParams);
		}
		
		return macdList;
	}
	
	/**
	 * 通过当日股票交易数据与昨日各项高级指标计算当日macd各项指标
	 * 
	 * @param dailyPrice
	 * @param yesAdanceIndex
	 * @return
	 */
	public double[] calcMacd(DailyPrice dailyPrice, AdanceIndex yesAdanceIndex) {
		double[] params = new double[5];
		double ema12 = Macd.ema12(yesAdanceIndex.getEma12(), dailyPrice.getClosePrice());
		double ema26 = Macd.ema26(yesAdanceIndex.getEma26(), dailyPrice.getAdjustClosePrice());
		double diff = Macd.diff(ema12, ema26);
		double dea = Macd.dea(yesAdanceIndex.getDea(), diff);
		double macd = Macd.macd(diff, dea);
		params[0] = ema12;
		params[1] = ema26;
		params[2] = diff;
		params[3] = dea;
		params[4] = macd;
		return params;
	}
	
	/**
	 * 通过当日股票交易数据与昨日ema12、ema26、dea计算当日macd各项指标
	 * 
	 * @param dailyPrice
	 * @param ema12Yesterday
	 * @param ema26yesterday
	 * @param deaYesterday
	 * @return
	 */
	public double[] calcMacd(DailyPrice dailyPrice, double ema12Yesterday, double ema26yesterday, double deaYesterday) {
		double[] params = new double[5];
		double ema12 = Macd.ema12(ema12Yesterday, dailyPrice.getClosePrice());
		double ema26 = Macd.ema26(ema26yesterday, dailyPrice.getAdjustClosePrice());
		double diff = Macd.diff(ema12, ema26);
		double dea = Macd.dea(deaYesterday, diff);
		double macd = Macd.macd(diff, dea);
		params[0] = ema12;
		params[1] = ema26;
		params[2] = diff;
		params[3] = dea;
		params[4] = macd;
		return params;
	}
	
	public static List<Double> calcMean(List<DailyPrice> dailyPrices, int n) {
		List<Double> meanList = new ArrayList<Double>();
		if (dailyPrices.size() <= n) {
			double mean = calcMean(dailyPrices);
			meanList.add(mean);
			return meanList;
		} else {
			return meanList;
		}
	}
	
	public static double calcMean(List<DailyPrice> dailyPrices) {
		double sum = 0.0;
		for (DailyPrice dailyPrice : dailyPrices) {
			sum += dailyPrice.getClosePrice();
		}
		return sum;
	}
	
}
