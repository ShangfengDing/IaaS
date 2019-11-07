package appcloud.vmscheduler;

import org.apache.log4j.Logger;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.vmscheduler.impl.VMSchedulerImpl;

public class VMSchedulerServer extends BTYunhaiService{
	public VMSchedulerServer(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}

	private static Logger logger = Logger.getLogger(VMSchedulerServer.class);
	
	public static void main(String args[]) {
		logger.info("start scheduler");
		
		VMSchedulerImpl service = new VMSchedulerImpl();
		VMSchedulerServer server = new VMSchedulerServer(RoutingKeys.VM_SCHEDULER, VMSchedulerImpl.class, service);
		server.run();
	}
}
