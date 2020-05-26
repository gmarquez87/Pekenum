package com.msarecovery.dockerparser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DockerComposeFile {

    public String version;
    public Map<String, Service> services;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }
    
}
