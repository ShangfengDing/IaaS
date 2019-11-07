package appcloud.vmscheduler.strategy.filter;

import appcloud.common.model.Host;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstanceType;

/**
 * the base class of filter,other filter extends this and implements the
 * hostPass function.
 * 
 * @author haowei.yu
 * 
 */
public abstract class BaseHostFilter extends BaseFilter {

	public boolean filterOne(Host host, VmInstance instance, VmInstanceType instanceType, Integer clusterID) {
		return hostPass(host, instance, instanceType, clusterID);
	}

	protected abstract boolean hostPass(Host host, VmInstance instance, VmInstanceType instanceType, Integer clusterID);

}
