package com.msarecovery.dockerparser;

import java.util.Map;

public class DependsOn {
	
	public Map<String, Config> config;
	
	public Map<String, Config> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Config> config) {
        this.config = config;
    }

}
