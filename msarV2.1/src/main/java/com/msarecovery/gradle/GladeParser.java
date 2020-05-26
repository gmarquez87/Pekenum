package com.msarecovery.gradle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class GladeParser {
	
	private String gadleFile;

	public GladeParser(String gadleFile) {
		this.gadleFile=gadleFile;
	}
	
	public LinkedList<String> getBuilGradleMicroservices(String fileGradle) throws FileNotFoundException {
		File file = new File(fileGradle);
		LinkedList<String> microservicesGradle=new LinkedList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		try {
			while ((st = br.readLine()) != null) {
				if(st.contains("include")) {
					st=st.replace(" ", ",");
				    String[] ary = st.split(",");
				    st=ary[1];
				    st=st.replace("'", "");
				    st=st.replace(":", "");
				    st=st.replaceAll("\\s+","");
				    microservicesGradle.add(st);
				}
				if(st.contains("baseName =")) {
					st=st.replace(" ", "");
					st=st.replace("baseName=", "");
					st=st.replace("'", "");
					st=st.replace("[", "");
					st=st.replace("]", "");
					st=st.replaceAll("\\s+","");
				    microservicesGradle.add(st);
			  }
		  }
		  }catch (FileNotFoundException e) {
	        	e.printStackTrace();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }finally {
	        	if (br != null) {
	        		try {
	        			br.close();
	        		}catch (IOException e) {
	        			e.printStackTrace();
	        		}
	        	}
	     }
		return microservicesGradle;
	}
	
	public List<File> getGradleFiles (String directoryName) throws FileNotFoundException{
		File directory = new File(directoryName);
        String gradleBuild;
        List<File> resultList = new ArrayList<File>();
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
        		if (file.isFile()) {
        			gradleBuild=file.getAbsolutePath();
        				if(gradleBuild.endsWith("build.gradle")) {
        					resultList.add(file);
        				}
        		}else if (file.isDirectory()) {
        			resultList.addAll(getGradleFiles(file.getAbsolutePath()));
        		}
        		
        }
        Set<File> files = new HashSet<File>(resultList);
        List<File> resultList2 = new ArrayList<File>(files);
        return resultList2;
	}
	
	public LinkedList<String> getGradleDependencies (String directoryName, int status) throws FileNotFoundException{
		LinkedList<String> microservicesGradle=new LinkedList<String>();
		LinkedList<String> microservicesList= new LinkedList<String>();
		LinkedList<String> microservicesGradleDependency= new LinkedList<String> ();
		LinkedList<String> tmpCombination= new LinkedList<String> ();
		List<File> gradleFileTmp;
		String st;
		String tmpG;
		String combination = null;
		String combination2 = null;
		if(status!=0) {
			microservicesGradle= getBuilGradleMicroservices(gadleFile);
	        List<File> gradleFile= getGradleFiles (directoryName);
	        for(int j=0; j<gradleFile.size(); j++) {
	        	if(gradleFile.get(j).isFile() && gradleFile.get(j).toString().endsWith("build.gradle")) {
	        		String tmpFile=gradleFile.get(j).toString();
	        		BufferedReader br = new BufferedReader(new FileReader(tmpFile));
	        		//String tmpPath=gradleFile.get(j).getAbsolutePath();
	        		try {
	        			while ((st = br.readLine()) != null) {
	        				if(st.contains("compile project(") || st.contains("compileOnly project(")) {
	        					if(st.contains("compile project(")) {
	        						st=st.replace("compile project", "");
	        					}
	        					if(st.contains("compileOnly project(")) {
	        						st=st.replace("compileOnly project", "");
	        					}
	        					st=st.replace(" ", "");
	        					st=st.replace("'", "");
	        					st=st.replace(":", "");
	        					st=st.replace("(", "");
	        					st=st.replace(")", "");
	        					st=st.replace("\t", "");
	        					st=st.replaceAll("\\s+","");
	        					for(int i=0; i<microservicesGradle.size(); i++) {
	        						String tmp=microservicesGradle.get(i).toString();
	        						if(tmpFile.contains(tmp)) {
	        							combination=tmp+st;
	        							if(!tmpCombination.contains(combination)) {
        									tmpCombination.add(combination);
    										microservicesGradleDependency.add("Microservice: "+tmp);
    										microservicesGradleDependency.add("Dependency of " + tmp + " is: " + st);
    									}
	        						}
	        					}
	        				}
	        			}
	        		}catch (FileNotFoundException e) {
	        			e.printStackTrace();
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}finally {
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
		}else {
			gradleFileTmp=getGradleFiles (directoryName);
			for(int a=0;a<gradleFileTmp.size(); a++) {
				if(gradleFileTmp.get(a).toString().endsWith(".gradle")) {
					tmpG=gradleFileTmp.get(a).toString();
					microservicesList=getBuilGradleMicroservices(tmpG);
					microservicesGradle.addAll(microservicesList);
				}
			}
	        List<File> gradleFile= getGradleFiles (directoryName);
	        for(int j=0; j<gradleFile.size(); j++) {
	        	if(gradleFile.get(j).isFile() && gradleFile.get(j).toString().endsWith("build.gradle")) {
	        		String tmpFile=gradleFile.get(j).toString();
	        		BufferedReader br = new BufferedReader(new FileReader(tmpFile));
	        		try {
	        			while ((st = br.readLine()) != null) {
	        				if(st.contains("baseName =")) {
	        					st=st.replace(" ", "");
	        					st=st.replace("baseName=", "");
	        					st=st.replace("'", "");
	        					st=st.replace("[", "");
	        					st=st.replace("]", "");
	        					st=st.replaceAll("\\s+","");
	        					for(int i=0; i<microservicesGradle.size(); i++) {
	        						String tmp=microservicesGradle.get(i).toString();
	        						if(tmp.contains(st)) {
	        							String tmpPath=gradleFile.get(j).getAbsolutePath();
	        							for(int k=0;k<microservicesGradle.size(); k++) {
	        								String tmpDependency=microservicesGradle.get(k).toString();
	        								if(tmpPath.contains(tmpDependency) && tmp!=tmpDependency) {
	        									microservicesGradleDependency.add("Microservice: "+tmp);
	        									microservicesGradleDependency.add("Dependency of " + tmp + " is: " + tmpDependency);
	        								}
	        							}
	        						}
	        						microservicesGradleDependency.add("Microservice: "+tmp);
									microservicesGradleDependency.add("Dependency of " + tmp + " is: " + "No dependecy");
	        					}
	        				}
	        			}
	        		}catch (FileNotFoundException e) {
	        			e.printStackTrace();
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}finally {
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
        return microservicesGradleDependency;
	}
	
	public LinkedList<String> getBuilGradleFrameworks(String directoryName) throws FileNotFoundException {
		List<File> gradleFile= getGradleFiles (directoryName);
		LinkedList<String> microservicesFrameworkGradle=new LinkedList<String>();
		for(int i=0;i<gradleFile.size();i ++) {
			if(gradleFile.get(i).isFile() && gradleFile.get(i).toString().endsWith("build.gradle")) {
				String tmpFile=gradleFile.get(i).toString();
				BufferedReader br = new BufferedReader(new FileReader(tmpFile));
				String st;
				try {
					while ((st = br.readLine()) != null) {
						if(st.contains("compile group:")) {
							st=st.replace(" ", "");
							String[] ary = st.split(",");
							st=ary[0];
							st=st.replace("compilegroup:", "");
							st=st.replace("'", "");
							st=st.replace(":", "");
							st=st.replace("org.", "");
							st=st.replace("com.", "");
							st=st.replace("io.", "");
							st=st.replaceAll("\\s+","");
							microservicesFrameworkGradle.add(st);
						}
					}
				}catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
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
		Set<String> hs = new HashSet<>();
        hs.addAll(microservicesFrameworkGradle);
        microservicesFrameworkGradle.clear();
        microservicesFrameworkGradle.addAll(hs);
        return microservicesFrameworkGradle;
	}
	
	public LinkedList<String> getGradleFrameworkDependencies (String directoryName, int status) throws FileNotFoundException{
		LinkedList<String> microservicesGradle= new LinkedList<String> ();
		LinkedList<String> microservicesList= new LinkedList<String>();
		LinkedList<String> tmpCombination= new LinkedList<String>();
		List<File> gradleFileTmp;
		String st;
		String tmpG;
		String st2;
		String combination;
		if(status!=0) {
			microservicesGradle= getBuilGradleMicroservices(gadleFile);
		}else {
			gradleFileTmp=getGradleFiles (directoryName);
			for(int a=0;a<gradleFileTmp.size(); a++) {
				if(gradleFileTmp.get(a).toString().endsWith(".gradle")) {
					tmpG=gradleFileTmp.get(a).toString();
					microservicesList=getBuilGradleMicroservices(tmpG);
					microservicesGradle.addAll(microservicesList);
				}
			}
		}
        LinkedList<String> microservicesGradleDependency= new LinkedList<String> ();
        LinkedList<String> microservicesGradleFramework=getBuilGradleFrameworks(directoryName);
        List<File> gradleFile= getGradleFiles (directoryName);
        for(int j=0; j<gradleFile.size(); j++) {
        	if(gradleFile.get(j).isFile() && gradleFile.get(j).toString().endsWith("build.gradle")) {
        		String tmpFile=gradleFile.get(j).toString();
        		BufferedReader br = new BufferedReader(new FileReader(tmpFile));
        		try {
        			while ((st = br.readLine()) != null) {
        				if(st.contains("compile project(") || st.contains("compile ") || st.contains("include ") || st.contains("compile(")) {
        					if(st.contains("compile project(")) {
        						st=st.replace("compile project", "");
        					}
        					st=st.replace(" ", "");
        					st=st.replace("'", "");
        					st=st.replace(":", "");
        					st=st.replace("(", "");
        					st=st.replace(")", "");
        					st=st.replace("\t", "");
        					st=st.replaceAll("\\s+","");
        					for(int i=0; i<microservicesGradle.size(); i++) {
        						String tmp=microservicesGradle.get(i).toString();
        						if(tmp.contains(st)) {
        							String tmpPath=gradleFile.get(j).getAbsolutePath();
        							for(int k=0;k<microservicesGradle.size(); k++) {
        								String tmpDependency=microservicesGradle.get(k).toString();
        								if(tmpPath.contains(tmpDependency)) {
        									String tmpFile2=gradleFile.get(j).toString();
        					        		BufferedReader br2 = new BufferedReader(new FileReader(tmpFile2));
        					        		try {
        					        			while ((st2 = br2.readLine()) != null) {
        					        				for(int a=0;a<microservicesGradleFramework.size();a++) {
        					        					String tmpFramework=microservicesGradleFramework.get(a).toString();
        					        					if(st2.contains(tmpFramework)) {
        					        						combination=tmp+tmpDependency+tmpFramework;
        					        						if(!tmpCombination.contains(combination)) {
        					        							tmpCombination.add(combination);
        					        							microservicesGradleDependency.add(tmp);
        					        							microservicesGradleDependency.add(tmpDependency);
        					        							microservicesGradleDependency.add(tmpFramework);
        					        						}
        					        					}
        					        				}
        					        			}
        					        		}catch (FileNotFoundException e) {
        					        			e.printStackTrace();
        					        		} catch (IOException e) {
        					        			e.printStackTrace();
        					        		}finally {
        					        			if (br2 != null) {
        					        				try {
        					        					br2.close();
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
        			}
        		}catch (FileNotFoundException e) {
        			e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}finally {
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
        return microservicesGradleDependency;
	}
	
	public List<File> getFiles (String directoryName) throws IOException {
        File directory = new File(directoryName);
        String name;
        List<File> resultList = new ArrayList<File>();
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
        		if (file.isFile()) {
        			name=file.getAbsolutePath();
        				if(name.endsWith(".java") || name.endsWith(".py") || name.endsWith(".php") || name.endsWith(".go") || name.endsWith(".rb")
        						|| name.endsWith(".js") || name.endsWith(".cs")) {
        					resultList.add(file);
        				}
        		}else if (file.isDirectory()) {
        			resultList.addAll(getFiles(file.getAbsolutePath()));
        		}
        		
        }
        Set<File> files = new HashSet<File>(resultList);
        List<File> resultList2 = new ArrayList<File>(files);
        return resultList2;
    }
	
	public LinkedList<String> dependencyFileFolder (String directoryName, int status) throws IOException, XmlPullParserException {
		List<File> javaFiles=getFiles(directoryName);
		LinkedList<String> famework=getGradleFrameworkDependencies(directoryName, status);
		LinkedList<String> dependecyFF= new LinkedList<String>();
		List<String> gradleMicroservices = new ArrayList<String>();
		BufferedReader br = null;
		gradleMicroservices=getBuilGradleMicroservices(gadleFile);
        String microservice;
        int microLength;
        int micro;
        int flag=0;
        for(int m=0; m<gradleMicroservices.size(); m++) {
			microservice=gradleMicroservices.get(m);
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
        Set<String> hs = new HashSet<>();
        hs.addAll(dependecyFF);
        dependecyFF.clear();
        dependecyFF.addAll(hs);
        return dependecyFF;
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

}
