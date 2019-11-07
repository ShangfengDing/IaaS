/**
 * File: TestNetworkScheduler.java
 * Author: weed
 * Create Time: 2013-4-22
 */
package appcloud.resourcescheduler.impl;

import java.util.Map.Entry;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.NetworkAddress;
import appcloud.common.service.NetworkProviderService;
import appcloud.common.util.ConnectionFactory;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class TestNetworkScheduler {

	/**
	 * @param args
	 * @throws RpcException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws RpcException, InterruptedException {
		
		NetworkProviderService networkProvider = (NetworkProviderService) ConnectionFactory
				.getAMQPService(NetworkProviderService.class,
						RoutingKeys.NETWORK_SCHEDULER);
		
		Integer clusterId = 1;
		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.231", null);
		networkProvider.releasePrivateIpAddress(clusterId, "192.168.17.8", null);
		
//		NetworkAddress mac_ip = networkProvider.getNewPublicIpAddress(clusterId);
//		System.out.println("PublicIpAddress: " + mac_ip);
//		networkProvider.releasePublicIpAddress(clusterId, mac_ip.getIp());
		
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.150");
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.2");
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.151");
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.3");
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.152");
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.4");
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.153");
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.5");
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.154");
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.6");
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.155");
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.7");
		
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.175");
//		Thread.sleep(100);
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.174");
//		Thread.sleep(100);
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.166");
//		Thread.sleep(100);
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.159");
//		Thread.sleep(100);
//		networkProvider.releasePublicIpAddress(clusterId, "10.109.247.160");
//		Thread.sleep(100);
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.101");
//		Thread.sleep(100);
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.102");
//		Thread.sleep(100);
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.103");
//		Thread.sleep(100);
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.104");
//		Thread.sleep(100);
//		networkProvider.releasePrivateIpAddress(clusterId, "192.168.15.105");
		System.out.println("END");
	}

}
