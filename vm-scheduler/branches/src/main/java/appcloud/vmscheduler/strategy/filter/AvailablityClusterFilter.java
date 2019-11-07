package appcloud.vmscheduler.strategy.filter;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;


/**
 * judge the host by cluster!
 * @author haowei.yu
 *
 */
public class AvailablityClusterFilter extends BaseHostFilter {


	@Override
	public boolean hostPass(Host host, VmInstance instance) {
		if(host.getClusterId() == instance.getAvailabilityClusterId()){
			return true;
		}else{
			return false;
		}
		
	}
}
