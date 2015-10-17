package com.fuda.dc.lhtz.ml.classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Adaboost {
	
	private static final String TAB_SPLITER = "\t";
	/**
	 * alpha参数列表
	 */
	private List<Double> alphas;
	/**
	 * 弱分类器阈值列表
	 */
	private List<Double> thresholds;
	/**
	 * 弱分类器b参数列表
	 */
	private List<Double> bList;
	/**
	 * 特征个数
	 */
	private int featureLen;
	
	/**
	 * 加载样本数据
	 * 
	 * @param sampleFileName
	 */
	public void loadData(String sampleFileName) {
		
	}
	
	/**
	 * 训练样本
	 */
	public void train() {
		
	}
	
	/**
	 * 训练弱分类器
	 */
	private void trainWeakClassifier() {
		
	}
	
	/**
	 * 输出训练好的模型
	 * 
	 * @param modelFileName
	 */
	public void outputModel(String modelFileName) {
		
	}
	
	/**
	 * 加载训练好的模型
	 * 
	 * @param modelFileName
	 */
	public void loadModel(String modelFileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(modelFileName));
			String line = br.readLine();
			if (line != null) {
				String[] items = line.split(TAB_SPLITER);
				for (String item : items) {
					alphas.add(Double.valueOf(item));
				}
			}
			
			line = br.readLine();
			if (line != null) {
				String[] items = line.split(TAB_SPLITER);
				for (String item : items) {
					thresholds.add(Double.valueOf(item));
				}
			}
			
			line = br.readLine();
			if (line != null) {
				String[] items = line.split(TAB_SPLITER);
				for (String item : items) {
					bList.add(Double.valueOf(item));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 预测分类结果
	 * 
	 * @param features
	 * @return
	 */
	public int predict(List<Double> features) {
		double sum = 0.0;
		double alphaSum = 0.0;
		
		for (int i = 0; i < featureLen; i++) {
			if (weakClassifier(features.get(i), thresholds.get(i), bList.get(i)) == 1) {
				sum += alphas.get(i);
			} else {
				sum -= alphas.get(i);
			}
			alphaSum += alphas.get(i);
		}
		sum -= 0.5 * alphaSum;
		return sign(sum);
	}
	
	/**
	 * Adaboost弱分类器
	 * 
	 * @param feature
	 * @param threshold
	 * @param b
	 * @return
	 */
	private int weakClassifier(double feature, double threshold, double b) {
		if (b == 1.0) {
			if (feature > threshold) {
				return 1;
			} else {
				return -1;
			}
		} else {
			if (feature < threshold) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	
	/**
	 * 判断弱分类器分类类别是否与给定的样本类别一致
	 * 
	 * @param feature
	 * @param y
	 * @param threshold
	 * @param b
	 * @return
	 */
	private int isClassifyEqualSample(double feature, double y, double threshold, double b) {
		if (weakClassifier(feature, threshold, b) == y) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * sign函数（为0时取-1）
	 * 
	 * @param value
	 * @return
	 */
	private int sign(double value) {
		if (value > 0.0) {
			return 1;
		} else {
			return -1;
		}
	}

}
 