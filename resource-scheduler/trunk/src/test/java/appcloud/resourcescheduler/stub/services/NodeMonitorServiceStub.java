// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.services;

import appcloud.common.model.Host;
import appcloud.common.model.HostLoad;
import appcloud.common.model.Service;
import appcloud.common.service.NodeMonitorService;
import java.lang.String;

import appcloud.rpc.ampq.annotation.RoutingKeyAnnotation;
import appcloud.rpc.tools.RpcException;
;

public class NodeMonitorServiceStub implements NodeMonitorService{
	
	@Override
	public Service startService(String arg0, String arg1) throws RpcException{
		return new Service();
	}
	@Override
	public void stopService(String arg0, String arg1) throws RpcException{
	}
	/* (non-Javadoc)
	 * @see appcloud.common.service.NodeMonitorService#getService(java.lang.String, java.lang.String)
	 */
	@Override
	public Service getService(String arg0, String arg1) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@RoutingKeyAnnotation(index = 0)
	public HostLoad getCurrentLoad(String routingKey, Host host)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

}
