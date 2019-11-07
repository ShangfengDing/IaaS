package appcloud.vmcontroller;

import org.apache.log4j.Logger;
import appcloud.vmcontroller.impl.VMControllerImpl;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.VMControllerService;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.common.util.RoutingKeyGenerator;


/**
 * @author liyuan
 * 监听server
 *
 */
public class VMControllerServer extends BTYunhaiService{
	public VMControllerServer(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}

	private static VMControllerConf agent = VMControllerConf.getInstance();
	private static VMControllerImpl service = VMControllerImpl.getInstance();
	private static Logger logger = Logger.getLogger(VMControllerServer.class);
	
	public static void main(String args[]) throws Exception{
		logger.info("VMController starts!");
		
		String routingkey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.VM_CONTROLLER_PRE, VMControllerServer.getHostUuid(true));
		VMControllerServer server = new VMControllerServer(routingkey, VMControllerService.class, service);
		server.run();
		
		//启动一个线程，每隔段时间，收集信息
		
	}
}
