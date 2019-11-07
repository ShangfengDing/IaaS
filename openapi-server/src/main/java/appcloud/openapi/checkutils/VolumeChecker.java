package appcloud.openapi.checkutils;

import org.apache.log4j.Logger;

import appcloud.api.enums.VolumeOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.ConnectionFactory;

public class VolumeChecker {

	private static Logger logger = Logger.getLogger(VolumeChecker.class);
	private static VmVolumeProxy volumeProxy = (VmVolumeProxy) 
			ConnectionFactory.getTipProxy(VmVolumeProxy.class, 
			appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	
	public static VmVolume checkOwner(String tenantId, String volumeId) throws Exception{
		if(tenantId == null) {
			logger.info("in checkOwner : tenantId is NULL");
			throw new OperationFailedException("tenantId is NULL");
		}
		
		if(volumeId == null) {
			logger.info("in checkOwner : volumeId is NULL");
			throw new OperationFailedException("volumeId is NULL");
		}
		
		boolean isAdmin = false;
		try {
			Integer.parseInt(tenantId);
		} catch(NumberFormatException e) {
			isAdmin = true;
		}
		
		VmVolume vmVolume = volumeProxy.getByUUID(volumeId, false, false, false, false);
		if (vmVolume == null) {
			logger.info("disk does not exist");
			throw new ItemNotFoundException("disk does not exist");
		}
		if(!isAdmin) {
			if (vmVolume.getUserId() == null) {
				logger.info("in vm_volume : user_id is NULL");
				throw new OperationFailedException("user id is NULL");
			}
			if (!vmVolume.getUserId().toString().equals(tenantId)) {
				logger.info("volume does not belong to the tenant");
				throw new OperationFailedException("The disk does not belong to this user");
			}
		}
		return vmVolume;
	}
	
	public static VmVolume checkAttach(String tenantId, String serverId, String volumeId) throws Exception{
		VmVolume vmVolume = checkOwner(tenantId, volumeId);
		if (vmVolume.getAttachStatus() == null || 
				vmVolume.getAttachStatus().equals(VmVolumeAttachStatusEnum.DETACHED)) {
			throw new OperationFailedException("disk status is in the type of detached ");
		}
		if (vmVolume.getInstanceUuid() == null) {
			logger.info("in vm_volume : instance_id is NULL");
			throw new OperationFailedException("instance id is NULL");
		}
		if (serverId == null || vmVolume.getInstanceUuid().equals(serverId)){
			return vmVolume;
		} else {
			throw new OperationFailedException("disk is not attached in this vm");
		}
	}
	
	
	public static VmVolume checkBackup(String tenantId, String volumeId) throws Exception{
		VmVolume vmVolume = checkOwner(tenantId, volumeId);
		if (vmVolume.getBackupVolumeUuid() == null){
			logger.info("volume is not backup");
			throw new OperationFailedException("it is not backup");
		}
		return vmVolume;
	}
	
	public static VmVolume checkBackupAvailable(String tenantId, String volumeId) throws Exception {
		VmVolume vmVolume = checkBackup(tenantId, volumeId);
		if(vmVolume.getStatus() == null || !vmVolume.getStatus().equals(VmVolumeStatusEnum.AVAILABLE)) {
			logger.info("backup is not available");
			throw new OperationFailedException("backup is not available");
		}
		return vmVolume;
	}
	
	public static VmInstance checkInstanceStatus (String tenantId, String serverId, 
			VolumeOperationEnum operation) throws Exception {
		VmInstance vmInstance = InstanceChecker.checkReady(tenantId, serverId);
		VmStatusEnum vmStatus = vmInstance.getVmStatus();
		boolean allowed  = false;
		if(vmStatus != null){
			/*
			switch (operation) {
			case DELETE:
				;
				break;
			case CREATE_BACKUP:
				; 
				break;
			case REVERT_BACKUP:
				;
				break;
			case CREATE_SNAPSHOT:
				;
				break;
			case REVER_SNAPSHOT :
				;
				break;
			case ATTACH:
				;
				break;
			case DETTACH:
				;
				break;
				
			}*/
			if(vmStatus.equals(VmStatusEnum.ACTIVE) || vmStatus.equals(VmStatusEnum.SUSPENDED)
					|| vmStatus.equals(VmStatusEnum.STOPPED))
				 allowed = true;
		}
		if(!allowed) {
			logger.info("operation is not allowed now");
			throw new OperationFailedException("check server status");
		}
		return vmInstance;
	}
	
}
