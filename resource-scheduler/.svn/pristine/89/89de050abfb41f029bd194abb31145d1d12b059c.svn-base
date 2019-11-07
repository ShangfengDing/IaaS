/**
 * File: AggregateServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import appcloud.common.model.Cluster;
import appcloud.common.model.MessageLog;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.AlertUtil;
import appcloud.common.util.LolLogUtil;
import appcloud.resourcescheduler.ResourceScheduler;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.resourcescheduler.service.AggregateService;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 *
 */
public class AggregateServiceImpl implements AggregateService {
	private static Logger logger = Logger
			.getLogger(AggregateServiceImpl.class);
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = ResourceScheduler.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.RESOURCE_SCHEDULER, AggregateServiceImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	private static ClusterProxy clusterProxy = (ClusterProxy) ConnectionManager.getInstance().getDBProxy(ClusterProxy.class);
	private static VmZoneProxy zoneProxy  = (VmZoneProxy) ConnectionManager.getInstance()
			.getDBProxy(VmZoneProxy.class);
	
	@Override
	public Integer createAggregate(String name, Integer availabilityZoneId, RpcExtra rpcExtra)
			throws RpcException {
		String paramInfos = "createAggregate : " + name + ", " + availabilityZoneId;
		logger.debug(paramInfos);
		if (name == null || availabilityZoneId == null){
			return null;
		}
		
		Cluster cluster = new Cluster();
		cluster.setName(name);
		cluster.setAvailabilityZoneId(availabilityZoneId);
		try {
			clusterProxy.save(cluster);
			loller.info(LolLogUtil.CREATE_AGGREGATE, paramInfos + " successfully!", rpcExtra);
			return clusterProxy.getByName(name).getId();
		} catch (Exception e) {
			String error = "createAggregate failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.CREATE_AGGREGATE, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("集群创建失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
		return null;
	}

	@Override
	public Integer addHostToAggregate(String hostId, Integer aggregateId, RpcExtra rpcExtra)
			throws RpcException {
		String paramInfos = "addHostToAggregate : " + hostId + ", " + aggregateId;
		logger.debug(paramInfos);
		loller.info(LolLogUtil.ADD_HOST_TO_AGGREGATE, paramInfos + " successfully!", rpcExtra);
//		hostProxy.save(new Host());
//		clusterProxy
		return null;
	}

	@Override
	public Integer removeHostFromAggregate(String hostId, Integer aggregateId, RpcExtra rpcExtra)
			throws RpcException {
		String paramInfos = "removeHostFromAggregate : " + hostId + ", " + aggregateId;
		logger.debug(paramInfos);
		loller.info(LolLogUtil.REMOVE_HOST_FROM_AGGREGATE, paramInfos + " successfully!", rpcExtra);
		
//		hostProxy.deleteByUuid(uuid)
//		clusterProxy
		return null;
	}

	@Override
	public Integer createAvailabilityZone(String name, RpcExtra rpcExtra)
			throws RpcException {
		String paramInfos = "createAvailabilityZone : " + name + ", ";
		logger.debug(paramInfos);
		if (name == null){
			return null;
		}
		
		VmZone zone = new VmZone();
		zone.setName(name);
		zone.setCreatedTime( new Timestamp(System.currentTimeMillis()));
		try {
			zoneProxy.save(zone);
			loller.info(LolLogUtil.CREATE_AVAILABILITY_ZONE, paramInfos + " successfully!", rpcExtra);
			return zoneProxy.getByName(name).getId();
		} catch (Exception e) {
			String error = "createAvailabilityZone failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.CREATE_AVAILABILITY_ZONE, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("数据中心创建失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
		return null;
	}

	@Override
	public Integer updateAvailabilityZone(Integer zoneId, String name,
			RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "updateAvailabilityZone : " + zoneId + ", "+ name + ", ";
		logger.debug(paramInfos);
		if (zoneId == null || name == null){
			return null;
		}
		
		try {
			VmZone zone = zoneProxy.getById(zoneId);
			if(zone == null)
				return null;
			zone.setName(name);
			zoneProxy.update(zone);
			loller.info(LolLogUtil.UPDATE_AVAILABILITY_ZONE, paramInfos + " successfully!", rpcExtra);
			return zone.getId();
		} catch (Exception e) {
			String error = "updateAvailabilityZone failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.UPDATE_AVAILABILITY_ZONE, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("更新数据中心创建失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
		return null;
	}

	@Override
	public void deleteAvailabilityZone(Integer zoneId, RpcExtra rpcExtra)
			throws RpcException {
		String paramInfos = "deleteAvailabilityZone : " + zoneId;
		logger.debug(paramInfos);
		if (zoneId == null){
			return ;
		}
		try {
			zoneProxy.deleteById(zoneId);
			loller.info(LolLogUtil.DELETE_AVAILABILITY_ZONE, paramInfos + " successfully!", rpcExtra);
		} catch (Exception e) {
			String error = "deleteAvailabilityZone failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.DELETE_AVAILABILITY_ZONE, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("删除数据中心创建失败", "输入参数为" + paramInfos, e);
		}
	}

}
