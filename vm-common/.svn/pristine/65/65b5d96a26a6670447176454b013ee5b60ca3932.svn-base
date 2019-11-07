package appcloud.common.service;

import appcloud.common.errorcode.NAEC;
import appcloud.common.model.Instance;
import appcloud.rpc.ampq.annotation.RoutingKeyAnnotation;
import appcloud.rpc.tools.RpcException;

/**
 * @author jianglei
 *
 */
public interface NodeAgentService {
	/**
	 * @param routingKey
	 * 				routing key to certain nodeagent, should be ignored on server sided.
	 * @param appId
	 * 				appId of instance to be deployed
	 * @param warLocation
	 * 				war package location of app
	 * @param xms
	 * 				minimum memory requirements
	 * @param xmx
	 * 				maximum memory requirements
	 * @return	the instance object that was deployed, with a auto-generated UUID 
	 */
	@RoutingKeyAnnotation(index=0)
	public Instance StartJ2EEInstance(String routingKey, String appId, String warLocation,
			int xms, int xmx) throws RpcException;
	
	/**
	 * @param routingKey
	 * 				routing key to certain nodeagent, should be ignored on server sided.
	 * @param appId
	 * 				appId of instance to be deleted
	 * @param uuid
	 * 				UUID of instance to be deleted
	 * @param waitingTime
	 * 				time to wait before actually stop the instance(not implemented)
	 * @return	true if successfully stop the instance
	 */
	@RoutingKeyAnnotation(index=0)
	public NAEC StopJ2EEInstance(String routingKey, String appId, String uuid, int waitingTime) throws RpcException;
	
	@RoutingKeyAnnotation(index=0)
	public Instance createVM(String routingKey, String uuid, Integer templateId, String mac, String ip) throws RpcException;
	
	@RoutingKeyAnnotation(index=0)
	public NAEC deleteVM(String routingKey, String uuid) throws RpcException;
	
	@RoutingKeyAnnotation(index=0)
	public NAEC dealVM(String routingKey, String uuid, String action, Integer templateId) throws RpcException;
}
