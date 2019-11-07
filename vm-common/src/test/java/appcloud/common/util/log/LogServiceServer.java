/**
 * File: LogServiceServer.java
 * Author: weed
 * Create Time: 2012-11-23
 */
package appcloud.common.util.log;

import java.io.IOException;

import com.rabbitmq.client.ConsumerCancelledException;

import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.LogService;
import appcloud.rpc.ampq.BasicRPCServer;

/**
 * @author weed
 *
 */
public class LogServiceServer {
	public static void main(String[] args) throws IOException {
		System.out.println("LogServiceServer is running!");

		LogServiceImpl serverInstance = new LogServiceImpl();
		BasicRPCServer server = new BasicRPCServer(
				ConnectionConfigs.AMPQ_SERVER_ADDRESS, "jlmsg",
				RoutingKeys.LOGGER, LogService.class, serverInstance);

		try {
			server.mainloop();
		} catch (ConsumerCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
