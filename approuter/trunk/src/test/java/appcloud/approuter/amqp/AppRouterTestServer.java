package appcloud.approuter.amqp;

import java.io.IOException;

import appcloud.approuter.amqp.service.AppRouterServiceImpl;
import appcloud.common.constant.ConnectionConfigs;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.AppRouterService;
import appcloud.rpc.ampq.BasicRPCServer;

import com.rabbitmq.client.ConsumerCancelledException;

public class AppRouterTestServer {

	public static void main(String[] args) throws IOException {
		System.out.println("AppRouterTestServer is running!");

		AppRouterServiceImpl serverInstance = new AppRouterServiceImpl();
		BasicRPCServer server = new BasicRPCServer(
				ConnectionConfigs.AMPQ_SERVER_ADDRESS, "jlmsg",
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
	}
}
