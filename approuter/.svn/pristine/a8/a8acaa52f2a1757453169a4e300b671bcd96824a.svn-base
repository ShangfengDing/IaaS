/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appcloud.approuter.bootstrap;

import org.apache.log4j.Logger;

import appcloud.approuter.amqp.service.AppRouterServiceImpl;
import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.AppRouterService;
import appcloud.rpc.ampq.BasicRPCServer;

import com.rabbitmq.client.ConsumerCancelledException;

/**
 * 
 * @author weed
 */
public class Main {
	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		logger.info("***************************************************");
		logger.info("* Welcome to use Appcloud AppRouter!");
		logger.info("* Version: " + appcloud.approuter.util.Version.ID);
		logger.info("***************************************************");

		AppRouterServiceImpl serverInstance = new AppRouterServiceImpl();
		BasicRPCServer server = new BasicRPCServer(
				ConnectionConfigs.AMPQ_SERVER_ADDRESS, "",
				RoutingKeys.APP_ROUTER, AppRouterService.class, serverInstance);

		try {
			server.mainloop();
		} catch (ConsumerCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("* Appcloud AppRouter closed! *");
	}
}
