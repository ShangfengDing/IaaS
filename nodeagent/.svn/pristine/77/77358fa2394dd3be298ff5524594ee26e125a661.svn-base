package appcloud.nodeagent.monitor;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import tip.client.ClientFactory;
import tip.client.Request;
import tip.client.Response;
import tip.client.TipClient;
import tip.client.TipEndPoint;
import appcloud.common.InfoProto;
import appcloud.common.InfoProto.EntityMessage;
import appcloud.common.InfoProto.InfoMessage;
import appcloud.common.InfoProto.InfoMessage.Builder;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.Host;
import appcloud.common.model.HostLoad;
import appcloud.common.model.Instance;
import appcloud.common.model.Load;
import appcloud.common.model.Service;
import appcloud.common.model.VmDailyLoad;
import appcloud.common.model.Instance.InstanceStatusEnum;
import appcloud.common.model.Instance.InstanceTypeEnum;
import appcloud.common.model.Service.ServiceTypeEnum;
import appcloud.common.proxy.InstanceProxy;
import appcloud.common.proxy.LoadProxy;
import appcloud.common.proxy.VmLoadProxy;
import appcloud.common.service.VMControllerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.RoutingKeyGenerator;
import appcloud.nodeagent.info.NodeAgent;
import appcloud.nodeagent.util.ServiceManager;
import appcloud.rpc.tip.TipRPCClient;

/**
 * 
 * @author wylazy
 *
 */
public class VmMonitor implements Runnable {

	private static final Logger logger = Logger.getLogger(VmMonitor.class);
	
    public static final String INFO_TIP_ID = "info";
    private VMControllerService vmController;
    private VmLoadProxy vmLoadProxy;
    private Host host;
    private ServiceMonitor serviceMonitor;
    
    public VmMonitor() {
	}
    
	public void run() {
		try {
			
			String vmControllerName = ServiceTypeEnum.VM_CONTROLLER.toString();
			ServiceManager sm = ServiceManager.getInstance();
			
			
			if (vmController == null) {
				int vmPort = sm.getService(vmControllerName).getMonitorPort();
				vmController = (VMControllerService) ConnectionFactory.getTipProxy(VMControllerService.class, "tcp://127.0.0.1:"+vmPort);
			}

			if (sm.isRunning(vmControllerName)) {
				String routingKey = RoutingKeyGenerator.getRoutingKey(RoutingKeys.VM_CONTROLLER_PRE, host.getUuid());
				List<HostLoad> loads = vmController.collectVMLoadList(routingKey);
				if (loads.isEmpty()) {
					logger.debug("vmLoad list is empty.");
				} else {
					for (HostLoad vmLoad : vmController.collectVMLoadList(routingKey)) {
						logger.debug("save vmLoad " + vmLoad);
						vmLoadProxy.save(vmLoad);
					}
				}
			} else {
				logger.debug("VM Service is not running in this host");
			}
			
		} catch (Throwable tr) {
			logger.error("vmController error", tr);
		}
	}
    
	public VMControllerService getVmController() {
		return vmController;
	}
	
	public void setVmController(VMControllerService vmController) {
		this.vmController = vmController;
	}
	
	public VmLoadProxy getVmLoadProxy() {
		return vmLoadProxy;
	}
	
	public void setVmLoadProxy(VmLoadProxy vmLoadProxy) {
		this.vmLoadProxy = vmLoadProxy;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}

	public ServiceMonitor getServiceMonitor() {
		return serviceMonitor;
	}

	public void setServiceMonitor(ServiceMonitor serviceMonitor) {
		this.serviceMonitor = serviceMonitor;
	}

}
