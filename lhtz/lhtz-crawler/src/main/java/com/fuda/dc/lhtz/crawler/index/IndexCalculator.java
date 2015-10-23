package com.fuda.dc.lhtz.crawler.index;

import java.util.ArrayList;
import java.util.List;

import com.fuda.dc.lhtz.crawler.vo.AdvanceIndex;
import com.fuda.dc.lhtz.crawler.vo.DailyPrice;

public class IndexCalculator {
	/**
	 * 通过当日股票交易数据与昨日各项高级指标计算当日高级指标
	 * 
	 * @param dailyPrice
	 * @param yesAdanceIndex
	 * @return
	 */
	public static AdvanceIndex calculate(DailyPrice dailyPrice, AdvanceIndex yesAdanceIndex) {
		AdvanceIndex adanceIndex = new AdvanceIndex();
		double[] macdParams = calcMacd(dailyPrice, yesAdanceIndex);
		adanceIndex.setMacdEma12(macdParams[0]);
		adanceIndex.setMacdEma26(macdParams[1]);
		adanceIndex.setMacdDiff(macdParams[2]);
		adanceIndex.setMacdDea(macdParams[3]);
		adanceIndex.setMacd(macdParams[4]);
		
		
		return adanceIndex;
	}
	
	/**
	 * 通过股票所有交易日交易数据计算各项高级指标
	 * 
	 * @param dailyPrices
	 * @return
	 */
	public static List<AdvanceIndex> calculate(List<DailyPrice> dailyPrices) {
		List<AdvanceIndex> advanceIndices = new ArrayList<AdvanceIndex>();
		// 计算macd各项指标
		List<double[]> macdList = calcMacd(dailyPrices);
		// 计算5天均线
		List<Double> mean5List = calcMean(dailyPrices, 5);
		// 计算10天均线
		List<Double> mean10List = calcMean(dailyPrices, 10);
		// 计算20天均线
		List<Double> mean20List = calcMean(dailyPrices, 20);
		// 计算30天均线
		List<Double> mean30List = calcMean(dailyPrices, 30);
		// 计算60天均线
		List<Double> mean60List = calcMean(dailyPrices, 60);
		List<double[]> kdjList = calcKdj(dailyPrices, 9);
		
		List<Double> obvList = calcObv(dailyPrices);
		
		List<Double> rsiList = calcRsi(dailyPrices);
		
		List<double[]> bollList = calcBoll(dailyPrices);
		
		for (int i = 0; i < dailyPrices.size(); i++) {
			AdvanceIndex advanceIndex = new AdvanceIndex();
			double[] macdParams = macdList.get(i);
			advanceIndex.setMacdEma12(macdParams[0]);
			advanceIndex.setMacdEma26(macdParams[1]);
			advanceIndex.setMacdDiff(macdParams[2]);
			advanceIndex.setMacdDea(macdParams[3]);
			advanceIndex.setMacd(macdParams[4]);
			
			advanceIndex.setMean5(mean5List.get(i));
			advanceIndex.setMean10(mean10List.get(i));
			advanceIndex.setMean20(mean20List.get(i));
			advanceIndex.setMean30(mean30List.get(i));
			advanceIndex.setMean60(mean60List.get(i));
			
			double[] kdjParams = kdjList.get(i);
			advanceIndex.setKdjK(kdjParams[0]);
			advanceIndex.setKdjD(kdjParams[1]);
			advanceIndex.setKdjJ(kdjParams[2]);
			
			advanceIndex.setObv(obvList.get(i));
			advanceIndex.setRsi(rsiList.get(i));
			
			double[] bollParams = bollList.get(i);
			advanceIndex.setBollMd(bollParams[0]);
			advanceIndex.setBollUp(bollParams[1]);
			advanceIndex.setBollDn(bollParams[2]);
			
			advanceIndices.add(advanceIndex);
		}
		
		return advanceIndices;
	}
	
	/**
	 * 通过股票所有交易日交易数据计算macd各项指标
	 * 
	 * @param dailyPrices
	 * @return
	 */
	public static List<double[]> calcMacd(List<DailyPrice> dailyPrices) {
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
	public static double[] calcMacd(DailyPrice dailyPrice, AdvanceIndex yesAdanceIndex) {
		double[] params = new double[5];
		double ema12 = Macd.ema12(yesAdanceIndex.getMacdEma12(), dailyPrice.getClosePrice());
		double ema26 = Macd.ema26(yesAdanceIndex.getMacdEma26(), dailyPrice.getAdjustClosePrice());
		double diff = Macd.diff(ema12, ema26);
		double dea = Macd.dea(yesAdanceIndex.getMacdDea(), diff);
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
	public static double[] calcMacd(DailyPrice dailyPrice, double ema12Yesterday, double ema26yesterday, double deaYesterday) {
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
	
	/**
	 * 计算n天均线值
	 * 
	 * @param dailyPrices
	 * @param n n天均线
	 * @return
	 */
	public static List<Double> calcMean(List<DailyPrice> dailyPrices, int n) {
		List<Double> meanList = new ArrayList<Double>();
		
		for (int i = 0; i < dailyPrices.size(); i++) {
			if (i < n) {
				double mean = calcMean(dailyPrices, 0, i);
				meanList.add(mean);
			} else {
				double mean = calcMean(dailyPrices, i - n, i);
				meanList.add(mean);
			}
		}
		
		return meanList;
	}
	
	/**
	 * 计算n天均线值
	 * 
	 * @param dailyPrices
	 * @param start
	 * @param end
	 * @return
	 */
	public static double calcMean(List<DailyPrice> dailyPrices, int start, int end) {
		if (dailyPrices == null || dailyPrices.size() == 0) {
			return 0;
		}
		
		double sum = 0.0;
		for (int i = start; i < end; i++) {
			sum += dailyPrices.get(i).getClosePrice();
		}
		return sum / dailyPrices.size();
	}
	
	/**
	 * 计算obv
	 * 
	 * @param dailyPrices
	 * @return
	 */
	public static List<Double> calcObv(List<DailyPrice> dailyPrices) {
		List<Double> obvList = new ArrayList<Double>(dailyPrices.size());
		for (int i = 0; i < dailyPrices.size(); i++) {
			if (i == 0) {
				double obv = dailyPrices.get(0).getVolume();
				obvList.add(obv);
			} else {
				double obv = calcObv(obvList.get(0), dailyPrices.get(i));
				obvList.add(obv);
			}
		}
		
		return obvList;
	}
	
	/**
	 * 计算obv
	 * 
	 * @param obvYesterday
	 * @param dailyPrice
	 * @return
	 */
	public static double calcObv(double obvYesterday, DailyPrice dailyPrice) {
		return Obv.obv(obvYesterday, dailyPrice.getOpenPrice(), dailyPrice.getClosePrice(), dailyPrice.getVolume());
	}

	/**
	 * 
	 * 
	 * @param dailyPrices
	 * @return
	 */
	public static List<Double> calcRsi(List<DailyPrice> dailyPrices) {
		final int RSI_RANGE = 15;
		List<Double> rsiList = new ArrayList<Double>(dailyPrices.size());
		for (int i = 0; i < dailyPrices.size(); i++) {
			if (i < RSI_RANGE) {
				double rsi = calcMean(dailyPrices, 0, i);
				rsiList.add(rsi);
			} else {
				double rsi = calcRsi(dailyPrices, i - RSI_RANGE, i);
				rsiList.add(rsi);
			}
		}
		
		return rsiList;
	}
	
	/**
	 * 
	 * 
	 * @param dailyPrices
	 * @param start
	 * @param end
	 * @return
	 */
	public static double calcRsi(List<DailyPrice> dailyPrices, int start, int end) {
		double[] closePrices = new double[dailyPrices.size()];
		int n = 0;
		for (int i = start; i < end; i++) {
			closePrices[n] = dailyPrices.get(i).getClosePrice();
			n++;
		}
		
		return Rsi.rsi(closePrices);
	}
	
	public static List<double[]> calcKdj(List<DailyPrice> dailyPrices, int n) {
		List<double[]> kdjList = new ArrayList<double[]>(dailyPrices.size());
		for (int i = 0; i < dailyPrices.size(); i++) {
			if (i < n) {
				kdjList.add(new double[] {50, 50, Kdj.j(50, 50)});
			} else {
				double[] params = kdjList.get(i - 1);
				double[] kdjParams = calcKdj(dailyPrices, n, params[0], params[1]);
				kdjList.add(kdjParams);
			}
		}
		
		return kdjList;
	}
	
	public static double[] calcKdj(List<DailyPrice> dailyPrices, int n, AdvanceIndex adanceIndex) {
		double[] params = new double[3];
		double rsv = Kdj.rsv(dailyPrices, n);
		double k = Kdj.k(adanceIndex.getKdjK(), rsv);
		double d = Kdj.d(adanceIndex.getKdjD(), k);
		double j = Kdj.j(k, d);
		params[0] = k;
		params[1] = d;
		params[2] = j;
		return params;
	}
	
	public static double[] calcKdj(List<DailyPrice> dailyPrices, int n, double kYesterday, double dYesterday) {
		double[] params = new double[3];
		double rsv = Kdj.rsv(dailyPrices, n);
		double k = Kdj.k(kYesterday, rsv);
		double d = Kdj.d(dYesterday, k);
		double j = Kdj.j(k, d);
		params[0] = k;
		params[1] = d;
		params[2] = j;
		return params;
	}
	
	public static List<double[]> calcBoll(List<DailyPrice> dailyPrices) {
		final int RANGE = 20;
		List<double[]> bollList = new ArrayList<double[]>(dailyPrices.size());
		for (int i = 0; i < dailyPrices.size(); i++) {
			if (i < RANGE) {
				double[] bollParams = calcBoll(dailyPrices, 0 , i);
				bollList.add(bollParams);
			} else {
				double[] kdjParams = calcBoll(dailyPrices, i - RANGE, i);
				bollList.add(kdjParams);
			}
		}
		
		return bollList;
	}
	
	public static double[] calcBoll(List<DailyPrice> dailyPrices, int start, int end) {
		int len = end - start;
		double[] closePrices = new double[len];
		for (int i = start; i < end; i++) {
			closePrices[i] = dailyPrices.get(i).getClosePrice();
		}
		double[] params = new double[3];
		double mb = Boll.mb(closePrices);
		double md = Boll.md(closePrices, mb);
		double up = Boll.up(mb, md);
		double dn = Boll.dn(mb, md);
		params[0] = md;
		params[1] = up;
		params[2] = dn;
		return params;
	}
}
