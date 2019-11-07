package appcloud.vmscheduler.strategy;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.Host.HostStatusEnum;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.vmscheduler.impl.DBAgent;

public class LoadBalanceSelector implements SelectorService {
	private static DBAgent dbAgent = DBAgent.getInstance(); 
	private static Logger logger = Logger.getLogger(RandomSelector.class);
	
	@Override
	public List<Host> selectNodes(int hostNum, 
								  VmInstance instance,
								  VmInstanceType instanceType,
								  Integer clusterID) {
		List<Host> selectedHosts = new ArrayList<Host>();
		
		//get running vm controller services
		List<Service> vmcList = dbAgent.getRunningServiceList(instance.getAvailabilityZoneId(), ServiceTypeEnum.VM_CONTROLLER);
		logger.debug("selected service before filtering:" + vmcList);
		
		for (Service service : vmcList) {
			if (!service.getHost().getStatus().equals(HostStatusEnum.CRASH)) {
				selectedHosts.add(service.getHost());
			}
		}
		logger.debug("selectedHosts before sort:" + selectedHosts);
		
		//sorted by host status
		//Collections.sort(selectedHosts, new HostComparator());
		
		return selectedHosts;
	}
	
	 

}
