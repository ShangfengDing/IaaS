package appcloud.vmcontroller;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.service.VMControllerService;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.vmcontroller.impl.VMControllerImpl;
import appcloud.vmcontroller.monitor.VMMonitor;


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

	private static VMControllerImpl service = VMControllerImpl.getInstance();
	private static Logger logger = Logger.getLogger(VMControllerServer.class);
	private MonitorThread t = null;
	
	class MonitorThread extends Thread{
		boolean running = true;
		@Override
		public void run() {
			VMMonitor monitor = VMMonitor.getInstance();
			//TODO:这个地方不能直接用conf里的参数赋值
			long TIMEOUT = 300000;
			logger.info("init monitor load: " + monitor.getDomainMonitorList());
			logger.info("start refresh monitor thread: " + VMControllerConf.MONITER_INTERVAL);
			while(running){
				try {
					sleep(TIMEOUT);
					
				} catch (InterruptedException e) {
				    logger.error("Thread sleep error", e);
					logger.error(e.getMessage());
				}
				monitor.refreshMonitorLoad();
				logger.info("refreshed monitor load: " + monitor.getDomainMonitorList());
			}
		}
		
		public void markStop(){
			running = false;
		}
	}
	
	public static void main(String args[]) throws Exception{
		logger.info("VMController starts!");
		
		String routingkey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.VM_CONTROLLER_PRE, VMControllerServer.getHostUuid(true));
		VMControllerServer server = new VMControllerServer(routingkey, VMControllerService.class, service);
		server.run();
	}
	
	@Override
	public void initialize() {
		super.initialize();
		tipserver.addService(VMControllerService.class, service);
		t = new MonitorThread();
		t.setName("monitor");
		t.setDaemon(true);
	}
	
	@Override
	public void start() {
		super.start();
		if(!t.isAlive()){	
		t.start();
		}
	}
	
	@Override
	public void stop() {
		t.markStop();
		super.stop();
	}
}
