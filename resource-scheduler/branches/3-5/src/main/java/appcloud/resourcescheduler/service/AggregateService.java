/**
 * File: AggregateService.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service;

import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public interface AggregateService {
	/**
	 * @param name 名字
	 * @param availabilityZoneId
	 *
	 * @return 创建的集群的Id
	 */
	public Integer createAggregate(String name, Integer availabilityZoneId) throws RpcException;
	
	/**
	 * @param hostId host uuid
	 * @param aggregateId
	 *
	 * @return 创建的集群的Id
	 */
	public Integer addHostToAggregate(String hostId, Integer aggregateId) throws RpcException;
	
	/**
	 * @param hostId host uuid
	 * @param aggregateId
	 *
	 * @return 创建的集群的Id
	 */
	public Integer removeHostFromAggregate(String hostId, Integer aggregateId) throws RpcException;
}
