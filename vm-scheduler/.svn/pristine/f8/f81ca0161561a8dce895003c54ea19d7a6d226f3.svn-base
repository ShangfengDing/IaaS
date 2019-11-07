package appcloud.vmscheduler.strategy.filter;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;
import appcloud.vmscheduler.impl.DBAgent;


/**
 * judge the host by ram!
 * @author haowei.yu
 *
 */
public class RamFilter extends BaseHostFilter {

	@Override
	public boolean hostPass(Host host, VmInstance instance) {
		float memoryLeft = getMemoryUse(host) - instance.getVmInstanceType().getMemoryMb();
		if(memoryLeft > 0){
			return true;
		}else{
			return false;
		}
		
	}
	private float getMemoryUse(Host host){
		return host.getMemoryMb() * DBAgent.getInstance().getFreshHostLoad(host.getUuid()).getMemPercent()/100;
	}
}
