package com.msarecovery.dockerparser;

import java.util.Map;

public class DockerComposeFile2 {
    public Map<String, Service> services;

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }

}
