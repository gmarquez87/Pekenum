package com.msarecovery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class ClusterNum {
	/** random number generator */
	private static Random m_rr;
	
	/** number of clusters selected by the user or cross validation */
	private static int m_num_clusters;
 
 	private static Instances trainingData = null;

	public static void main(String[] args) {
		int seed = 500;
		File training = null;

		m_num_clusters = 4;
		for (int i=0;i<args.length;i++) {
			if (args[i].equals("-t")) {			// Q. training ARFF?
				i++;
				training = new File(args[i]);
			}
			else if (args[i].equals("-S")) {
				++i;
				seed = new Integer(args[i]).intValue();
			}	
			else if (args[i].equals("-N")) {
				++i;
				m_num_clusters = new Integer(args[i]).intValue();
			}
		}
		try {
			BufferedReader trainingRdr = new BufferedReader(new FileReader("csvToWekaSemanticARFF.arff"));
			Instances inst = new Instances(trainingRdr);
//			inst.setClassIndex(inst.numAttributes() - 1);
		
			m_rr = new Random(seed);
		
			// run k means 2000 times and choose best solution
			SimpleKMeans bestK = null;
			double bestSqE = Double.MAX_VALUE;
			for (int i = 0; i < 2000; i++) {
				SimpleKMeans sk = new SimpleKMeans();
				sk.setSeed(m_rr.nextInt());
				sk.setNumClusters(m_num_clusters);
				sk.setDisplayStdDevs(true);
				sk.buildClusterer(inst);
				if (sk.getSquaredError() < bestSqE) {
					bestSqE = sk.getSquaredError();
					bestK = sk;
					m_num_clusters = bestK.numberOfClusters();		// mjh - switch to current best cluster num
				}
				//System.out.println(bestSqE + " " + bestK.numberOfClusters());
			}
			System.out.println("Cluster num: " + bestK.numberOfClusters());
			System.out.println("Squared err: " + bestK.getSquaredError());
		}
		catch (Exception ex) { ex.printStackTrace(); }
	}
}