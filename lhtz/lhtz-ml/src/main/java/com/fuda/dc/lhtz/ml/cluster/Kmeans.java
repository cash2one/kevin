package com.fuda.dc.lhtz.ml.cluster;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 
 * @author kevin
 * @date 2015.10.19
 */
public class Kmeans {
	private static final String TAB_SPLITER = "\t";
	private static final String COMMAR_SPLITER = ":";
	/**
	 * 各个类别质心
	 */
	private List<double[]> curCentroids = new ArrayList<double[]>();
	/**
	 * 样本集
	 */
	private List<double[]> dataList = new ArrayList<double[]>();
	/**
	 * 类别数
	 */
	private int catagoryNum;
	/**
	 * 特征维度
	 */
	private int featureLen;

	/**
	 * 载入数据
	 * 
	 * @param dataFileName
	 */
	public void loadData(String dataFileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(dataFileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] items = line.split(TAB_SPLITER);
				double[] features = new double[items.length];
				for (int i = 0; i < items.length; i++) {
					features[i] = Double.valueOf(items[i]);
				}
				dataList.add(features);
				featureLen = items.length;
				System.out.println(items.length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化质心（随机初始化）
	 */
	private void initCentroids() {
		int[] seeds = new int[catagoryNum];
		Random random = new Random();
		for (int i = 0; i < catagoryNum; i++) {
			while (true) {
				int index = random.nextInt(dataList.size());
				if (seeds[i] == 0) {
					seeds[i] = index;
					break;
				}
			}
		}
		
		for (int i = 0; i < catagoryNum; i++) {
			double[] centroid = dataList.get(i);
			curCentroids.add(centroid);
		}
	}

	/**
	 * 欧式距离
	 * 
	 * @param vector1
	 * @param vector2
	 * @return
	 */
	private double euclideanDistance(double[] vector1, double[] vector2) {
		double distance = 0;
		for (int i = 0; i < vector1.length; i++) {
			distance += (vector1[i] - vector2[i]) * (vector1[i] - vector2[i]);
		}
		return Math.sqrt(distance);
	}
	
	/**
	 * 判断样本属于哪个类
	 * 
	 * @param features
	 * @param curCentroids
	 * @return
	 */
	private int belongToCluster(double[] features, List<double[]> curCentroids) {
		double minDistance = Double.MAX_VALUE;
		int minIndex = 0;
		for (int i = 0; i < catagoryNum; i++) {
			double distance = euclideanDistance(curCentroids.get(i), features);
			if (minDistance > distance) {
				minDistance = distance;
				minIndex = i;
			}
		}
		
		return minIndex;
	}

	/**
	 * 计算质心
	 * 
	 * @param dataList
	 * @param catagoryStaticList
	 * @return
	 */
	private double[] calcCentroid(List<double[]> dataList, List<Integer> catagoryStaticList) {
		double[] centroid = new double[featureLen];
		for (Integer index : catagoryStaticList) {
			double[] features = dataList.get(index);
			for (int j = 0; j < featureLen; j++) {
				centroid[j] += features[j];
			}
		}
		
		for (int j = 0; j < featureLen; j++) {
			centroid[j] /= catagoryStaticList.size();
		}
		
		return centroid;
	}
	
	/**
	 * 计算总误差
	 * 
	 * @param dataList
	 * @param curCentroids
	 * @param catagoryStaticMap
	 * @return
	 */
	private double calcError(List<double[]> dataList, List<double[]> curCentroids, Map<Integer, List<Integer>> catagoryStaticMap) {
		double error = 0;
		for (int i = 0; i < catagoryNum; i++) {
			List<Integer> catagoryStaticList = catagoryStaticMap.get(i);
			double[] centroid = curCentroids.get(i);
			for (Integer index : catagoryStaticList) {
				double[] features = dataList.get(index);
				for (int j = 0; j < featureLen; j++) {
					error += (centroid[j] - features[j]) * (centroid[j] - features[j]);
				}
			}
		}
		
		return error;
	}
	
	/**
	 * 训练样本
	 * 
	 * @param catagoryNum
	 * @param maxIterNum
	 * @param e
	 */
	public void train(int catagoryNum, int maxIterNum, double e) {
		this.catagoryNum = catagoryNum;
		initCentroids();
		
		Map<Integer, List<Integer>> catagoryStaticMap = new HashMap<Integer, List<Integer>>(catagoryNum);
		int iterNum = 0;
		double minError = Double.MAX_VALUE;
		while (iterNum < maxIterNum) {
			List<double[]> nextCentroids = new ArrayList<double[]>(catagoryNum);
			for (int i = 0; i < dataList.size(); i++) {
				int classNo = belongToCluster(dataList.get(i), curCentroids);
				List<Integer> catagoryStaticList = catagoryStaticMap.get(classNo);
				if (catagoryStaticList == null) {
					catagoryStaticList = new ArrayList<Integer>();
					catagoryStaticList.add(i);
					catagoryStaticMap.put(i, catagoryStaticList);
				} else {
					catagoryStaticList.add(i);
					catagoryStaticMap.put(i, catagoryStaticList);
				}
			}
			
			for (int j = 0; j < catagoryNum; j++) {
				nextCentroids.add(j, calcCentroid(dataList, catagoryStaticMap.get(j)));
			}
			
			curCentroids = nextCentroids;
			double error = calcError(dataList, curCentroids, catagoryStaticMap);
			if (error < e) {
				System.out.println("train finish! iterNum: " + iterNum + "minError: " + minError);
				break;
			}
			System.out.println("iterNum: " + iterNum + "minError: " + minError);
			iterNum++;
		}
		
		System.out.println("train finish! iterNum: " + iterNum + "minError: " + minError);
	}
	
	/**
	 * 训练样本
	 * 
	 * @param catagoryNum
	 */
	public void train(int catagoryNum) {
		this.train(catagoryNum, 10000, 0.0000001);
	}
	
	/**
	 * 输出模型
	 * 
	 * @param modelFileName
	 */
	public void outputModel(String modelFileName) {
		FileWriter fw = null;
		try {
			fw = new FileWriter(modelFileName);
			fw.write("catagroy:" + catagoryNum + "\n");
			if (curCentroids == null || curCentroids.size() < 2) {
				System.out.println("centroids size must be greater than 1!");
				return;
			}
			
			String line = implode(curCentroids.get(0));
			fw.write(line);
			
			for (int i = 1; i < curCentroids.size(); i++) {
				line = implode(curCentroids.get(i));
				fw.write(line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private String implode(double[] centroid) {
		if (centroid.length == 0) {
			return "";
		}
		
		if (centroid.length == 1) {
			return String.valueOf(centroid[0]);
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.valueOf(centroid[0]));
		for (int i = 1; i < centroid.length; i++) {
			sb.append(String.valueOf(TAB_SPLITER));
			sb.append(String.valueOf(centroid[i]));
		}
		sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * 加载模型
	 * 
	 * @param modelFileName
	 */
	public void loadModel(String modelFileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(modelFileName));
			String line = br.readLine();
			if (line != null) {
				String[] items = line.split(COMMAR_SPLITER);
				catagoryNum = Integer.valueOf(items[1]);
			}
			
			while ((line = br.readLine()) != null) {
				String[] items = line.split(TAB_SPLITER);
				double[] centroid = new double[items.length];
				for (int i = 0; i < items.length; i++) {
					centroid[i] = Double.valueOf(items[i]);
				}
				curCentroids.add(centroid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
			    
	/**
	 * 预测输入所属类别
	 * 
	 * @param features
	 * @return
	 */
	public int predict(double[] features) {
		return belongToCluster(features, curCentroids);
	}
			    
	public static void main(String[] args) {
		Kmeans1 kmeans = new Kmeans1();
		kmeans.loadData("E:\\sampledata.txt");
		kmeans.train(2, 10000000, 0.0000001);
		kmeans.outputModel("E:\\model.txt");
		kmeans.loadModel("E:\\model.txt");
		kmeans.predict(new double[]{1.0, 2.0});
	}
}