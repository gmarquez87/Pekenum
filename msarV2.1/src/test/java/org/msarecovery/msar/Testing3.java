package org.msarecovery.msar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.msarecovery.dockerparser.DockerReader;
import com.msarecovery.gradle.GladeParser;
import com.msarecovery.microservices.Microservice;

public class Testing3 {
	public static void main (String [] args) throws IOException, XmlPullParserException {
		List<String> infrastructureMicroservices = new ArrayList<String>();
		LinkedList<String> tmpInfrastructure= new LinkedList<String>();
		LinkedList<String> infrastructureGD= new LinkedList<String>();
		List<String> cfMicroservices = new ArrayList<String>();
		
		String dockerFileInfrastructure=null;
		String gradleFile=null;
		String microservice;
		String filesProject="/Users/gaston.marquez/Documents/RIT/MS recovery tool/Paper/Empirical study/Projects dataset/P9-Sitewhere analysis/sitewhere-master/";
		String projectDestination="/Users/gaston.marquez/Documents/RIT/MS recovery tool/Paper/Empirical study/Projects dataset/P9-Sitewhere analysis/";
		dockerFileInfrastructure="/Users/gaston.marquez/Documents/RIT/MS recovery tool/Paper/Empirical study/sitewhere-recipes (release 31)/docker-compose/infrastructure_default/docker-compose2.yml";
		gradleFile="/Users/gaston.marquez/Documents/RIT/MS recovery tool/Paper/Empirical study/Projects dataset/P9-Sitewhere analysis/sitewhere-master/settings.gradle";
		int microLength;
		int micro;
		
		infrastructureGD= infrastructureGradleDocker(dockerFileInfrastructure, gradleFile, filesProject, 1);
		
		//DockerReader docker= new  DockerReader(dockerFileInfrastructure);
		//cfMicroservices=docker.parseDocker();
		
		//Microservice microservices= new Microservice(filesProject, cfMicroservices);
		//for(int i=0; i<cfMicroservices.size(); i++ ) {
		//	microservice=cfMicroservices.get(i);
		//	if(microservice.contains("Microservice")) {
		//		microLength=microservice.length();
		//		micro=microservice.indexOf(":");
		//		microservice= microservice.substring((micro+1), microLength);
		//		microservice=microservice.replaceAll("\\s+","");
		//		microservice=microservice.toLowerCase();
		//		infrastructureMicroservices=microservices.infrastructureDependency(filesProject, dockerFileInfrastructure, microservice);
		//		for(int u=0;u<=infrastructureMicroservices.size(); u ++) {
		//			if((u+1)<=infrastructureMicroservices.size()) {
		//				String infra1=infrastructureMicroservices.get(u).toString();
		//				String infra2=infrastructureMicroservices.get(u+1).toString();
		//				if(!infra1.equals(infra2) && !tmpInfrastructure.contains(infra1+infra2)) {
		//					System.out.println(infra1 + "-" + infra2);
		//					//writer.println(xml.createEdge(infra1, infra2));
		//					//tmpInfrastructure.add(infra1+infra2);
		//				}
		//				u++;
		//			}
		//		}
		//	}
		//}	
	}
	
	public static LinkedList<String> infrastructureGradleDocker(String dockerFilePath, String gradleFile, String directoryName, int status) throws IOException {
		List<String> dockerMicroservices = new ArrayList<String>();
		LinkedList<String> infrastructureGradleDocker= new LinkedList<String>();
		LinkedList<String> recoveredDockerMicroservices= new LinkedList<String>();
		LinkedList<String> recoveredGradleMicroservices= new LinkedList<String>();
		LinkedList<String> tmpCombination= new LinkedList<String>();
		List<String> gradleMicroservices = new ArrayList<String>();
		List<String> resultList = new ArrayList<String>();
		BufferedReader br = null;
		String microservice;
		String microservice2;
		String name;
		String infrastructure;
		String functional;
		String combination;
		int microLength;
        int micro;
		int microLength2;
        int micro2;
		
        resultList= getAllFiles(directoryName);
		DockerReader dockerInfrastructure= new  DockerReader(dockerFilePath);
		GladeParser gParser= new GladeParser(gradleFile);
        dockerMicroservices=dockerInfrastructure.parseDocker();
        gradleMicroservices=gParser.getGradleDependencies(directoryName, status);
        for(int i=0;i<dockerMicroservices.size(); i++) {
        	microservice=dockerMicroservices.get(i);
        	if(microservice.contains("Microservice")) {
        		microLength=microservice.length();
				micro=microservice.indexOf(":");
				microservice= microservice.substring((micro+1), microLength);
				microservice=microservice.replaceAll("\\s+","");
				recoveredDockerMicroservices.add(microservice);
        	}
        }
		for(int j=0;j<gradleMicroservices.size(); j++) {
			microservice2=gradleMicroservices.get(j);
			if(microservice2.contains("Microservice")) {
				microLength2=microservice2.length();
				micro2=microservice2.indexOf(":");
				microservice2= microservice2.substring((micro2+1), microLength2);
				microservice2=microservice2.replaceAll("\\s+","");
				recoveredGradleMicroservices.add(microservice2);
				Set<String> hs = new HashSet<>();
				hs.addAll(recoveredGradleMicroservices);
				recoveredGradleMicroservices.clear();
				recoveredGradleMicroservices.addAll(hs);
			}
		}
		for(int k=0; k<resultList.size(); k++) {
			name=resultList.get(k);
			try {
				br = new BufferedReader(new FileReader(name));
				String st;
				while ((st = br.readLine()) != null) {
					for(int a=0; a<recoveredDockerMicroservices.size(); a++) {
						infrastructure=recoveredDockerMicroservices.get(a);
						if(st.contains(infrastructure)) {
							for(int b=0; b<recoveredGradleMicroservices.size(); b++) {
								functional=recoveredGradleMicroservices.get(b);
								if(name.contains(functional)) {
									combination=infrastructure+functional;
									if(!tmpCombination.contains(combination)) {
										tmpCombination.add(combination);
										infrastructureGradleDocker.add(infrastructure);
										infrastructureGradleDocker.add(functional);
										//System.out.println(infrastructure + " |||| " + functional);
										//break;
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
        
        return infrastructureGradleDocker;
	}
	
	public static List<String> getAllFiles (String directoryName) throws IOException {
        File directory = new File(directoryName);
        List<String> resultList = new ArrayList<String>();
        File[] fList = directory.listFiles();
        for (File file : fList) {
        		if (file.isFile()) {
        			resultList.add(file.getAbsolutePath());
        		}else if (file.isDirectory()) {
        			resultList.addAll(getAllFiles(file.getAbsolutePath()));
        		}     		
        }
        return resultList;
    }

}
