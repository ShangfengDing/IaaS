package appcloud.openapi.operate.impl;

import appcloud.api.beans.Volume;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.*;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.*;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.openapi.checkutils.InstanceChecker;
import appcloud.openapi.checkutils.VolumeChecker;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.datatype.ImageBackItem;
import appcloud.openapi.manager.util.BeansGenerator;
import appcloud.openapi.manager.util.DealException;
import appcloud.openapi.manager.util.LolHelper;
import appcloud.openapi.manager.util.StringUtil;
import appcloud.openapi.operate.VolumeOperate;
import appcloud.openapi.response.DescribeDiskImageBackResponse;
import appcloud.openapi.response.DisksDetailReponse;
import appcloud.rpc.tools.RpcException;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VolumeOperateImpl implements VolumeOperate {

	private static Logger logger = Logger.getLogger(VolumeOperateImpl.class);
	private static LolLogUtil loller = LolHelper
			.getLolLogUtil(VolumeOperateImpl.class);
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private ResourceSchedulerService scheduler;
	private VmUserProxy vmUserProxy;
	private VmGroupProxy vmGroupProxy;
	private VmVolumeProxy volumeProxy;
	private VmZoneProxy vmZoneProxy;
	private VmImageBackProxy vmImageBackProxy;

	private BeansGenerator generator;
	private VmHdEndtimeManager vmHdEndtimeManager;
	
	public VolumeOperateImpl() {
		scheduler = (ResourceSchedulerService) ConnectionFactory
				.getAMQPService(ResourceSchedulerService.class,
						RoutingKeys.RESOUCE_SCHEDULER);
		vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
				VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmGroupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(
				VmGroupProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(VmVolumeProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmZoneProxy = (VmZoneProxy) ConnectionFactory.getTipProxy(
				VmZoneProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		vmImageBackProxy = (VmImageBackProxy) ConnectionFactory.getTipProxy(
				VmImageBackProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		generator = BeansGenerator.getInstance();
		vmHdEndtimeManager = new VmHdEndtimeManager();
	}
	
	@Override
	public Volume create(String appkeyId, String aggregateId, String zoneId,
			Integer size, String displayName, String description, String type, String requestId)
			throws Exception {
		
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		logger.info(String.format("User %s request to CREATE VOLUME %s", userId, displayName));
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		loller.info(LolLogUtil.CREATE_VOLUME, String.format("User %s request to CREATE VOLUME %s", userId, displayName),
				rpcExtra);
		
		String uuid = null ;
		try {
			List<Integer> aggregateIdsT = new ArrayList<Integer>();
			if(StringUtil.isNumeric(aggregateId))
				aggregateIdsT.add(Integer.valueOf(aggregateId));
			else {
				QueryObject<VmUser> query = new QueryObject<VmUser>();
				query.addFilterBean(new FilterBean<VmUser>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
				List<VmUser> vmUsers = (List<VmUser>) vmUserProxy.searchAll(query);
				if(vmUsers == null || vmUsers.size() == 0)
					throw new ItemNotFoundException("tenant does not exist");
				
				VmGroup vmGroup = vmGroupProxy.getById(vmUsers.get(0).getGroupId());
				if(vmGroup == null)
					throw new ItemNotFoundException("group does not exist");
				
				aggregateIdsT = generator.groupToAcGroup(vmGroup).availableClusters;
			}
			
			VmZone zone = vmZoneProxy.getByZoneId(zoneId);
			if (zone == null) {
				throw new ItemNotFoundException("zoneId:"+zoneId+" not fund");
			}
			try {
				Map<String, String> metadata = new HashMap<String, String>();
				uuid = scheduler.createVolume(Integer.valueOf(userId), 
						zone.getId(), aggregateIdsT, "name", displayName, description, size, null, metadata , rpcExtra);
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
		if (null == vmVolume.getAttachStatus() ) {
			logger.info(ToStringBuilder.reflectionToString(vmVolume));
			vmVolume.setAttachStatus(VmVolumeAttachStatusEnum.DETACHED);
			vmVolume.setDisplayName(displayName);
			vmVolume.setDisplayDescription(description);
			volumeProxy.update(vmVolume);
		}
		return generator.vmVolumeToVolume(vmVolume, true);
	}



	@Override
	public String createImageBack(String appkeyId, String displayName, String description, String volumeUuid, String requestId) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		if (vmUser == null) throw new ItemNotFoundException("tenant does not exist");
		VmGroup vmGroup = vmGroupProxy.getById(vmUser.getGroupId());
		if (vmGroup == null) throw new ItemNotFoundException("group does not exist");

		String userId = vmUser.getUserId() + "";

		logger.info(String.format("User %s request to CREATE VOLUME %s", userId, displayName));

		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		loller.info(LolLogUtil.CREATE_VOLUME_IMAGEBACK, String.format("User %s request to CREATE VOLUME %s", userId, displayName), rpcExtra);

		String uuid;
		try {
			uuid = scheduler.createVolumeImageBack(Integer.valueOf(userId),displayName, description,volumeUuid,rpcExtra);
			logger.info(String.format("VOLUME created successfully, uuid is %s", uuid));
			return uuid;
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_VOLUME, rpcExtra);
			throw e;
		}
	}
	@Override
	public Boolean attach(String appkeyId, String instanceId,
			String diskId, String requestId) throws Exception{
		
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		logger.info(String.format("User %s request to ATTACH VOLUMEATTACHMENT %s to server %s", userId, diskId, instanceId));
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId, instanceId);
		loller.info(LolLogUtil.ATTACH_VOLUME, String.format("User %s request to ATTACH VOLUMEATTACHMENT %s to server %s",
				userId, diskId, instanceId),
				rpcExtra);
		String mountPoint = null;
		try {
			mountPoint = scheduler.attachVolume(diskId, instanceId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.ATTACH_VOLUME, rpcExtra);
			return false;
		}
		logger.info(String.format("VOLUMEATTACHMENT %s attached to %s successfully", diskId, mountPoint));
		return true;
	}

	@Override
	public Boolean detach(String appkeyId, String instanceId, String diskId,
			String requestId) throws Exception {
		
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId, instanceId);
		loller.info(LolLogUtil.DETACH_VOLUME, String.format("User %s request to DETACH VOLUMEATTACHMENT %s to server %s",
				userId, diskId, instanceId),
				rpcExtra);
		
		logger.info(String.format("User %s request to DETACH VOLUMEATTACHMENT %s from server %s", userId, diskId, instanceId));
		
		try {
			VmInstance vmInstance = InstanceChecker.checkOwner(userId, instanceId);
			if (vmInstance.getVmStatus() == VmStatusEnum.DELETED) {
				VmVolume vmVolume = VolumeChecker.checkOwner(userId, diskId);
				vmVolume.setAttachStatus(VmVolumeAttachStatusEnum.DETACHED);
				volumeProxy.update(vmVolume);
			} else {
				scheduler.detachVolume(diskId, instanceId, rpcExtra);
			}
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DETACH_VOLUME, rpcExtra);
			return false;
		}
		
		logger.info(String.format("VOLUMEATTACHMENT %s detached successfully", diskId));
		return true;
	}

	@Override
	public Boolean delete(String appkeyId,
			String diskId, String requestId) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId, diskId);
		
		loller.info(LolLogUtil.DELETE_VOLUME, String.format("User %s request to DELETE VOLUME %s", userId, diskId),
				rpcExtra);
		try {
			scheduler.deleteVolume(diskId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DELETE_VOLUME, rpcExtra);
			return false;
		}
		logger.info(String.format("VOLUME %s deleted successfully", diskId));
		return true;
	}

	@Override
	public DisksDetailReponse describe(String appkeyId, String diskIds,
			String requestId, String zoneId, String instanceId,
			String diskName, String description, String diskType,
			String diskStatus, String diskAttachStatus,String pageNum, String pageSize)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		List<DiskDetailItem> apiDisks = new ArrayList<DiskDetailItem>();
		
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		query.addFilterBean(new FilterBean<VmVolume>("userId", vmUser.getUserId(), FilterBeanType.EQUAL));
		//query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.AVAILABLE, FilterBeanType.EQUAL));
		
		if (!StringUtil.isEmpty(zoneId)) {
			VmZone zone = vmZoneProxy.getByZoneId(zoneId);
			if (zone == null) {
				throw new ItemNotFoundException("zoneId:"+zoneId+" not fund");
			}
			query.addFilterBean(new FilterBean<VmVolume>("availabilityZoneId", zone.getId(), FilterBeanType.EQUAL));
		}
		
		if (null != diskIds) {
			List<String> volumeUuids = new ArrayList<String>();
			JSONArray array = new JSONArray(diskIds);
			for (int i = 0; i < array.length(); i++) {
				String diskId = (String) array.get(i);
				volumeUuids.add(diskId);
				//VmVolume vmVolume =  VolumeChecker.checkOwner(userId, diskId);
				//apiDisks.add(generator.volumeToDiskDetailItem(vmVolume));
			}
			/*DisksDetailReponse disksDetailReponse = new DisksDetailReponse(requestId,apiDisks,apiDisks.size(), pageNum, 
					pageSize);
			return disksDetailReponse;*/
			if (! volumeUuids.isEmpty()) {
				query.addFilterBean(new FilterBean<VmVolume>("volumeUuid", volumeUuids, FilterBeanType.IN));
			}
		}
		
		if (!StringUtil.isEmpty(instanceId)) {
			query.addFilterBean(new FilterBean<VmVolume>("instanceUuid", instanceId, FilterBeanType.EQUAL));
		}
		
		if (!StringUtil.isEmpty(diskName)) {
//			query.addFilterBean(new FilterBean<VmVolume>("displayName", diskName, FilterBeanType.EQUAL));
			query.addFilterBean(new FilterBean<VmVolume>("displayName", diskName, FilterBeanType.BOTH_LIKE));
		}
		
		if (!StringUtil.isEmpty(description)) {
			query.addFilterBean(new FilterBean<VmVolume>("displayDescription", description, FilterBeanType.EQUAL));
		}
		
		if (!StringUtil.isEmpty(diskType)) {
			query.addFilterBean(new FilterBean<VmVolume>("usageType", VmVolume.VmVolumeUsageTypeEnum.valueOf(diskType.toUpperCase()), FilterBeanType.EQUAL));
		}
		
		if (!StringUtil.isEmpty(diskStatus)) {
			query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.valueOf(diskStatus.toUpperCase()), FilterBeanType.EQUAL));
		}
		
		if (!StringUtil.isEmpty(diskAttachStatus)) {
			query.addFilterBean(new FilterBean<VmVolume>("attachStatus", VmVolume.VmVolumeAttachStatusEnum.valueOf(diskAttachStatus.toUpperCase()), FilterBeanType.EQUAL));
		}
		
		int page = 0;
		if (StringUtil.isNumeric(pageNum)) {
			page = Integer.parseInt(pageNum);
			if(page > 0)
				page -= 1;
		}
		
		int size =10;
		if (StringUtil.isNumeric(pageSize)) {
			size = Integer.parseInt(pageSize);
		}
		
		List<? extends VmVolume> vmVolumes = volumeProxy.searchAll(query, false, false, false, false, page, size);
		
		for(VmVolume vmVolume : vmVolumes) {
			DiskDetailItem diskDetailItem = generator.volumeToDiskDetailItem(vmVolume);
			VmHdEndtime hdEndtime = vmHdEndtimeManager.getHdEndtimeByUuid(vmVolume.getVolumeUuid());
			if (hdEndtime != null) {
				diskDetailItem.setChargeType(hdEndtime.getPayType());
				diskDetailItem.setExpiredTime(dateFormat.format(hdEndtime.getEndTime()));
			} else {
				logger.warn("can't find hdEndTime with volumeUuis:"+vmVolume.getVolumeUuid());
			}
			apiDisks.add(diskDetailItem);
		}
		logger.info("disk size "+apiDisks.size());
		
		long totalCount = volumeProxy.countSearch(query);
		
		DisksDetailReponse disksDetailReponse = new DisksDetailReponse(requestId, apiDisks, 
				totalCount, pageNum, String.valueOf(size));
		return disksDetailReponse;
	}

	@Override
	public DescribeDiskImageBackResponse describeImageBack(String requestId, String appkeyId, String volumeUuid,
														   String activeVolumeId, String zoneId, String instanceId,
														   String diskName, String imageBackStatus, String volumeType,
														   String usageType, String backUp, String top)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";

		QueryObject<VmImageBack> query = new QueryObject<VmImageBack>();
		query.addFilterBean(new FilterBean<VmImageBack>("userId", userId, FilterBeanType.EQUAL));

		if (!StringUtil.isEmpty(zoneId)) {
			VmZone zone = vmZoneProxy.getByZoneId(zoneId);
			if (zone == null) {
				throw new ItemNotFoundException("zoneId:"+zoneId+" not fund");
			}
			query.addFilterBean(new FilterBean<VmImageBack>("availabilityZoneId", zone.getId(), FilterBeanType.EQUAL));
		}

		if (null != activeVolumeId) {
			query.addFilterBean(new FilterBean<VmImageBack>("activeVolumeUuid", activeVolumeId, FilterBeanType.EQUAL));
		}

		if (null != volumeUuid) {
			query.addFilterBean(new FilterBean<VmImageBack>("volumeUuid", volumeUuid, FilterBeanType.EQUAL));
		}

		if (!StringUtil.isEmpty(instanceId)) {
			query.addFilterBean(new FilterBean<VmImageBack>("instanceUuid", instanceId, FilterBeanType.EQUAL));
		}

		if (!StringUtil.isEmpty(diskName)) {
			query.addFilterBean(new FilterBean<VmImageBack>("displayName", diskName, FilterBeanType.BOTH_LIKE));
		}

		if (!StringUtil.isEmpty(imageBackStatus)) {
			query.addFilterBean(new FilterBean<VmImageBack>("usageType", VmImageBack.VmImageBackStatusTypeEnum.valueOf(imageBackStatus.toUpperCase()), FilterBeanType.EQUAL));
		} else {
			query.addFilterBean(new FilterBean<VmImageBack>("usageType", VmImageBack.VmImageBackStatusTypeEnum.valueOf("AVAILABLE"), FilterBeanType.EQUAL));
		}

		if (!StringUtil.isEmpty(volumeType)) {
			query.addFilterBean(new FilterBean<VmImageBack>("volumeType", VmVolume.VmVolumeTypeEnum.valueOf(volumeType.toUpperCase()), FilterBeanType.EQUAL));
		}

		if (!StringUtil.isEmpty(usageType)) {
			query.addFilterBean(new FilterBean<VmImageBack>("usageType", VmVolume.VmVolumeUsageTypeEnum.valueOf(usageType.toUpperCase()), FilterBeanType.EQUAL));
		} else {
			query.addFilterBean(new FilterBean<VmImageBack>("usageType", VmVolume.VmVolumeUsageTypeEnum.valueOf("SYSTEM"), FilterBeanType.EQUAL));
		}

		if (!StringUtil.isEmpty(backUp)) {
			Boolean isBackUp = Boolean.parseBoolean(backUp);
			query.addFilterBean(new FilterBean<VmImageBack>("isBackUp", isBackUp, FilterBeanType.EQUAL));
		}

		if (!StringUtil.isEmpty(top)) {
			Boolean isTop = Boolean.parseBoolean(top);
			query.addFilterBean(new FilterBean<VmImageBack>("isTop", isTop, FilterBeanType.EQUAL));
		}

		List<? extends VmImageBack> vmImageBacks = vmImageBackProxy.searchAll(query);

		List<ImageBackItem> imageBackItems = new ArrayList<>();
		for(VmImageBack imageBack : vmImageBacks) {
			ImageBackItem imageBackItem = generator.VmImageBackToImageBackItem(imageBack);
			imageBackItems.add(imageBackItem);
		}
		logger.info("imageBack size "+imageBackItems.size());
		String desInstanceId = null;
		String desVolumeId = null;
		if (imageBackItems.size()!=0) {
			ImageBackItem item = imageBackItems.get(0);
			desInstanceId = item.getInstanceUuid();
			desVolumeId = item.getVolumeUuid();
		} else {
			desInstanceId = instanceId;
			desVolumeId = volumeUuid;
		}

		DescribeDiskImageBackResponse describeDiskImageBackResponse = new DescribeDiskImageBackResponse(requestId, imageBackItems,
				desInstanceId, desVolumeId);
		return describeDiskImageBackResponse;
	}

	@Override
	public Boolean reset(String appkeyId, String snapshotId, String diskId,
			String requestId) throws Exception {

		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		loller.info(LolLogUtil.DETACH_VOLUME, String.format("User %s request to reset disk %s to snapshot %s",
				userId, diskId, snapshotId),
				rpcExtra);
		
		logger.info(String.format("User %s request to reset disk %s to snapshot %s", userId, diskId, snapshotId));
		
		try {
			scheduler.revertSnapshot(snapshotId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REVERT_SNAPSHOT, rpcExtra);
			return false;
		}
		
		logger.info(String.format("disk %s reset successfully", diskId));
		return true;
	}
	
	@Override
	public Boolean modify(String appkeyId, String diskId, String diskName, String description) {
		try {
			VmVolume volume = volumeProxy.getByUUID( diskId, false, false, false, false);
			if (!StringUtil.isEmpty(diskName)) {
				volume.setDisplayName(diskName);
			}
			if (!StringUtil.isEmpty(description)) {
				volume.setDisplayDescription(description);
			}
			volumeProxy.update(volume);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}
	}

	@Override
	public Boolean modifyImageBack(String appkeyId, String diskId, String diskName, String status, String volumeType, String volumeUsageType, String backUp, String top) {
		try {
			VmImageBack imageBack = vmImageBackProxy.getByUuid( diskId);
			if (!StringUtil.isEmpty(diskName)) {
				imageBack.setDisplayName(diskName);
			}
			if (!StringUtil.isEmpty(volumeType)) {
				imageBack.setVolumeType(VmVolume.VmVolumeTypeEnum.valueOf(volumeType));
			}
			if (!StringUtil.isEmpty(volumeUsageType)) {
				imageBack.setUsageType(VmVolume.VmVolumeUsageTypeEnum.valueOf(volumeUsageType));
			}
			if (!StringUtil.isEmpty(backUp)) {
				imageBack.setBackUp(Boolean.parseBoolean(backUp));
			}
			if (StringUtil.isEmpty(top) || StringUtil.isEmpty(status)) {
				vmImageBackProxy.update(imageBack);
				return true;
			}
			if (!StringUtil.isEmpty(status)) {
				if (status.equalsIgnoreCase("error")) {
					imageBack.setImageStatus(VmImageBack.VmImageBackStatusTypeEnum.valueOf(status));
					try {
						vmImageBackProxy.update(imageBack);
						String parentVolumeId = imageBack.getParentVolumeUuid();
						String sonVolumeId = imageBack.getSonVolumeUuid();
						if (parentVolumeId == null) {
							return true;
						}
						if (sonVolumeId == null) {
							VmImageBack parentImageBack = vmImageBackProxy.getByUuid(parentVolumeId);
							parentImageBack.setSonVolumeUuid(null);
							parentImageBack.setTop(true);
							vmImageBackProxy.update(parentImageBack);
							return true;
						}
						VmImageBack parentImageBack = vmImageBackProxy.getByUuid(parentVolumeId);
						VmImageBack sonImageBack = vmImageBackProxy.getByUuid(sonVolumeId);
						parentImageBack.setSonVolumeUuid(sonVolumeId);
						sonImageBack.setParentVolumeUuid(parentVolumeId);
						vmImageBackProxy.update(parentImageBack);
						vmImageBackProxy.update(sonImageBack);
						return true;
					} catch (Exception e) {
					    e.printStackTrace();
					}
				}
			}
			if (!StringUtil.isEmpty(top)) {
				if (top.equalsIgnoreCase("true")) {
					VmImageBack oldTopImageBack = vmImageBackProxy.getByInstanceUuidAndTop(imageBack.getInstanceUuid(), true);
					oldTopImageBack.setTop(false);
					try {
						vmImageBackProxy.update(oldTopImageBack);
						imageBack.setTop(true);
						vmImageBackProxy.update(imageBack);
						return true;
					} catch (Exception e) {
					    e.printStackTrace();
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		}
	}

}
