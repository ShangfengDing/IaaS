package appcloud.nodeagent.util;

import org.apache.log4j.Logger;

import appcloud.common.service.monitor.YunhaiService;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tip.TipRPCClient;

public class ServiceUnit {
	private static Logger logger = Logger.getLogger(ServiceUnit.class);
	
	private String name ="";
	private String classpath = "";    //relative path, for example: services/dbproxy 
	private String mainClass ="";
	private Integer monitorPort = 9001;  //default port
	private YunhaiService monitorService = null;
	
	public YunhaiService getMonitorService() {
		if(monitorService == null){
			logger.info("create " + name +" monitorService:" + monitorPort);
			monitorService = (YunhaiService) ConnectionFactory.getTipProxy(YunhaiService.class,"tcp://127.0.0.1:"+monitorPort);
		}
		return monitorService;
	}
	
	
	public boolean assureServiceRunning(long timeout) {
		long time = System.currentTimeMillis() + timeout;
		boolean running = false;
		
		monitorService = getMonitorService();
		while(!running){
			if(System.currentTimeMillis() > time)
				break;
			logger.info("test if the service is running");
			try{
				running = monitorService.isRunning();
			}catch (Exception e) {
				logger.info("testing fail");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					logger.warn("interrupted",e);
				}
				logger.info("retrying ");
			}
		}
		return running;
	}
	
	public void setMonitorService(YunhaiService monitorService) {
		this.monitorService = monitorService;
	}
	public Integer getMonitorPort() {
		return monitorPort;
	}
	public void setMonitorPort(Integer monitorPort) {
		this.monitorPort = monitorPort;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClasspath() {
		return classpath;
	}
	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}
	public String getMainClass() {
		return mainClass;
	}
	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	
	
	
}
