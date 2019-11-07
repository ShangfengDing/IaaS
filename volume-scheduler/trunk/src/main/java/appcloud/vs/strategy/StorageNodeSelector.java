package appcloud.vs.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceStatus;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.util.ConnectionFactory;

@Deprecated
public class StorageNodeSelector {
	
	//
	private static Logger logger = Logger.getLogger(StorageNodeSelector.class);
	private HostProxy hostproxy = null;
	private ServiceProxy serviceProxy = null;
	
	private StorageNodeSelector() {
		hostproxy =  (HostProxy) ConnectionFactory.getDBProxy(HostProxy.class);
		serviceProxy = (ServiceProxy) ConnectionFactory.getDBProxy(ServiceProxy.class);
	}
	
	public static StorageNodeSelector getInstance() {
		return new StorageNodeSelector();
	}
	// 算法模块，抛就抛吧
	@SuppressWarnings("unchecked")
	public List<Host> selectNodes(VmZone zone, Host exhost) {
		logger.info("starting select Host in zone: "+ zone + " exhost: " + exhost);
		if(zone == null){
			logger.error("zone is null");
			return Collections.EMPTY_LIST;
		}
		List<Host> selectedHosts = new ArrayList<Host>();
//
//		QueryObject<Host> queryObject = new QueryObject<Host>();
//		queryObject.addFilterBean(new FilterBean<Host>("type", HostTypeEnum.STORAGE_NODE, FilterBeanType.EQUAL));
//		queryObject.addFilterBean(new FilterBean<Host>("zoneId", zone.getId(), FilterBeanType.EQUAL));
//		selectedHosts = (List<Host>)hostproxy.searchAll(queryObject, false, true, true);
		try {
			
			List<? extends Service> services = serviceProxy.getServiceByType(Service.ServiceTypeEnum.VOLUME_PROVIDER, true);
			
			for (Service service : services) {
				
				if(service.getServiceStatus() == ServiceStatus.RUNNING){
					logger.debug("service: " + service.getHostIp() +" "+ service.getHostUuid());
//					if(exhost!=null && exhost.getUuid().equals(service.getHostUuid())) {
//						continue;
//					}
					selectedHosts.add(service.getHost());
				}
			}
			logger.debug("selected hosts num before filtering:" + selectedHosts.size());
			
			// filter by pool size
			
			// sorted by freedisk
			// TODO sorted by free pool size
			Collections.sort(selectedHosts, new HostComparator(new FreediskHostSorter(),selectedHosts));
			
		} catch (Exception e) {
			logger.warn(e);
		}
		logger.info("selected hosts are:" + selectedHosts);
		return selectedHosts;
	}
	
	
	public static void main(String[] args) {
		
		List<Host> hosts = StorageNodeSelector.getInstance().selectNodes(new VmZone(), null);
		for(Host at:hosts) {
			logger.debug(at);
		}
	}

}

