package appcloud.nodeagent;

import appcloud.common.model.Service;
import appcloud.common.service.NodeMonitorService;
import appcloud.nodeagent.util.ServiceManager;
import appcloud.rpc.ampq.annotation.RoutingKeyAnnotation;
import appcloud.rpc.tools.RpcException;

public class NodeMonitorServiceImpl implements NodeMonitorService{

	public Service startService(String routingKey, String serviceName)
			throws RpcException {
		ServiceManager sm = ServiceManager.getInstance();
		if(!sm.hasService(serviceName)){
			throw new RpcException("no such service" + serviceName);
		}

		if(!sm.isRunning(serviceName)){
			sm.startService(serviceName);
		}
		return sm.getService(serviceName);
	}

	public void stopService(String routingKey, String serviceName)
			throws RpcException {
		ServiceManager sm = ServiceManager.getInstance();
		if(!sm.hasService(serviceName)){
			throw new RpcException("no such service" + serviceName);
		}
		
		if (sm.isRunning(serviceName)) {
			sm.stopService(serviceName);
		}
	}

	@Override
	@RoutingKeyAnnotation(index = 0)
	public Service getService(String routingKey, String serviceName) throws RpcException {
		ServiceManager sm = ServiceManager.getInstance();
		if (sm.hasService(serviceName)) {
			return sm.getService(serviceName);
		} else {
			return null;
		}
	}

}
