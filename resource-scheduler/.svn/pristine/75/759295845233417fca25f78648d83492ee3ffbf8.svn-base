// Update time: 2013-05-08 13:24:49
package appcloud.resourcescheduler.stub.services;

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

;

public class ResourceSchedulerServiceStub implements ResourceSchedulerService{
	
	@Override
	public RSEC createVM(String arg0, Integer arg1, String arg2, String arg3) throws RpcException{
		return RSEC.NO_ERROR;
	}
	@Override
	public RSEC deleteVM(String arg0) throws RpcException{
		return RSEC.NO_ERROR;
	}
	@Override
	public RSEC dealVM(String arg0, String arg1, Integer arg2) throws RpcException{
		return RSEC.NO_ERROR;
	}
	@Override
	public RSEC createJ2EEApp(J2EEApp arg0, int arg1, int arg2) throws RpcException{
		return RSEC.NO_ERROR;
	}
	@Override
	public RSEC stopJ2EEApp(String arg0, int arg1) throws RpcException{
		return RSEC.NO_ERROR;
	}
	@Override
	public RSEC deleteJ2EEApp(String arg0, int arg1, String arg2) throws RpcException{
		return RSEC.NO_ERROR;
	}
	@Override
	public RSEC deleteJ2EEInstance(String arg0) throws RpcException{
		return RSEC.NO_ERROR;
	}
	@Override
	public RSEC increaseInstanceCopy(String arg0, int arg1) throws RpcException{
		return RSEC.NO_ERROR;
	}
	@Override
	public String createVM(String name, String userId, String imgUuid, Integer flavorId,
						   Integer sgId, Integer avalibilityZoneId, List<Integer> clusterIds,
						   String hostUuid, String newPassword, Map<String, String> metadata, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String recoveryVM(String name, String userId, String imgUuid, Integer flavorId, Integer sgId, Integer avalibilityZoneId, String hostUuid, String newPassword, Map<String, String> metadata, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public void startVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stopVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void forceStopVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resumeVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void rebootVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void suspendVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void forceDeleteVM(String uuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void terminateVM(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public VmStatusEnum getVMState(String uuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String createVolume(Integer userId, Integer avalibilityZoneId, List<Integer> culsterIds,
			String name, String displayName, String discription, Integer size,
			String type, Map<String, String> metadata, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createVolumeImageBack(Integer userId, String displayName, String discription, String volumeUuid, RpcExtra rpcExtra) throws RpcException {
		return null;
	}

	@Override
	public String attachVolume(String volumeUuid, String instanceId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void detachVolume(String volumeUuid, String instanceId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteVolume(String volumeUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void forceDeleteVolume(String volumeUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String createSnapshot(String displayName, String displayDescription,
			String volumeUuid, Boolean force, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteSnapshot(String snapshotUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void revertSnapshot(String snapshotUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String createBackUp(String displayName, String displayDescription,
			String volumeUuid, Boolean force, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteBackUp(String backUpUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void revertBackUp(String backUpUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateBackUp(String backUpUuid, String displayName,
			String discription, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Integer createAggregate(String name, Integer availabilityZoneId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer addHostToAggregate(String hostId, Integer aggregateId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer removeHostFromAggregate(String hostId, Integer aggregateId,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer createFlavor(String name, Integer ram, Integer disk,
			Integer vcpus, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer createSecurityGroup(String userId, String name,
			String description, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean deleteSecurityGroup(Integer sgId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer createSecurityGroupRule(Integer sgId, Integer fromPort,
			Integer toPort, String protocal, String range, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteSecurityGroupRule(Integer ruleId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void createIpSegment(Integer type, Integer clusterId,
			VmIpSegMent ipSegment, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean delNetIpSegment(Integer ipSegId, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return false;
	}
	/*@Override
	public String createImage(Integer userId, String serverUuid,
			String volumeUuid, String displayName, String displayDescription,
			Boolean isPublic, String distribution, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return null;
	}*/
	/*@Override
	public boolean deleteImage(String imageUuid, RpcExtra rpcExtra)
			throws RpcException {
		// TODO Auto-generated method stub
		return false;
	}*/
	/*@Override
	public void updateImage(String imageUuid, String name, String description,
			Boolean isPublic, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}*/
	@Override
	public void bootFromISO(String imageUuid, String instanceUuid,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void detachISO(String uuid, RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
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
	public void updateVolume(String volumeUuid, String displayName,
			String discription, HashMap<String, String> metadata,
			RpcExtra rpcExtra) throws RpcException {
		// TODO Auto-generated method stub
		
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
