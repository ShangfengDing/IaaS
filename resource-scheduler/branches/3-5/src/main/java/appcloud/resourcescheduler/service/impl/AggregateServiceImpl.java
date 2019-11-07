/**
 * File: AggregateServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import org.apache.log4j.Logger;

import appcloud.common.model.Cluster;
import appcloud.common.proxy.ClusterProxy;
import appcloud.dbproxy.mysql.MySQLClusterProxy;
import appcloud.resourcescheduler.service.AggregateService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class AggregateServiceImpl implements AggregateService {
	private static Logger logger = Logger
			.getLogger(AggregateServiceImpl.class);
	
	ClusterProxy clusterProxy = new MySQLClusterProxy();
	
	@Override
	public Integer createAggregate(String name, Integer availabilityZoneId)
			throws RpcException {
		Cluster cluster = new Cluster();
		cluster.setName(name);
		cluster.setAvailabilityZoneId(availabilityZoneId);
		try {
			clusterProxy.save(cluster);
			return clusterProxy.getByName(name).getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer addHostToAggregate(String hostId, Integer aggregateId)
			throws RpcException {
		return null;
	}

	@Override
	public Integer removeHostFromAggregate(String hostId, Integer aggregateId)
			throws RpcException {
		return null;
	}

}
