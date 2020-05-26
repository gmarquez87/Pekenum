package org.msarecovery.docker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import com.msarecovery.dockerparser.DockerReader;
import com.msarecovery.dockerparser.GetContainerLog;

public class TestingDocker {
	public static void main(String [] args) throws InterruptedException, IOException {
		/*LinkedList<String> dockerLog= new LinkedList<String>();
		List<String> cfMicroservices = new ArrayList<String>();
		LinkedList<String> obtainedMicroservices= new LinkedList<String>();
		String fileT="/Users/gaston.marquez/Documents/RIT/MS recovery tool/Paper/Empirical study/Microservices analysis/logProject.txt";
		File file = new File(fileT); 
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		String dockerFile=null;
		String microservice;
		int microLength;
		int micro;
		while ((st = br.readLine()) != null) {
			dockerLog.add(st); 
		}
		String filesProject="/Users/gaston.marquez/Documents/RIT/MS recovery tool/Paper/Empirical study/Microservices analysis/microservices-master/";
		dockerFile=filesProject+"docker-compose2.yml";
		DockerReader docker= new  DockerReader(dockerFile);
		cfMicroservices=docker.parseDocker();
		for(int x=0; x<cfMicroservices.size(); x++ ) {
			microservice=cfMicroservices.get(x);
			if(microservice.contains("Microservice")) {
				microLength=microservice.length();
				micro=microservice.indexOf(":");
				microservice= microservice.substring((micro+1), microLength);
				microservice=microservice.replaceAll("\\s+","");
				obtainedMicroservices.add(microservice);
			}
		}
		for(int i=0; i<obtainedMicroservices.size(); i ++) {
			String microserviceTMP1=obtainedMicroservices.get(i);
			for(int j=0; j<dockerLog.size(); j++) {
				String logLine=dockerLog.get(j).toString();
				if(logLine.contains(microserviceTMP1) && !logLine.contains("Attaching to")) {
					for(int k=0; k<obtainedMicroservices.size(); k++) {
						String microserviceTMP2=obtainedMicroservices.get(k);
						if(logLine.contains("Mapped") && !logLine.contains("autoconfigure")) {
						if(logLine.contains(microserviceTMP2) && microserviceTMP1!=microserviceTMP2) {
							System.out.println(logLine);
							System.out.println(microserviceTMP1);
							System.out.println(microserviceTMP2);
							System.out.println("-----");					
						}
						}
					}
					//System.out.println(logLine);
				}
			}
		}*/
		/*for(int i=0; i<dockerLog.size(); i ++) {
			String logLine=dockerLog.get(i).toString();
			for(int j=0; j<obtainedMicroservices.size(); j++) {
				String microserviceTMP=obtainedMicroservices.get(j);
				if(logLine.contains(microserviceTMP) && !logLine.contains("Attaching to")) {
					System.out.println(logLine);
				}
			}
		}*/
		DockerClient dockerClient = DockerClientBuilder.getInstance().build();
		//List<Container> containers = dockerClient.listContainersCmd().exec();
		//LogContainerResultCallback loggingCallback = new LogContainerResultCallback();
		//System.out.println(dockerClient.logContainerCmd("8cec0ee5e101").withStdOut(true).withStdErr(true).withFollowStream(true).withTail(100).exec(loggingCallback));
		//System.out.println("Log: " + new GetContainerLog(dockerClient,"8cec0ee5e101").getDockerLogs());
		//DockerContainersUtil dockers =new DockerContainersUtil(containers);
		String containerID;
		//System.out.println(dockers.getDockerLogs("8370df9040a0"));
		//System.out.println();
		//for(int i=0; i<containers.size(); i++) {
		//	containerID=containers.get(i).getId();
		//	System.out.println(dockers.getIpAddresses());
			//System.out.println(dockers.getGatewayIpAddress((containerID.substring(0, 12))));
			//System.out.println(dockers.getDockerLogs(containerID.substring(0, 12)));
		//}
		//LinkedList<String> containersID=new LinkedList<String>();
		
		//System.out.println("Log: " + new GetContainerLog(dockerClient,"8370df9040a0").getDockerLogs());
		//System.out.println(loger.getDockerLogs());
		//List<Network> networks = dockerClient.listNetworksCmd().exec();
		//for(int i=0; i<networks.size(); i++) {
		//	System.out.println(networks.get(i).toString());
		//}
		//Network network 
		//  = dockerClient.inspectNetworkCmd().withNetworkId("b4da5bd3c79d4a238919cb6239d0886150ec9b47991f64ffd59f441079a8ca38").exec();
		//System.out.println(network.getName());
		//System.out.println("---------");
		//InspectContainerResponse container = dockerClient.inspectContainerCmd("0e902b04ef7ab880c71f63f12b35c02cdc9cc35301ac802e530aa10206d9e29a").exec();
		//System.out.println(container.getNetworkSettings().getNetworks().get("piggymetrics-master_default").getIpAddress());
		List<Container> containers = dockerClient.listContainersCmd().exec();
		for(int i=0; i<containers.size(); i++) {
			containerID=containers.get(i).getId().substring(0, 12);
			System.out.println("Name: "+containers.get(i).getImage());
			System.out.println("ID: "+ containerID);
			System.out.println("Port: "+containers.get(i).getPorts());
			System.out.println("End point: "+containers.get(i).getNetworkSettings().getNetworks().get(containers.get(i).getHostConfig().getNetworkMode()).getEndpointId());
			if(!containers.get(i).getNetworkSettings().getNetworks().get(containers.get(i).getHostConfig().getNetworkMode()).getIpAddress().isEmpty()) {
				System.out.println("IP address: "+containers.get(i).getNetworkSettings().getNetworks().get(containers.get(i).getHostConfig().getNetworkMode()).getIpAddress());
			}
			System.out.println("---------");
		}
		}
	}


