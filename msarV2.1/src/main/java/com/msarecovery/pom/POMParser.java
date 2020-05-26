package com.msarecovery.pom;

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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class POMParser {
	
	public POMParser() {
		
	}
	
	public List<File> dependencyPOMFiles(String directoryName) throws IOException{
		File directory = new File(directoryName);
        String pomName;
        List<File> resultList = new ArrayList<File>();
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
        		if (file.isFile()) {
        			pomName=file.getAbsolutePath();
        			if(pomName.endsWith("pom.xml")) {
        				resultList.add(file);
        			}
        	    }else if (file.isDirectory()) {
        			resultList.addAll(dependencyPOMFiles(file.getAbsolutePath()));
        		}
        		
        }
        return resultList;
	}
	
	public LinkedList<String> getPomMicroservices (String directoryName) throws IOException, XmlPullParserException{
		MavenXpp3Reader reader = new MavenXpp3Reader();
		List<File> pomFiles=dependencyPOMFiles(directoryName);
		LinkedList<String> pomMicroservices= new LinkedList<String>();
		for(int i=0; i<pomFiles.size(); i++) {
			String tmpFile=pomFiles.get(i).toString();
			if(tmpFile.endsWith("pom.xml")) {
				Model model = reader.read(new FileReader(tmpFile));
				for(int j=0; j<model.getModules().size(); j++) {
					if(!model.getModules().get(j).toString().contains("example")) {
						pomMicroservices.add(model.getModules().get(j).toString());
					}
				}
			}
		}
		Set<String> hs = new HashSet<>();
        hs.addAll(pomMicroservices);
        pomMicroservices.clear();
        pomMicroservices.addAll(hs);
        return pomMicroservices;
	}
	
	public LinkedList<String> getPomMicroservicesArtifact (String directoryName) throws IOException, XmlPullParserException{
		MavenXpp3Reader reader = new MavenXpp3Reader();
		List<File> pomFiles=dependencyPOMFiles(directoryName);
		LinkedList<String> pomMicroservices= new LinkedList<String>();
		for(int i=0; i<pomFiles.size(); i++) {
			String tmpFile=pomFiles.get(i).toString();
			if(tmpFile.endsWith("pom.xml")) {
				Model model = reader.read(new FileReader(tmpFile));
				pomMicroservices.add(model.getArtifactId().toString());
			}
		}
		Set<String> hs = new HashSet<>();
        hs.addAll(pomMicroservices);
        pomMicroservices.clear();
        pomMicroservices.addAll(hs);
        return pomMicroservices;
	}
	
	public LinkedList<String> getPomMicroservicesDescription (String directoryName) throws IOException, XmlPullParserException{
		MavenXpp3Reader reader = new MavenXpp3Reader();
		List<File> pomFiles=dependencyPOMFiles(directoryName);
		LinkedList<String> pomMicroservices= new LinkedList<String>();
		for(int i=0; i<pomFiles.size(); i++) {
			String tmpFile=pomFiles.get(i).toString();
			if(tmpFile.endsWith("pom.xml")) {
				Model model = reader.read(new FileReader(tmpFile));
				pomMicroservices.add(model.getDescription().toString());
			}
		}
		Set<String> hs = new HashSet<>();
        hs.addAll(pomMicroservices);
        pomMicroservices.clear();
        pomMicroservices.addAll(hs);
        return pomMicroservices;
	}
	
	public LinkedList<String> getPomMicroservicesDependecy (String directoryName) throws IOException, XmlPullParserException{
		LinkedList<String> microservices=getPomMicroservices(directoryName);
		if(microservices.isEmpty()) {
			LinkedList<String> microservicesArtifact=getPomMicroservicesArtifact(directoryName);
			microservices.addAll(microservicesArtifact);
			if(microservicesArtifact.isEmpty()) {
				LinkedList<String> microservicesDescription=getPomMicroservicesDescription(directoryName);
				microservices.addAll(microservicesDescription);
			}
		}
		List<File> pomFiles=dependencyPOMFiles(directoryName);
		LinkedList<String> microServicesDependencies= new LinkedList<String>();
		BufferedReader br=null;
		String line;
		for(int i=0; i< pomFiles.size(); i++) {
			String tmpPath=pomFiles.get(i).toString();
			if(tmpPath.endsWith("pom.xml")) {
				try {
					br = new BufferedReader(new FileReader(tmpPath));
					while ((line = br.readLine()) != null) {
						for(int j=0; j<microservices.size(); j++) {
							String tmpMicroservice=microservices.get(j).toString();
							if(line.contains(tmpMicroservice)) {
								for(int k=0;k<microservices.size(); k++) {
									String tmpMicroToPath=microservices.get(k).toString();
									//if(tmpPath.contains(tmpMicroToPath) && (tmpMicroservice!=tmpMicroToPath|| !microServicesDependencies.contains("Microservice: "+ tmpMicroservice))) {
									if(tmpPath.contains(tmpMicroToPath) && !microServicesDependencies.contains("Microservice: "+ tmpMicroservice)) {
										microServicesDependencies.add("Microservice: "+ tmpMicroservice);
										microServicesDependencies.add("Dependency of " + tmpMicroservice + " is: " + tmpMicroToPath);
									}
									if(!tmpPath.contains(tmpMicroToPath)) {
										microServicesDependencies.add("Microservice: "+ tmpMicroservice);
										microServicesDependencies.add("Dependency of " + tmpMicroservice + " is: No dependecy");
									}
								}
							}
						}
					}
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						}catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return microServicesDependencies;
	}
	
	public LinkedList<String> getPomMicroservicesFF (String directoryName) throws IOException, XmlPullParserException{
		LinkedList<String> microservices=getPomMicroservices(directoryName);
		List<File> pomFiles=dependencyPOMFiles(directoryName);
		LinkedList<String> microServicesDependencies= new LinkedList<String>();
		BufferedReader br=null;
		String line;
		for(int i=0; i< pomFiles.size(); i++) {
			String tmpPath=pomFiles.get(i).toString();
			if(tmpPath.endsWith(".java")) {
				try {
					br = new BufferedReader(new FileReader(tmpPath));
					while ((line = br.readLine()) != null) {
						for(int j=0; j<microservices.size(); j++) {
							String tmpMicroservice=microservices.get(j).toString();
							if(line.contains(tmpMicroservice)) {
								for(int k=0;k<microservices.size(); k++) {
									String tmpMicroToPath=microservices.get(k).toString();
									if(tmpPath.contains(tmpMicroToPath)) {
										microServicesDependencies.add(tmpMicroservice);
										microServicesDependencies.add(pomFiles.get(i).getAbsolutePath());
									}
								}
							}
						}
					}
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						}catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return microServicesDependencies;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public HashMap<String, String> pomDependencyFiles(String directoryName) throws IOException, XmlPullParserException{
		MavenXpp3Reader reader = new MavenXpp3Reader();
		List<File> pomFiles = dependencyPOMFiles(directoryName);
		LinkedList<String> dependencies= new LinkedList<String>();
		LinkedList<String> tmpSizeList= new LinkedList<String>();
		List<String> dockerMicroservices = new ArrayList<String>();
        dockerMicroservices=getPomMicroservicesDependecy(directoryName);
        HashMap<String, String> hmap = new HashMap<String, String>();
        String microservice;
        String artifactKeywords="";
        int microLength;
        int micro;
        int flag=0;
		for(int i=0; i<pomFiles.size(); i++) {
			String path=pomFiles.get(i).getAbsolutePath();
			if(path.endsWith("pom.xml")) {
				Model model = reader.read(new FileReader(path));
				String nameMS=model.getName();
				String nameMS2=model.getArtifactId();
				if(nameMS!=null || nameMS2!=null) {
					if(nameMS==null) {
						nameMS=model.getArtifactId();
					}
					nameMS=nameMS.replaceAll("null", "");
					for(int m=0; m<dockerMicroservices.size(); m++) {
						microservice=dockerMicroservices.get(m);
						if(microservice.contains("Microservice")) {
							microLength=microservice.length();
							micro=microservice.indexOf(":");
							microservice= microservice.substring((micro+1), microLength);
							microservice=microservice.replaceAll("\\s+","");
							if(microservice.equals(nameMS) && !dependencies.contains(nameMS)) {
								dependencies.add(nameMS);
								for(int j=0; j<model.getDependencies().size(); j++) {
								/*In Spring, the Spring cloud provides tools 
								 * for developers to quickly build some of the common patterns 
								 * in distributed systems. */
									String dependency=model.getDependencies().get(j).getArtifactId();
									artifactKeywords=artifactKeywords+ dependency+ ", ";	
								}
								artifactKeywords=artifactKeywords.replace("-", ",");
								String[] ary = artifactKeywords.split(",");
							@SuppressWarnings("rawtypes")
								HashMap map = new HashMap();
							@SuppressWarnings({ "rawtypes", "unchecked" })
								LinkedList newArray = new LinkedList(Arrays.asList(ary));
							@SuppressWarnings("rawtypes")
								Iterator iterator = newArray.iterator();
								while (iterator.hasNext()) {
									String word = (String) iterator.next();
									word=word.replace(" ", "");
									word=word.replace("null", "");
									if(map.containsKey(word)) {
										Integer count = (Integer)map.get(word);
										map.put(word, new Integer(count.intValue() + 1));
									} else {
										map.put(word, new Integer(1));
									}
								}
							@SuppressWarnings({ "rawtypes" })
								ArrayList arraylist = new ArrayList(map.keySet());
								Collections.sort(arraylist);
								for (int k = 0; k < arraylist.size(); k++) {
									String key = (String)arraylist.get(k);
									Integer count = (Integer)map.get(key);
									String tmpMS= "Microservice: "+key;
									if(count<=1 && key.equals("")==false && dockerMicroservices.contains(tmpMS)==false) {
										dependencies.add(key);
									}
								}
								if(flag==0) {
									int tmpSize=dependencies.size();
									String key=dependencies.get(0);
									String values=Arrays.toString(dependencies.subList(1, tmpSize).toArray());
									hmap.put(key, values);
									tmpSizeList.addFirst(String.valueOf(tmpSize));
								}
								else {
									int tmpLast=dependencies.size();
									String tmpB=tmpSizeList.get(0);
									int tmpBeginnig=Integer.parseInt(tmpB);
									String key=dependencies.get(tmpBeginnig);
									String values=Arrays.toString(dependencies.subList(tmpBeginnig+1, tmpLast).toArray());
									hmap.put(key, values);
									tmpSizeList.addFirst(String.valueOf(tmpLast));
								}
								flag=1;
								artifactKeywords="";
							}
						}
					}
				}
			}
		}
		return hmap;
	}
	
	public LinkedList<String> pomGetDependencyFiles (String directoryName) throws IOException, XmlPullParserException {
		HashMap<String, String> hmap=pomDependencyFiles(directoryName);
		HashMap<String, String> hmap2=pomDependencyFiles(directoryName);
		LinkedList<String> dependenciesLink= new LinkedList<String>();
		List<String> myList2 = null;
		Set set = hmap.entrySet();
		Set set2 = hmap2.entrySet();
	    Iterator iterator = set.iterator();
	    Map.Entry mentry2 = null;
	    String keyword1="";
	    int flag=0;
	    while(iterator.hasNext()) {
	    	Map.Entry mentry = (Map.Entry)iterator.next();
	    	//System.out.println("MicroServiceA: "+mentry.getKey());
	    	String tmp=(String) mentry.getValue();
	    	tmp=tmp.replace("[", "");
	    	tmp=tmp.replace("]", "");
	    	//String[] ary = tmp.split("");
	    	List<String> myList = new ArrayList<String>(Arrays.asList(tmp.split(",")));
	    	Iterator iterator2 = set2.iterator();
	    	while(iterator2.hasNext()) {
	    		mentry2 = (Map.Entry)iterator2.next();
	    		if(mentry2.getKey().equals(mentry.getKey())==false) {
	    			String tmp2=(String) mentry2.getValue();
	    			tmp2=tmp2.replace("[", "");
	    			tmp2=tmp2.replace("]", "");
	    			//String[] ary2 = tmp2.split("");
	    			myList2 = new ArrayList<String>(Arrays.asList(tmp2.split(",")));
	    			for(int i=0; i<myList.size(); i++) {
	    				for(int j=0; j<myList2.size(); j++) {
	    					keyword1=myList.get(i).toString();
	    					keyword1=keyword1.replace(" ", "");
	    					String keyword2=myList2.get(j).toString();
	    					keyword2=keyword2.replace(" ", "");
	    					if(keyword1.equals(keyword2) && flag==0) {
	    						dependenciesLink.add(mentry.getKey().toString());
	    						dependenciesLink.add(mentry2.getKey().toString());
	    						dependenciesLink.add(keyword1);
	    					}
	    				}
	    			}
	    		}
	    	}
	    	flag=1;
	    	for(int a=0; a<myList.size(); a++) {
				for(int b=0; b<myList2.size(); b++) {
					keyword1=myList.get(a).toString();
					keyword1=keyword1.replace(" ", "");
					String keyword2=myList2.get(b).toString();
					keyword2=keyword2.replace(" ", "");
					if(dependenciesLink.size()>=0 && !dependenciesLink.contains(keyword1) && flag==1) {
						dependenciesLink.add(mentry.getKey().toString());
						dependenciesLink.add(mentry2.getKey().toString());
						dependenciesLink.add(keyword1);
					}
				}
			}
	    }
	    return dependenciesLink;
	}
	
	public LinkedList<String> getFrameworks (String directoryName) throws IOException, XmlPullParserException {
		HashMap<String, String> hmap=pomDependencyFiles(directoryName);
		HashMap<String, String> hmap2=pomDependencyFiles(directoryName);
		LinkedList<String> frameworks= new LinkedList<String>();
		List<String> myList2 = null;
		Set set = hmap.entrySet();
		Set set2 = hmap2.entrySet();
	    Iterator iterator = set.iterator();
	    Map.Entry mentry2 = null;
	    String keyword1="";
	    int flag=0;
	    while(iterator.hasNext()) {
	    	Map.Entry mentry = (Map.Entry)iterator.next();
	    	String tmp=(String) mentry.getValue();
	    	tmp=tmp.replace("[", "");
	    	tmp=tmp.replace("]", "");
	    	List<String> myList = new ArrayList<String>(Arrays.asList(tmp.split(",")));
	    	Iterator iterator2 = set2.iterator();
	    	while(iterator2.hasNext()) {
	    		mentry2 = (Map.Entry)iterator2.next();
	    		if(mentry2.getKey().equals(mentry.getKey())==false) {
	    			String tmp2=(String) mentry2.getValue();
	    			tmp2=tmp2.replace("[", "");
	    			tmp2=tmp2.replace("]", "");
	    			myList2 = new ArrayList<String>(Arrays.asList(tmp2.split(",")));
	    			for(int i=0; i<myList.size(); i++) {
	    				for(int j=0; j<myList2.size(); j++) {
	    					keyword1=myList.get(i).toString();
	    					keyword1=keyword1.replace(" ", "");
	    					String keyword2=myList2.get(j).toString();
	    					keyword2=keyword2.replace(" ", "");
	    					if(keyword1.equals(keyword2) && flag==0) {
	    						frameworks.add(keyword1);
	    					}
	    				}
	    			}
	    		}
	    	}
	    	flag=1;
	    	for(int a=0; a<myList.size(); a++) {
				for(int b=0; b<myList2.size(); b++) {
					keyword1=myList.get(a).toString();
					keyword1=keyword1.replace(" ", "");
					String keyword2=myList2.get(b).toString();
					keyword2=keyword2.replace(" ", "");
					if(frameworks.size()>=0 && !frameworks.contains(keyword1) && flag==1) {
						frameworks.add(keyword1);
					}
				}
			}
	    }
	    Set<String> hs = new HashSet<>();
        hs.addAll(frameworks);
        frameworks.clear();
        frameworks.addAll(hs);
	    return frameworks;
	}
	
	public static List<File> getJavaFiles (String directoryName) throws IOException {
        File directory = new File(directoryName);
        String javaName;
        List<File> resultList = new ArrayList<File>();
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
        		if (file.isFile()) {
        			javaName=file.getAbsolutePath();
        			if(javaName.toLowerCase().contains(".java".toLowerCase())==true) {
        				if(javaName.toLowerCase().contains("/main".toLowerCase())==true) {
        					resultList.add(file);
        				}
        			}
        	    }else if (file.isDirectory()) {
        			resultList.addAll(getJavaFiles(file.getAbsolutePath()));
        		}
        		
        }
        return resultList;
    }
	
	public LinkedList<String> dependencyFileFolder (String directoryName) throws IOException, XmlPullParserException {
		List<File> javaFiles=getJavaFiles(directoryName);
		LinkedList<String> famework=getFrameworks(directoryName);
		LinkedList<String> dependecyFF= new LinkedList<String>();
		List<String> dockerMicroservices = new ArrayList<String>();
		BufferedReader br = null;
		dockerMicroservices=getPomMicroservicesDependecy(directoryName);
        String microservice;
        int microLength;
        int micro;
        int flag=0;
        for(int m=0; m<dockerMicroservices.size(); m++) {
			microservice=dockerMicroservices.get(m);
			if(microservice.contains("Microservice")) {
        		microLength=microservice.length();
        		micro=microservice.indexOf(":");
        		microservice= microservice.substring((micro+1), microLength);
        		microservice=microservice.replaceAll("\\s+","");
        		for(int j=0; j<javaFiles.size(); j++) {
        			for(int f=0; f<famework.size(); f++) {
        				if(javaFiles.get(j).toString().contains(microservice) && javaFiles.get(j).toString().endsWith(".java") && javaFiles.get(j).toString().contains("/main/")) {
        					File file = new File(javaFiles.get(j).getAbsolutePath());
        					try {
        						br = new BufferedReader(new FileReader(file));
        						String st;
        						while ((st = br.readLine()) != null) {
        							String tmpF=famework.get(f).toString();
        							if(st.contains(tmpF)) {
        								flag=1;
        								break;
        							}
        						}
        						if(flag==1) {
        							if(!dependecyFF.contains(javaFiles.get(j).getAbsolutePath())) {
        								dependecyFF.add(microservice);
        								dependecyFF.add(javaFiles.get(j).getAbsolutePath());
        								flag=0;
        							}
        						}
        						}catch (FileNotFoundException e) {
        							e.printStackTrace();
        						} catch (IOException e) {
        							e.printStackTrace();
        						} finally {
        							if (br != null) {
        								try {
        									br.close();
        								}catch (IOException e) {
        									e.printStackTrace();
        								}
        							}
        						}
        					}
        				}
        			}
        		}
        	}
        return dependecyFF;
        }
	
}

