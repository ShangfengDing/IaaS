package appcloud.nodeagent;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.Host;
import appcloud.common.model.Service;
import appcloud.common.proxy.HostProxy;
import appcloud.common.proxy.ServiceProxy;
import appcloud.common.service.NodeMonitorService;
import appcloud.common.service.monitor.BTYunhaiService;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.nodeagent.info.NodeCmd;
import appcloud.nodeagent.util.AppUtils;
import appcloud.nodeagent.util.ServiceManager;
public class NodeMonitorServer extends BTYunhaiService{
	
	private static Logger logger = Logger.getLogger(NodeMonitorService.class);
	public NodeMonitorServer(String routingkey, Class<?> interfaceClass,
			Object interfaceInstance) {
		super(routingkey, interfaceClass, interfaceInstance);
	}
	
	public static void printUsage() {
		StringBuilder usage = new StringBuilder(NodeMonitorService.class.getName());
		usage.append(" (cmd)\n")
			 .append("cmd是一下中的一个\n")
		     .append("  start 启动NodeMonitor\n")
		     .append("  last  显示上次运行的服务\n")
		     .append("  resume 恢复运行上次运行的服务\n");
		System.out.println(usage);
	}
	
	/**
	 * 启动NodeMonnitor
	 * @param uuid hostUuid
	 */
	public static void startNodeMonitor(String uuid) {
		AppUtils.saveOrUpdateHost();//将host信息更新到数据库
		AppUtils.startMonitors();  //开启监控功能

		NodeMonitorService ns = new NodeMonitorServiceImpl();
		
		String routingkey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.NODE_MONITOR_PRE, uuid);	
		
		logger.debug("***********start server with routingKey = " + routingkey);
		NodeMonitorServer server = new NodeMonitorServer(routingkey, NodeMonitorService.class, ns);
		server.run();
	}
	
	/**
	 * 打印上次运行的服务
	 * @param uuid hostUuid
	 */
	public static void printLastServices(String uuid) {
		List<Service> services = lastRunningServices(uuid);
		for (Service service : services) {
			System.out.println(service.getType());
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		if(args.length == 0) {
			printUsage();
			return;
		}

		NodeCmd nodeCmd = NodeCmd.valueOf(args[0].toUpperCase());
		String uuid = AppUtils.getHostUuid();
		
		switch (nodeCmd) {
		case START :
			startNodeMonitor(uuid);
			break;
		case LAST :
			printLastServices(uuid);
			break;
		case RESUME :
			startServices(lastRunningServices(uuid));
			break;
		default:
			break;
		}
	}
	
	/**
	 * 上次运行的服务
	 * @param uuid hostUuid
	 * @return 上次运行的服务
	 */
	private static List<Service> lastRunningServices(String uuid) {
		ServiceProxy serviceProxy = (ServiceProxy) AppUtils.getApplicationContext().getBean("serviceProxy");
		List<? extends Service> serviceLists = serviceProxy.getHostServicesByUuid(uuid);
		
		List<Service> runningLists = new LinkedList<Service>();
		for (Service service : serviceLists) {
			if (service.getMonitorPort() != 0 && Service.ServiceStatus.RUNNING.equals(service.getServiceStatus())) {
				runningLists.add(service);
			}
		}
		
		return runningLists;
	}
	
	/**
	 * 启动服务
	 * @param services
	 */
	private static void startServices(List<Service> services) {
		ServiceManager sm = ServiceManager.getInstance();
		for (Service service : services) {
			sm.startService(service.getType().toString());
		}
	}
	
}
