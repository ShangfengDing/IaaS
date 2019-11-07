/**
 * File: StubScheduler.java
 * Author: weed
 * Create Time: 2013-4-27
 */
package appcloud.resourcescheduler.fake;


import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.service.monitor.BTYunhaiService;

/**
 * @author weed
 *
 */
public class FakeScheduler extends BTYunhaiService{

	/**
	 * @param routingkey
	 * @param interfaceClass
	 * @param interfaceInstance
	 */
	public FakeScheduler(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}

	public static void main(String args[]) {
		FakeResourceScheduler stubRS = new FakeResourceScheduler();
		final FakeScheduler stubServer = new FakeScheduler(RoutingKeys.RESOUCE_SCHEDULER,ResourceSchedulerService.class,stubRS);
		
		stubServer.run();
		
		
//		logger.info("start scheduler");
//		
//		StubResourceScheduler scheduler = new StubResourceScheduler();
//		try {
//			BasicRPCServer server = new BasicRPCServer(ConnectionConfigs.AMPQ_SERVER_ADDRESS, "", RoutingKeys.RESOUCE_SCHEDULER, ResourceSchedulerService.class, scheduler);
//			logger.info("server init SUCCESS!");
//			while (true) {
//				server.mainloop();
//				logger.info("Lost connection, try again");
//				try {
//					server = new BasicRPCServer(ConnectionConfigs.AMPQ_SERVER_ADDRESS, "", RoutingKeys.RESOUCE_SCHEDULER, ResourceSchedulerService.class, scheduler);
//					logger.info("Connected to ampq server");
//				} catch (Exception e) {
//					logger.warn("Cannot connect to ampq server", e);
//					Thread.sleep(1000);
//				}
//			}
//		} catch (IOException e) {
//			logger.warn("cannot start server");
//			e.printStackTrace();
//		} catch (ConsumerCancelledException e) {
//			logger.warn("ConsumerCancelledException");
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			logger.warn("Interruptted");
//			e.printStackTrace();
//		} catch (Exception e) {
//			logger.warn("Exit", e);
//		}
//		
//		logger.info("server exit");
		
	}
	
}
