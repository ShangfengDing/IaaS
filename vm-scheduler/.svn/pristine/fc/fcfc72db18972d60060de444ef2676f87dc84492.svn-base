package appcloud.vmscheduler.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.vmscheduler.impl.DBAgent;

public class HostSelector implements SelectorService {
	private static Logger logger = Logger.getLogger(HostSelector.class);
	private static DBAgent dbAgent = DBAgent.getInstance();

	@Override
	public List<Host> selectNodes(int hostNum, VmInstance instance,
			VmInstanceType instanceType, Integer clusterID) {
		List<Service> hosts = dbAgent
				.getRunningServiceList(instance.getAvailabilityZoneId(),
						ServiceTypeEnum.VM_CONTROLLER);
		List<Host> selectedHosts = new ArrayList<Host>();
		List<Host> hostList = new ArrayList<Host>();
		for (Service host : hosts) {
			hostList.add(host.getHost());
		}
		HostFilterHandler hostFilterHandler = new HostFilterHandler();
		selectedHosts = hostFilterHandler.getAvailHost(instance,
				ServiceTypeEnum.VM_CONTROLLER, hostList);
		HostSorter hostSorter = new HostSorter();
		logger.info("before sort hosts:");
		for (Host host : selectedHosts) {
			try {
				logger.info(host.getUuid() + "'s CPU&memory:"
						+ hostSorter.scoreHost(host));
			} catch (Exception e) {
				logger.error("memoryHostSorter error");
				e.printStackTrace();
			}
		}
		Collections.sort(selectedHosts, HostComparatorFactory
				.getHostComparator(clusterID, selectedHosts));
		logger.info("after sort hosts:");
		for (Host host : selectedHosts) {
			try {
				logger.info(host.getUuid() + "'s CPU&memory:"
						+ hostSorter.scoreHost(host));
			} catch (Exception e) {
				logger.error("memoryHostSorter error");
				e.printStackTrace();
			}
		}
		return selectedHosts;
	}

	public static void main(String[] args) {
		VmInstance vmInstance = new VmInstance();
		VmInstanceType vmInstanceType = new VmInstanceType();
		vmInstanceType.setVcpus(4);
		vmInstanceType.setMemoryMb(32100);
		vmInstance.setAvailabilityZoneId(1);
		vmInstance.setAvailabilityClusterId(2);
		vmInstance.setVmInstanceType(vmInstanceType);

		HostSelector a = new HostSelector();
		List<Host> hostList = a.selectNodes(1, vmInstance, vmInstanceType, 1);
		//logger.info(hostList);
		// char ch = 'a';
		// while(ch!='s'){
		// logger.info("memory weight:"+Constants.config.getInt("memoryWeight"));
		// try {
		// ch=(char)System.in.read();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }//while
		//System.exit(0);
	}

}
