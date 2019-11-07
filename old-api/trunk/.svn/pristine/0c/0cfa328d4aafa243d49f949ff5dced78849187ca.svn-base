package appcloud.api.manager.real;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.jersey.api.NotFoundException;

import appcloud.api.beans.Load;
import appcloud.api.beans.Server;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.HostManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.common.model.HostLoad;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.proxy.CommonLoadProxy;
import appcloud.common.proxy.DailyLoadProxy;
import appcloud.common.proxy.HostLoadProxy;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.MonthLoadProxy;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;

public class RealHostManager implements HostManager{
	private static Logger logger = Logger.getLogger(RealHostManager.class);

	private HostProxy hostProxy;
	private static HostLoadProxy hostLoadProxy;
	private static DailyLoadProxy dailyLoadProxy;
	private static MonthLoadProxy monthLoadProxy;
	private static ServiceProxy serviceProxy;
	private BeansGenerator generator;
	
	private static RealHostManager manager = new RealHostManager();
	
	public static RealHostManager getInstance() {
		return manager;
	}
	
	private RealHostManager() {
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
		serviceProxy = (ServiceProxy) ConnectionFactory.getTipProxy(ServiceProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}

	@Override
	public List<appcloud.api.beans.Host> getList(String tenantId, boolean detail) throws Exception{
		logger.info(String.format("GET HOSTS request got: %s", tenantId));
		return getHosts(tenantId, detail);
	}

	@Override
	public appcloud.api.beans.Host get(String tenantId, String hostName) throws Exception{
		logger.info(String.format("GET HOST request got: %s, %s", tenantId, hostName));
		return getSingleHost(hostName);
	}

	private List<appcloud.api.beans.Host> getHosts(String tenantId, boolean detailed) throws Exception{
		QueryObject<appcloud.common.model.Host> query = new QueryObject<appcloud.common.model.Host>();
		List<appcloud.api.beans.Host>apiHosts = new ArrayList<appcloud.api.beans.Host>();
		
		//FIXME
		List<? extends appcloud.common.model.Host> vmHosts = hostProxy.searchAll(query, false, false, false);
		for(appcloud.common.model.Host vmHost: vmHosts){
			apiHosts.add(generator.hostToHost(vmHost, false));
		}
		return apiHosts;
	}
	
	
	private appcloud.api.beans.Host getSingleHost(String hostName) throws Exception{
		QueryObject<appcloud.common.model.Host> query = new QueryObject<appcloud.common.model.Host>();
		//WARNING
		query.addFilterBean(new FilterBean<appcloud.common.model.Host>("uuid", hostName, FilterBeanType.EQUAL));
		List<? extends appcloud.common.model.Host> vmHosts = null;
		
		vmHosts = hostProxy.searchAll(query, false, false, false);
		
		if(vmHosts.size() == 1)
			return generator.hostToHost(vmHosts.get(0), true);
		else{
			throw new NotFoundException();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Load> getMonitorData(String tenantId, String hostName,
			String type, Timestamp startTime, Timestamp endTime) {
		logger.info(String.format("Monitor request: serverId:%s, type:%s, start:%s, end:%s",
				hostName, type, startTime, endTime));
		CommonLoadProxy proxy;
		
		if (type.equalsIgnoreCase("day")) {
			proxy = hostLoadProxy;
		} else if (type.equalsIgnoreCase("month")) {
			proxy = dailyLoadProxy;
		} else {
			proxy = monthLoadProxy;
		}
		
		List<HostLoad> hostLoads = (List<HostLoad>) proxy.getLoads(hostName, startTime, endTime);
		
		List<Load> loads = new ArrayList<Load>();
		
		for (HostLoad hostLoad : hostLoads) {
			loads.add(generator.vmLoadToLoad(hostLoad));
		}
		
		return loads;
	}
}
