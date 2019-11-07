package appcloud.flowcontroller.service;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.FLowControllerService;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.flowcontroller.impl.FlowControllerImpl;

public class FlowControllerServer extends BTYunhaiService{
	private static Logger logger = Logger.getLogger(FlowControllerServer.class);
	
	public FlowControllerServer(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}
	
	public static void main(String[] args) {
		try {
			logger.info("FlowController start!!!");
			String hostUuid = FlowControllerServer.getHostUuid(true);
			logger.info("host uuid : " + hostUuid);
			String routingKey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.FLOW_CONTROLLER, hostUuid);
			FlowControllerImpl fcImpl = new FlowControllerImpl();
			FlowControllerServer fcServer = new FlowControllerServer(routingKey, FLowControllerService.class, fcImpl);
			fcServer.run();
		} catch(Exception e) {
			logger.error("start FlowController failed", e);
		}
	}
}
