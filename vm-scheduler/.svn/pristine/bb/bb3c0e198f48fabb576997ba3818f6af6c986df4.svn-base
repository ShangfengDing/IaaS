package appcloud.vmscheduler.strategy.filter;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;


/**
 * judge the host by cluster!
 * @author haowei.yu
 *
 */
public class AvailablityClusterFilter extends BaseHostFilter {


	@Override
	public boolean hostPass(Host host, VmInstance instance, VmInstanceType instanceType, Integer clusterID) {
		if(host.getClusterId() == clusterID){
			return true;
		}else{
			return false;
		}
		
	}
}
