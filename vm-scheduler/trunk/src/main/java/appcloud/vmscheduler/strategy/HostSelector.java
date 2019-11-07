package appcloud.vmscheduler.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.vmscheduler.impl.DBAgent;
import appcloud.vmscheduler.strategy.filter.CoreFilter;
import appcloud.vmscheduler.strategy.filter.RamFilter;

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
		
		Set<String> filterSet = new LinkedHashSet<String>();
		String [] filterNameList = new String[] { "AvailablityClusterFilter" , "CoreFilter", "RamFilter" };
		for (int i = 0; i < filterNameList.length; i++) {
			filterSet.add(filterNameList[i]);
		}
		HostFilterHandler hostFilterHandler = new HostFilterHandler(filterSet);
		
		selectedHosts = hostFilterHandler.getAvailHost(instance, instanceType,
				clusterID, ServiceTypeEnum.VM_CONTROLLER, hostList);
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
//		VmInstance vmInstance = new VmInstance();
//		VmInstanceType vmInstanceType = new VmInstanceType();
//		vmInstanceType.setVcpus(4);
//		vmInstanceType.setMemoryMb(4);
//		vmInstance.setAvailabilityZoneId(1);
//		vmInstance.setAvailabilityClusterId(1);
//		vmInstance.setVmInstanceType(vmInstanceType);
//
//		HostSelector a = new HostSelector();
//		List<Host> hostList = a.selectNodes(1, vmInstance, vmInstanceType, 1);
//		for (Host host : hostList) {
//			logger.info(host);
//		}
		
		
		
		List<Service> hosts = dbAgent
				.getRunningServiceList(1,
						ServiceTypeEnum.VM_CONTROLLER);
		List<Host> hostList = new ArrayList<Host>();
		for (Service host : hosts) {
			hostList.add(host.getHost());
		}
		
		CoreFilter cf = new CoreFilter();
		RamFilter rf = new RamFilter();
		for(Host h : hostList) {
			logger.info(h.getIp() + ": Core:" + h.getCpuCore() + "/" + cf.getUsedCpuCore(h) + "/" + (float)cf.getUsedCpuCore(h)/(float)h.getCpuCore()
					+ ",Mem:" + h.getMemoryMb() + "/" + rf.getUsedRamMb(h) + "/" + (float)rf.getUsedRamMb(h)/(float)h.getMemoryMb());
		}
	}

}
