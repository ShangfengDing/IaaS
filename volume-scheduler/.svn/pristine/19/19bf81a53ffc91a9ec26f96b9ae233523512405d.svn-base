package appcloud.vs.strategy.filter;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.model.Cluster;
import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.vs.impl.DBUtil;


public abstract class BaseFilter {
	public List<Host> filterAll(List<Host> hostList, Cluster cluster, Integer size, ServiceTypeEnum serviceTypeEnum, Host exHost){
		List<Host> resultHostList = new ArrayList<Host>();
		if(hostList == null){
			hostList = getLivingHost(cluster, serviceTypeEnum);
		}
		for(Host host : hostList){
			if(filterOne(host, size, exHost)){
				resultHostList.add(host);
			}
		}
		return resultHostList;
	}
	protected abstract boolean filterOne(Host host, Integer size, Host exHost);

	private static DBUtil dbUtil = DBUtil.getInstance(); 
	
	private List<Host> getLivingHost(Cluster cluster, ServiceTypeEnum serviceTypeEnum){
		List<Service> hosts = dbUtil.getRunningServiceList(cluster.getAvailabilityZoneId(), cluster.getId(), serviceTypeEnum);
		List<Host> selectedHosts = new ArrayList<Host>();
		for(Service host:hosts){
			selectedHosts.add(host.getHost());
		}
		return selectedHosts;
	}
}
