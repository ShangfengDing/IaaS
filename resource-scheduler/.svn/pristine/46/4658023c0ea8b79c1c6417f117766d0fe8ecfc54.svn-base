package appcloud.resourcescheduler;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.rabbitmq.client.ConsumerCancelledException;

import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.resourcescheduler.impl.ResourceSchedulerImpl;
import appcloud.rpc.ampq.BasicRPCServer;
import tip.util.log.Log;

public class Scheduler {
	private static Logger logger = Logger.getLogger(Scheduler.class);
	
	public static void main(String args[]) {
		logger.info("start scheduler");
		
		ResourceSchedulerImpl scheduler = new ResourceSchedulerImpl();
		try {
			BasicRPCServer server = new BasicRPCServer(ConnectionConfigs.AMPQ_SERVER_ADDRESS, "", RoutingKeys.RESOUCE_SCHEDULER, ResourceSchedulerService.class, scheduler);
			logger.info("server init SUCCESS!");
			while (true) {
				server.mainloop();
				logger.info("Lost connection, try again");
				try {
					server = new BasicRPCServer(ConnectionConfigs.AMPQ_SERVER_ADDRESS, "", RoutingKeys.RESOUCE_SCHEDULER, ResourceSchedulerService.class, scheduler);
					logger.info("Connected to ampq server");
				} catch (Exception e) {
					logger.warn("Cannot connect to ampq server", e);
					Thread.sleep(1000);
				}
			}
		} catch (IOException e) {
			logger.warn("cannot start server");
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			logger.warn("ConsumerCancelledException");
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.warn("Interruptted");
			e.printStackTrace();
		} catch (Exception e) {
			logger.warn("Exit", e);
		}
		
		logger.info("server exit");
		
	}
}
