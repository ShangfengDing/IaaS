package appcloud.common.service;

import appcloud.common.model.Host;
import appcloud.common.model.HostLoad;
import appcloud.common.model.Service;
import appcloud.rpc.ampq.annotation.RoutingKeyAnnotation;
import appcloud.rpc.tools.RpcException;

public interface NodeMonitorService {
	@RoutingKeyAnnotation(index=0)
	public Service startService(String routingKey,String serviceName) throws RpcException;
	
	@RoutingKeyAnnotation(index=0)
	public void stopService(String routingKey,String serviceName)throws RpcException;
	
	@RoutingKeyAnnotation(index=0)
	public Service getService(String routingKey,String serviceName)throws RpcException;
	
	@RoutingKeyAnnotation(index = 0)
	public HostLoad getCurrentLoad(String routingKey, Host host) throws RpcException ;
}
