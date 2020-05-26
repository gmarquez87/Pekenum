package com.msarecovery;

import java.io.File;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.Cobweb;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class GSM {
	/**
	   * Expects an ARFF file as first argument.
	   *
	   * @param args        the commandline arguments
	   * @throws Exception  if something goes wrong
	   */
	  public static void main(String[] args) throws Exception {
	    // load data
	    ArffLoader loader = new ArffLoader();
	    loader.setFile(new File("csvToWekaSemanticARFF.arff"));
	    Instances structure = loader.getStructure();

	    // train Cobweb
	    Cobweb cw = new Cobweb();
	    cw.buildClusterer(structure);
	    Instance current;
	    while ((current = loader.getNextInstance(structure)) != null)
	      cw.updateClusterer(current);
	    cw.updateFinished();

	    // output generated model
	    System.out.println(cw);
	  }

}
