/**
 * File: PublicIpAddrResource.java
 * Author: weed
 * Create Time: 2013-4-21
 */
package appcloud.resourcescheduler.transaction.rollback.resource;

import org.apache.log4j.Logger;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.service.NetworkProviderService;
import appcloud.common.transaction.rollback.IRollbackable;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class PublicIpAddrResource implements IRollbackable {
	private static Logger logger = Logger.getLogger(PublicIpAddrResource.class);
	private static NetworkProviderService networkProvider = (NetworkProviderService) ConnectionManager.getInstance()
			.getAMQPService(NetworkProviderService.class,
					RoutingKeys.NETWORK_SCHEDULER);
	
	private Integer cluserId;
	private String ip;
	
	public PublicIpAddrResource(Integer cluserId, String ip) {
		super();
		this.cluserId = cluserId;
		this.ip = ip;
	}

	/* (non-Javadoc)
	 * @see appcloud.resourcescheduler.service.rollback.IRollbackable#rollback()
	 */
	@Override
	public void rollback(RpcExtra rpcExtra) {
		logger.info("PublicIpAddrResource.rollback");
		try {
			networkProvider.releasePublicIpAddress(cluserId, ip, rpcExtra);
		} catch (RpcException e) {
			e.printStackTrace();
		}
	}

}
