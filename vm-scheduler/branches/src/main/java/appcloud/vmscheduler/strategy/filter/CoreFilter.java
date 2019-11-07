package appcloud.vmscheduler.strategy.filter;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;


/**
 * judge the host by cpu num!
 * test
 * @author haowei.yu
 *
 */
public class CoreFilter extends BaseHostFilter {


	@Override
	public boolean hostPass(Host host, VmInstance instance) {
		if(host.getCpuCore() < instance.getVmInstanceType().getVcpus()){
			return false;
		}else{
			return true;
		}
	
		
	}
}
