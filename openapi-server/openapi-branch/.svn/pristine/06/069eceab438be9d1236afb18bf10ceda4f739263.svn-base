package appcloud.openapi.checkutils;

import org.apache.log4j.Logger;

import appcloud.api.enums.VolumeOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmSnapshot;
import appcloud.common.model.VmSnapshot.VmSnapshotStatusEnum;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmSnapshotProxy;
import appcloud.common.util.ConnectionFactory;

public class SnapshotChecker {
	private static Logger logger = Logger.getLogger(SnapshotChecker.class);
	private static VmSnapshotProxy snapshotProxy = (VmSnapshotProxy) ConnectionFactory.getTipProxy(VmSnapshotProxy.class,
			appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	public static VmSnapshot checkOwner (String tenantId, String snapshotId) throws Exception{
		if(tenantId == null) {
			logger.info("in checkOwner : tenantId is NULL");
			throw new OperationFailedException("tenantId is NULL");
		}
		
		if(snapshotId == null) {
			logger.info("in checkOwner : snapshotId is NULL");
			throw new OperationFailedException("snapshotId is NULL");
		}
		
		boolean isAdmin = false;
		try {
			Integer.parseInt(tenantId);
		} catch(NumberFormatException e) {
			isAdmin = true;
		}
		
		VmSnapshot vmSnapshot = snapshotProxy.getByUuid(snapshotId, false);
		if (vmSnapshot == null) {
			logger.info("snapshot does not exist");
			throw new ItemNotFoundException("snapshot does not exist");
		}
		if(!isAdmin) {
			if (vmSnapshot.getUserId() == null) {
				logger.info("in vm_snapshot : user_id is NULL");
				throw new OperationFailedException("tenant id is NULL");
			}
			if (!vmSnapshot.getUserId().equals(Integer.valueOf(tenantId))) {
				logger.info("snapshot does not belong to the tenant");
				throw new OperationFailedException("check tenant id");
			}
		}
		return vmSnapshot;
	}
	
	public static VmSnapshot checkSnapshotAvailable(String tenantId, String volumeId) throws Exception {
		VmSnapshot vmSnapshot = checkOwner(tenantId, volumeId);
		if(vmSnapshot.getStatus() == null || 
				!vmSnapshot.getStatus().equals(VmSnapshotStatusEnum.AVAILABLE)) {
			logger.info("snapshot is not available");
			throw new OperationFailedException("snapshot is not available");
		}
		return vmSnapshot;
	}
	
	public static VmVolume checkCreateSnapshot (String tenantId, String volumeId) throws Exception {
		VmVolume vmVolume = VolumeChecker.checkOwner(tenantId, volumeId);
		VolumeChecker.checkInstanceStatus(tenantId, vmVolume.getInstanceUuid(),
				VolumeOperationEnum.CREATE_SNAPSHOT);
		return vmVolume;
	}
	
	public static VmVolume checkRevertSnapshot (String tenantId, String snapshotId)
		throws Exception {
		VmSnapshot vmSnapshot = checkSnapshotAvailable(tenantId, snapshotId);
		VmVolume vmVolume = VolumeChecker.checkOwner(tenantId, vmSnapshot.getVolumeUuid());
		if (vmVolume.getAttachStatus().equals(VmVolume.VmVolumeAttachStatusEnum.DETACHED)) {
			logger.info("volume is detached");
			return vmVolume;
		}
		VolumeChecker.checkInstanceStatus(tenantId,  vmVolume.getInstanceUuid(),
				VolumeOperationEnum.REVERT_SNAPSHOT);
		logger.info("volume is attached");
		return vmVolume;
	}
}
