package appcloud.api.manager.real;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.jersey.api.NotFoundException;

import appcloud.api.beans.Snapshot;
import appcloud.api.checkutils.AcGroupChecker;
import appcloud.api.checkutils.SnapshotChecker;
import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.api.manager.SnapshotManager;
import appcloud.api.manager.util.BeansGenerator;
import appcloud.api.manager.util.DealException;
import appcloud.api.manager.util.LolHelper;
import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.RpcExtra;
import appcloud.common.model.VmSnapshot;
import appcloud.common.model.VmSnapshot.VmSnapshotStatusEnum;
import appcloud.common.model.VmVolume;
import appcloud.common.proxy.VmSnapshotProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.service.ResourceSchedulerService;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.rpc.tools.RpcException;

public class RealSnapshotManager implements SnapshotManager {
	private static Logger logger = Logger.getLogger(RealSnapshotManager.class);
	
	private VmSnapshotProxy snapshotProxy;
	private VmVolumeProxy volumeProxy;
	private BeansGenerator generator;
	private ResourceSchedulerService scheduler;
	
	private static RealSnapshotManager manager = new RealSnapshotManager();
	
	private static LolLogUtil loller = LolHelper.getLolLogUtil(RealSnapshotManager.class);
			
	public static RealSnapshotManager getInstance() {
		return manager;
	}
	
	private RealSnapshotManager() {
		super();
		scheduler = (ResourceSchedulerService) ConnectionFactory.getAMQPService(
				ResourceSchedulerService.class,
				RoutingKeys.RESOUCE_SCHEDULER);
		generator = BeansGenerator.getInstance();
		snapshotProxy = (VmSnapshotProxy) ConnectionFactory.getTipProxy(VmSnapshotProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
		volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(VmVolumeProxy.class, 
				appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	}
	
	@Override
	public List<Snapshot> getList(String tenantId, boolean detailed, String serverId) throws Exception {
		String logInfo = String.format("User %s request to get SNAPSHOTS", tenantId);
		if(!serverId.equals("")){
			logInfo += String.format(" of %s", serverId);
		}
		if(detailed)
			logInfo += ", detailed";
		logger.info(logInfo);
		QueryObject<VmSnapshot> query = new QueryObject<VmSnapshot>();
		query.addFilterBean(new FilterBean<VmSnapshot>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmSnapshot>("status", VmSnapshotStatusEnum.AVAILABLE, FilterBeanType.EQUAL));
		List<Snapshot> apiSnapshots = new ArrayList<Snapshot>();
		//FIXME
		List<? extends VmSnapshot> vmSnapshots = snapshotProxy.searchAll(query, false, 0, 0);
		for(VmSnapshot vmSnapshot: vmSnapshots) {
			if(!serverId.equals("")){
				VmVolume vmVolume = volumeProxy.getByUUID(vmSnapshot.getVolumeUuid(), false, false, false, false);
				if(!serverId.equals(vmVolume.getInstanceUuid()))
					continue;
			}
			apiSnapshots.add(generator.vmSnapshotToSnapshot(vmSnapshot, tenantId, true));
		}
		return apiSnapshots;
	}

	@Override
	public Snapshot get(String tenantId, String snapshotId) throws Exception {
		logger.info(String.format("User %s request to GET SNAPSHOT %s", tenantId, snapshotId));
		VmSnapshot vmSnapshot = snapshotProxy.getByUuid(snapshotId, false);
		if (vmSnapshot == null) {
			throw new ItemNotFoundException("snapshot does not exist");
		}
		if (!vmSnapshot.getUserId().equals(Integer.valueOf(tenantId))) {
			throw new OperationFailedException("check tenant id");
		}
		return generator.vmSnapshotToSnapshot(vmSnapshot, tenantId, true);
	}

	@Override
	public Snapshot create(String tenantId, Snapshot snapshot) throws Exception{
		logger.info(String.format("User %s request to CREATE SNAPSHOT %s for volume %s",
				tenantId, snapshot.displayName, snapshot.volumeId));
		VmVolume vmVolume = null;
		
		String message = "";
		try {
			vmVolume = SnapshotChecker.checkCreateSnapshot(tenantId, snapshot.volumeId);
			
			if(!AcGroupChecker.checkSnapshotCount(tenantId)) {
				message = "user " + tenantId + " request to create snapshot while snapshot number reaches the upper limit";
				logger.warn(message);
				throw new OperationFailedException("user's snapshot number reaches the upper limit");
			}
		}catch (Exception e) {
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
			loller.warn(LolLogUtil.CREATE_SNAPSHOT, message, rpcExtra);
			loller.info(LolLogUtil.CREATE_SNAPSHOT, String.format("User %s request to CREATE SNAPSHOT %s for volume %s",
				tenantId, snapshot.displayName, snapshot.volumeId), rpcExtra);
			loller.warn(LolLogUtil.CREATE_SNAPSHOT, "create snapshot warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, vmVolume.getInstanceUuid());
		loller.info(LolLogUtil.CREATE_SNAPSHOT, String.format("User %s request to CREATE SNAPSHOT %s for volume %s",
				tenantId, snapshot.displayName, snapshot.volumeId), rpcExtra);
		String uuid = null;
		try {
			uuid = scheduler.createSnapshot(snapshot.displayName, snapshot.displayDescription, snapshot.volumeId, snapshot.force, rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.CREATE_SNAPSHOT, rpcExtra);
			return new Snapshot();
		}
		if (uuid != null) {
			snapshot.id = uuid;
			logger.info(String.format("SNAPSHOT created successfully, uuid is %s", uuid));
			return snapshot;
		}else {
			throw new OperationFailedException("create snapshot failed");
		}
	}

	@Override
	public void delete(String tenantId, String snapshotId) throws Exception{
		logger.info(String.format("User %s request to DELETE SNAPSHOT %s", tenantId, snapshotId));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.DELETE_SNAPSHOT, String.format("User %s request to DELETE SNAPSHOT %s", tenantId, snapshotId),
				rpcExtra);
		try {
			SnapshotChecker.checkOwner(tenantId, snapshotId);
		}catch (Exception e) {
			loller.warn(LolLogUtil.DELETE_SNAPSHOT, "delete snapshot warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.deleteSnapshot(snapshotId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.DELETE_SNAPSHOT, rpcExtra);
			return;
		}
		logger.info(String.format("SNAPSHOT %s deleted successfully", snapshotId));
		
	}

	@Override
	public void revert(String tenantId, String snapshotId) throws Exception {
		logger.info(String.format("User %s request to REVERT SNAPSHOT %s", tenantId, snapshotId));
		
		VmVolume vmVolume = null;
		try {
			vmVolume = SnapshotChecker.checkRevertSnapshot(tenantId, snapshotId);
		}catch (Exception e) {
			RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
			loller.info(LolLogUtil.REVERT_SNAPSHOT, String.format("User %s request to REVERT SNAPSHOT %s", tenantId, snapshotId),
					rpcExtra);
			loller.warn(LolLogUtil.REVERT_SNAPSHOT, "revert snapshot warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId, vmVolume.getInstanceUuid());
		loller.info(LolLogUtil.REVERT_SNAPSHOT, String.format("User %s request to REVERT SNAPSHOT %s", tenantId, snapshotId),
				rpcExtra);
		try {
			scheduler.revertSnapshot(snapshotId, rpcExtra);
		}catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.REVERT_SNAPSHOT, rpcExtra);
			return;
		}
		logger.info(String.format("SNAPSHOT %s reverted successfully", snapshotId));
	}

	@Override
	public Snapshot update(String tenantId, String snapshotId, Snapshot snapshot)
			throws Exception {
		logger.info(String.format("User %s request to UPDATE SNAPSHOT %s", tenantId, snapshot.displayName));
		
		RpcExtra rpcExtra = new RpcExtra(RpcExtra.genTranctionId(), tenantId);
		loller.info(LolLogUtil.UPDATE_SNAPSHOT, String.format("User %s request to UPDATE SNAPSHOT %s", tenantId, snapshot.displayName),
				rpcExtra);
		try {
			SnapshotChecker.checkOwner(tenantId, snapshotId);
		}catch (Exception e) {
			loller.warn(LolLogUtil.UPDATE_SNAPSHOT, "update snapshot warn:"+e.getMessage(), rpcExtra);
			throw e;
		}
		try {
			scheduler.updateSnapshot(snapshotId, snapshot.displayName, snapshot.displayDescription, new Integer(snapshot.tenantId), rpcExtra);
		} catch(RpcException e) {
			DealException.isRSTimeoutException(e, LolLogUtil.UPDATE_SNAPSHOT, rpcExtra);
			return new Snapshot();
		}
		VmSnapshot vmSnapshot = snapshotProxy.getByUuid(snapshotId, false);
		if (vmSnapshot == null) {
			throw new NotFoundException();
		}
		logger.info(String.format("SNAPSHOT %s updated successfully", snapshotId));
		return generator.vmSnapshotToSnapshot(vmSnapshot, tenantId, false);
	}

	@Override
	public List<Snapshot> searchByProperties(String tenantId, String userId,
			String snapshotId, String volumeUuid, String displayName, String status,
			Integer page, Integer size) throws Exception {
		String logStr = String.format("User %s request to search SNAPSHOTS", tenantId);
		
		QueryObject<VmSnapshot> query = new QueryObject<VmSnapshot>();
		if(userId != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if(snapshotId != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("uuid", snapshotId, FilterBeanType.EQUAL));
			logStr += ", snapshotId:" + snapshotId;
		}
		if(volumeUuid != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("volumeUuid", volumeUuid, FilterBeanType.EQUAL));
			logStr += ", volumeUuid:" + volumeUuid;
		}
		if(displayName != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("displayName", displayName, FilterBeanType.BOTH_LIKE));
			logStr += ", displayName:" + displayName;
		}
		if(status != null) {
			try{
				query.addFilterBean(new FilterBean<VmSnapshot>("status", VmSnapshotStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status	;
			}catch (Exception e) {
				logger.info("status illegal:" + status);
				throw new OperationFailedException("status illegal");
			}
		}
		else {
			query.addFilterBean(new FilterBean<VmSnapshot>("status", VmSnapshotStatusEnum.AVAILABLE, FilterBeanType.EQUAL));
		}
		logStr += ", page:" + page;
		logStr += ", size:" + size;
		if(page != 0)
			page -= 1;
		logger.info(logStr);
		List<Snapshot> apiSnapshots = new ArrayList<Snapshot>();
		List<? extends VmSnapshot> vmSnapshots = snapshotProxy.searchAll(query, false, page, size);
		for(VmSnapshot vmSnapshot: vmSnapshots) {
			apiSnapshots.add(generator.vmSnapshotToSnapshot(vmSnapshot, tenantId, true));
		}
		return apiSnapshots;
	}

	@Override
	public String countByProperties(String tenantId, String userId,
			String snapshotId, String volumeUuid, String displayName,
			String status) throws Exception {
		String logStr = String.format("User %s request to count SNAPSHOTS", tenantId);
		
		QueryObject<VmSnapshot> query = new QueryObject<VmSnapshot>();
		if(userId != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("userId", Integer.valueOf(userId), FilterBeanType.EQUAL));
			logStr += ", userId:" + userId;
		}
		if(snapshotId != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("uuid", snapshotId, FilterBeanType.EQUAL));
			logStr += ", snapshotId:" + snapshotId;
		}
		if(volumeUuid != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("volumeUuid", volumeUuid, FilterBeanType.EQUAL));
			logStr += ", volumeUuid:" + volumeUuid;
		}
		if(displayName != null) {
			query.addFilterBean(new FilterBean<VmSnapshot>("displayName", displayName, FilterBeanType.BOTH_LIKE));
			logStr += ", displayName:" + displayName;
		}
		if(status != null) {
			try{
				query.addFilterBean(new FilterBean<VmSnapshot>("status", VmSnapshotStatusEnum.valueOf(status.toUpperCase()), FilterBeanType.EQUAL));
				logStr += ", status:" + status	;
			}catch (Exception e) {
				logger.info("status illegal:" + status);
				throw new OperationFailedException("status illegal");
			}
		}
		else {
			query.addFilterBean(new FilterBean<VmSnapshot>("status", VmSnapshotStatusEnum.AVAILABLE, FilterBeanType.EQUAL));
		}
		logger.info(logStr);
		Long c = snapshotProxy.countSearch(query);
		logger.info(c);
		return c.toString();
	}
}
