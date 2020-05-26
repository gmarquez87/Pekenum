package com.msarecovery;

import weka.clusterers.SimpleKMeans;
import weka.core.*;
import weka.core.Utils;
import weka.core.matrix.DoubleVector;
import weka.core.matrix.Matrix;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import java.io.*;
import java.util.Random;


public class Clusters {

	private static long now = System.currentTimeMillis();

	/** random number generator */
	private static Random m_rr = null;
	
	/** number of clusters selected by the user or cross validation */
	private static int m_num_clusters;
 	private static int iters = 10;
 	private static Instances trainingData = null;
	private static int max = -1;
	private static int minNumClusters = 0;
	private static int B = -1;
	private static boolean verbose = false,clazz = false;

	public static void main(String[] args) {
		int seed = 100;
		File training = null;

		m_num_clusters = 2;
		for (int i=0;i<args.length;i++) {
			if (args[i].equals("-t")) {			// Q. training ARFF?
				++i;
				training = new File(args[i]);
			}
			else if (args[i].equals("-i")) {
				++i;
				if (i >= args.length) {
					System.out.println("ClusterNum: Missing args");
					return;
				}
				iters = new Integer(args[i]).intValue();
			}
			else if (args[i].equals("-S")) {
				++i;
				if (i >= args.length) {
					System.out.println("ClusterNum: Missing arg for seed");
					return;
				}
				seed = new Integer(args[i]).intValue();
			}
			else if (args[i].equals("-M")) {
				++i;
				if (i >= args.length) {
					System.out.println("ClusterNum: Missing arg for max clusters number parm -M");
					return;
				}
				max = new Integer(args[i]).intValue();
			}
			else if (args[i].equals("-B")) {
				++i;
				if (i <- args.length) {
					System.out.println("GapClusterNum: Missing arg for number of reference datasets -B");
					B = new Integer(args[i]).intValue();
				}
			}
			else if (args[i].equals("-N")) {
				++i;
				m_num_clusters = new Integer(args[i]).intValue();
			}
			else if (args[i].equals("-v")) {
				verbose = true;
			}
			else if (args[i].equals("-c")) {
				clazz = true;			// Do not remove last 'class' attribute
			}
		}
		m_rr = new Random(seed);
		try {
			BufferedReader trainingRdr = new BufferedReader(new FileReader("csvToWekaSemanticARFF.arff"));
			Instances inst = new Instances(trainingRdr);
			//inst.setClassIndex(inst.numAttributes() - 1);
    		if (!clazz) {
				// remove the last column (class attribute)
    			Remove r = new Remove();
    			r.setAttributeIndices("last");
    			r.setInputFormat(inst);
    			inst = Filter.useFilter(inst, r);
    		}
			Bounds bounds = getBounds(inst);
			// run k means iters times and choose best solution
			if (max == -1) max = m_num_clusters;
			if (B == -1) B = max;
			double minError = Double.MAX_VALUE;
			GapKMeans gkm,bestGkm = null;
			double bestSqE = Double.MAX_VALUE;
//			m_rr = new Random(seed);
			double[] Wk = new double[max-m_num_clusters+1];
			double[][] Wkref = new double[B][max-m_num_clusters+1];
			double[] gaps = new double[max-m_num_clusters+1];
			double[] sk = new double[max-m_num_clusters+1];
			int bestK = -1;
			for (int k = m_num_clusters; k <= max; k++) {
				for (int j=0; j<iters;j++) {
					gkm = new GapKMeans();
					gkm.setSeed(m_rr.nextInt());
					gkm.setNumClusters(k);
					gkm.buildClusterer(inst);
					if (gkm.getSquaredError() < bestSqE) {
						bestSqE = gkm.getSquaredError();
						bestGkm = gkm;
					}
				}
				Wk[k-m_num_clusters] = getWk(bestGkm);
				bestSqE = Double.MAX_VALUE;
				bestGkm = null;
				// Monte Carlo reference data
				for (int b=0;b<B;b++) {
					for (int i=0;i<iters;i++) {
						gkm = new GapKMeans();
						gkm.setSeed(m_rr.nextInt());
						gkm.setNumClusters(k);
						System.out.println(getReferenceInstances(bounds,inst));
						gkm.buildClusterer(getReferenceInstances(bounds,inst));
						if (gkm.getSquaredError() < bestSqE) {
							bestSqE = gkm.getSquaredError();
							bestGkm = gkm;
						}
					}
					Wkref[b][k-m_num_clusters] = getWk(bestGkm);
					bestSqE = Double.MAX_VALUE;
					bestGkm = null;	
				} 
				// Calculate the gaps 
				for (int b=0;b<B;b++) {
					gaps[k-m_num_clusters] += Math.log(Wkref[b][k-m_num_clusters]);
				}
				double l_bar = gaps[k-m_num_clusters]/B;
				gaps[k-m_num_clusters] = l_bar - Math.log(Wk[k-m_num_clusters]);
		
				// need std and sk
				sk[k-m_num_clusters] = 0d;
				for (int b=0;b<B;b++) {
					sk[k-m_num_clusters] += Math.pow(Math.log(Wkref[b][k-m_num_clusters]) - l_bar,2);				
				}
				sk[k-m_num_clusters] = Math.sqrt(sk[k-m_num_clusters]/B);
				sk[k-m_num_clusters] = sk[k-m_num_clusters] * Math.sqrt(1.0d + 1.0d/B);
				/* find best k */
				if (k > m_num_clusters) {		// Do we have at least one +1?
//					if (gaps[k-m_num_clusters] >= gaps[k-m_num_clusters+1] - sk[k-m_num_clusters+1]) {
					if (gaps[k-1-m_num_clusters] >= gaps[k-m_num_clusters] - sk[k-m_num_clusters]) {
						bestK = k;
						break;
					}			
				}
			}
			System.out.println("Best k is " + bestK + " elapsed " + (System.currentTimeMillis() - now));
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * If the distance d is the squared Euclidean distance, then Wk is the pooled 
	 * within-cluster sum of squares around the cluster means (the factor 2 makes this
	 * work exactly). The sample size n is suppressed in this notation.
	 */	
	private static double getWk(GapKMeans gkm) {
		double[] d = gkm.getSquaredErrors();	// Same as squared Euclidean distance(?)
		double[] sizes = gkm.getClusterSizes();
		for (int i=0;i<gkm.getNumClusters();i++) {
			d[i] = d[i]/(2*sizes[i]);		// is now D/2n
		}
		return Utils.sum(d);    // sum of D/2n over all clusters
	}
	
	private static Instances getReferenceInstances(Bounds bounds,Instances inst) {
		Instances ref = new Instances(inst,inst.numInstances());
		double[] max = bounds.getMax();
		double[] min = bounds.getMin();		
		for (int i=0;i<inst.numInstances();i++) {
			DenseInstance instance = new DenseInstance(min.length);
			for (int j=0;j<min.length;j++) {
				instance.setValue(j,m_rr.nextDouble()*(max[j]-min[j])+min[j]);
			}
			ref.add(instance);
		}
		return ref;
	}
	
	private static Bounds getBounds(Instances inst) {
		double[] max = new double[inst.numAttributes()];
		double[] min = new double[inst.numAttributes()];
		for (int i=0;i<min.length;i++) {
			min[i] = Double.MAX_VALUE;
		}
		for (int i=0;i<inst.numInstances();i++) {
			Instance row = inst.instance(i);
			for (int j=0;j<row.numAttributes();j++) {
     			if (row.attribute(j).isNumeric()) {
     				double v = row.value(j);
     				if (v > max[j]) max[j] = v;
     				if (v < min[j]) min[j] = v;
     			}
			}
		}
		return new Bounds(max,min);
	}
}

class Bounds {
	double[] max,min;
	
	public Bounds(double [] max,double [] min) {
		this.max = max;
		this.min = min;
	}
	
	public double[] getMax() { return max; }
	public double[] getMin() { return min; }
}

class GapKMeans extends SimpleKMeans {

	public double[] getSquaredErrors() {
		return m_squaredErrors;
	}
}