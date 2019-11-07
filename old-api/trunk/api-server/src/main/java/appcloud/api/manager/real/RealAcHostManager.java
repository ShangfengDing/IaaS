package appcloud.api.manager.real;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.AcHost;
import appcloud.api.beans.Load;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.manager.AcHostManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.Cluster;
import appcloud.common.model.Host.HostStatusEnum;
import appcloud.common.model.Host.HostTypeEnum;
import appcloud.common.model.HostLoad;
import appcloud.common.model.Service;
import appcloud.common.model.VmZone;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.CommonLoadProxy;
import appcloud.common.proxy.DailyLoadProxy;
import appcloud.common.proxy.HostLoadProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.MonthLoadProxy;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.service.NodeMonitorService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;

public class RealAcHostManager implements AcHostManager {

	private static Logger logger = Logger.getLogger(RealAcHostManager.class);

	private HostProxy hostProxy;
	private static HostLoadProxy hostLoadProxy;
	private static DailyLoadProxy dailyLoadProxy;
	private static MonthLoadProxy monthLoadProxy;
	private static ServiceProxy serviceProxy;
	private ClusterProxy clusterProxy;
	private VmZoneProxy zoneProxy;
	private BeansGenerator generator;
	
	private NodeMonitorService nodeMonitor;
	
	private static RealAcHostManager manager = new RealAcHostManager();
	
	public static RealAcHostManager getInstance() {
		return manager;
	}
	
	private RealAcHostManager() {
		super();
		generator = BeansGenerator.getInstance();
		hostProxy = (HostProxy) ConnectionFactory.getTipProxy(HostProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		hostLoadProxy = (HostLoadProxy) ConnectionFactory.getTipProxy(
				HostLoadProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		dailyLoadProxy = (DailyLoadProxy) ConnectionFactory.getTipProxy(
				DailyLoadProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		monthLoadProxy = (MonthLoadProxy) ConnectionFactory.getTipProxy(
				MonthLoadProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);

		clusterProxy = (ClusterProxy) ConnectionFactory.getTipProxy(ClusterProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		zoneProxy = (VmZoneProxy) ConnectionFactory.getTipProxy(VmZoneProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		serviceProxy = (ServiceProxy) ConnectionFactory.getTipProxy(ServiceProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		
		
		nodeMonitor = ConnectionFactory.getNodeMonitor();
	}

	@Override
	public List<AcHost> getList(String tenantId, boolean detail, Integer aggregateId, Integer zoneId) 
			throws Exception{
		String logStr = String.format("Administrator %s request to GET ACHOSTS", tenantId);

		List<appcloud.api.beans.AcHost>acHosts = new ArrayList<appcloud.api.beans.AcHost>();
		QueryObject< appcloud.common.model.Host> query = new QueryObject< appcloud.common.model.Host>();
		if(zoneId != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>("availabilityZoneId", zoneId, FilterBeanType.EQUAL));
			logStr += ", zoneId:" + zoneId;
		}
		if(aggregateId != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>("clusterId", aggregateId, 
					FilterBeanType.EQUAL));
			logStr += ", aggregateId:" + aggregateId;
		}
		logger.info(logStr);
		List<? extends appcloud.common.model.Host> vmHosts = hostProxy.searchAll(query, false, false, false);
		for(appcloud.common.model.Host vmHost: vmHosts){
			AcHost acHost = generator.hostToAcHost(vmHost);
			fillUpAcHost(acHost);
			acHosts.add(acHost);
		}
		return acHosts;
	}

	@Override
	public AcHost get(String tenantId, String hostId) throws Exception{
		logger.info(String.format("Administrator %s request to GET ACHOST %s, ", tenantId, hostId));

		QueryObject<appcloud.common.model.Host> query = new QueryObject<appcloud.common.model.Host>();
		
		query.addFilterBean(new FilterBean<appcloud.common.model.Host>("uuid", hostId, FilterBeanType.EQUAL));
		List<? extends appcloud.common.model.Host> vmHosts = null;
		
		vmHosts = hostProxy.searchAll(query, false, false, false);
		
		if(vmHosts.size() == 1){
			AcHost acHost = generator.hostToAcHost(vmHosts.get(0));
			fillUpAcHost(acHost);
			return acHost;
		}
		else{
			throw new ItemNotFoundException("host does not exist");
		}
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<Load> getMonitorData(String tenantId, String hostId,
			String type, Timestamp startTime, Timestamp endTime) {
		logger.info(String.format("Administrator %s request to get Monitor: serverId:%s, type:%s, start:%s, end:%s",
				tenantId, hostId, type, startTime, endTime));
		CommonLoadProxy proxy;
		
		if (type.equalsIgnoreCase("day")) {
			proxy = hostLoadProxy;
		} else if (type.equalsIgnoreCase("month")) {
			proxy = dailyLoadProxy;
		} else {
			proxy = monthLoadProxy;
		}
		
		List<HostLoad> hostLoads = (List<HostLoad>) proxy.getLoads(hostId, startTime, endTime);
		
		List<Load> loads = new ArrayList<Load>();
		
		for (HostLoad hostLoad : hostLoads) {
			loads.add(generator.vmLoadToLoad(hostLoad));
		}
		
		return loads;
	}
	


	@Override
	public Load getCurrentLoad(String tenantId, String hostId) throws Exception {

		logger.info(String.format("Administrator %s request to get current load of %s",
				tenantId, hostId));
		String routingKey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.NODE_MONITOR_PRE, hostId);
		appcloud.common.model.Host host = hostProxy.getByUuid(hostId, false, false, false);
		HostLoad load = nodeMonitor.getCurrentLoad(routingKey, host);
		
		return generator.vmLoadToLoad(load);
	}

	private void fillUpAcHost(AcHost acHost) throws Exception{
		if(acHost == null)
			return ;
		Cluster cluster = clusterProxy.getById(acHost.aggregateId, false, false, false);
		if(cluster != null)
			acHost.aggregateName = cluster.getName();
		VmZone zone = zoneProxy.getById(acHost.availabilityZoneId);
		if(zone != null)
			acHost.availabilityZoneName = zone.getName();
	}
	
	public long countByProperties(String tenantId, String ip, HostTypeEnum hostType, HostStatusEnum hostStatus, 
			   Integer aid, Integer zid, ServiceTypeEnum serviceType, 
			   int cpuOperator, Integer cpuCount,
			   int memoryOperator, Integer memoryCount,
			   int diskOperator, Integer diskCount) throws Exception {
		String logStr = null;
		logStr = String.format("User %s request to count Hosts by ", tenantId);
		QueryObject<appcloud.common.model.Service> queryService = new QueryObject<appcloud.common.model.Service>();
		QueryObject<appcloud.common.model.Host> query = new QueryObject<appcloud.common.model.Host>();
		
		List<String> hostIPs = new ArrayList<String>();
		if(serviceType != null) {
			logStr += "service=" + serviceType;
			queryService.addFilterBean(new FilterBean<appcloud.common.model.Service>("type", serviceType, FilterBeanType.EQUAL));
			List<Service> services = (List<Service>)serviceProxy.searchAll(queryService, false);
			if(services == null || services.size() == 0) {
				logger.info(logStr);
				return 0;
			} else {
				for(Service service :services) {
					hostIPs.add(service.getHostIp());
				}
				query.addFilterBean(new FilterBean<appcloud.common.model.Host>("ip", hostIPs, FilterBeanType.IN));
			}
		}
		
		if(ip != null && !ip.equals("")) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>(
					"ip", ip, FilterBeanType.EQUAL));
			logStr += "ip=" + ip + ", ";
		}
		
		if(hostType != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>(
					"type", hostType, FilterBeanType.EQUAL));
			logStr += "type=" + hostType + ", ";
		}
		
		if(hostStatus != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"status", hostStatus, FilterBeanType.EQUAL));
			logStr += "status=" + hostStatus + ", ";
		}
		
		if(aid != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>(
					"clusterId", aid, FilterBeanType.EQUAL));
			logStr += "cluster=" + aid + ", ";
		}
		
		if(zid != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"availabilityZoneId", zid, FilterBeanType.EQUAL));
			logStr += "zone=" + zid + ", ";
		}
		
		if(cpuCount != null) {
			FilterBeanType operator = FilterBeanType.LESS_THAN;
			switch(cpuOperator) {
				case 0:
					operator = FilterBeanType.LESS_THAN;
					break;
				case 1:
					operator = FilterBeanType.LESS_EQUAL;
					break;
				case 2:
					operator = FilterBeanType.EQUAL;
					break;
				case 3:
					operator = FilterBeanType.MORE_EQUAL;
					break;
				case 4:
					operator = FilterBeanType.MORE_THAN;
					break;
			}
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"cpuCore", cpuCount, operator));
			logStr += "cpuCore " + operator + " " + cpuCount +", ";
		}
		
		if(memoryCount != null) {
			FilterBeanType operator = FilterBeanType.LESS_THAN;
			switch(memoryOperator) {
				case 0:
					operator = FilterBeanType.LESS_THAN;
					break;
				case 1:
					operator = FilterBeanType.LESS_EQUAL;
					break;
				case 2:
					operator = FilterBeanType.EQUAL;
					break;
				case 3:
					operator = FilterBeanType.MORE_EQUAL;
					break;
				case 4:
					operator = FilterBeanType.MORE_THAN;
					break;
			}
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"memoryMb", memoryCount, operator));
			logStr += "memoryMb " + operator + " " + memoryCount +", ";
		}
		
		if(diskCount != null) {
			FilterBeanType operator = FilterBeanType.LESS_THAN;
			switch(diskOperator) {
				case 0:
					operator = FilterBeanType.LESS_THAN;
					break;
				case 1:
					operator = FilterBeanType.LESS_EQUAL;
					break;
				case 2:
					operator = FilterBeanType.EQUAL;
					break;
				case 3:
					operator = FilterBeanType.MORE_EQUAL;
					break;
				case 4:
					operator = FilterBeanType.MORE_THAN;
					break;
			}
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"diskMb", diskCount, operator));
			logStr += "diskMb " + operator + " " + diskCount +", ";
		}
		
		long count = hostProxy.countSearch(query);
		return count;
	}
	
	
	
	public List<AcHost> getHostsByProperties(String tenantId, String ip, HostTypeEnum hostType, HostStatusEnum hostStatus, 
															   Integer aid, Integer zid, ServiceTypeEnum serviceType, 
															   int cpuOperator, Integer cpuCount,
															   int memoryOperator, Integer memoryCount,
															   int diskOperator, Integer diskCount,
															   Integer page, Integer size) throws Exception {
		
		String logStr = null;
		logStr = String.format("User %s request to search Hosts by ", tenantId);
		QueryObject<appcloud.common.model.Service> queryService = new QueryObject<appcloud.common.model.Service>();
		QueryObject<appcloud.common.model.Host> query = new QueryObject<appcloud.common.model.Host>();
		
		List<String> hostIPs = new ArrayList<String>();
		if(serviceType != null) {
			logStr += "service=" + serviceType;
			queryService.addFilterBean(new FilterBean<appcloud.common.model.Service>("type", serviceType, FilterBeanType.EQUAL));
			List<Service> services = (List<Service>)serviceProxy.searchAll(queryService, false);
			if(services == null || services.size() == 0) {
				logger.info(logStr);
				return new ArrayList<AcHost>();
			} else {
				for(Service service :services) {
					hostIPs.add(service.getHostIp());
				}
				query.addFilterBean(new FilterBean<appcloud.common.model.Host>("ip", hostIPs, FilterBeanType.IN));
			}
		}
		
		if(ip != null && !ip.equals("")) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>(
					"ip", ip, FilterBeanType.EQUAL));
			logStr += "ip=" + ip + ", ";
		}
		
		if(hostType != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>(
					"type", hostType, FilterBeanType.EQUAL));
			logStr += "type=" + hostType + ", ";
		}
		
		if(hostStatus != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"status", hostStatus, FilterBeanType.EQUAL));
			logStr += "status=" + hostStatus + ", ";
		}
		
		if(aid != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>(
					"clusterId", aid, FilterBeanType.EQUAL));
			logStr += "cluster=" + aid + ", ";
		}
		
		if(zid != null) {
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"availabilityZoneId", zid, FilterBeanType.EQUAL));
			logStr += "zone=" + zid + ", ";
		}
		
		if(cpuCount != null) {
			FilterBeanType operator = FilterBeanType.LESS_THAN;
			switch(cpuOperator) {
				case 0:
					operator = FilterBeanType.LESS_THAN;
					break;
				case 1:
					operator = FilterBeanType.LESS_EQUAL;
					break;
				case 2:
					operator = FilterBeanType.EQUAL;
					break;
				case 3:
					operator = FilterBeanType.MORE_EQUAL;
					break;
				case 4:
					operator = FilterBeanType.MORE_THAN;
					break;
			}
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"cpuCore", cpuCount, operator));
			logStr += "cpuCore " + operator + " " + cpuCount +", ";
		}
		
		if(memoryCount != null) {
			FilterBeanType operator = FilterBeanType.LESS_THAN;
			switch(memoryOperator) {
				case 0:
					operator = FilterBeanType.LESS_THAN;
					break;
				case 1:
					operator = FilterBeanType.LESS_EQUAL;
					break;
				case 2:
					operator = FilterBeanType.EQUAL;
					break;
				case 3:
					operator = FilterBeanType.MORE_EQUAL;
					break;
				case 4:
					operator = FilterBeanType.MORE_THAN;
					break;
			}
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"memoryMb", memoryCount, operator));
			logStr += "memoryMb " + operator + " " + memoryCount +", ";
		}
		
		if(diskCount != null) {
			FilterBeanType operator = FilterBeanType.LESS_THAN;
			switch(diskOperator) {
				case 0:
					operator = FilterBeanType.LESS_THAN;
					break;
				case 1:
					operator = FilterBeanType.LESS_EQUAL;
					break;
				case 2:
					operator = FilterBeanType.EQUAL;
					break;
				case 3:
					operator = FilterBeanType.MORE_EQUAL;
					break;
				case 4:
					operator = FilterBeanType.MORE_THAN;
					break;
			}
			query.addFilterBean(new FilterBean<appcloud.common.model.Host>( 
					"diskMb", diskCount, operator));
			logStr += "diskMb " + operator + " " + diskCount +", ";
		}
		if(page.intValue() != 0) {
			page = page - 1;
		}
		
		List<appcloud.common.model.Host> hosts = (List<appcloud.common.model.Host>)hostProxy.searchAll(query, false, false, false, page, size);
		
		List<AcHost> acHosts = new ArrayList<AcHost>();
		if(hosts != null && hosts.size() != 0) {
			for(appcloud.common.model.Host host: hosts){
				AcHost acHost = generator.hostToAcHost(host);
				fillUpAcHost(acHost);
				acHosts.add(acHost);
			}
		}
		
		logger.info(logStr);
		return acHosts;
	}

}
