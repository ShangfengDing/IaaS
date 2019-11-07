/**
 * File: VolumeServiceImpl.java
 * Author: weed
 * Create Time: 2013-4-16
 */
package appcloud.resourcescheduler.service.impl;

import appcloud.common.constant.RoutingKeys;
import appcloud.common.model.*;
import appcloud.common.model.VmInstance.TaskStatusEnum;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeAttachStatusEnum;
import appcloud.common.model.VmVolume.VmVolumeUsageTypeEnum;
import appcloud.common.proxy.*;
import appcloud.common.service.VMSchedulerService;
import appcloud.common.service.VolumeSchedulerService;
import appcloud.common.transaction.TFuture;
import appcloud.common.transaction.TTask;
import appcloud.common.transaction.Transaction;
import appcloud.common.util.AlertUtil;
import appcloud.common.util.LolLogUtil;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;
import appcloud.resourcescheduler.ResourceScheduler;
import appcloud.resourcescheduler.common.ConnectionManager;
import appcloud.resourcescheduler.service.VolumeService;
import appcloud.rpc.tools.RpcException;
import appcloud.rpc.tools.RpcTimeoutException;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weed
 * 
 */
public class VolumeServiceImpl implements VolumeService {

	private static Logger logger = Logger.getLogger(VolumeServiceImpl.class);
	private static LolLogUtil loller = null;
	static {
		try {
			String ipAddress = ResourceScheduler.getHost().getIp();
			loller = new LolLogUtil(MessageLog.ModuleEnum.RESOURCE_SCHEDULER, VolumeServiceImpl.class, ipAddress);
		} catch (RpcException e) {
			logger.error(e.getMessage());
		}
	}
	private static VolumeSchedulerService volumeScheduler = (VolumeSchedulerService) ConnectionManager.getInstance()
			.getAMQPService(VolumeSchedulerService.class,
					RoutingKeys.VOLUME_SCHEDULER);
	private static VMSchedulerService vmScheduler = (VMSchedulerService) ConnectionManager.getInstance()
			.getAMQPService(VMSchedulerService.class, RoutingKeys.VM_SCHEDULER);
	private static VmHostUsedProxy vmHostUsedProxy = (VmHostUsedProxy) ConnectionManager
			.getInstance().getDBProxy(VmHostUsedProxy.class);
	private static VmInstanceProxy vmInstanceProxy = (VmInstanceProxy) ConnectionManager.getInstance()
			.getDBProxy(VmInstanceProxy.class);
	private static VmVolumeProxy vmVolumeProxy = (VmVolumeProxy) ConnectionManager.getInstance()
			.getDBProxy(VmVolumeProxy.class);
	private static VmZoneProxy vmZoneProxy = (VmZoneProxy) ConnectionManager.getInstance()
			.getDBProxy(VmZoneProxy.class);
	private static ClusterProxy clusterProxy = (ClusterProxy) ConnectionManager.getInstance()
			.getDBProxy(ClusterProxy.class);
	private static VmSnapshotProxy vmSnapshotProxy = (VmSnapshotProxy) ConnectionManager.getInstance()
			.getDBProxy(VmSnapshotProxy.class);

	@Override
	public String createVolume(final Integer userId, final Integer avalibilityZoneId, final List<Integer> clusterIds,
			String name, final String displayName, final String discription, final Integer size,
			String type, Map<String, String> metadata, final RpcExtra rpcExtra) throws RpcException {

		String paramInfos = "createVolume: " + userId + ", "
				+ avalibilityZoneId + ", " + name + ", " + displayName + ", "
				+ discription + ", " + size + ", " + type + ", " + metadata;
		logger.debug(paramInfos);

		VmZone zone = null;
		try {
			zone = vmZoneProxy.getById(avalibilityZoneId);
			if (zone == null) {
				logger.error("No avalibilityZone with uuid " + avalibilityZoneId);
				loller.error(LolLogUtil.CREATE_VOLUME, "No avalibilityZone with uuid " + avalibilityZoneId, rpcExtra);
				return null;
			}
			logger.info("zone: " + zone);
		} catch(Exception e) {
			logger.error("connect to db-proxy failed", e);
			loller.error(LolLogUtil.CREATE_VOLUME, "connect to db-proxy failed" + e.getMessage(), rpcExtra);
			return null;
		}

	    VmVolume volumeTmp = null;
		try {
			Host volumeHost = null;
			List<Host> volumeHosts = null;
			for (Integer clusterId : clusterIds) {
				Cluster cluster = clusterProxy.getById(clusterId, false, false, false);
				volumeHosts = volumeScheduler.selectHost(cluster, size, null, rpcExtra);
				if (volumeHosts != null && volumeHosts.size() != 0) {
					volumeHost = volumeHosts.get(0);
					break;
				}
			}
			if (volumeHost == null) {
				String why = "volume-scheduler select host fail!";
				logger.error(why);
				loller.error(LolLogUtil.CREATE_VOLUME, why, rpcExtra);
				throw new RpcException(why);
			}
			logger.info("volumeHost: " + volumeHost);

			volumeTmp = volumeScheduler.defineVolume(VmVolumeUsageTypeEnum.NETWORK, userId, size, zone,
					null, volumeHost, rpcExtra);
		} catch(Exception e) {
			if(e instanceof RpcTimeoutException) {
				logger.error("no response from volume-scheduler");
				loller.error(LolLogUtil.CREATE_VOLUME, "no response from volume-scheduler", rpcExtra);
			} else {
				String message = "Create volume failed!";
				logger.error(message + e.getMessage(), e);
				loller.error(LolLogUtil.CREATE_VOLUME, message + e.getMessage(), rpcExtra);
			}
			return null;
		}
		String volumeUuid = volumeTmp.getVolumeUuid();
		logger.info("VolumeUuid: " + volumeUuid);

		final VmVolume volume = volumeTmp;
		Transaction transaction = new Transaction(new TTask() {
			@Override
			public boolean preProcess() throws Exception {
				return true;
			}

			@Override
			public void process() throws Exception {
				try {
					VmVolume savedVolume = vmVolumeProxy.getByUUID(
							volume.getVolumeUuid(), false, false, false, false);
					savedVolume.setDisplayName(displayName);
					savedVolume.setDisplayDescription(discription);
					vmVolumeProxy.update(savedVolume);

					volumeScheduler.createVolume(volume.getVolumeUuid(), rpcExtra);

				} catch(RpcException e) {
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from volume-scheduler");
						loller.error(LolLogUtil.CREATE_VOLUME, "no response from volume-scheduler", rpcExtra);
						throw new RpcException("no response from volume-scheduler ");
					} else if (e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
						String why = " connect to db-proxy failed! ";
						logger.error(why, e);
						loller.error(LolLogUtil.CREATE_VOLUME, why, rpcExtra);
						throw new RpcException(why+e.getMessage());
					} else {
						String message = "Create volume failed!";
						logger.error(message + e.getMessage(), e);
						loller.error(LolLogUtil.CREATE_VOLUME, message + e.getMessage(), rpcExtra);
						throw new RpcException(message + e.getMessage());
					}
				}
			}

			@Override
			public void postProcess() throws Exception {
				VmVolume savedVolume = vmVolumeProxy.getByUUID(volume.getVolumeUuid(), true, false, false, false);
//				updateVmHostUsed(savedVolume, LolLogUtil.CREATE_VOLUME, rpcExtra);
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
			}

		});

		transaction.asyncExecute(rpcExtra);

		return volumeUuid;
	}


	@Override
	public String createVolumeImageBack(final Integer userId, final String displayName, final String discription, final String volumeUuid,final RpcExtra rpcExtra) throws RpcException {

		String paramInfos = "createVolume: " + userId + ", "
				+  displayName + ", " + discription;
		logger.debug(paramInfos);

		VmImageBack imageBackTmp = null;
		try {
			imageBackTmp = volumeScheduler.defineVolumeImageBack(volumeUuid, displayName, discription, rpcExtra);
			final String vmInstaceUuid = imageBackTmp.getInstanceUuid();
			if (vmInstaceUuid == null) {
				logger.error("vmInstanceUuid is null from volume " + imageBackTmp.getVolumeUuid());
				loller.error(LolLogUtil.CREATE_VOLUME_IMAGEBACK, "vmInstanceUuid is null from volume " + imageBackTmp.getVolumeUuid(), rpcExtra);
				return null;
			}
			final VmInstance instance = vmInstanceProxy.getByUuid(vmInstaceUuid, false, false,
					false, false, false, false, false, false);
			if (instance == null) {
				logger.error("No instance with UUID " + vmInstaceUuid);
				loller.error(LolLogUtil.CREATE_VOLUME_IMAGEBACK, "No instance with UUID " + vmInstaceUuid, rpcExtra);
				return null;
			}


			final VmImageBack imageBack = imageBackTmp;
			Transaction transaction = new Transaction(new TTask() {
				@Override
				public boolean preProcess() throws Exception {
					return true;
				}

				@Override
				public void process() throws Exception {
					try {
						if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0) {
							vmScheduler.stopVM(vmInstaceUuid, rpcExtra);
							logger.debug("createSnapshot.vmScheduler.stopVM succeed!");
							Thread.sleep(1000);
						}
						volumeScheduler.createVolumeImageBack(imageBack.getVolumeUuid(), rpcExtra);
					} catch (RpcException e) {
						if (e instanceof RpcTimeoutException) {
							logger.error("no response from volume-scheduler");
							loller.error(LolLogUtil.CREATE_VOLUME_IMAGEBACK, "no response from volume-scheduler", rpcExtra);
							throw new RpcException("no response from volume-scheduler ");
						} else if (e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
							String why = " connect to db-proxy failed! ";
							logger.error(why, e);
							loller.error(LolLogUtil.CREATE_VOLUME_IMAGEBACK, why, rpcExtra);
							throw new RpcException(why + e.getMessage());
						} else {
							String message = "Create volumeImageBack failed!";
							logger.error(message + e.getMessage(), e);
							loller.error(LolLogUtil.CREATE_VOLUME_IMAGEBACK, message + e.getMessage(), rpcExtra);
							throw new RpcException(message + e.getMessage());
						}
					}
				}

				@Override
				public void postProcess() throws Exception {
				}

				@Override
				public void onError() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onCompleted() {
					// TODO Auto-generated method stub
				}

			});
			transaction.asyncExecute(rpcExtra);
			return volumeUuid;
		} catch(Exception e) {
			if(e instanceof RpcTimeoutException) {
				logger.error("no response from volume-scheduler");
				loller.error(LolLogUtil.CREATE_VOLUME_IMAGEBACK, "no response from volume-scheduler", rpcExtra);
			} else {
				String message = "Create volumeImageback failed!";
				logger.error(message + e.getMessage(), e);
				loller.error(LolLogUtil.CREATE_VOLUME_IMAGEBACK, message + e.getMessage(), rpcExtra);
			}
			return null;
		}
	}

	@Override
	public String revertVolume(final String volumeUuid, final String instanceUuid,
			final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "revertVolume: " + volumeUuid + ", " + instanceUuid;
		logger.debug(paramInfos);

		if(volumeUuid == null || "".equals(volumeUuid) || instanceUuid == null || "".equals(instanceUuid)) {
			logger.error("Failed to revert volume, parameter error");
			loller.error(LolLogUtil.REVERT_VOLUME, "Failed to revert volume, parameter error", rpcExtra);
			return null;
		}
		
		try {
			Transaction revertVolumeTransaction = new Transaction(new TTask() {
				@Override
				public boolean preProcess() throws Exception {
					return checkAndSetVmTaskStatus(instanceUuid, TaskStatusEnum.REINSTALL);
				}
				
				@Override
				public void process() throws Exception {
					try {
						VmInstance instance = vmInstanceProxy.getByUuid(instanceUuid, false, false,
								false, false, false, false, false, false);
						if (instance == null) {
							logger.error("No instance with UUID " + instanceUuid);
							loller.error(LolLogUtil.REVERT_VOLUME, "No instance with UUID " + instanceUuid, rpcExtra);
							throw new RpcException("No instance with UUID " + instanceUuid);
						}
						if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
							vmScheduler.stopVM(instanceUuid, rpcExtra);
							logger.debug("revertVolume.vmScheduler.stopVM succeed!");
							Thread.sleep(1000);
						}
						volumeScheduler.revertVolume(volumeUuid, rpcExtra);
						logger.info("revertVolume succeed!");
					} catch (Exception e) {
						if(e instanceof RpcTimeoutException) {
							String why = " no response from volume-scheduler ";
							logger.error(why);
							loller.error(LolLogUtil.REVERT_VOLUME, why + e.getMessage(), rpcExtra);
							throw new RpcException(why);
						} else if (e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
							String why = " connect to db-proxy failed!";
							logger.error(why, e);
							loller.error(LolLogUtil.REVERT_VOLUME, why + e.getMessage(), rpcExtra);
							throw new RpcException(why+e.getMessage());
						} else {
							String error = "revertVolume failed! " + paramInfos;
							logger.error(error, e);
							loller.error(LolLogUtil.REVERT_VOLUME, error + e.getMessage(), rpcExtra);
							AlertUtil.alert("卷" + volumeUuid + "格式化硬盘/系统失败", "输入参数为" + paramInfos, e);
							throw new RpcException(error + e.getMessage());
						}
						
					}
				}
				
				
				@Override
				public void postProcess() throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onCompleted() {
					resumeVmTaskStatus(instanceUuid);
					
				}
			});
			revertVolumeTransaction.asyncExecute(rpcExtra);
		} catch (Exception e) {
			String error = "revertVolume failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.REVERT_VOLUME, error + e.getMessage(), rpcExtra);
			AlertUtil.alert("卷" + volumeUuid + "格式化硬盘/系统失败", "输入参数为" + paramInfos, e);
		}
		return null;
	}

	@Override
	public String attachVolume(final String volumeUuid, final String instanceUuid, final RpcExtra rpcExtra)
			throws RpcException {
		String paramInfos = "attachVolume: " + volumeUuid + ", " + instanceUuid;
		logger.debug(paramInfos);

		Transaction transaction = new Transaction(new TTask() {

			@Override
			public boolean preProcess() throws Exception {
				return checkAndSetVmTaskStatus(instanceUuid, TaskStatusEnum.ATTACHING);
			}

			@Override
			public void process() throws Exception {
				VmInstance instance = null;
				VmVolume volumeTmp = null;
				try {
					instance = vmInstanceProxy.getByUuid(instanceUuid, false, false,
							false, false, false, false, false, false);
	
					if (instance == null) {
						logger.error("No instance with UUID " + instanceUuid);
						loller.error(LolLogUtil.ATTACH_VOLUME, "No instance with UUID " + instanceUuid, rpcExtra);
						throw new RpcException("No instance with UUID " + instanceUuid);
					}
					
					volumeTmp = vmVolumeProxy.getByUUID(volumeUuid, false, false, false, false);
					if(volumeTmp == null) {
						logger.error("No volume with UUID " + volumeUuid);
						loller.error(LolLogUtil.ATTACH_VOLUME, "No volume with UUID " + volumeUuid, rpcExtra);
						throw new RpcException("No volume with UUID " + volumeUuid);
					} else {
						logger.info("volume: " + volumeTmp);
					}
				} catch(Exception e) {
					logger.error("connect to db-proxy failed", e);
					loller.error(LolLogUtil.ATTACH_VOLUME, "connect to db-proxy failed " + e.getMessage(), rpcExtra);
					throw new Exception("connect to db-proxy failed"+e.getMessage());
				}
				
				try {
					if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
						vmScheduler.stopVM(instanceUuid, rpcExtra);
						logger.debug("attachVolume.vmScheduler.stopVM succeed!");
						Thread.sleep(1000);
					}
					
					final VmVolume volume = volumeTmp;
					vmScheduler.attachVolume(instanceUuid, volume, rpcExtra);
					logger.info("attachVolume succeed!");
				} catch(Exception e) {
					if(e instanceof RpcTimeoutException) {
						logger.error(" no response from vm-scheduler ");
						loller.error(LolLogUtil.DETACH_VOLUME, " no response from vm-scheduler ", rpcExtra);
						throw new RpcException(" no response from vm-scheduler ");
					} else {
						logger.error(e.getMessage(), e);
						loller.error(LolLogUtil.ATTACH_VOLUME, e.getMessage(), rpcExtra);
						throw new RpcException(e.getMessage());
					}
				}
				
			}

			@Override
			public void postProcess() throws Exception {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
			}

			@Override
			public void onCompleted() {
				resumeVmTaskStatus(instanceUuid);
			}
			
		});
		
		transaction.asyncExecute(rpcExtra);
		
		
		try {
			VmVolume tmpVolume;
			tmpVolume = vmVolumeProxy.getByUUID(volumeUuid, false, false, false, false);
			loller.info(LolLogUtil.ATTACH_VOLUME, "attach volume " + volumeUuid + " to instance with uuid " + 
					instanceUuid +  " on " + tmpVolume.getMountPoint() + " succeed!", rpcExtra);
			
		} catch (Exception e) {
			logger.error("connect to db-proxy failed", e);
			loller.error(LolLogUtil.ATTACH_VOLUME, "connect to db-proxy failed " + e.getMessage(), rpcExtra);
		}
		return "fake mount point";
	}

	@Override
	public void detachVolume(final String volumeUuid, final String instanceUuid, final RpcExtra rpcExtra)
			throws RpcException {
		String paramInfos = "detachVolume: " + volumeUuid + ", " + instanceUuid;
		logger.debug(paramInfos);

		Transaction transaction = new Transaction(new TTask(){
			@Override
			public boolean preProcess() {
				return checkAndSetVmTaskStatus(instanceUuid, TaskStatusEnum.DETACHING);
			}

			@Override
			public void process() throws Exception {
				VmInstance instance = null;
				VmVolume volumeTmp = null;
				try {
					instance = vmInstanceProxy.getByUuid(instanceUuid, false, false,
							false, false, false, false, false, false);
					if (instance == null) {
						logger.error("No instance with UUID " + instanceUuid);
						loller.error(LolLogUtil.DETACH_VOLUME, "No instance with UUID " + instanceUuid, rpcExtra);
						throw new RpcException(" No instance with UUID" + instanceUuid);
					}
					
					volumeTmp = vmVolumeProxy.getByUUID(volumeUuid, false, false,
							false, false);
					if (volumeTmp == null) {
						logger.error("No volume with UUID " + volumeUuid);
						loller.error(LolLogUtil.DETACH_VOLUME, "No volume with UUID " + volumeUuid, rpcExtra);
						throw new RpcException(" No volume with UUID" + volumeUuid);
					} else {
						logger.info("volume: " + volumeTmp);
					}
				} catch (Exception e) {
					logger.error("connect to db-proxy failed", e);
					loller.error(LolLogUtil.DETACH_VOLUME, "connect to db-proxy failed " + e.getMessage(), rpcExtra);
					throw new RpcException("connect to db-proxu failed " + e.getMessage());
				}
				
				try {
					if(instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
						vmScheduler.stopVM(instanceUuid, rpcExtra);
						logger.debug("attachVolume.vmScheduler.stopVM succeed!");
						Thread.sleep(1000);
					}
					
					final VmVolume volume = volumeTmp;
					vmScheduler.detachVolume(instanceUuid, volume, rpcExtra);
					logger.info("detachVolume succeed!");
					loller.info(LolLogUtil.DETACH_VOLUME, "detach volume on instance with uuid " + instanceUuid + " succeed!", rpcExtra);
				} catch(Exception e) {
					if(e instanceof RpcTimeoutException) {
						logger.error("no response from vm-scheduler");
						loller.error(LolLogUtil.DETACH_VOLUME, "no response from vm-scheduler", rpcExtra);
						throw new RpcException( "no response from vm-scheduler ");
					} else {
						logger.error(e.getMessage(), e);
						loller.error(LolLogUtil.DETACH_VOLUME, e.getMessage(), rpcExtra);
						throw new RpcException(e.getMessage());
					}
				}
			}
				
			public void onCompleted() {
				resumeVmTaskStatus(instanceUuid);
			}

			@Override
			public void postProcess() throws Exception {
				
			}

			@Override
			public void onError() {
				
			}
		});
		
		transaction.asyncExecute(rpcExtra);
	}

	@Override
	public void updateVolume(final String volumeUuid, final String displayName,
			final String discription, final HashMap<String, String> metadata, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "updateVolume: " + volumeUuid + ", " + displayName
				+ ", " + discription;
		logger.debug(paramInfos);

		Transaction updateVolumeTransaction = new Transaction(new TTask() {
			
			@Override
			public void process() throws Exception {
				try {
					VmVolume vmVolume = vmVolumeProxy.getByUUID(volumeUuid, false,
							false, false, false);
					vmVolume.setDisplayName(displayName);
					vmVolume.setDisplayDescription(discription);
					if(metadata != null && metadata.containsKey("userId"))
						vmVolume.setUserId(new Integer(metadata.get("userId")));
					
					vmVolumeProxy.update(vmVolume);
				} catch (Exception e) {
					String error = "connect to db-proxy failed, so updateVolume failed! " + paramInfos;
					logger.error(error, e);
					loller.error(LolLogUtil.UPDATE_VOLUME, error+e.getMessage(), rpcExtra);
					AlertUtil.alert("卷" + volumeUuid + "更新失败", "输入参数为" + paramInfos, e);
					throw new RpcException(error);
				}
				
			}
			
			@Override
			public boolean preProcess() throws Exception {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public void postProcess() throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				
			}
		});
		updateVolumeTransaction.asyncExecute(rpcExtra);
	}

	@Override
	public String createSnapshot(String displayName, String displayDescription,
			final String volumeId, Boolean force, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "createSnapshot: " + displayName + ", "
				+ displayDescription + ", " + volumeId + ", " + force;
		logger.debug(paramInfos);

		String snapshotUuid = null;
		try {
			VmVolume volume = vmVolumeProxy.getByUUID(volumeId, false, false,
					false, false);
			if (volume == null) {
				logger.error("No volume with UUID " + volumeId);
				loller.error(LolLogUtil.CREATE_SNAPSHOT, "No volume with UUID " + volumeId, rpcExtra);
				return null;
			}
			
			final String vmInstaceUuid = volume.getInstanceUuid();
			if(vmInstaceUuid == null) {
				logger.error("vmInstanceUuid is null from volume " + volume.getVolumeUuid());
				loller.error(LolLogUtil.CREATE_SNAPSHOT,"vmInstanceUuid is null from volume " + volume.getVolumeUuid(), rpcExtra);
				return null;
			}
			final VmInstance instance = vmInstanceProxy.getByUuid(vmInstaceUuid, false, false,
					false, false, false, false, false, false);
			if (instance == null || !force) {
				logger.error("No instance with UUID " + vmInstaceUuid);
				loller.error(LolLogUtil.CREATE_SNAPSHOT, "No instance with UUID " + vmInstaceUuid, rpcExtra);
				return null;
			}

			final VmSnapshot vmSnapshot = volumeScheduler.defineSnapshot(volumeId,
					displayName, displayDescription, rpcExtra);
			logger.info("defineSnapshot: " + vmSnapshot);
			snapshotUuid = vmSnapshot.getUuid();
			
			Transaction transaction = new Transaction(new TTask(){

				@Override
				public boolean preProcess() {
					return checkAndSetVmTaskStatus(vmInstaceUuid, TaskStatusEnum.REBOOTING);
				}

				@Override
				public void process() throws Exception {
					try {
						if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
							vmScheduler.stopVM(vmInstaceUuid, rpcExtra);
							logger.debug("createSnapshot.vmScheduler.stopVM succeed!");
							Thread.sleep(1000);
						}
						
						volumeScheduler.createSnapshot(volumeId, vmSnapshot.getId(), rpcExtra);
					} catch (Exception e) {
						if (e instanceof RpcTimeoutException) {
							String why = "no response from vm-scheduler OR volume-scheduler ";
							logger.error(why);
							loller.error(LolLogUtil.CREATE_SNAPSHOT, why, rpcExtra);
							throw new RpcException(why);
						} 
						String error = "createSnapshot failed! " + paramInfos;
						logger.error(error, e);
						loller.error(LolLogUtil.CREATE_SNAPSHOT, error+e.getMessage(), rpcExtra);
						AlertUtil.alert("卷" + volumeId + "创建快照" + vmSnapshot.getUuid() + "失败", "输入参数为" + paramInfos, e);
						throw new RpcException(error + e.getMessage());
					}
				}

				@Override
				public void onCompleted() {
					if (vmInstaceUuid != null) {
//						VMSEC ret = vmScheduler.startVM(instance);
//						if (ret.compareTo(VMSEC.SUCCESS) == 0){
//							logger.debug("createBackUp.vmScheduler.startVM succeed!");
//						}
//						else{
//							logger.error("createBackUp.vmScheduler.startVM failed!");
//						}
						
						resumeVmTaskStatus(vmInstaceUuid);
					}
				}

				@Override
				public void postProcess() throws Exception {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError() {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			transaction.asyncExecute(rpcExtra);
		} catch (Exception e) {
			if (e instanceof RpcTimeoutException) {
				String why = "no response from vm-scheduler OR volume-scheduler ";
				logger.error(why);
				loller.error(LolLogUtil.CREATE_SNAPSHOT, why, rpcExtra);
			} else 	if (e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
				String why = " connect to db-proxy failed!";
				logger.error(why, e);
				loller.error(LolLogUtil.CREATE_SNAPSHOT, why + e.getMessage(), rpcExtra);
			} else {
				String error = "createSnapshot failed! " + paramInfos;
				logger.error(error, e);
				loller.error(LolLogUtil.CREATE_SNAPSHOT, error+e.getMessage(), rpcExtra);
				AlertUtil.alert("卷" + volumeId + "创建快照" + snapshotUuid + "失败", "输入参数为" + paramInfos, e);
			}
//			throw new RpcException(error);
		}

		return snapshotUuid;
	}

	@Override
	public void deleteSnapshot(final String snapshotUuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "deleteSnapshot: " + snapshotUuid;
		logger.debug(paramInfos);

		Transaction delSnaTransaction = new Transaction(new TTask() {
			
			@Override
			public void process() throws Exception {
				try {
					VmSnapshot vmSnapshot = vmSnapshotProxy.getByUuid(snapshotUuid,
							true);
					if (vmSnapshot != null) {
						volumeScheduler.deleteSnapshot(vmSnapshot.getVolumeUuid(), vmSnapshot.getId(), rpcExtra);
					} else {
						logger.error("No snapshot with " + snapshotUuid);
						return;
					}
				} catch (Exception e) {
					if (e instanceof RpcTimeoutException) {
						String why = "no response from volume-sechduler! ";
						logger.error(why);
						loller.error(LolLogUtil.DELETE_SNAPSHOT, why, rpcExtra);
						throw new RpcException(why);
					}
					String error = "deleteSnapshot failed! " + paramInfos;
					logger.error(error, e);
					loller.error(LolLogUtil.DELETE_SNAPSHOT, error+e.getMessage(), rpcExtra);
					AlertUtil.alert("快照" + snapshotUuid + "删除失败", "输入参数为" + paramInfos, e);
					throw new RpcException(error);
				}
				
			}
			
			@Override
			public boolean preProcess() throws Exception {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public void postProcess() throws Exception {
				
				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				
			}
		});
		delSnaTransaction.asyncExecute(rpcExtra);
	}

	@Override
	public void revertSnapshot(String snapshotUuid, final RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "revertSnapshot: " + snapshotUuid;
		logger.debug(paramInfos);

		try {
			final VmSnapshot vmSnapshot = vmSnapshotProxy.getByUuid(snapshotUuid, true);
			if (vmSnapshot == null || vmSnapshot.getVmVolume() == null) {
				logger.error("No vmSnapshot with UUID " + snapshotUuid + " OR vmSnapshot's vmVolume is null");
				loller.error(LolLogUtil.REVERT_SNAPSHOT, "No vmSnapshot with UUID " + snapshotUuid + " OR vmSnapshot's vmVolume is null", rpcExtra);
				return;
			}

			final String vmInstaceUuid = vmSnapshot.getVmVolume().getInstanceUuid();
			if(vmInstaceUuid == null) {
				logger.error("vmInstanceUuid is null");
				loller.error(LolLogUtil.REVERT_SNAPSHOT, "vmInstanceUuid is null", rpcExtra);
				return;
			}
			final VmInstance instance = vmInstanceProxy.getByUuid(vmInstaceUuid, false, false,
					false, false, false, false, false, false);
			if (instance == null) {
				logger.error("No instance with UUID " + vmInstaceUuid);
				loller.error(LolLogUtil.REVERT_SNAPSHOT, "No instance with UUID " + vmInstaceUuid, rpcExtra);
				return;
			}

			Transaction transaction = new Transaction(new TTask(){
				@Override
				public boolean preProcess() {
					return checkAndSetVmTaskStatus(vmInstaceUuid, TaskStatusEnum.REBOOTING);
				}

				@Override
				public void process() throws Exception {
					try {
						if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
							vmScheduler.stopVM(vmInstaceUuid, rpcExtra);
							logger.debug("revertSnapshot.vmScheduler.stopVM succeed!");
							Thread.sleep(1000);
						}
						
						volumeScheduler.applySnapshot(vmSnapshot.getVmVolume()
								.getVolumeUuid(), vmSnapshot.getId(), rpcExtra);
					} catch (Exception e) {
						if (e instanceof RpcTimeoutException) {
							String why = " no response from vm-scheduler or volume-scheduler";
							logger.error(why);
							loller.error(LolLogUtil.REVERT_SNAPSHOT, why, rpcExtra);
							throw new RpcException(why);
						}
						logger.error("volumeScheduler.applySnapshot failed!", e);
						loller.error(LolLogUtil.REVERT_SNAPSHOT, "volumeScheduler.applySnapshot failed!" + e.getMessage(), rpcExtra);
						AlertUtil.alert("快照" + vmSnapshot.getUuid() + "回滚失败", e);
						throw new RpcException("volumeScheduler.applySnapshot failed!");
					}
				}

				@Override
				public void onCompleted() {
					if (vmInstaceUuid != null) {
//						VMSEC ret = vmScheduler.startVM(instance);
//						if (ret.compareTo(VMSEC.SUCCESS) == 0){
//							logger.debug("revertSnapshot.vmScheduler.startVM succeed!");
//						}
//						else{
//							logger.error("revertSnapshot.vmScheduler.startVM failed!");
//						}
						
						resumeVmTaskStatus(vmInstaceUuid);
					}
				}

				@Override
				public void postProcess() throws Exception {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError() {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			transaction.asyncExecute(rpcExtra);

		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
				String why = " connect to db-proxy failed!";
				logger.error(why, e);
				loller.error(LolLogUtil.REVERT_SNAPSHOT, why + e.getMessage(), rpcExtra);
				return;
			} 
			String error = "revertSnapshot failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.REVERT_SNAPSHOT, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("快照" + snapshotUuid + "回滚失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		} 
	}

	@Override
	public void updateSnapshot(final String snapshotUuid, final String displayName,
			final String discription, final Integer userId, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "updateSnapshot: " + snapshotUuid + ", "
				+ displayName + ", " + discription;
		logger.debug(paramInfos);
		
		Transaction updateSnaTransaction = new Transaction(new TTask() {
			
			@Override
			public void process() throws Exception {
				try {
					VmSnapshot vmSnapshot = vmSnapshotProxy.getByUuid(snapshotUuid,
							false);
					vmSnapshot.setDisplayName(displayName);
					vmSnapshot.setDisplayDescription(discription);
					if(userId != null) 
						vmSnapshot.setUserId(userId);
					vmSnapshotProxy.update(vmSnapshot);
				} catch (Exception e) {
					if (e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
						String why = " connect to db-proxy failed!";
						logger.error(why, e);
						loller.error(LolLogUtil.UPDATE_SNAPSHOT, why + e.getMessage(), rpcExtra);
						throw new RpcException(why);
					} 
					String error = "updateSnapshot failed! " + paramInfos;
					logger.error(error, e);
					loller.error(LolLogUtil.UPDATE_SNAPSHOT, error+e.getMessage(), rpcExtra);
					AlertUtil.alert("快照" + snapshotUuid + "更新失败", "输入参数为" + paramInfos, e);
					throw new RpcException(error);
				}
				
			}
			
			@Override
			public boolean preProcess() throws Exception {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public void postProcess() throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				
			}
		});
		updateSnaTransaction.asyncExecute(rpcExtra);
	}

	@Override
	public String createBackUp(String displayName, String displayDescription,
			final String volumeUuid, Boolean force, final RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "createBackUp: " + displayName + ", "
				+ displayDescription + ", " + volumeUuid + ", " + force;
		logger.debug(paramInfos);

		String backupUuid = null;
		try {
			VmVolume vmVolume = vmVolumeProxy.getByUUID(volumeUuid, false, false, true, false);
			
			if (vmVolume == null){
				logger.error("No volume with UUID " + volumeUuid);
				loller.error(LolLogUtil.CREATE_BACKUP, "No volume with UUID " + volumeUuid, rpcExtra);
				return null;
			}
			//logger.debug("vmVolume: " + vmVolume);
			
			final String vmInstaceUuid = vmVolume.getInstanceUuid();
			if(vmInstaceUuid == null) {
				logger.error("vmInstanceUuid is null");
				loller.error(LolLogUtil.CREATE_BACKUP, "vmInstanceUuid is null", rpcExtra);
				return null;
			}
			final VmInstance instance = vmInstanceProxy.getByUuid(vmInstaceUuid, false, false,
					false, false, false, false, false, false);
			if (instance == null) {
				logger.error("No instance with UUID " + vmInstaceUuid);
				loller.error(LolLogUtil.CREATE_BACKUP, "No instance with UUID " + vmInstaceUuid, rpcExtra);
				return null;
			}
			
			final VmVolume backup = volumeScheduler.defineVolume(vmVolume.getUsageType(), vmVolume.getUserId(), vmVolume.getSize(), vmVolume.getZone(),
					null, null, rpcExtra);
			logger.debug("backup: " + backup);
			backupUuid = backup.getVolumeUuid();
			VmVolume savedBackup = vmVolumeProxy.getByUUID(
					backup.getVolumeUuid(), false, false, false, false);
			savedBackup.setAvailabilityClusterId(vmVolume.getAvailabilityClusterId());;
			savedBackup.setDisplayName(displayName);
			savedBackup.setDisplayDescription(displayDescription);
			savedBackup.setInstanceUuid(vmInstaceUuid);
			vmVolumeProxy.update(savedBackup);
			
			
			Transaction transaction = new Transaction(new TTask(){
				@Override
				public boolean preProcess() {
					return checkAndSetVmTaskStatus(vmInstaceUuid, TaskStatusEnum.REBOOTING);
				}

				@Override
				public void process() throws Exception {
					try {
						if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
							vmScheduler.stopVM(vmInstaceUuid, rpcExtra);
							logger.debug("createBackUp.vmScheduler.stopVM succeed!");
							Thread.sleep(1000);
						}
						
						volumeScheduler.cloneVolume(volumeUuid, backup.getVolumeUuid(), rpcExtra);
						logger.debug("cloneVolume: " + volumeUuid + " succeed");
					} catch (Exception e) {
						if (e instanceof RpcTimeoutException) {
							String why = " no response from vm-scheduler OR volume-scheduler! ";
							logger.error(why);
							loller.error(LolLogUtil.CREATE_BACKUP, why, rpcExtra);
							throw new RpcException(why);
						} 
						logger.error("createBackUp volumeScheduler.cloneVolume failed! volumeUuid: " + volumeUuid, e);
						loller.error(LolLogUtil.CREATE_BACKUP, "createBackUp volumeScheduler.cloneVolume failed! volumeUuid: " + volumeUuid + e.getMessage(), rpcExtra);
						AlertUtil.alert("卷" + volumeUuid + "创建备份" + backup.getVolumeUuid() + "失败", e);
						throw new RpcException("createBackUp volumeScheduler.cloneVolume failed! volumeUuid: " + volumeUuid + e.getMessage());
					}
				}

				@Override
				public void onCompleted() {
					if (vmInstaceUuid != null) {
						resumeVmTaskStatus(vmInstaceUuid);
					}
				}

				@Override
				public void postProcess() throws Exception {
					VmVolume savedBackup = vmVolumeProxy.getByUUID(backup.getVolumeUuid(), true, false, false, false);
//					updateVmHostUsed(savedBackup, LolLogUtil.CREATE_BACKUP, rpcExtra);
				}

				@Override
				public void onError() {
					
				}
				
			});

			transaction.asyncExecute(rpcExtra);
		} catch (Exception e) {
			if (e instanceof RpcTimeoutException) {
				String why = " no response from vm-scheduler OR volume-scheduler! ";
				logger.error(why);
				loller.error(LolLogUtil.CREATE_BACKUP, why, rpcExtra);
			} else if(e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
				String why = " connect to db-proxy failed!";
				logger.error(why, e);
				loller.error(LolLogUtil.CREATE_BACKUP, why + e.getMessage(), rpcExtra);
			} else {
				logger.error("createBackUp volumeScheduler.cloneVolume failed! volumeUuid: " + volumeUuid, e);
				loller.error(LolLogUtil.CREATE_BACKUP, "createBackUp volumeScheduler.cloneVolume failed! volumeUuid: " + volumeUuid + e.getMessage(), rpcExtra);
				AlertUtil.alert("卷" + volumeUuid + "创建备份" + backupUuid + "失败", e);
			}
			return null;
		}
		
		logger.info("BackupUuid: " + backupUuid);
		return backupUuid;
	}
	
	@Override
	public void deleteBackUp(final String backUpUuid, final RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "deleteBackUp: " + backUpUuid;
		logger.debug(paramInfos);
		try {
			Transaction delBackUpTransaction = new Transaction(new TTask() {
				VmVolume backup;
				@Override
				public void process() throws Exception {
					volumeScheduler.destroyVolume(backUpUuid, rpcExtra);
				}
				
				@Override
				public boolean preProcess() throws Exception {
					backup = vmVolumeProxy.getByUUID(backUpUuid, true, false, false, false);
					return true;
				}
				
				@Override
				public void postProcess() throws Exception {
//					updateVmHostUsed(backup, LolLogUtil.DELETE_BACKUP, rpcExtra);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onCompleted() {
					// TODO Auto-generated method stub
					
				}
			});
			//此种方法可以将异常集中处理
			TFuture future = delBackUpTransaction.asyncExecute(rpcExtra);
			if (future.getFuture().get() != null && future.getException() != null) {
				throw future.getException();
			}
		} catch (Exception e) {
			if (e instanceof RpcTimeoutException) {
				String why = " no response from volume-scheduler ";
				logger.error(why);
				loller.error(LolLogUtil.DELETE_BACKUP, why, rpcExtra);
			} else {
				String error = "deleteBackUp failed! " + paramInfos;
				logger.error(error, e);
				loller.error(LolLogUtil.DELETE_BACKUP, error+e.getMessage(), rpcExtra);
				AlertUtil.alert("备份" + backUpUuid + "删除失败", "输入参数为" + paramInfos, e);
			}
		}
	}

	@Override
	public void revertBackUp(final String BackUpUuid, final RpcExtra rpcExtra) throws RpcException {
		String paramInfos = "revertBackUp: " + BackUpUuid;
		logger.debug(paramInfos);

		try {
			final VmVolume backupVolume = vmVolumeProxy.getByUUID(BackUpUuid, false, false, false, false);
			if (backupVolume == null){
				logger.error("No backupVolume with UUID " + BackUpUuid);
				loller.error(LolLogUtil.REVERT_BACKUP, "No backupVolume with UUID " + BackUpUuid, rpcExtra);
				return;
			}
			if(backupVolume.getInstanceUuid() == null) {
				logger.error("instance UUID is null");
				loller.error(LolLogUtil.REVERT_BACKUP, "instance UUID is null", rpcExtra);
				return;				
			}
			
			QueryObject<VmVolume> originvolumeQO = new QueryObject<VmVolume>();
			originvolumeQO.addFilterBean(new FilterBean<VmVolume>(
					"instanceUuid", backupVolume.getInstanceUuid(),
					FilterBeanType.EQUAL));
			originvolumeQO.addFilterBean(new FilterBean<VmVolume>(
					"usageType",  VmVolumeUsageTypeEnum.SYSTEM,
					FilterBeanType.EQUAL));
			originvolumeQO.addFilterBean(new FilterBean<VmVolume>(
					"attachStatus",  VmVolumeAttachStatusEnum.ATTACHED,
					FilterBeanType.EQUAL));
			List<? extends VmVolume> originVolomes = vmVolumeProxy.searchAll(originvolumeQO, false, false, false, false);
			if(originVolomes == null || originVolomes.isEmpty()) {
				logger.error("can not find originVolumes");
				loller.error(LolLogUtil.REVERT_BACKUP, "can not find originVolumes", rpcExtra);
				return;		
			}
			final VmVolume originVolume = originVolomes.get(0);
			
			if(originVolume == null) {
				logger.error("can not find origin volume for backup volume: " + backupVolume.getBackupVolumeUuid());
				loller.error(LolLogUtil.REVERT_BACKUP, "can not find origin volome for backup volume", rpcExtra);
				return;
			}
			logger.info("originVolume:"+originVolume);
			
			final String vmInstaceUuid = backupVolume.getInstanceUuid();
			if(vmInstaceUuid == null) {
				logger.error("vmInstanceUuid of backupVolume is null");
				loller.error(LolLogUtil.REVERT_BACKUP, "vmInstanceUuid of backupVolume is null", rpcExtra);
				return;
			}
			final VmInstance instance = vmInstanceProxy.getByUuid(vmInstaceUuid, false, false,
					false, false, false, false, false, false);
			if (instance == null) {
				logger.debug("No instance with uuid " + vmInstaceUuid);
				loller.error(LolLogUtil.REVERT_BACKUP, "No instance with uuid " + vmInstaceUuid, rpcExtra);
				return;
			}
			
			Transaction transaction = new Transaction(new TTask(){

				@Override
				public boolean preProcess() {
					return checkAndSetVmTaskStatus(vmInstaceUuid, TaskStatusEnum.REBOOTING);
				}

				@Override
				public void process() throws Exception {
					try{
						if (instance.getVmStatus().compareTo(VmStatusEnum.STOPPED) != 0){
							vmScheduler.stopVM(vmInstaceUuid, rpcExtra);
							logger.debug("revertBackUp.vmScheduler.stopVM succeed!");
							Thread.sleep(1000);
						}

						try{
							vmScheduler.detachVolume(vmInstaceUuid, originVolume, rpcExtra);
							vmScheduler.attachVolume(vmInstaceUuid, backupVolume, rpcExtra);
							//如果有多个备份，这样删就有问题了，先注释掉
//							volumeScheduler.destroyVolume(originVolume.getVolumeUuid(), rpcExtra);
						}
						catch (Exception e) {
							vmScheduler.attachVolume(vmInstaceUuid, originVolume, rpcExtra);
							vmScheduler.detachVolume(vmInstaceUuid, backupVolume, rpcExtra);
							if (e instanceof RpcTimeoutException) {
								String why = " no response from vm-scheduler ";
								logger.error(why);
								loller.error(LolLogUtil.REVERT_BACKUP, why, rpcExtra);
								throw new RpcTimeoutException(why);
							}
							logger.error(e.getMessage());
							loller.error(LolLogUtil.REVERT_BACKUP, e.getLocalizedMessage(), rpcExtra);
							throw new RpcException(e);
						} finally {
							logger.info(originVolume.getAttachStatus().toString());
							if (originVolume.getAttachStatus().toString().equals(VmVolumeAttachStatusEnum.DETACHED) ||
									originVolume.getAttachStatus() == VmVolumeAttachStatusEnum.DETACHED) {
								volumeScheduler.destroyVolume(originVolume.getVolumeUuid(), rpcExtra);
							}
						}
					} catch (Exception e) {
						if (e instanceof RpcTimeoutException) {
							String why = "no response from volume-scheduler ";
							logger.error(why);
							loller.error(LolLogUtil.REVERT_BACKUP, why, rpcExtra);
							throw new RpcException(why);
						}
						String error = "revert from backup failed, attach the old volume to instance with uuid "
								+ vmInstaceUuid;
						logger.error(error, e);
						loller.error(LolLogUtil.REVERT_BACKUP, error, rpcExtra);
						AlertUtil.alert("备份" + BackUpUuid + "回滚失败", e);
						throw new RpcException(error);
					}
				}

				@Override
				public void onCompleted() {
					if (vmInstaceUuid != null) {
//						VMSEC ret = vmScheduler.startVM(instance);
//						if (ret.compareTo(VMSEC.SUCCESS) == 0){
//							logger.debug("createBackUp.vmScheduler.startVM succeed!");
//						}
//						else{
//							logger.error("createBackUp.vmScheduler.startVM failed!");
//						}
						
						resumeVmTaskStatus(vmInstaceUuid);
					}
				}

				@Override
				public void postProcess() throws Exception {
					
				}

				@Override
				public void onError() {
					
				}
				
			});
			
			transaction.asyncExecute(rpcExtra);
			
		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().startsWith(("TIP Client"))) {
				String why = " connect to db-proxy failed!";
				logger.error(why, e);
				loller.error(LolLogUtil.REVERT_BACKUP, why + e.getMessage(), rpcExtra);
				return;
			} 
			String error = "revertBackUp failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.REVERT_BACKUP, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("备份" + BackUpUuid + "回滚失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
	}

	@Override
	public void updateBackUp(final String BackUpUuid, final String displayName,
			final String discription, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "updateBackUp: " + BackUpUuid + ", " + displayName
				+ ", " + discription;
		logger.debug(paramInfos);
		if (BackUpUuid == null){
			logger.error("backup uuid is null");
			loller.error(LolLogUtil.UPDATE_BACKUP, "backup uuid is null", rpcExtra);
			return;
		}
		try {
			Transaction updateBackUpTransaction = new Transaction(new TTask() {
				
				@Override
				public void process() throws Exception {
					try {
						VmVolume vmVolume = vmVolumeProxy.getByUUID(BackUpUuid, false,
								false, false, false);
						vmVolume.setDisplayName(displayName);
						vmVolume.setDisplayDescription(discription);
						vmVolumeProxy.update(vmVolume);
					} catch (Exception e) {
						String why = "connect to db-proxy failed";
						logger.error(why, e);
						loller.error(LolLogUtil.UPDATE_BACKUP, why + e.getMessage(), rpcExtra);
						throw new RpcException(why);
					}
					
				}
				
				@Override
				public boolean preProcess() throws Exception {
					// TODO Auto-generated method stub
					return true;
				}
				
				@Override
				public void postProcess() throws Exception {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onCompleted() {
					// TODO Auto-generated method stub
					
				}
			});
			updateBackUpTransaction.asyncExecute(rpcExtra);
		} catch (Exception e) {
			String error = "updateBackUp failed! " + paramInfos;
			logger.error(error, e);
			loller.error(LolLogUtil.UPDATE_BACKUP, error+e.getMessage(), rpcExtra);
			AlertUtil.alert("备份" + BackUpUuid + "更新失败", "输入参数为" + paramInfos, e);
//			throw new RpcException(error);
		}
		
	}
	
	@Override
	public void deleteVolume(final String volumeUuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "deleteVolume: " + volumeUuid ;
		logger.debug(paramInfos);

		Transaction delVolTransaction = new Transaction(new TTask() {
			VmVolume volume;
			@Override
			public void process() throws Exception {
				try {
					if (volumeUuid != null){
						VmVolume vmVolume = vmVolumeProxy.getByUUID(volumeUuid, false,
								false, false, false);
						if (vmVolume == null){
							logger.error("No volume with UUID " + volumeUuid);
							loller.error(LolLogUtil.DELETE_VOLUME, "No volume with UUID " + volumeUuid, rpcExtra);
							return;
						}
					}
						
					volumeScheduler.destroyVolume(volumeUuid, rpcExtra);
						
					QueryObject<VmSnapshot> snapshotQO = new QueryObject<VmSnapshot>();
					snapshotQO.addFilterBean(new FilterBean<VmSnapshot>("volumeUuid", volumeUuid,FilterBeanType.EQUAL));
					List<? extends VmSnapshot> vmSnapshots = vmSnapshotProxy
								.searchAll(snapshotQO, false);
					for (VmSnapshot vmSnapshot : vmSnapshots) {
						volumeScheduler.deleteSnapshot(
									vmSnapshot.getVolumeUuid(),
									vmSnapshot.getId(), rpcExtra);

					}
						
					List<? extends VmVolume>  vmVolumeList =vmVolumeProxy.getBySrcBackupUUID(volumeUuid, false,
								false, false, false);
					for(VmVolume at:vmVolumeList) {
						volumeScheduler.destroyVolume(at.getVolumeUuid(), rpcExtra);
						logger.debug("delete backup:" + at.getVolumeUuid());
					}  
				}catch (Exception e) {
					if (e instanceof RpcTimeoutException) {
						String why = " no response from volume-scheduler! ";
						logger.error(why);
						loller.error(LolLogUtil.DELETE_VOLUME, why, rpcExtra);
						throw new RpcException(why);
					} else if(e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
						String why = " connect to db-proxy failed!";
						logger.error(why, e);
						loller.error(LolLogUtil.DELETE_VOLUME, why + e.getMessage(), rpcExtra);
						throw new RpcException(why+e.getMessage());
					} else {
						String error = "deleteVolume failed! " + paramInfos;
						logger.error(error, e);
						loller.error(LolLogUtil.DELETE_VOLUME, error+e.getMessage(), rpcExtra);
						AlertUtil.alert("卷" + volumeUuid + "删除失败", "输入参数为" + paramInfos, e);
						throw new Exception(error+e);
					}
				}
			}
				
			@Override
			public boolean preProcess() throws Exception {
					if(volumeUuid != null) 
						volume = vmVolumeProxy.getByUUID(volumeUuid, true, false, false, false);
					return true;
				}
				
				@Override
				public void postProcess() throws Exception {
//					updateVmHostUsed(volume, LolLogUtil.DELETE_VOLUME, rpcExtra);
				}
				
				@Override
				public void onError() {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onCompleted() {
					// TODO Auto-generated method stub
				}
			});
		delVolTransaction.asyncExecute(rpcExtra);
	}

	@Override
	public void forceDeleteVolume(final String volumeUuid, final RpcExtra rpcExtra) throws RpcException {
		final String paramInfos = "forceDeleteVolume: " + volumeUuid;
		logger.debug(paramInfos);

		Transaction forceDelTransaction = new Transaction(new TTask() {

			@Override
			public void process() throws Exception {
				try {
					if (volumeUuid != null) {
						VmVolume vmVolume = vmVolumeProxy.getByUUID(volumeUuid,
								false, false, false, false);
						if (vmVolume == null) {
							logger.error("No vmVolume with uuid " + volumeUuid);
							return;
						}

						volumeScheduler.destroyVolume(volumeUuid, rpcExtra);

						QueryObject<VmSnapshot> snapshotQO = new QueryObject<VmSnapshot>();
						snapshotQO.addFilterBean(new FilterBean<VmSnapshot>("volumeUuid",volumeUuid, FilterBeanType.EQUAL));
						List<? extends VmSnapshot> vmSnapshots = vmSnapshotProxy
								.searchAll(snapshotQO, false);
						for (VmSnapshot vmSnapshot : vmSnapshots) {
							volumeScheduler.deleteSnapshot(
									vmSnapshot.getVolumeUuid(),
									vmSnapshot.getId(), rpcExtra);

						}

						// if (vmVolume.getBackupVolumeUuid() != null) {
						// volumeScheduler.deleteVolume(vmVolume
						// .getBackupVolumeUuid());
						// }
						List<? extends VmVolume> vmVolumeList = vmVolumeProxy
								.getBySrcBackupUUID(volumeUuid, false, false,
										false, false);
						for (VmVolume at : vmVolumeList) {
							volumeScheduler.destroyVolume(at.getVolumeUuid(),
									rpcExtra);
							logger.debug("delete backup:" + at.getVolumeUuid());
						}
					}
				} catch (Exception e) {
					if (e instanceof RpcTimeoutException) {
						String why = " no response from volume-scheduler! ";
						logger.error(why);
						loller.error(LolLogUtil.FORCE_DELETE_VM, why, rpcExtra);
						throw new RpcTimeoutException(why);
					} else if (e.getMessage() != null && e.getMessage().startsWith("TIP Client")) {
						String why = " connect to db-proxy failed!";
						logger.error(why, e);
						loller.error(LolLogUtil.FORCE_DELETE_VM, why + e.getMessage(), rpcExtra);
						throw new RpcException(why + e.getMessage());
					} else {
						String error = "forceDeleteVolume failed! "	+ paramInfos;
						logger.error(error, e);
						loller.error(LolLogUtil.FORCE_DELETE_VM, error + e.getMessage(), rpcExtra);
						AlertUtil.alert("卷" + volumeUuid + "强制删除失败", "输入参数为" + paramInfos, e);
						throw new RpcException(error);
					}
				}

			}

			@Override
			public boolean preProcess() throws Exception {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void postProcess() throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub

			}
		});
		forceDelTransaction.asyncExecute(rpcExtra);
	}
	
	private boolean checkAndSetVmTaskStatus(String uuid, TaskStatusEnum status){
		try{
			VmInstance instance = vmInstanceProxy.getByUuid(
					uuid, false, false, false, false, false,
					false, false, false);
			
			if (instance == null || instance.getTaskStatus().compareTo(TaskStatusEnum.READY) != 0) {
				logger.error("No instance with UUID " + uuid + " OR instance is not ready");
				return false;
			} else {
				logger.info("instance: " + instance);
				instance.setTaskStatus(status);
				vmInstanceProxy.update(instance);
				return true;
			}
		}
		catch (Exception e){
			logger.error("checkAndSetVmTaskStatus failed uuid:" + uuid, e);
			return false;
		}
	}
	
	private void resumeVmTaskStatus(String uuid){
		try{
			VmInstance instance = vmInstanceProxy.getByUuid(
					uuid, false, false, false, false, false,
					false, false, false);
			
			if (instance == null) {
				logger.error("No instance with UUID " + uuid);
				return;
			} else {
				logger.info("instance: " + instance);
				instance.setTaskStatus(TaskStatusEnum.READY);
				vmInstanceProxy.update(instance);
				return;
			}
		}
		catch (Exception e){
			AlertUtil.alert("虚拟机" + uuid +"TaskStatus恢复失败！", e);
			return;
		}
	}
	
	private void updateVmHostUsed(VmVolume volume, String operation, RpcExtra rpcExtra) throws RpcException {
		try {
			if(volume == null) {
				logger.error("volume is null");
				loller.error(operation, "update resource used failed, volume is null", rpcExtra);
				return;
			}
			String hostUuid = volume.getHostUuid();
			VmHostUsed vmHostUsed = vmHostUsedProxy.getByHostUuid(hostUuid);
			
			if(operation.equals(LolLogUtil.CREATE_VOLUME) || operation.endsWith(LolLogUtil.CREATE_BACKUP)) {
				vmHostUsed.setDiskUsed(vmHostUsed.getDiskUsed() + volume.getSize());
				vmHostUsed.setTime(new Timestamp(System.currentTimeMillis()));
			} else if(operation.equals(LolLogUtil.DELETE_VM) || operation.equals(LolLogUtil.DELETE_BACKUP)) {
				vmHostUsed.setCpuUsed(vmHostUsed.getDiskUsed() - volume.getSize());
				vmHostUsed.setTime(new Timestamp(System.currentTimeMillis()));
			} 
			vmHostUsedProxy.update(vmHostUsed);
		} catch(Exception e) {
			loller.error(operation, "update vm_host_used table failed, caused by " + e.getMessage());
			throw new RpcException("update vm_host_used table failed, caused by " + e.getMessage());
		}
	}

	@Override
	public String KeepAlive() throws Exception {
//		try{
			return volumeScheduler.KeepAlive();
//		}
//		catch(RpcException rpcEx) {
//			return "fail";
//		}
	}

	
}
