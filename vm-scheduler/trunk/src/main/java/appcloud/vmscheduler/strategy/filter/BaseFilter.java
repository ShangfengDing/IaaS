package appcloud.vmscheduler.strategy.filter;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.vmscheduler.impl.DBAgent;

/**
 * 
 * @author haowei.yu
 *
 */
public abstract class BaseFilter {
	public List<Host> filterAll(List<Host> hostList, VmInstance instance, VmInstanceType instanceType, Integer clusterID, ServiceTypeEnum serviceTypeEnum){
		List<Host> resultHostList = new ArrayList<Host>();
		if(hostList == null){
			hostList = getLivingHost(instance, serviceTypeEnum);
		}
		for(Host host : hostList){
			if(filterOne(host, instance, instanceType, clusterID)){
				resultHostList.add(host);
			}
		}
		return resultHostList;
	}
	protected abstract boolean filterOne(Host host, VmInstance instance,  VmInstanceType instanceType, Integer clusterID);

	private static DBAgent dbAgent = DBAgent.getInstance(); 
	
	private List<Host> getLivingHost(VmInstance instance, ServiceTypeEnum serviceTypeEnum){
		List<Service> hosts = dbAgent.getRunningServiceList(instance.getAvailabilityZoneId(), serviceTypeEnum);
		List<Host> selectedHosts = new ArrayList<Host>();
		for(Service host:hosts){
			selectedHosts.add(host.getHost());
		}
		return selectedHosts;
	}
}
