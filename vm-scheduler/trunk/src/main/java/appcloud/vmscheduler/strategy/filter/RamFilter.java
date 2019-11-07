package appcloud.vmscheduler.strategy.filter;

import java.util.List;

import appcloud.common.model.Cluster;
import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;
import appcloud.vmscheduler.impl.DBAgent;


/**
 * judge the host by ram!
 * @author haowei.yu
 *
 */
public class RamFilter extends BaseHostFilter {
	
	private DBAgent agent = DBAgent.getInstance();

	@Override
	public boolean hostPass(Host host, VmInstance instance, VmInstanceType instanceType, Integer clusterID) {
		Cluster cluster = agent.getClusterById(clusterID);
		Integer memOverSell = cluster.getMemoryOversell();
		//主机预留4G内存
		Integer freeMem = (host.getMemoryMb() - 4000) * memOverSell - getUsedRamMb(host) * 100;
		Integer needMem = instanceType.getMemoryMb() * 100;
		if(freeMem > needMem){
			return true;
		}else{
			return false;
		}
	}
	
//	private float getFreeMemory(Host host){
//		return host.getMemoryMb() * (100 - DBAgent.getInstance().getFreshHostLoad(host.getUuid()).getMemPercent())/100;
//	}
	
	public Integer getUsedRamMb(Host host) {
		Integer usedRamCount = 0;
		List<VmInstance> instances = agent.getVmInstanceByHostUuid(host.getUuid());
		VmInstanceType instanceType = null;
		for(VmInstance instance : instances) {
			instanceType = agent.getVmInstanceTypeByID(instance.getInstanceTypeId());
			if(instanceType != null) {
				usedRamCount = usedRamCount + instanceType.getMemoryMb();
			}
		}
		return usedRamCount * 1000;
	}
}
