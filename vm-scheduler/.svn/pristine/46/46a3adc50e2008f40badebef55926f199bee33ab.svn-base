package appcloud.vmscheduler.vmservice;

import appcloud.rpc.tools.RpcException;
import appcloud.common.exception.IllegalRpcArgumentException;
import appcloud.common.model.RpcExtra;


public interface SchedulerService {
	public void VMOperationProcess(RpcExtra rpcExtra) throws IllegalRpcArgumentException, RpcException;

	public void processSuccess(RpcExtra rpcExtra);	
	public void processError(RpcExtra rpcExtra);
}
