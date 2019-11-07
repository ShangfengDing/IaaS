/**
 * File: FlavorService.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service;

import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public interface FlavorService {
	/**
	 * @param name 名字
	 * @param availabilityZoneId
	 *
	 * @return 创建的集群的Id
	 */
	public Integer createFlavor(String name, Integer ram, Integer disk, Integer vcpus) throws RpcException ;

}
