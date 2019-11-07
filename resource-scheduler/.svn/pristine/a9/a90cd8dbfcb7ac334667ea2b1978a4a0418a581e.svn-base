/**
 * File: VmVolumeResource.java
 * Author: weed
 * Create Time: 2013-4-21
 */
package appcloud.resourcescheduler.transaction.rollback.resource;


import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmVolume;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.transaction.rollback.IRollbackable;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class VmVolumeResource implements IRollbackable {

	private static VolumeSchedulerService volumeScheduler = (VolumeSchedulerService) ConnectionManager.getInstance().getAMQPService(VolumeSchedulerService.class, RoutingKeys.VOLUME_SCHEDULER);
	private VmVolume volume;
	
	private static Logger logger = Logger.getLogger(VmVolumeResource.class);
	
	public VmVolumeResource(VmVolume volumes) {
		super();
		this.volume = volumes;
	}

	/* (non-Javadoc)
	 * @see appcloud.resourcescheduler.rollback.IRollbackable#rollback()
	 */
	@Override
	public void rollback(RpcExtra rpcExtra) {
		logger.info("VmVolumeResource.rollback");
		try {
			volumeScheduler.destroyVolume(volume.getVolumeUuid(), rpcExtra);
		} catch (RpcException e) {
			e.printStackTrace();
		}
	}
}
