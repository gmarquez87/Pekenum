package com.msarecovery;

import weka.clusterers.SimpleKMeans;
import weka.core.AttributeStats;
import weka.core.Instances;

public class ElbowC {
	public static void main (String [] args) throws Exception {
		Instances data = new weka.core.converters.ConverterUtils.DataSource("csvToWekaSemanticARFF.arff").getDataSet();
		//data.deleteAttributeAt(data.numAttributes() - 1); // Delete class attribute in iris data
		int maxNumClusters = 50;
		Instances randomData = null;
		SimpleKMeans kMeans = new weka.clusterers.SimpleKMeans();
		double[] expected_log_W_k;
		double[] log_W_k = new double[maxNumClusters + 1];
		int numRuns;
		
		for (int i = 1; i <= maxNumClusters; i++) {
			kMeans.setNumClusters(i);
			kMeans.buildClusterer(data);
			log_W_k[i] = Math.log(kMeans.getSquaredError() / data.numInstances());
		}
		numRuns = 100;
		AttributeStats[] attributeStats = new weka.core.AttributeStats[data.numAttributes()];
		for (int i = 0; i < data.numAttributes(); i++) {
			attributeStats[i] = data.attributeStats(i);
			System.out.println(attributeStats[i]);
		}
		expected_log_W_k = new double[maxNumClusters + 1];
		for (int j = 0; j < numRuns; j++) {
			randomData = new weka.core.Instances(data, 0);
			for (int k = 0; k < data.numInstances(); k++) {
				double[] randomInst = new double[data.numAttributes()];
				for (int i = 0; i < data.numAttributes(); i++) {
					randomInst[i] = Math.random() * (attributeStats[i].numericStats.max - attributeStats[i].numericStats.min) + attributeStats[i].numericStats.min;
				}
				    randomData.add(new weka.core.DenseInstance(1.0, randomInst));
			}
		}
		for (int i = 1; i <= maxNumClusters; i++) {
			kMeans = new weka.clusterers.SimpleKMeans();
			kMeans.setNumClusters(i);
			kMeans.buildClusterer(randomData);
		    expected_log_W_k[i] += Math.log(kMeans.getSquaredError() / data.numInstances());
		  }
		
	for (int i = 1; i <= maxNumClusters; i++) {
		
		System.out.println(i + " " + ((expected_log_W_k[i] / numRuns) - log_W_k[i]));
		} 
	
}
}
