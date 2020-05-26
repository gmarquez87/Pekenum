package com.msarecovery.dockerparser;

public class Option {
	
	public String maxSize;
	public String maxFile;
	
	public String getMaxSize(){
    	return maxSize;
    }
    
    public void setMaxSize(String maxSize){
    	maxSize="max-size";
    	this.maxSize=maxSize;
    }
    
    public String getMaxFile(){
    	return maxFile;
    }
    
    public void setMaxFile(String maxFile){
    	this.maxFile=maxFile;
    }

}
