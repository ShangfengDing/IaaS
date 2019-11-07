package appcloud.vmscheduler.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import appcloud.common.model.Host;
import appcloud.common.model.Host.HostStatusEnum;
import appcloud.common.model.HostLoad;
import appcloud.common.model.Service;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.vmscheduler.impl.DBAgent;

public class RandomSelector implements SelectorService {
	private static DBAgent dbAgent = DBAgent.getInstance(); 
	private static Random randomIndex = new Random();
	private static Logger logger = Logger.getLogger(RandomSelector.class);
	
	@Override
	public List<Host> selectNodes(int hostNum, 
								  VmInstance instance,	
								  VmInstanceType instanceType,
								  Integer cluserID) {
		List<Host> selectedHosts = new ArrayList<Host>();
		/*
		 * step1: 选择该availability zone里的所有host:
		 * 由于service与host分离,此处获取出的都是service list,每个service里用with方式获取出了host
		 */
		//List<Service> hosts = dbAgent.getRunningServiceList(instance.getAvailabilityZoneId(), ServiceTypeEnum.VM_CONTROLLER);
		List<Service> hosts = dbAgent.getRunningServiceListAllCluster(instance.getAvailabilityZoneId(), ServiceTypeEnum.VM_CONTROLLER);
		/*
		 * step2：如果host多于0，在这个host list中选择一个合适的节点：两重选择
		 * 1）选择host status正常或低负载的节点
		 * 2）获取host load，根据给定公式，选择各个利用率符合instanceType的节点
		 * 3）如果按照以上要求，未选择到合适的节点，则随机选择一个节点即可（此处可能会返回一个高负载的节点，再做一下抽象）
		 */
		/**
		 * 每个集群都返回一个host,只有一个集群，就返回hostNum个host
		 * 
		 */
		if (hosts.size() > 0) {
			
			for(int i = 0; i < hosts.size(); i++){
				selectedHosts.add(hosts.get(i).getHost());
				logger.debug("select the host: " + hosts.get(i));
			}
		}
		return selectedHosts;
	}
	
	private boolean ServiceMeetTheNeeds(Service host) {
		if (host.getHost().getStatus().equals(HostStatusEnum.LOW_LOAD) || 
			host.getHost().getStatus().equals(HostStatusEnum.NORMAL_LOAD)) {
			return true;
		}
		return false;
	}
	
	private int randomSelect(int index) {
		return Math.abs(randomIndex.nextInt()) % index;		
	}

	private boolean HostLoadMeetTheNeeds(HostLoad hostLoad, VmInstanceType instanceType) {
		//比较公式放在这
		//if (hostLoad.getCpuPercent() < 0.8)
		return true;
	}
	
	public static void main(String[] args) {
		RandomSelector a = new RandomSelector();
		VmInstance vmInstance = new VmInstance();
		vmInstance.setAvailabilityZoneId(1);
		VmInstanceType instanceType = new VmInstanceType();
		//a.selectNodes(0, vmInstance, instanceType);
	}
}
