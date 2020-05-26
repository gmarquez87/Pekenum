package com.msarecovery.dockerparser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.msarecovery.pom.POMParser;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class DockerReader {
	 public String file;
	 
	 public DockerReader(String file){
	        this.file = file;
	    }
	 
	 public ArrayList<String> parseDocker(){
		 ArrayList<String> docker = new ArrayList<String>();
		 ArrayList<String> microServices = new ArrayList<String>();
		 ArrayList<String> microServicesPosition = new ArrayList<String>();
		 ArrayList<String> microServicesDependencies = new ArrayList<String>();
		 List<String> dockerTMPDP = null;
		 String iMicroService;
		 int end=0;
	     ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
	     mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        
	     try {
	    	 DockerComposeFile dockerComposeFile = mapper.readValue(new File(file), DockerComposeFile.class);
	         String services= ReflectionToStringBuilder.toString(dockerComposeFile, ToStringStyle.SIMPLE_STYLE);
	         services= services.replace(',', ' ');
	         services= services.replace('{', ' ');
	         services= services.replace('}', ' ');
	         String[] listServices = services.split(" ");
	         for (int i=0; i<listServices.length; i ++){
	        	 int index= listServices[i].indexOf("=");
	        	 if(index!=-1) {
	        		 String nameMS=listServices[i].substring(0, index);
	        		 microServices.add(nameMS);
	        	 }
	         }
	         Scanner s = new Scanner(new File(getFile()));
	         while (s.hasNext()){
	        	 docker.add(s.next());
	         }
	         s.close();
	         for(int i=0; i<microServices.size(); i++){
	        	 String microService=microServices.get(i);
	        	 microServicesPosition.add(microService);
	        	 for(int k=0; k<docker.size(); k++){
	        		 if(docker.get(k).toLowerCase().contains(microService)){
	        			 microServicesPosition.add(Integer.toString(k));
	        			 break;
	        		 }
	        	 }
	         }
	         for(int a=0; a<microServicesPosition.size(); a++){
	        	 int count=0;
	        	 if(a==0) {
	        		 iMicroService=microServicesPosition.get(a);
	        	 }else {
	        		 a=a+1;
	        		 iMicroService=microServicesPosition.get(a);
	        		 if(a==microServicesPosition.size()-2) {
	        			 int beginning=Integer.parseInt(microServicesPosition.get(a+1));
	        			 microServicesDependencies.add("Microservice: "+ iMicroService);
						 //System.out.println("Microservice: "+ iMicroService);
	    	        	 for(int t=0; t<docker.subList(beginning, docker.size()).size(); t ++){
	    	        		 for(int f=0; f<microServices.size(); f++){
	    	        			 String tmp2=iMicroService;
	    	        			 String tmp1=docker.subList(beginning, docker.size()).get(t);
	    	        			 tmp1=tmp1.toLowerCase();
	    	        			 tmp1=tmp1.replaceAll("_","-");
	    	        			 if(docker.subList(beginning, docker.size()).get(t).contains(microServices.get(f))) {
	    	        				 tmp1=tmp1.replace(":", "");
	    	        				 int uno=tmp1.length();
	    	        				 int dos=tmp2.length();
	    	        				 String tmpMicro=microServices.get(f).toString();
	    	        				 if(uno!=dos && tmp1.contains(iMicroService)==false) {
	    	        					 if(!microServicesDependencies.contains("Dependency of " + iMicroService + " is: " + tmpMicro)) {
	    	        						 if(count>0) {
	    	        							 microServicesDependencies.add("Microservice: "+ iMicroService);
	    	        							 microServicesDependencies.add("Dependency of " + iMicroService + " is: " + tmpMicro);
	    	        							 count++;
	    	        						 }else {
	    	        							 microServicesDependencies.add("Dependency of " + iMicroService + " is: " + tmpMicro);
	    	        							 count++;
	    	        						 }
	    	        					 }
	    	        				 }
	    	        			 }
	    	        		 }
	    	        	 }
	    	        	 if(count==0) {
	            			 microServicesDependencies.add("Dependency of " + iMicroService + " is: " + "No dependecy");
	    	        	 }
	        		 }
	        	 }
	        	 int beginning=Integer.parseInt(microServicesPosition.get(a+1));
	        	 if(a+3<microServicesPosition.size()) {
	        		 end=Integer.parseInt(microServicesPosition.get(a+3));
	        	 }else {
	        		 break;
	        	 }
	        	 microServicesDependencies.add("Microservice: "+iMicroService);
	        	 List<String> dockerTMP=docker.subList(beginning, end);
	        	 int dependency=dockerTMP.indexOf("depends_on:");
	        	 int ports=dockerTMP.indexOf("ports:");
	        	 int logging=dockerTMP.indexOf("logging:");
	        	 
	        	 if(dependency<0) {
	        		 microServicesDependencies.add("Dependency of " + iMicroService + " is: " + "No dependecy");
	        	 }else {
	        		 for(int b=0; b<dockerTMP.size(); b ++){
		        		 for(int c=0; c<microServices.size(); c++){
		        			 String tmp2=iMicroService;
		        			 String tmp1=docker.subList(beginning, end).get(b);
		        			 tmp1=tmp1.toLowerCase();
		        			 tmp1=tmp1.replaceAll("_","-");
		        			 String tmpContains=microServices.get(c).toString();
		        			 if(tmp1.contains(tmpContains)) {
		        				 tmp1=tmp1.replace(":", "");
		        				 int uno=tmp1.length();
		        				 int dos=tmp2.length();
		        				 String tmpMicro=microServices.get(c).toString();
		        				 if(uno!=dos && tmp1.contains(iMicroService)==false) {
		        					 if(!microServicesDependencies.contains("Dependency of " + iMicroService + " is: " + tmpMicro)) {
		        						 if(ports>0) {
		        							 dockerTMPDP=dockerTMP.subList(dependency, ports);
		        							 for(int t=0; t<dockerTMPDP.size(); t++) {
			        							 if(dockerTMPDP.get(t).contains(tmpMicro)) {
			        								 microServicesDependencies.add("Dependency of " + iMicroService + " is: " + tmpMicro);
			        							 }
			        						 }
		        						 }else {
		        							 if(logging>0) {
		        								 dockerTMPDP=dockerTMP.subList(dependency, logging);
		        								 for(int t=0; t<dockerTMPDP.size(); t++) {
		        									 if(dockerTMPDP.get(t).contains(tmpMicro)) {
		        										 microServicesDependencies.add("Dependency of " + iMicroService + " is: " + tmpMicro);
		        									 }
		        								 }
		        							 }
		        						 }
		        					 }
		        				 }
		        			 }
		        		 }
		        	 }
	        	 }
	        	 dependency=0;
	        	 ports=0;
	        	 logging=0;
	         }
	     } catch (Exception e) {
	            e.printStackTrace();
	        }
	     
	     return microServicesDependencies;
	    }
	 
	 public LinkedList<String> checkApplicationYAML (List<String> microServicesDependencies, List<String> allFiles) {
		 LinkedList<String> ndMicroservicesTMP= new LinkedList<String>();
		 LinkedList<String> microD= new LinkedList<String>();
		 LinkedList<String> microND= new LinkedList<String>();
		 LinkedList<String> microFINAL= new LinkedList<String>();
		 String microserviceTMP;
		 String line = " ";
		 int microLength;
		 int micro;
		 BufferedReader br = null;
		 for(int i=0; i<microServicesDependencies.size(); i++) {
			 String nd=microServicesDependencies.get(i).toString();
			 if(nd.contains("No dependecy")) {
				 ndMicroservicesTMP.add(microServicesDependencies.get(i-1).toString());
			 }
		 }
		 for(int j=0; j<microServicesDependencies.size(); j++) {
			 microserviceTMP=microServicesDependencies.get(j);
				if(microserviceTMP.contains("Microservice")) {
					microLength=microserviceTMP.length();
					micro=microserviceTMP.indexOf(":");
					microserviceTMP= microserviceTMP.substring((micro+1), microLength);
					microserviceTMP=microserviceTMP.replaceAll("\\s+","");
					microserviceTMP=microserviceTMP.toLowerCase();
					microD.add(microserviceTMP);
				}
		 }
		 for(int k=0; k<ndMicroservicesTMP.size(); k++) {
			 microserviceTMP=ndMicroservicesTMP.get(k);
				if(microserviceTMP.contains("Microservice")) {
					microLength=microserviceTMP.length();
					micro=microserviceTMP.indexOf(":");
					microserviceTMP= microserviceTMP.substring((micro+1), microLength);
					microserviceTMP=microserviceTMP.replaceAll("\\s+","");
					microserviceTMP=microserviceTMP.toLowerCase();
					microND.add(microserviceTMP);
				}
		 }
		 for(int l=0; l<allFiles.size(); l ++) {
			 String tmpPath=allFiles.get(l).toString();
			 if(tmpPath.endsWith("application.yml")) {
				 for(int n=0; n<microND.size(); n++) {
					 String tmpNDM=microND.get(n).toString();
					 try {
			        	br = new BufferedReader(new FileReader(tmpPath));
			        	while ((line = br.readLine()) != null) {
			        		if(line.contains(tmpNDM)) {
			        			for(int m=0; m<microD.size(); m++) {
			        				String tmpDM=microD.get(m).toString();
			        				if(tmpPath.contains("/"+tmpDM+"/")) {
			        					if(!tmpNDM.equals(tmpDM)) {
			        						if(!microFINAL.contains("Microservice: "+tmpNDM) && !microFINAL.contains("Dependency of " + tmpNDM + " is: " + tmpDM)) {
			        							microFINAL.add("Microservice: "+tmpNDM);
			        							microFINAL.add("Dependency of " + tmpNDM + " is: " + tmpDM);
			        						}
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
		 }
		 return microFINAL;
	 }
	 
	 public String getFile() {
	        return file;
	    }

	    public void setFile(String file) {
	        this.file = file;
	    }

}
