package com.msarecovery.microservices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.dkpro.similarity.algorithms.api.SimilarityException;
import org.dkpro.similarity.algorithms.api.TextSimilarityMeasure;
import org.dkpro.similarity.algorithms.lexical.ngrams.WordNGramJaccardMeasure;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import com.msarecovery.dockerparser.DockerReader;
import com.msarecovery.dockerparser.GetContainerLog;
import com.msarecovery.pom.POMParser;
import com.msarecovery.semantic.DocumentParser;
import com.msarecovery.semantic.Rake;
import com.msarecovery.semantic.RakeLanguages;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

import com.msarecovery.gradle.GladeParser;

public class Microservice {
	ArrayList<String> microservices = new ArrayList<String> ();
	DocumentParser dp = new DocumentParser("");
	String directoryName;
	List<String> docker;
	
	public Microservice(String directoryName, List<String> docker) throws IOException, XmlPullParserException {
		this.directoryName=directoryName;
		this.docker=docker;
	}
	
	
}
