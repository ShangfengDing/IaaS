/**
 * File: VMInstanceResource.java
 * Author: weed
 * Create Time: 2013-5-21
 */
package appcloud.resourcescheduler.transaction.rollback.resource;

import org.apache.log4j.Logger;

import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.transaction.rollback.IRollbackable;
import appcloud.resourcescheduler.common.ConnectionManager;

/**
 * @author weed
 *
 */
public class VMInstanceResource implements IRollbackable {

	private static Logger logger = Logger.getLogger(VMInstanceResource.class);
	private static VmInstanceProxy vmInstanceProxy = (VmInstanceProxy) ConnectionManager
			.getInstance().getDBProxy(VmInstanceProxy.class);
	
	String instanceUuid;
	
	public VMInstanceResource(String instanceUuid) {
		super();
		this.instanceUuid = instanceUuid;
	}

	@Override
	public void rollback(RpcExtra rpcExtra) {
		logger.info("VMInstanceResource.rollback");
		try {
			VmInstance instance = vmInstanceProxy.getByUuid(instanceUuid, false, false, false, false, false, false, false, false);
			instance.setVmStatus(VmStatusEnum.ERROR);
			vmInstanceProxy.update(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
