package appcloud.resourcescheduler;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.common.util.AlertUtil;
import appcloud.resourcescheduler.common.Constants;
import appcloud.resourcescheduler.impl.ResourceSchedulerImpl;

public class ResourceScheduler extends BTYunhaiService{
	private static Logger logger = Logger.getLogger(ResourceScheduler.class);
	
	/**
	 * @param routingkey
	 * @param interfaceClass
	 * @param interfaceInstance
	 */
	public ResourceScheduler(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}
	
	public static void main(String args[]) {
		logger.info("start scheduler");
		
		AlertUtil.setMODULE_NAME(Constants.MODULE_NAME);
		
		ResourceSchedulerImpl schedulerImpl = new ResourceSchedulerImpl();
		ResourceScheduler scheduler = new ResourceScheduler(RoutingKeys.RESOUCE_SCHEDULER, ResourceSchedulerService.class, schedulerImpl);
		scheduler.run();
		
		logger.info("server exit");
		
	}
}
