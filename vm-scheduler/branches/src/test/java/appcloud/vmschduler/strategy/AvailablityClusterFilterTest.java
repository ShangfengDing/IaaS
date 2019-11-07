package appcloud.vmschduler.strategy;

import java.util.ArrayList;
import java.util.List;

import appcloud.common.model.Host;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.vmscheduler.strategy.HostFilterHandler;

public class AvailablityClusterFilterTest {
	public static void main(String[] args) {
		ServiceTypeEnum serviceTypeEnum = ServiceTypeEnum.VM_CONTROLLER;
		VmInstance instance = new VmInstance();
		VmInstanceType vmInstanceType = new VmInstanceType();
		vmInstanceType.setVcpus(2);
		vmInstanceType.setMemoryMb(512);
		
		instance.setAvailabilityZoneId(1);
		instance.setAvailabilityClusterId(1);
		instance.setVmInstanceType(vmInstanceType);
		
		
//		BaseHostFilter availablityClusterFilter = new AvailablityClusterFilter();
//		List<Host> resultHostList = availablityClusterFilter.getFilterPassHost(instance, ServiceTypeEnum.VM_CONTROLLER);
//		HostFilterHandler hostFilterHandler = new HostFilterHandler();
//		List<Host> resultHostList = hostFilterHandler.getAvailHost(instance, serviceTypeEnum);
//		
//		List<String> ipList = new ArrayList<String>();
//		for(int i = 0; i < resultHostList.size(); i++){
//			ipList.add(resultHostList.get(i).getIp());
//		}
//		System.out.println(ipList);
		
//		System.exit(0);
//		List<String> ipResult = new ArrayList<String>();
//		ipResult.add("192.168.1.24");
//		ipResult.add("192.168.1.29");
//		ipResult.add("192.168.1.57");
	
	}
}
