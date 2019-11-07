/**
 * File: VmVirtualInterfaceResource.java
 * Author: weed
 * Create Time: 2013-4-21
 */
package appcloud.resourcescheduler.transaction.rollback.resource;

import org.apache.log4j.Logger;

import appcloud.common.model.RpcExtra;
import appcloud.common.proxy.VmVirtualInterfaceProxy;
import appcloud.common.transaction.rollback.IRollbackable;
import appcloud.resourcescheduler.common.ConnectionManager;

/**
 * @author weed
 *
 */
public class VmVirtualInterfaceResource implements IRollbackable {
	private static Logger logger = Logger.getLogger(VmVirtualInterfaceResource.class);
	private static VmVirtualInterfaceProxy vmVirtualInterfaceProxy = (VmVirtualInterfaceProxy) ConnectionManager.getInstance().getDBProxy(VmVirtualInterfaceProxy.class);
	
	String instanceUuid;
	
	public VmVirtualInterfaceResource(String instanceUuid) {
		super();
		this.instanceUuid = instanceUuid;
	}

	/* (non-Javadoc)
	 * @see appcloud.resourcescheduler.service.rollback.IRollbackable#rollback()
	 */
	@Override
	public void rollback(RpcExtra rpcExtra) {
		logger.info("VmVirtualInterfaceResource.rollback");
		try {
			vmVirtualInterfaceProxy.deleteByInstanceUuid(instanceUuid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
