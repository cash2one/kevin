package com.fuda.dc.lhtz.ml.classifier;

import java.util.ArrayList;
import java.util.List;

public class ClassifierTest {
	public static void main(String[] args) {
		List<Double> features = new ArrayList<Double>();
		
		Adaboost adaboost = new Adaboost();
		adaboost.loadData("");
		adaboost.train();
		adaboost.outputModel("");
		adaboost.loadModel("");
		adaboost.predict(features);
		
		LogicRegression lr = new LogicRegression();
		lr.loadData("");
		lr.train();
		lr.outputModel("");
		lr.loadModel("");
		lr.predict(features);
	}

}
