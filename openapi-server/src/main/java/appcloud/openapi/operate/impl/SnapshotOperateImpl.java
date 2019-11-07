package appcloud.openapi.operate.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import appcloud.api.beans.Snapshot;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmSnapshot;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmSnapshot.VmSnapshotStatusEnum;
import appcloud.common.proxy.VmSnapshotProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.openapi.checkutils.SnapshotChecker;
import appcloud.openapi.datatype.SnapshotDetailItem;
import appcloud.openapi.manager.util.BeansGenerator;
import appcloud.openapi.manager.util.DealException;
import appcloud.openapi.manager.util.LolHelper;
import appcloud.openapi.manager.util.StringUtil;
import appcloud.openapi.operate.SnapshotOperate;
import appcloud.openapi.response.DisksDetailReponse;
import appcloud.openapi.response.SnapshotsDetailReponse;
import appcloud.rpc.tools.RpcException;

@Component
public class SnapshotOperateImpl implements SnapshotOperate {
	private static Logger logger = Logger.getLogger(SnapshotOperateImpl.class);
	
	private static LolLogUtil loller = LolHelper
			.getLolLogUtil(SnapshotOperateImpl.class);
	private ResourceSchedulerService scheduler;
	private VmUserProxy vmUserProxy;
	private VmSnapshotProxy snapshotProxy;
	private BeansGenerator generator;

	public SnapshotOperateImpl() {
		scheduler = (ResourceSchedulerService) ConnectionFactory
				.getAMQPService(ResourceSchedulerService.class,
						RoutingKeys.RESOUCE_SCHEDULER);
		vmUserProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
				VmUserProxy.class,
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		snapshotProxy = (VmSnapshotProxy) ConnectionFactory.getTipProxy(VmSnapshotProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		generator = BeansGenerator.getInstance();
	}

	@Override
	public Snapshot create(String appkeyId, Snapshot snapshot, String reuesetId)
			throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		snapshot.tenantId = userId;
		logger.info(String.format(
				"User %s request to CREATE SNAPSHOT %s for volume %s", userId,
				snapshot.displayName, snapshot.volumeId));

		RpcExtra rpcExtra = new RpcExtra(reuesetId, userId);
		loller.info(LolLogUtil.CREATE_SNAPSHOT, String.format(
				"User %s request to CREATE SNAPSHOT %s for volume %s", userId,
				snapshot.displayName, snapshot.volumeId), rpcExtra);
		
		String uuid = null;
		try {
			uuid = scheduler.createSnapshot(snapshot.displayName,
					snapshot.displayDescription, snapshot.volumeId,
					snapshot.force, rpcExtra);
		} catch (RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_SNAPSHOT,
					rpcExtra);
			return new Snapshot();
		}
		if (uuid != null) {
			snapshot.id = uuid;
			logger.info(String.format(
					"SNAPSHOT created successfully, uuid is %s", uuid));
			return snapshot;
		} else {
			throw new OperationFailedException("create snapshot failed");
		}
	}

	@Override
	public Boolean delete(String appkeyId, String snapshotId,
			String requestId) throws Exception {
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		RpcExtra rpcExtra = new RpcExtra(requestId, userId);
		loller.info(LolLogUtil.DELETE_SNAPSHOT, String.format("User %s request to DELETE SNAPSHOT %s", userId, snapshotId),
				rpcExtra);
		
		try {
			scheduler.deleteSnapshot(snapshotId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DELETE_SNAPSHOT, rpcExtra);
			return false;
		}
		logger.info(String.format("SNAPSHOT %s deleted successfully", snapshotId));
		
		return true;
	}

	@Override
	public SnapshotsDetailReponse describe(String appkeyId, String snapshotIds,
			String requestId, String diskId, String snapshotName,
			String description, String snapshotStatus, String pageNum,
			String pageSize) throws Exception {
		
		VmUser vmUser = vmUserProxy.getByAppKeyId(appkeyId);
		String userId = vmUser.getUserId() + "";
		
		List<SnapshotDetailItem> apiSnapshots = new ArrayList<SnapshotDetailItem>();
		
		if (null != snapshotIds) {
			JSONArray array = new JSONArray(snapshotIds);
			for (int i = 0; i < array.length(); i++) {
				String snapshotId = (String) array.get(i);
				VmSnapshot vmSnapshot = SnapshotChecker.checkOwner(userId, snapshotId);
				Snapshot snapshot = generator.vmSnapshotToSnapshot(vmSnapshot, userId, true);
				apiSnapshots.add(generator.snapshotToSnapshotDetailItem(snapshot));
			}
			
			SnapshotsDetailReponse snapshotsDetailReponse = new SnapshotsDetailReponse(requestId,apiSnapshots,apiSnapshots.size(), pageNum, 
					pageSize);
			return snapshotsDetailReponse;
		}
		
		
		String logStr = String.format("User %s request to search SNAPSHOTS", userId);
		
		QueryObject<VmSnapshot> query = new QueryObject<VmSnapshot>();
		if(userId != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if(diskId != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("volumeUuid", diskId, FilterBeanType.EQUAL));
			logStr += ", volumeUuid:" + diskId;
		}
		if(snapshotName != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("displayName", snapshotName, FilterBeanType.EQUAL));
			logStr += ", displayName:" + snapshotName;
		}
		if(description != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("displayDescription", description, FilterBeanType.EQUAL));
			logStr += ", displayDescription:" + description;
		}
		if(snapshotStatus != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("status", VmSnapshotStatusEnum.valueOf(snapshotStatus.toUpperCase()), FilterBeanType.EQUAL));
			logStr += ", status:" + snapshotStatus	;
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
		
		logger.info(logStr);
		
		List<? extends VmSnapshot> vmSnapshots = snapshotProxy.searchAll(query, false, page, size);
		for(VmSnapshot vmSnapshot: vmSnapshots) {
			Snapshot snapshot = generator.vmSnapshotToSnapshot(vmSnapshot, userId, true);
			apiSnapshots.add(generator.snapshotToSnapshotDetailItem(snapshot));
		}
		
		long totalCount = snapshotProxy.countSearch(query);
		
		SnapshotsDetailReponse snapshotsDetailReponse = new SnapshotsDetailReponse(requestId,apiSnapshots,totalCount, pageNum, 
				pageSize);
		return snapshotsDetailReponse;
	}

}
