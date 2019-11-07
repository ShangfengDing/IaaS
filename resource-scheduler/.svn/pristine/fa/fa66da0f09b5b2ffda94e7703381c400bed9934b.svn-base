/**
 * File: FlavorServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import org.apache.log4j.Logger;

import appcloud.common.model.VmInstanceType;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.resourcescheduler.service.FlavorService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class FlavorServiceImpl implements FlavorService {
	private static Logger logger = Logger
			.getLogger(FlavorServiceImpl.class);
	
	VmInstanceTypeProxy vmInstanceTypeProxy;
	
	@Override
	public Integer createFlavor(String name, Integer ram, Integer disk,
			Integer vcpus) throws RpcException {
		String flavorUuid = null;
		try {
			vmInstanceTypeProxy.save(new VmInstanceType(null, flavorUuid, name, vcpus, ram, disk));
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
		
		VmInstanceType vmInstanceType = null;
		try {
			vmInstanceType = vmInstanceTypeProxy.getByUuid(flavorUuid);
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
		if (vmInstanceType != null){
			return vmInstanceType.getId();
		}
		else{
			return null;
		}
	}

}
