package appcloud.vmscheduler.strategy.filter;

import java.util.List;

import appcloud.common.model.Cluster;
import appcloud.common.model.Host;
import appcloud.common.model.ResourceStrategy;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.vmschduler.utils.RSParamUtil;
import appcloud.vmscheduler.impl.DBAgent;


/**
 * judge the host by cpu num!
 * test
 * @author haowei.yu
 *
 */
public class CoreFilter extends BaseHostFilter {
	
	private DBAgent agent = DBAgent.getInstance();


	@Override
	public boolean hostPass(Host host, VmInstance instance, VmInstanceType instanceType, Integer clusterID) {
		Cluster cluster = agent.getClusterById(clusterID);
		Integer cpuOverSell = cluster.getCpuOversell();
		Integer freeCpu = host.getCpuCore() * cpuOverSell - getUsedCpuCore(host) * 100;
		Integer needCpu = instanceType.getVcpus() * 100;
		if(freeCpu > needCpu){
			return true;
		}else{
			return false;
		}
	
		
	}
	
	public Integer getUsedCpuCore(Host host) {
		Integer usedCpuCount = 0;
		List<VmInstance> instances = agent.getVmInstanceByHostUuid(host.getUuid());
//		for(VmInstance in : instances) {
//			System.out.println(in);
//		}
		
		VmInstanceType instanceType = null;
		for(VmInstance instance : instances) {
			instanceType = agent.getVmInstanceTypeByID(instance.getInstanceTypeId());
			if(instanceType != null) {
				usedCpuCount = usedCpuCount + instanceType.getVcpus();
			}
		}
		return usedCpuCount;
	}
}
