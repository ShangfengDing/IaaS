package appcloud.nodeagent.util;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.service.monitor.YunhaiService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.io.ShellUtil;
import appcloud.nodeagent.dao.ServiceDAO;
import appcloud.rpc.tools.RpcException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * If you use this class, to release the resources,
 * make sure you call ConnectionFactory.close()
 */
public class ServiceManager {
	static private ServiceManager sm = null;
	private Map<String,ServiceUnit> services = null;
	private static Logger logger = Logger.getLogger(ServiceManager.class);
	
	private ServiceDAO serviceDAO;
	
	static public ServiceManager getInstance(){
		if(sm == null){
			sm = new ServiceManager();
		}
		return sm;
	}
	
	private ServiceManager() {
		serviceDAO = (ServiceDAO) AppUtils.getApplicationContext().getBean("serviceDAO");
		services = ConfigReader.readFromJson(ServiceManager.class.getClassLoader().getResource("naservices.json"));
	}
	
	public boolean hasService(String serviceName){
		return services.containsKey(serviceName); 
	}
	
	public Service getService(String serviceName){
		//TODO get service stats from services
		// not from db
		return serviceDAO.getService(services.get(serviceName));
	}
	public boolean startService(String serviceName){
		ServiceUnit su = services.get(serviceName);
		if(su == null){
			logger.error("no such service " + serviceName);
			return false;
		}
		try {
			if(su.getMonitorService().isRunning()){
				logger.error("service already running: " + serviceName);
				return false;
			}
		} catch (Exception e) {
			logger.info("service get failed");
		}
		
		//com.fasterxml.jackson.databind.ObjectWriter		
		
		String classpath  = System.getProperty("java.class.path") ;
		if(su.getClasspath().trim().length() > 0 ){
			classpath = su.getClasspath() +":" + classpath;
		}
		
		Host host = (Host) AppUtils.getApplicationContext().getBean("host");
		String hostUuid = AppUtils.getHostUuid();
		String args = " -Dhost.uuid=" + hostUuid;
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String hostStr = mapper.writeValueAsString(host);
			
			hostStr = Base64.encodeBase64String(hostStr.getBytes("utf-8"));
			args += " -Dhost.all=" + hostStr;
			logger.debug("serialize host:" + hostStr);
			
		} catch (Exception e) {
			logger.error("cannot serialize host to json", e);
		}

		logger.debug("args:" + args);
		String cmd =  "java" + args + " -cp " + classpath +" " + su.getMainClass();
		String logfile = "logs/"+ su.getName()+".log";
		
		Service service = serviceDAO.getService(su);
		try {
			ShellUtil.runInBackground(cmd, logfile, false);
		} catch (IOException e) {
			logger.warn("failed to startService: "+ serviceName,e);
			return false;
		}

		boolean running = su.assureServiceRunning(5000);
		if(running){
			logger.info("successfully started service: "+serviceName);
			service = serviceDAO.setStarted(service);
		}else {
			logger.warn("service may not been started: "+serviceName);
			service = serviceDAO.setStoped(service);
		}
		return true;
	}
	
	public boolean isRunning(String serviceName){
		ServiceUnit su = services.get(serviceName);
		if(su == null){
			logger.warn("no such service " + serviceName);
			return false;
		}
		YunhaiService ys = su.getMonitorService();
		try {
			return ys.isRunning();
		} catch (Exception e) {
//			logger.debug("isRunning service:"+serviceName,e);
		}
		return false;
	}
	
	public void stopService(String serviceName){
		logger.warn("stopping "+ serviceName);
		ServiceUnit su = services.get(serviceName);
		if(su == null){
			logger.error("no such service " + serviceName);
			return;
		}
		YunhaiService ys = su.getMonitorService();
		
		try {
			ys.markAsStopped();
			Service service = serviceDAO.getService(su);
			service = serviceDAO.setStoped(service);
			logger.warn("stopped "+ serviceName);
		} catch (Exception e) {
			logger.warn("failed to mark stop "+ serviceName,e);
		}
	}
	
	public ServiceDAO getServiceDAO() {
		return serviceDAO;
	}

	public void setServiceDAO(ServiceDAO serviceDAO) {
		this.serviceDAO = serviceDAO;
	}

	public static void main(String[] args) {
		if(args.length != 2){
			System.out.println("" + args.length);
			System.out.println("ServiceManager [start|stop|uptime] [service-name]");
			return ;
		}
		
		//如果host没有id，需要从数据库获取id
		Host host = (Host) AppUtils.getApplicationContext().getBean("host");
		if (host.getId() == null) {
			AppUtils.saveOrUpdateHost();
		}
		
		String action = args[0];
		String serviceName = args[1];
		if(action.equalsIgnoreCase("start")){
			ServiceManager.getInstance().startService(serviceName);
		} else if(action.equalsIgnoreCase("stop")){
			ServiceManager.getInstance().stopService(serviceName);
		} else if(action.equalsIgnoreCase("uptime")){
			boolean running = ServiceManager.getInstance().isRunning(serviceName);
			System.out.println(serviceName + "is running: " + running);
		}
		
		logger.debug("host is " + host);
//		String service = Service.ServiceTypeEnum.VM_SCHEDULER.toString();
//		ServiceManager.getInstance().startService(service);
//		ServiceManager.getInstance().stopService(service);
		//ServiceManager.getInstance().close();
		System.exit(0);
		ConnectionFactory.close();
	}
}
