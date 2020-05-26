package org.msarecovery.msar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Testing2 {
	public static void main (String [] args) {
		String directoryName="/Users/gaston.marquez/Documents/RIT/MS recovery tool/Paper/Empirical study/Projects dataset/P3-Piggy metrics analysis/PiggyMetrics-master/";
		
		
	}
	
	public /*LinkedList <String>*/ static void microservicesMethods(String directoryName, List<File> microservicesFiles, LinkedList<String> obtainedMicroservices){
		String line = " ";
		BufferedReader br = null;
		for(int i=0; i<microservicesFiles.size(); i ++) {
			try {
				br = new BufferedReader(new FileReader(microservicesFiles.get(i)));
				while ((line = br.readLine()) != null) {
					if(line.contains("@RequestMapping(")) {
						System.out.println(microservicesFiles.get(i).getName());
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
