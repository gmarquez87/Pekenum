package com.msarecovery.dockerparser;

import java.util.List;
import java.util.Map;

public class Service {
	
	private String enviroment;
	private String image;
	private String restart;
	private Map<String, DependsOn> dependsOn;
	private List<String> networks;
	private List<String> ports;
	private Map<String, Logging> logging;
    
    public String getEnviroment(){
    	return enviroment;
    }
    
    public void setEnviroment(String enviroment){
    	this.enviroment=enviroment;
    }
	
	public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public String getRestart(){
    	return restart;
    }
    
    public void setRestart(String restart){
    	this.restart=restart;
    }
    
    public Map<String, DependsOn> getDependsOn() {
        return dependsOn;
    }
    
    public void setDependsOn(Map<String, DependsOn> dependsOn) {
        this.dependsOn = dependsOn;
    }

    public List<String> getNetworks() {
        return networks;
    }

    public void setNetworks(List<String> networks) {
        this.networks = networks;
    }

    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }
    
    public Map<String, Logging> getLogging() {
        return logging;
    }
    
    public void setLogging(Map<String, Logging> logging) {
        this.logging = logging;
    }

}
