package appcloud.openapi.operate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import appcloud.api.beans.Volume;
import appcloud.api.enums.VolumeOperationEnum;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmZone;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.openapi.checkutils.AcGroupChecker;
import appcloud.openapi.checkutils.VolumeChecker;
import appcloud.openapi.constant.Constants;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.manager.util.BeansGenerator;
import appcloud.openapi.manager.util.DealException;
import appcloud.openapi.manager.util.LolHelper;
import appcloud.openapi.manager.util.StringUtil;
import appcloud.openapi.operate.VolumeOperate;
import appcloud.openapi.response.DisksDetailReponse;
import appcloud.rpc.tools.RpcException;

@Component
public class VolumeOperateImpl implements VolumeOperate {

	private static Logger logger = Logger.getLogger(VolumeOperateImpl.class);
	private static LolLogUtil loller = LolHelper
			.getLolLogUtil(VolumeOperateImpl.class);
	
	private ResourceSchedulerService scheduler;
	private VmUserProxy vmUserProxy;
	private VmGroupProxy vmGroupProxy;
	private VmVolumeProxy volumeProxy;
	private VmZoneProxy vmZoneProxy;
	
	private BeansGenerator generator;
	
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
		generator = BeansGenerator.getInstance();
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
		
		return generator.vmVolumeToVolume(vmVolume, true);
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
		
		logger.info(String.format("User %s request to ATTACH VOLUMEATTACHMENT %s to server %s", userId, diskId, instanceId));
		
		try {
			scheduler.detachVolume(diskId, instanceId, rpcExtra);
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
		
		if (null != diskIds) {
			JSONArray array = new JSONArray(diskIds);
			for (int i = 0; i < array.length(); i++) {
				String diskId = (String) array.get(i);
				VmVolume vmVolume =  VolumeChecker.checkOwner(userId, diskId);
				Volume volume = generator.vmVolumeToVolume(vmVolume, true);
				apiDisks.add(generator.volumeToDiskDetailItem(volume));
			}
			
			DisksDetailReponse disksDetailReponse = new DisksDetailReponse(requestId,apiDisks,apiDisks.size(), pageNum, 
					pageSize);
			return disksDetailReponse;
		}
		
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
		
		if (!StringUtil.isEmpty(instanceId)) {
			query.addFilterBean(new FilterBean<VmVolume>("instanceUuid", instanceId, FilterBeanType.EQUAL));
		}
		
		if (!StringUtil.isEmpty(diskName)) {
			query.addFilterBean(new FilterBean<VmVolume>("displayName", diskName, FilterBeanType.EQUAL));
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
			Volume volume = generator.vmVolumeToVolume(vmVolume, true);
			apiDisks.add(generator.volumeToDiskDetailItem(volume));
		}
		
		long totalCount = volumeProxy.countSearch(query);
		
		DisksDetailReponse disksDetailReponse = new DisksDetailReponse(requestId, apiDisks, 
				totalCount, pageNum, pageSize);
		return disksDetailReponse;
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

}
