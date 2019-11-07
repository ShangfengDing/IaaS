/**
 * File: VmMetadataResource.java
 * Author: weed
 * Create Time: 2013-4-21
 */
package appcloud.resourcescheduler.transaction.rollback.resource;

import org.apache.log4j.Logger;

import appcloud.common.model.RpcExtra;
import appcloud.common.proxy.VmInstanceMetadataProxy;
import appcloud.common.transaction.rollback.IRollbackable;
import appcloud.resourcescheduler.common.ConnectionManager;

/**
 * @author weed
 *
 */
public class VmMetadataResource implements IRollbackable {

	private static Logger logger = Logger.getLogger(VmMetadataResource.class);
	private static VmInstanceMetadataProxy vmInstanceMetadataProxy = (VmInstanceMetadataProxy) ConnectionManager.getInstance().getDBProxy(VmInstanceMetadataProxy.class);
	
	private Integer instanceId;
	
	public VmMetadataResource(Integer instanceId) {
		super();
		this.instanceId = instanceId;
	}

	/* (non-Javadoc)
	 * @see appcloud.resourcescheduler.service.rollback.IRollbackable#rollback()
	 */
	@Override
	public void rollback(RpcExtra rpcExtra) {
		logger.info("VmMetadataResource.rollback");
		try {
			vmInstanceMetadataProxy.deleteByVmInstanceId(instanceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
