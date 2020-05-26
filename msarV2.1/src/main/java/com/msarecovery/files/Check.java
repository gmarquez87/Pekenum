package com.msarecovery.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Check {
	public static void main (String [] args) throws IOException { 
		String filesProject="";
		//System.out.println(getFiles(filesProject).size());
		for(Object o : getFiles(filesProject))
			System.out.println(o);
	}
	
	public static List<File> getFiles (String directoryName) throws IOException {
        File directory = new File(directoryName);
        List<File> resultList = new ArrayList<File>();
        List<File> resultListFinal = new ArrayList<File>();
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
        	if (file.isFile()) {
        		resultList.add(file);
        	}else if (file.isDirectory()) {
        		resultList.addAll(getFiles(file.getAbsolutePath()));
        	}	
        }
        for(int i=0;i<resultList.size(); i++) {
        	if(resultList.get(i).getAbsolutePath().endsWith(".java") || resultList.get(i).getAbsolutePath().endsWith(".py") || resultList.get(i).getAbsolutePath().endsWith(".php") ||
        		resultList.get(i).getAbsolutePath().endsWith(".go") || resultList.get(i).getAbsolutePath().endsWith(".rb") || resultList.get(i).getAbsolutePath().endsWith(".js") ||
        		resultList.get(i).getAbsolutePath().endsWith(".cs")) {
        		if(!resultList.get(i).getAbsolutePath().contains("test") ||!resultList.get(i).getAbsolutePath().contains("Test")) {
        			resultListFinal.add(resultList.get(i));
        		}
        	}
        }
        Set<File> files = new HashSet<File>(resultListFinal);
        List<File> resultList2 = new ArrayList<File>(files);
        return resultList2;
    }
}
