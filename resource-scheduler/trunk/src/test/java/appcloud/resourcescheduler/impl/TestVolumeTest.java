/**
 * File: TestVolumeTest.java
 * Author: weed
 * Create Time: 2013-6-7
 */
package appcloud.resourcescheduler.impl;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class TestVolumeTest {
	public static void main(String[] args) throws RpcException {
		VolumeSchedulerService volumeScheduler = (VolumeSchedulerService) ConnectionManager
				.getInstance().getAMQPService(VolumeSchedulerService.class,
						RoutingKeys.VOLUME_SCHEDULER);
		
		volumeScheduler.deleteVolume("b2e33190b28448e6b048c7bff4c8c47e", null);
		volumeScheduler.deleteVolume("4cf2683a71394324bbe91370e41c3084", null);
	}
}
