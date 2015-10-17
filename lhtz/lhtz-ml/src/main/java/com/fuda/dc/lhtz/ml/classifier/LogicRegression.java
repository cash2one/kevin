package com.fuda.dc.lhtz.ml.classifier;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * 
 * @author kevin
 * @date 2015.10.17
 * @description
 * 	    逻辑回归模型，L2正则化
 */
public class LogicRegression {
	private static final String TAB_SPLITER = "\t";
	/**
	 * thetas参数列表 f(x) = 1 / (1 + exp(-thetas * x)), x为特征向量
	 */
	private List<Double> thetas;
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
					thetas.add(Double.valueOf(item));
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
		
		features.add(1.0);
		for (int i = 0; i < featureLen; i++) {
			sum += thetas.get(i) * features.get(i);
		}
		
		double result = sigmoid(sum);
		if (result > 0.5) {
			return 1;
		} else {
			return -1;
		}
	}
	
	/**
	 * sigmoid函数
	 * @param x
	 * @return
	 */
	private double sigmoid(double x) {
		return 1.0 / (1 + Math.exp(-x));
	}
}
