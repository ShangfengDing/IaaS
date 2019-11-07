package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.beans.Backup;
import appcloud.api.beans.Volume;
import appcloud.api.checkutils.AcGroupChecker;
import appcloud.api.checkutils.VolumeChecker;
import appcloud.api.enums.VolumeOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.VolumeManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.rpc.tools.RpcException;

public class RealVolumeManager implements VolumeManager {
	private static Logger logger = Logger.getLogger(RealVolumeManager.class);
	
	private VmVolumeProxy volumeProxy;
	private VmInstanceProxy instanceProxy;
	private VmUserProxy userProxy;
	private VmGroupProxy groupProxy;
	private BeansGenerator generator;
	private ResourceSchedulerService scheduler;
	
	private static RealVolumeManager manager = new RealVolumeManager();

	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealVolumeManager.class);
	
	public static RealVolumeManager getInstance() {
		return manager;
	}
	
	private RealVolumeManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		generator = BeansGenerator.getInstance();
		volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(VmVolumeProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		instanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(VmInstanceProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		userProxy = (VmUserProxy) ConnectionFactory.getTipProxy(VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		groupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(VmGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	@Override
	public List<Volume> getList(String tenantId, boolean detailed)
			throws Exception {
		if(detailed)
			logger.info(String.format("User %s request to get VOLUMES, detailed", tenantId));
		else
			logger.info(String.format("User %s request to get VOLUMES", tenantId));
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		query.addFilterBean(new FilterBean<VmVolume>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.AVAILABLE, FilterBeanType.EQUAL));
		List<Volume> apiVolumes = new ArrayList<Volume>();
		
		List<? extends VmVolume> vmVolumes = volumeProxy.searchAll(query, false, false, false, false);
		for(VmVolume vmVolume : vmVolumes) {
			apiVolumes.add(generator.vmVolumeToVolume(vmVolume, detailed));
		}
		return apiVolumes;
	}
	@Override
	public Volume get(String tenantId, String volumeId) throws Exception {
		logger.info(String.format("User %s request to GET VOLUME %s", tenantId, volumeId));
		VmVolume vmVolume = VolumeChecker.checkOwner(tenantId, volumeId);
		
		return generator.vmVolumeToVolume(vmVolume, true);
	}
	@Override
	public Volume create(String tenantId, Volume cReq) throws Exception {
		logger.info(String.format("User %s request to CREATE VOLUME %s", tenantId, cReq.displayName));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.CREATE_VOLUME, String.format("User %s request to CREATE VOLUME %s", tenantId, cReq.displayName),
				rpcExtra);
		
		String uuid = null ;
		try {
			List<Integer> aggregateIds = new ArrayList<Integer>();
			Integer aggretageId = Integer.valueOf(cReq.metadata.get("aggregate_id"));
			if(aggretageId != null)
				aggregateIds.add(aggretageId);
			else {
				QueryObject<VmUser> query = new QueryObject<VmUser>();
				query.addFilterBean(new FilterBean<VmUser>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
				List<VmUser> vmUsers = (List<VmUser>) userProxy.searchAll(query);
				if(vmUsers == null || vmUsers.size() == 0)
					throw new ItemNotFoundException("tenant does not exist");
				
				VmGroup vmGroup = groupProxy.getById(vmUsers.get(0).getGroupId());
				if(vmGroup == null)
					throw new ItemNotFoundException("group does not exist");
				
				aggregateIds = generator.groupToAcGroup(vmGroup).availableClusters;
			}
			
			if(!AcGroupChecker.checkDiskCount(tenantId)) {
				String message = "user " + tenantId + " request to create disk while his disk number reaches the upper limit";
				logger.warn(message);
				loller.warn(LolLogUtil.CREATE_VOLUME, message, rpcExtra);
				throw new OperationFailedException("user's disk number reaches the upper limit");
			}
			if(!AcGroupChecker.checkSelectedCluster(tenantId, aggregateIds)) {
				String message = "user " + tenantId + " request to create disk in a cluster not allowed";
				logger.warn(message);
				loller.warn(LolLogUtil.CREATE_VOLUME, message, rpcExtra);
				throw new OperationFailedException("selected aggregate is not allowed for user");
			}
				
			try {
				uuid = scheduler.createVolume(Integer.valueOf(tenantId), 
					Integer.valueOf(cReq.availabilityZone), aggregateIds, "name", cReq.displayName, cReq.displayDescription, cReq.size, cReq.volumeType, cReq.metadata, rpcExtra);
			}catch(RpcException e) {
				DealException.isRSTimeoutException(e, LolLogUtil.CREATE_VOLUME, rpcExtra);
				return new Volume();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RpcException e) {
			throw e;
		}
		
		if (uuid == null) {
			throw new OperationFailedException("create volume failed");
		}
		logger.info(String.format("VOLUME created successfully, uuid is %s", uuid));
		VmVolume vmVolume = volumeProxy.getByUUID(uuid, false, false, false, false);
		
		return generator.vmVolumeToVolume(vmVolume, true);
	}
	@Override
	public void delete(String tenantId, String volumeId) throws Exception {
		logger.info(String.format("User %s request to DELETE VOLUME %s", tenantId, volumeId));
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, volumeId);
		loller.info(LolLogUtil.DELETE_VOLUME, String.format("User %s request to DELETE VOLUME %s", tenantId, volumeId),
				rpcExtra);
		try {
			if (VolumeChecker.checkAttach(tenantId, null, volumeId) != null)  {
				logger.info(String.format("volume is attached"));
				throw new OperationFailedException("volume is attached");
			}
		}catch (Exception e) {
			loller.warn(LolLogUtil.DELETE_VOLUME, "delete volume:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.deleteVolume(volumeId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DELETE_VOLUME, rpcExtra);
			return;
		}
		logger.info(String.format("VOLUME %s deleted successfully", volumeId));
	}
	
	/* Following intefaces are used in backups*/

	@Override
	public Backup getBackup(String tenantId, String backupId) throws Exception {
		logger.info(String.format("User %s request to GET BACKUP %s", tenantId, backupId));
		VmVolume vol = VolumeChecker.checkBackup(tenantId, backupId);
		
		Backup backup = generator.volumeToBackup(vol);
		fillupBackupWithInstanceName(backup);
		return backup;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Backup> getBackupList(String tenantId, boolean detailed, String serverId)
			throws Exception {
		if(!serverId.equals(""))
			logger.info(String.format("User %s request to GET BACKUPS of %s", tenantId, serverId));
		else
		logger.info(String.format("User %s request to GET BACKUPS", tenantId));
		List<Backup> backups = new ArrayList<Backup>();
		
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		query.addFilterBean(new FilterBean<VmVolume>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.AVAILABLE, FilterBeanType.EQUAL));
		//TODO: this only applies to MySQL
		query.addFilterBean(new FilterBean<VmVolume>("backupVolumeUuid", "NULL", FilterBeanType.NOTEQUAL));
		if(!serverId.equals(""))
			query.addFilterBean(new FilterBean<VmVolume>("instanceUuid", serverId, FilterBeanType.EQUAL));
		List<VmVolume> vols = (List<VmVolume>) volumeProxy.searchAll(query,  false, false, false, false);
		
		for (VmVolume vol : vols) {
			Backup backup = generator.volumeToBackup(vol);
			fillupBackupWithInstanceName(backup);
			backups.add(backup);
		}
		
		return backups;
	}

	@Override
	public Backup createBackup(String tenantId, Backup backup) throws Exception {
		logger.info(String.format("User %s request to CREATE BACKUP %s", tenantId, backup.displayName));
		
		if (backup.volumeId == null) {
			logger.info("volume id is null");
			throw new OperationFailedException("volume id is null");
		}
		
		VmInstance vmInstance = null;
		
		String message = "";
		try {
			VmVolume vmVolume = VolumeChecker.checkOwner(tenantId, backup.volumeId);
			vmInstance = VolumeChecker.checkInstanceStatus(tenantId, vmVolume.getInstanceUuid(), VolumeOperationEnum.CREATE_BACKUP);
			if(!AcGroupChecker.checkBackupCount(tenantId)) {
				message = "user " + tenantId + " request to create backup while his backup number reaches the upper limit";
				logger.warn(message);
				
				throw new OperationFailedException("user's backup number reaches the upper limit");
			}
				
		}catch (Exception e) {
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
			loller.warn(LolLogUtil.CREATE_BACKUP, message, rpcExtra);
			loller.info(LolLogUtil.CREATE_BACKUP, String.format("User %s request to CREATE BACKUP %s", tenantId, backup.displayName),
					rpcExtra);
			loller.warn(LolLogUtil.CREATE_BACKUP, "create backup :"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, vmInstance.getUuid());
		loller.info(LolLogUtil.CREATE_BACKUP, String.format("User %s request to CREATE BACKUP %s", tenantId, backup.displayName),
				rpcExtra);
		String uuid = null;
		try {
			uuid = scheduler.createBackUp(backup.displayName, backup.displayDescription, backup.volumeId, backup.force, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_BACKUP, rpcExtra);
			return new Backup();
		}
		if(uuid == null) {
			throw new OperationFailedException("create backup failed");
		}
		logger.info(String.format("BACKUP created successfully, uuid is %s", uuid));
		backup.id = uuid;
		return backup;
	}

	@Override
	public void deleteBackup(String tenantId, String backupId) throws Exception {
		logger.info(String.format("User %s request to DELETE BACKUP %s", tenantId, backupId));
		
		VmVolume vmVolume = null;
		try {
			vmVolume = VolumeChecker.checkBackup(tenantId, backupId);
			VolumeChecker.checkInstanceStatus(tenantId, vmVolume.getInstanceUuid(), VolumeOperationEnum.REVERT_BACKUP);
			if (VolumeChecker.checkAttach(tenantId, null, backupId) != null)  {
				logger.info("backup is in use");
				throw new OperationFailedException("backup is in use");
			}
		}catch (Exception e) {
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
			loller.warn(LolLogUtil.DELETE_BACKUP, "delete backup :"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, vmVolume.getInstanceUuid());
		loller.info(LolLogUtil.DELETE_BACKUP, String.format("User %s request to DELETE BACKUP %s", tenantId, backupId),
				rpcExtra);
		try {
			scheduler.deleteBackUp(backupId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DELETE_BACKUP, rpcExtra);
			return;
		}
		logger.info(String.format("BACKUP %s deleted successfully", backupId));
	}

	@Override
	public Volume update(String tenantId, String volumeId, Volume volume)
			throws Exception {
		logger.info(String.format("User %s request to UPDATE VOLUME %s", tenantId, volumeId));
		
		VmVolume vmVolume0 = null;
		try {
			vmVolume0 = VolumeChecker.checkOwner(tenantId, volumeId);
		}catch (Exception e) {
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
			loller.info(LolLogUtil.UPDATE_VOLUME, String.format("User %s request to UPDATE VOLUME %s", tenantId, volumeId),
					rpcExtra);
			loller.warn(LolLogUtil.UPDATE_VOLUME, "update volume :"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, vmVolume0.getInstanceUuid());
		loller.info(LolLogUtil.UPDATE_VOLUME, String.format("User %s request to UPDATE VOLUME %s", tenantId, volumeId),
				rpcExtra);
		try {
			scheduler.updateVolume(volumeId, volume.displayName, volume.displayDescription, volume.metadata, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.UPDATE_VOLUME, rpcExtra);
			return new Volume();
		}
		VmVolume vmVolume = volumeProxy.getByUUID(volumeId, false, false, false, false);

		logger.info(String.format("VOLUME %s updated successfully", volumeId));
		return generator.vmVolumeToVolume(vmVolume, false);
	}

	@Override
	public void revertBackup(String tenantId, String backupId) throws Exception {
		logger.info(String.format("User %s request to REVERT BACKUP %s", tenantId, backupId));
		
		VmInstance vmInstance = null;
		try {
			VmVolume backup = VolumeChecker.checkBackupAvailable(tenantId, backupId);
			if (VolumeChecker.checkAttach(tenantId, null, backupId) != null)  {
				logger.info("backup is already in use");
				throw new OperationFailedException("backup is already in use");
			}
			vmInstance = VolumeChecker.checkInstanceStatus(tenantId, backup.getInstanceUuid(), VolumeOperationEnum.REVERT_BACKUP);
		}catch (Exception e) {
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
			loller.info(LolLogUtil.REVERT_BACKUP, String.format("User %s request to REVERT BACKUP %s", tenantId, backupId),
					rpcExtra);
			loller.warn(LolLogUtil.REVERT_BACKUP, "update volume :"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, vmInstance.getUuid());
		loller.info(LolLogUtil.REVERT_BACKUP, String.format("User %s request to REVERT BACKUP %s", tenantId, backupId),
				rpcExtra);
		try {
			scheduler.revertBackUp(backupId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REVERT_BACKUP, rpcExtra);
			return;
		}
		logger.info(String.format("BACKUP %s reverted successfully", backupId));
	}

	
	private void fillupBackupWithInstanceName(Backup backup) throws Exception {
		if(backup == null || backup.instanceId == null)
			return;
		VmInstance instance = instanceProxy.getByUuid(backup.instanceId, false, false, false, false, false, false, false, false);
		backup.instanceName = instance.getName();
	}

	
	@Override
	public List<Volume> searchByProperties(String tenantId, String uuid,
			String userId, String serverId, String usageType, String status, String attachStatus, 
			boolean isBackup, String displayName, Integer zoneId, Integer aggregateId, String hostId, 
			Integer page, Integer size) throws Exception {
		String logStr = String.format("User %s request to search VOLUMES", tenantId);
		
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		if(userId != null){
			query.addFilterBean(new FilterBean<VmVolume>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if( uuid != null){
			query.addFilterBean(new FilterBean<VmVolume>("volumeUuid", uuid, FilterBeanType.EQUAL));
			logStr += ", uuid:" + uuid;
		}
		if(serverId != null){
			query.addFilterBean(new FilterBean<VmVolume>("instanceUuid", serverId, FilterBeanType.EQUAL));
			logStr += ", serverId:" + serverId;
		}
			
		if(usageType != null)
			try {
				query.addFilterBean(new FilterBean<VmVolume>("usageType", VmVolume.VmVolumeUsageTypeEnum.valueOf(usageType.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", usageType:" + usageType;
			}catch (Exception e) {
				logger.info("usageType illegal:" + usageType);
				throw new OperationFailedException("usageType illegal");
			}
		if(status != null)
			try {
				query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status;
			}catch (Exception e) {
				logger.info("status illegal:" + status);
				throw new OperationFailedException("staus illegal");
			}
		else 
			query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		if(attachStatus != null)
			try {
				query.addFilterBean(new FilterBean<VmVolume>("attachStatus", VmVolume.VmVolumeAttachStatusEnum.valueOf(attachStatus.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", attachStatus:" + attachStatus;
			}catch (Exception e) {
				logger.info("attachStatus illegal:" + attachStatus);
				throw new OperationFailedException("attachStatus illegal");
			}
		if(isBackup){
			query.addFilterBean(new FilterBean<VmVolume>("backupVolumeUuid", "NULL", FilterBeanType.NOTEQUAL));
			logStr += ", isBackup:true";
		}
		if(displayName != null) {
			query.addFilterBean(new FilterBean<VmVolume>("displayName", displayName, FilterBeanType.BOTH_LIKE));
		    logStr += ", displayName:" + displayName;
		}
		//zone id
		if(zoneId != null) {
			query.addFilterBean(new FilterBean<VmVolume>("availabilityZoneId", zoneId, FilterBeanType.EQUAL));
			logStr += ", zoneId:" + zoneId;
		}
		//aggregate id
		if(aggregateId != null) {
			query.addFilterBean(new FilterBean<VmVolume>("availabilityClusterId", aggregateId, FilterBeanType.EQUAL));
			logStr += ", aggregateId: " + aggregateId;
		}
		if(hostId != null) {
			query.addFilterBean(new FilterBean<VmVolume>("hostUuid", hostId, FilterBeanType.EQUAL));
			logStr += ", hostId: " + hostId;
		}
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page != 0)
			page -= 1;

		logger.info(logStr);
		List <Volume> apiVolumes = new ArrayList<Volume>();
		List<? extends VmVolume> vmVolumes = volumeProxy.searchAll(query, false, false, false, false, page, size);
		for(VmVolume vmVolume : vmVolumes) {
			apiVolumes.add(generator.vmVolumeToVolume(vmVolume, true));
		}
		return apiVolumes;
	}

	@Override
	public String countByProperties(String tenantId, String uuid,
			String userId, String serverId, String usageType, String status, String attachStatus,
			boolean isBackup, String displayName, Integer zoneId, Integer aggregateId, String hostId)
			throws Exception {

		String logStr = String.format("User %s request to count VOLUMES", tenantId);
		
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		if(userId != null){
			query.addFilterBean(new FilterBean<VmVolume>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if( uuid != null){
			query.addFilterBean(new FilterBean<VmVolume>("volumeUuid", uuid, FilterBeanType.EQUAL));
			logStr += ", uuid:" + uuid;
		}

		if(serverId != null){
			query.addFilterBean(new FilterBean<VmVolume>("instanceUuid", serverId, FilterBeanType.EQUAL));
			logStr += ", serverId:" + serverId;
		}
		if(usageType != null)
			try {
				query.addFilterBean(new FilterBean<VmVolume>("usageType", VmVolume.VmVolumeUsageTypeEnum.valueOf(usageType.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", usageType:" + usageType;
			}catch (Exception e) {
				logger.info("usageType illegal:" + usageType);
				throw new OperationFailedException("usageType illegal");
			}
		if(status != null)
			try {
				query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status;
			}catch (Exception e) {
				logger.info("status illegal:" + status);
				throw new OperationFailedException("staus illegal");
			}
		else 
			query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		if(attachStatus != null)
			try {
				query.addFilterBean(new FilterBean<VmVolume>("attachStatus", VmVolume.VmVolumeAttachStatusEnum.valueOf(attachStatus.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", attachStatus:" + attachStatus;
			}catch (Exception e) {
				logger.info("attachStatus illegal:" + attachStatus);
				throw new OperationFailedException("attachStatus illegal");
			}
		if(isBackup){
			query.addFilterBean(new FilterBean<VmVolume>("backupVolumeUuid", "NULL", FilterBeanType.NOTEQUAL));
			logStr += ", isBackup:true";
		}
		if(displayName != null) {
			query.addFilterBean(new FilterBean<VmVolume>("displayName", displayName, FilterBeanType.BOTH_LIKE));
		    logStr += ", displayName:" + displayName;
		}
		//zone id
		if(zoneId != null) {
			query.addFilterBean(new FilterBean<VmVolume>("availabilityZoneId", zoneId, FilterBeanType.EQUAL));
			logStr += ", zoneId:" + zoneId;
		}
		//aggregate id
		if(aggregateId != null) {
			query.addFilterBean(new FilterBean<VmVolume>("availabilityClusterId", aggregateId, FilterBeanType.EQUAL));
			logStr += ", aggregateId: " + aggregateId;
		}
		if(hostId != null) {
			query.addFilterBean(new FilterBean<VmVolume>("hostUuid", hostId, FilterBeanType.EQUAL));
			logStr += ", hostId: " + hostId;
		}
		logger.info(logStr);
		
		return String.valueOf(volumeProxy.countSearch(query));
	}
}
