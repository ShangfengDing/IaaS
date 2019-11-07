package appcloud.common.service.monitor;

import appcloud.rpc.tools.RpcException;

public interface YunhaiService {
	/**
	 * initialize the service
	 * automatically done in run
	 */
	void initialize() throws RpcException;
	/**
	 * run the service
	 */
	void run() throws RpcException;
	/**
	 * whether the service is running
	 * @return
	 */
	boolean isRunning() throws RpcException;
	
	/**
	 * the uptime of the service
	 * @return
	 */
	long uptime() throws RpcException;
	/**
	 * mark the service as stopped
	 * the service will stop later on
	 */
	void markAsStopped() throws RpcException;
	/**
	 * do the real stop stuff
	 */
	void stop() throws RpcException;
	
}
