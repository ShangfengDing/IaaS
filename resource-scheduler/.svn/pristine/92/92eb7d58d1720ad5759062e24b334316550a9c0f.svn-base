/**
 * File: TestScheduler.java
 * Author: weed
 * Create Time: 2013-4-19
 */
package appcloud.resourcescheduler.impl;

import java.util.HashMap;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class TestScheduler {

	/**
	 * @param args
	 * @throws RpcException 
	 */
	public static void main(String[] args) throws RpcException {
//		ConnectionFactory.getResourceScheduler().createVM("testName", "125", "testImageId", 1, 1, 1, new HashMap<String, String>());
		ResourceSchedulerService scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(ResourceSchedulerService.class, RoutingKeys.RESOUCE_SCHEDULER);
		
//		scheduler.createVM("testName", "125", "1", 1, 1, 1, new HashMap<String, String>());
		Integer flavorId = scheduler.createFlavor("testFlavorName", 64, 500, 4, null);
		System.out.println(flavorId);
		ConnectionFactory.close();
	}

}
