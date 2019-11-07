/**
 * File: TestVmSchduler.java
 * Author: weed
 * Create Time: 2013-4-19
 */
package appcloud.resourcescheduler.impl;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.VMControllerService;
import appcloud.common.service.VMSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class TestVmSchduler {

	/**
	 * @param args
	 * @throws RpcException 
	 */
	public static void main(String[] args) throws RpcException {
		
		VMSchedulerService vmScheduler = (VMSchedulerService) ConnectionFactory.getAMQPService(VMSchedulerService.class, RoutingKeys.VM_SCHEDULER);
		
//		vmScheduler.selectHost("testuuid", "instanceTypeID", 1);
		
		VMControllerService vmController = (VMControllerService) ConnectionFactory.getAMQPService(VMControllerService.class, RoutingKeys.VM_CONTROLLER_PRE);

//		vmController.suspendVM(String routingKey, String instanceUUID);
	}

}
