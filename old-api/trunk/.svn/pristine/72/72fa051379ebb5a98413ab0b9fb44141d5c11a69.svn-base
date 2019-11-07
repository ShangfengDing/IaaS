package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcService;
import appcloud.api.enums.AcServiceTypeEnum;
import appcloud.api.manager.AcServiceManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.resources.AcServiceResource;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.Service;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.service.NodeMonitorService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.RoutingKeyGenerator;

public class RealAcServiceManager implements AcServiceManager {
	private static Logger logger = Logger.getLogger(RealAcServiceManager.class);
	private ServiceProxy serviceProxy;
	private BeansGenerator generator;
	private NodeMonitorService nodeMonitor;
	
	private static RealAcServiceManager manager = new RealAcServiceManager();
	
	public static RealAcServiceManager getInstance() {
		return manager;
	}
	
	private RealAcServiceManager () {

		super();
		nodeMonitor = ConnectionFactory.getNodeMonitor();
		serviceProxy = (ServiceProxy) ConnectionFactory.getTipProxy(ServiceProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		generator = BeansGenerator.getInstance();
	}
	public static void main(String[] args) throws Exception {
		List<AcService> testlist = new ArrayList<AcService>();
		RealAcServiceManager acs = new RealAcServiceManager();
		testlist = acs.getAll("10");
		System.out.println(testlist);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AcService> getAll(String tenantId) throws Exception {
		logger.info(String.format("Administrator %s request to GET ACSERVICES", tenantId));

		List<Service> services = (List<Service>) serviceProxy.findAll( false);
		//logger.info("services list size = " + services.size());
		List<AcService> acServices = new ArrayList<AcService>();
		for(Service service : services)
			acServices.add(generator.serviceToAcService(service));
		return acServices;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AcService> getHostServices(String tenantId, String hostId)
			throws Exception {
		logger.info(String.format("Administrator %s request to GET ACAGGREGATES on host %s", tenantId, hostId));
		List<Service> services = (List<Service>) serviceProxy.getHostServicesByUuid(hostId);
		List<AcService> acServices = new ArrayList<AcService>();
		for(Service service : services)
			acServices.add(generator.serviceToAcService(service));
		return acServices;
	}

	@Override
	public void startService(String tenantId, String hostId,
			List<AcServiceTypeEnum> serviceTypes) throws Exception {
		String typeStr = "";
		for(AcServiceTypeEnum type : serviceTypes) 
			typeStr += type.toString() + " ";
		logger.info(String.format("Administrator %s request to START host %s 's ACSERVICES: %s",
				tenantId, hostId, typeStr));
		String routingKey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.NODE_MONITOR_PRE, hostId);
		for(AcServiceTypeEnum serviceType : serviceTypes){
			if(serviceType.name().equals(AcServiceTypeEnum.UNKNOWN))
				continue;
			nodeMonitor.startService(routingKey, serviceType.serviceName());
			logger.info(String.format("ACSERVICE %s started succesfully", serviceType.name()));
		}
		logger.info(String.format("start ACSERVICES finished"));
	}

	@Override
	public void stopService(String tenantId, String hostId,
			List<AcServiceTypeEnum> serviceTypes) throws Exception {
		String typeStr = "";
		for(AcServiceTypeEnum type : serviceTypes) 
			typeStr += type.toString() + " ";
		logger.info(String.format("Administrator %s request to STOP host %s 's ACSERVICES: %s",
				tenantId, hostId, typeStr));
		String routingKey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.NODE_MONITOR_PRE, hostId);
		for(AcServiceTypeEnum serviceType : serviceTypes){
			if(serviceType.name().equals(AcServiceTypeEnum.UNKNOWN))
				continue;
			nodeMonitor.stopService(routingKey, serviceType.serviceName());
			logger.info(String.format("ACSERVICE %s stoped succesfully", serviceType.name()));
		}
		logger.info(String.format("stop ACSERVICES finished"));
	}

}
