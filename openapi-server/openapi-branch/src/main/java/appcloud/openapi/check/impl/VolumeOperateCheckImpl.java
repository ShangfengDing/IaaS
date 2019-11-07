package appcloud.openapi.check.impl;

import appcloud.api.enums.VolumeOperationEnum;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.check.VolumeOperateCheck;
import appcloud.openapi.checkutils.AcGroupChecker;
import appcloud.openapi.checkutils.SnapshotChecker;
import appcloud.openapi.checkutils.VolumeChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.constant.HttpConstants;
import appcloud.openapi.manager.util.CommonGenerator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class VolumeOperateCheckImpl implements VolumeOperateCheck{
	private static Logger logger = Logger.getLogger(VolumeOperateCheckImpl.class);
	
	private static VolumeOperateCheckImpl volumeOperateCheck = new VolumeOperateCheckImpl();
	public static VolumeOperateCheckImpl getInstance(){
		return volumeOperateCheck;
	}
	
	private static CommonGenerator commonGenerator  = new CommonGenerator();
	private static VmUserProxy vmUserProxy;
	private static VmVolumeProxy vmVolumeProxy;
	
	private VolumeOperateCheckImpl() {
		super();
		vmUserProxy = (VmUserProxy)ConnectionFactory.getTipProxy(
				VmUserProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmVolumeProxy = (VmVolumeProxy)ConnectionFactory.getTipProxy(
				VmVolumeProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}


	@Override
	public Map<String, String> checkCreate(Map<String, String> paramsMap)
			throws Exception {
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(user.getUserId());
		if(!AcGroupChecker.checkDiskCount(userId)) {
			logger.warn("user " + user.getUserId() + " request to create volume while his volume number reaches the upper limit");
			String message = "User living disks quota exceeded.";
			return commonGenerator.operationDeny(message, null);
		}
		
		//REGION_ID不再当做集群ID用
		/*List<Integer> regionsT = new ArrayList<Integer>();
		if(! StringUtil.isEmpty(paramsMap.get(Constants.REGION_ID))) {
			regionsT.add(Integer.valueOf(paramsMap.get(Constants.REGION_ID)));
			if(!AcGroupChecker.checkSelectedCluster(userId, regionsT)) {
				String message = "user " + userId + " request to create disk in a cluster not allowed";
				logger.warn(message);
				//loller.warn(LolLogUtil.CREATE_VOLUME, message, rpcExtra);
				return commonGenerator.operationDeny("selected region is not allowed for user", null);
			}
		}*/
			
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkCreateDiskImageBack(Map<String, String> paramsMap) throws Exception {
		VmUser user = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null==user) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String volumeUuid = paramsMap.get(Constants.DISK_ID);
		VmVolume volume = vmVolumeProxy.getByUUID(volumeUuid,false,false,false,false);
		if(volume == null) {
			logger.warn("user " + user.getUserId() + " request to create volume imageback while his volume does not exist");
			String message = "User living disks quota exceeded.";
			return commonGenerator.operationDeny(message, null);
		}

		if(volume.getUsageType().compareTo(VmVolume.VmVolumeUsageTypeEnum.SYSTEM) != 0) {
			logger.warn("user " + user.getUserId() + " request to create volume imageback while his volume is not system type");
			String message = "User living disks quota exceeded.";
			return commonGenerator.operationDeny(message, null);
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkAttach(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			VolumeChecker.checkOwner(userId, paramsMap.get(Constants.DISK_ID));	
			VolumeChecker.checkInstanceStatus(userId, paramsMap.get(Constants.INSTANCE_ID), VolumeOperationEnum.ATTACH);
		}catch (Exception e) {
			logger.warn("attach disk fialed! operation is not allowed:"+e.getMessage());
			return commonGenerator.operationDeny("attach disk fialed! operation is not allowed:"+e.getMessage(), null);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkDetach(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			String instanceId = paramsMap.get(Constants.INSTANCE_ID);
			String diskId = paramsMap.get(Constants.DISK_ID);
			VolumeChecker.checkAttach(userId, instanceId , diskId);
			VolumeChecker.checkInstanceStatus(userId, instanceId, VolumeOperationEnum.DETTACH);
		} catch (Exception e) {
			logger.warn("attach disk fialed! operation is not allowed:"+e.getMessage());
			return commonGenerator.operationDeny("detach disk fialed! operation is not allowed:"+e.getMessage(), null);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkDelete(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			String diskId = paramsMap.get(Constants.DISK_ID);
			if (VolumeChecker.checkOwner(userId, diskId).getAttachStatus() == VmVolumeAttachStatusEnum.ATTACHED)  {
				logger.info(String.format("disk is attached"));
				throw new OperationFailedException("disk is attached");
			}
		}catch (Exception e) {
			logger.warn("delete disk:"+e.getMessage());
			throw e;
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkDescribeParams(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		if (null != paramsMap.get(Constants.DISK_IDS)) {
			try {
				JSONArray array = new JSONArray(paramsMap.get(Constants.DISK_IDS));
				for (int i = 0; i < array.length(); i++) {
					String diskId = (String) array.get(i);
					VolumeChecker.checkOwner(userId, diskId);	
				}
				
			}catch (Exception e) {
				logger.warn("describe disk fialed! operation is not allowed:"+e.getMessage(),e);
				return commonGenerator.operationDeny("attach disk fialed! operation is not allowed:"+e.getMessage(), null);
			}
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkDescribeImageBackParams(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkResetParams(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			String snapshotId = paramsMap.get(Constants.SNAPSHOT_ID);
			String diskId = paramsMap.get(Constants.DISK_ID);
			
			VmVolume vmVolume = SnapshotChecker.checkRevertSnapshot(userId, snapshotId);
			
			if (! vmVolume.getVolumeUuid().equals(diskId)) {
				return commonGenerator.operationDeny("reset disk fialed! operation is not allowed:because this snapshotId not belong to the disk", null);
			}
		} catch (Exception e) {
			logger.warn("reset disk fialed! operation is not allowed:"+e.getMessage());
			return commonGenerator.operationDeny("reset disk fialed! operation is not allowed:"+e.getMessage(), null);
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}


	@Override
	public Map<String, String> checkModifyAttributesParams(Map<String, String> paramsMap) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			String diskId = paramsMap.get(Constants.DISK_ID);
			VolumeChecker.checkOwner(userId, diskId);
		}catch (Exception e) {
			logger.warn("modify disk attributes:"+e.getMessage());
			throw e;
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkModifyImageBackParams(Map<String, String> paramsMap) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());

//		try {
//			String diskId = paramsMap.get(Constants.VOLUME_ID);
//			VolumeChecker.checkOwner(userId, diskId);
//		}catch (Exception e) {
//			logger.warn("modify disk attributes:"+e.getMessage());
//			throw e;
//		}

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

	@Override
	public Map<String, String> checkRenewDisk(Map<String, String> paramsMap)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(paramsMap.get(Constants.APPKEY_ID));
		if(null == vmUser) {
			logger.warn("Get user failed by appkey_id, appkey_id=" + Constants.APPKEY_ID);
			return commonGenerator.internalError(null, null);
		}
		String userId = String.valueOf(vmUser.getUserId());
		
		try {
			String diskId = paramsMap.get(Constants.DISK_ID);
			VolumeChecker.checkOwner(userId, diskId);
		}catch (Exception e) {
			logger.warn("modify disk attributes:"+e.getMessage());
			throw e;
		}
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put(Constants.HTTP_CODE, HttpConstants.STATUS_OK);
		return resultMap;
	}

}
