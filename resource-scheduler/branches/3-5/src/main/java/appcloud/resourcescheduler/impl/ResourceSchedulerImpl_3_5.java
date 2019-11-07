/**
 * File: ResourceSchedulerImpl_3_5.java
 * Author: weed
 * Create Time: 2013-4-12
 */
package appcloud.resourcescheduler.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import appcloud.common.errorcode.RSEC;
import appcloud.common.model.Host;
import appcloud.common.model.J2EEApp;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.TaskStatusEnum;
import appcloud.common.model.VmInstanceMetadata;
import appcloud.common.model.VmInstanceType;
import appcloud.common.model.VmSecurityGroup;
import appcloud.common.model.VmSecurityGroupIngressRule;
import appcloud.common.model.VmVirtualInterface;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.proxy.ClusterProxy;
import appcloud.common.proxy.VmInstanceMetadataProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmInstanceTypeProxy;
import appcloud.common.service.NetworkProviderService;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.service.VMSchedulerService;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.util.UuidUtil;
import appcloud.resourcescheduler.service.AggregateService;
import appcloud.resourcescheduler.service.FlavorService;
import appcloud.resourcescheduler.service.SecurityGroupService;
import appcloud.resourcescheduler.service.VmService;
import appcloud.resourcescheduler.service.VolumeService;
import appcloud.resourcescheduler.service.impl.AggregateServiceImpl;
import appcloud.resourcescheduler.service.impl.FlavorServiceImpl;
import appcloud.resourcescheduler.service.impl.SecurityGroupServiceImpl;
import appcloud.resourcescheduler.service.impl.VmServiceImpl;
import appcloud.resourcescheduler.service.impl.VolumeServiceImpl;
import appcloud.rpc.tools.RpcException;

/**
 * @author weed
 * 
 */
public class ResourceSchedulerImpl_3_5 implements ResourceSchedulerService {

	VmService vmService = new VmServiceImpl();
	VolumeService volumeService = new VolumeServiceImpl();
	AggregateService aggregateService = new AggregateServiceImpl();
	FlavorService flavorService = new FlavorServiceImpl();
	SecurityGroupService securityGroupService = new SecurityGroupServiceImpl();
	
	@Override
	public String createVM(String name, String userId, String imgId,
			Integer flavorId, Integer sgId, Integer avalibilityZoneId,
			Map<String, String> metadata) throws RpcException {

		return vmService.createVM(name, userId, imgId, flavorId, sgId, avalibilityZoneId, metadata);
	}

	@Override
	public void startVM(String uuid) throws RpcException {
		vmService.startVM(uuid);
	}

	@Override
	public void stopVM(String uuid) throws RpcException {
		vmService.stopVM(uuid);
	}

	@Override
	public void resumeVM(String uuid) throws RpcException {
		vmService.resumeVM(uuid);
	}

	@Override
	public void rebootVM(String uuid) throws RpcException {
		vmService.rebootVM(uuid);
	}

	@Override
	public void rebuildVM(String name, String imgId, Integer flavorId,
			Integer sgId, Map<String, String> metadata) throws RpcException {
		vmService.rebuildVM(name, imgId, flavorId, sgId, metadata);
	}

	@Override
	public void resizeVM(String uuid, Integer flavorId) throws RpcException {
		vmService.resizeVM(uuid, flavorId);
	}

	@Override
	public void suspendVM(String uuid) throws RpcException {
		vmService.suspendVM(uuid);
	}

	@Override
	public void forceDeleteVM(String uuid) throws RpcException {
		vmService.forceDeleteVM(uuid);
	}

	@Override
	public void terminateVM(String uuid) throws RpcException {
		vmService.terminateVM(uuid);
	}

	@Override
	public Integer createVolume(String name, String displayName, Integer size,
			String type, Map<String, String> metadata) throws RpcException {
		return volumeService.createVolume(name, displayName, size, type, metadata);
	}

	@Override
	public String attachVolume(String volumeId, String instanceId) throws RpcException {
		return volumeService.attachVolume(volumeId, instanceId);
	}

	@Override
	public void detachVolume(String volumeId, String instanceId) throws RpcException {
		volumeService.detachVolume(volumeId, instanceId);
	}

	@Override
	public String createSnapshot(String displayName, String displayDescription,
			String volumeId, Boolean force) throws RpcException {
		return volumeService.createSnapshot(displayName, displayDescription, volumeId, force);
	}
	
	@Override
	public Integer createAggregate(String name, Integer availabilityZoneId) throws RpcException {
		return aggregateService.createAggregate(name, availabilityZoneId);
	}

	@Override
	public Integer addHostToAggregate(String hostId, Integer aggregateId) throws RpcException {
		return aggregateService.addHostToAggregate(hostId, aggregateId);
	}
	
	@Override
	public Integer removeHostFromAggregate(String hostId, Integer aggregateId) throws RpcException {
		return aggregateService.removeHostFromAggregate(hostId, aggregateId);
	}

	@Override
	public Integer createFlavor(String name, Integer ram, Integer disk,
			Integer vcpus) throws RpcException{
		return flavorService.createFlavor(name, ram, disk, vcpus);
	}
	
	@Override
	public Integer createSecurityGroup(String userId, String name,
			String description) throws RpcException {
		return securityGroupService.createSecurityGroup(userId, name, description);
	}

	@Override
	public Integer createSecurityGroup(Integer sgId, String userId) throws RpcException {
		return securityGroupService.createSecurityGroup(sgId, userId);
	}

	@Override
	public Integer createSecurityGroupRule(Integer sgId, Integer fromPort,
			Integer toPort, String protocal, String range) throws RpcException {
		return securityGroupService.createSecurityGroupRule(sgId, fromPort, toPort, protocal, range);
	}

	@Override
	public void deleteSecurityGroupRule(Integer ruleId) throws RpcException {
		securityGroupService.deleteSecurityGroupRule(ruleId);
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public RSEC createVM(String uuid, Integer templateId, String mac, String ip)
			throws RpcException {
		return null;
	}

	@Override
	public RSEC deleteVM(String uuid) throws RpcException {
		return null;
	}

	@Override
	public RSEC dealVM(String uuid, String action, Integer templateId)
			throws RpcException {
		return null;
	}

	@Override
	public RSEC createJ2EEApp(J2EEApp app, int copyNum, int resrcStrategyId)
			throws RpcException {
		return null;
	}

	@Override
	public RSEC stopJ2EEApp(String appUuid, int waitingTime)
			throws RpcException {
		return null;
	}

	@Override
	public RSEC deleteJ2EEApp(String appUuid, int waitingTime,
			String warLocation) throws RpcException {
		return null;
	}

	@Override
	public RSEC deleteJ2EEInstance(String instanceUuid) throws RpcException {
		return null;
	}

	@Override
	public RSEC increaseInstanceCopy(String appId, int num) throws RpcException {
		return null;
	}
}
