/**
 * File: StubResourceScheduler.java
 * Author: weed
 * Create Time: 2013-4-27
 */
package appcloud.resourcescheduler.fake;

import appcloud.common.errorcode.RSEC;
import appcloud.common.model.*;
import appcloud.common.model.VmImage.VmImageOSTypeEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.rpc.ampq.annotation.RPCTimeout;
import appcloud.rpc.tools.RpcException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weed
 *
 */
public class FakeResourceScheduler implements ResourceSchedulerService {

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

	@Override
	public String createVM(String name, String userId, String imgId,
			Integer flavorId, Integer sgId, Integer avalibilityZoneId, List<Integer> clusterIds,String hostUuid,String newPassword,
			Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public String recoveryVM(String name, String userId, String imgUuid, Integer flavorId, Integer sgId, Integer avalibilityZoneId, String hostUuid, String newPassword, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#startVM(java.lang.String)
	 */
	@Override
	public void startVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#stopVM(java.lang.String)
	 */
	@Override
	public void stopVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#resumeVM(java.lang.String)
	 */
	@Override
	public void resumeVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#rebootVM(java.lang.String)
	 */
	@Override
	public void rebootVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#rebuildVM(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.util.Map)
	 */

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#suspendVM(java.lang.String)
	 */
	@Override
	public void suspendVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#forceDeleteVM(java.lang.String)
	 */
	@Override
	public void forceDeleteVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#terminateVM(java.lang.String)
	 */
	@Override
	public void terminateVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createVM(java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public RSEC createVM(String uuid, Integer templateId, String mac, String ip)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#deleteVM(java.lang.String)
	 */
	@Override
	public RSEC deleteVM(String uuid) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#dealVM(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public RSEC dealVM(String uuid, String action, Integer templateId)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createVolume(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.util.Map)
	 */
	@Override
	public String createVolume(Integer userId, Integer avalibilityZoneId, List<Integer> clusterIds,
			String name, String displayName, String discription, Integer size,
			String type, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createVolumeImageBack(Integer userId, String displayName, String discription, String volumeUuid, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#attachVolume(java.lang.String, java.lang.String)
	 */
	@Override
	public String attachVolume(String volumeId, String instanceId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#detachVolume(java.lang.String, java.lang.String)
	 */
	@Override
	public void detachVolume(String volumeId, String instanceId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createSnapshot(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public String createSnapshot(String displayName, String displayDescription,
			String volumeUuid, Boolean force, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#deleteSnapshot(java.lang.String)
	 */
	@Override
	public void deleteSnapshot(String snapshotUuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#revertSnapshot(java.lang.String)
	 */
	@Override
	public void revertSnapshot(String snapshotUuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createAggregate(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer createAggregate(String name, Integer availabilityZoneId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#addHostToAggregate(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer addHostToAggregate(String hostId, Integer aggregateId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#removeHostFromAggregate(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Integer removeHostFromAggregate(String hostId, Integer aggregateId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createFlavor(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Integer createFlavor(String name, Integer ram, Integer disk,
			Integer vcpus, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createSecurityGroup(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer createSecurityGroup(String userId, String name,
			String description, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createSecurityGroupRule(java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public Integer createSecurityGroupRule(Integer sgId, Integer fromPort,
			Integer toPort, String protocal, String range, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#deleteSecurityGroupRule(java.lang.Integer)
	 */
	@Override
	public void deleteSecurityGroupRule(Integer ruleId, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#updateVolume(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateVolume(String volumeUuid, String displayName,
			String discription, HashMap<String,String> metadata, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createIpSegment(java.lang.Integer, java.lang.Integer, appcloud.common.model.VmIpSegMent)
	 */
	@Override
	public void createIpSegment(Integer type, Integer clusterId,
			VmIpSegMent ipSegment, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#updateImage(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
//	@Override
//	public void updateImage(String imageUuid, String name, String description,
//			Boolean isPublic, RpcExtra rpcExtra) throws RpcException {
//		// TODO Auto-generated method stub
//		
//	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createBackUp(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	@Override
	public String createBackUp(String displayName, String displayDescription,
			String volumeUuid, Boolean force, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#deleteBackUp(java.lang.String)
	 */
	@Override
	public void deleteBackUp(String BackUpUuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#revertBackUp(java.lang.String)
	 */
	@Override
	public void revertBackUp(String BackUpUuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#updateBackUp(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateBackUp(String BackUpUuid, String displayName,
			String discription, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#deleteSecurityGroup(java.lang.Integer)
	 */
	@Override
	public Boolean deleteSecurityGroup(Integer sgId, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#bootFromISO(java.lang.String, java.lang.String)
	 */
	@Override
	public void bootFromISO(String imageUuid, String instanceUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#detachISO(java.lang.String)
	 */
	@Override
	public void detachISO(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#forceStopVM(java.lang.String)
	 */
	@Override
	public void forceStopVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#deleteVolume(java.lang.String)
	 */
	@Override
	public void deleteVolume(String volumeUuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#forceDeleteVolume(java.lang.String)
	 */
	@Override
	public void forceDeleteVolume(String volumeUuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#createImage(java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
//	@Override
//	public String createImage(Integer userId, String serverUuid,
//			String volumeUuid, String displayName, String displayDescription,
//			Boolean isPublic, String distribution, RpcExtra rpcExtra) throws RpcException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#deleteImage(java.lang.String)
	 */
/*	@Override
	public boolean deleteImage(String imageUuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return false;
	}*/

	@Override
	public boolean delNetIpSegment(Integer ipSegId, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return false;
	}


	/* (non-Javadoc)
	 * @see appcloud.common.service.ResourceSchedulerService#getVMState(java.lang.String)
	 */
	@Override
	public VmStatusEnum getVMState(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer createVmZone(String name, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateVmZone(Integer zoneId, String name, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVmZone(Integer zoneId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return ;
	}

	@Override
	@RPCTimeout(timeout = 60)
	public String revertVolume(String volumeUuid, String instanceId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateSnapshot(String snapshotUuid, String displayName,
			String discription, Integer userId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSecurityGroup(Integer sgId, String name,
			String description, Integer userId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean modVmPasswd(String instanceUuid, String name,
			String newPasswd, VmImageOSTypeEnum OSType, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void migrateVM(String uuid, String hostUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean cancelVmMaxBandwidth(String instanceUuid,
			NetSegment netType, FlowType flowType, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public String authorizeImage(VmImage vmImage, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public String createImage(Integer userId, String serverUuid, String volumeUuid, String account, String displayName, String displayDescription, String groupIdList, String distribution, String software, RpcExtra rpcExtra) throws RpcException {
		return null;
	}


	@Override
	public void rebuildVM(String uuid, Integer userId, Integer flavorId,
			Integer sgId, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public int setVmMaxBandwidth(String instanceUuid,
			Map<String, String> metadatas, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return 0;
	}*/

	@Override
	public void onlineMigrateVM(String uuid, String hostUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int setVmMaxBandwidth(String instanceUuid,
			Map<String, String> metadatas, boolean release, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteImage(String imageUuid, String groupId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateImage(String imageUuid, String name, String description, String password, String software, String groupIdList, RpcExtra rpcExtra) throws RpcException {

	}

	@Override
	public String KeepAlive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean modVmHostName(String instanceUuid, String newHostName,
			VmImageOSTypeEnum OSType, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
}
