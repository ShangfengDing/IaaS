package appcloud.api.checkutils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import appcloud.api.exception.ItemNotFoundException;
import appcloud.api.exception.OperationFailedException;
import appcloud.common.model.VmGroup;
import appcloud.common.model.VmInstance;
import appcloud.common.model.VmInstance.VmStatusEnum;
import appcloud.common.model.VmSnapshot;
import appcloud.common.model.VmSnapshot.VmSnapshotStatusEnum;
import appcloud.common.model.VmUser;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.VmGroupProxy;
import appcloud.common.proxy.VmInstanceProxy;
import appcloud.common.proxy.VmSnapshotProxy;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.Filter.FilterLogic;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.QueryObject;
import appcloud.common.util.query.FilterBean.FilterBeanType;


public class AcGroupChecker {
	private static Logger logger = Logger.getLogger(VolumeChecker.class);
	private static VmGroupProxy groupProxy = (VmGroupProxy) ConnectionFactory.getTipProxy(
			VmGroupProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private static VmVolumeProxy volumeProxy = (VmVolumeProxy) ConnectionFactory.getTipProxy(
			VmVolumeProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private static VmSnapshotProxy snapshotProxy = (VmSnapshotProxy) ConnectionFactory.getTipProxy(
			VmSnapshotProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private static VmInstanceProxy instanceProxy = (VmInstanceProxy) ConnectionFactory.getTipProxy(
			VmInstanceProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS);
	private static VmUserProxy userProxy = (VmUserProxy) ConnectionFactory.getTipProxy(
			VmUserProxy.class, appcloud.common.constant.ConnectionConfigs.DB_PROXY_ADDRESS); 
	
	private static VmUser getUser(String tenantId)  {
		List<VmUser> users = null;
		if(tenantId == null || tenantId.equals("")) {
			logger.info("in getUser : user id is null");
			throw new OperationFailedException("tenantId is null");
		} else {
			try {
				QueryObject<VmUser> query = new QueryObject<VmUser>();
				query.addFilterBean(new FilterBean<VmUser>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
				
				users = (ArrayList<VmUser>)userProxy.searchAll(query);
				
				if(users == null || users.size() == 0) {
					throw new ItemNotFoundException("user does not exist");
				}
			} catch (Exception e) {
				throw new OperationFailedException("get user by id failed");
			}
		}
		return users.get(0);
	}
	
	private static VmGroup getGroup(Integer groupId) {
		VmGroup group = null;
		if(groupId == null || groupId.equals("")) {
			logger.info("in getGroup : group id is null");
			throw new OperationFailedException("group id is null");
		} else {
			try {
				group = groupProxy.getById(groupId);
				if(group == null) {
					throw new ItemNotFoundException("group does not exist");
				}
			} catch (Exception e) {
				throw new OperationFailedException("get group by id failed");
			}
		}
		return group;
	}
	
	public static boolean checkBackupCount(String tenantId) {
		VmUser user = getUser(tenantId);
		VmGroup group = getGroup(user.getGroupId());
		int max = group.getMaxNumberOfBackup();
		
		int count =  0;
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		query.addFilterBean(new FilterBean<VmVolume>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("backupVolumeUuid", "NULL", FilterBeanType.NOTEQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("status",VmVolumeStatusEnum.DELETED , FilterBeanType.NOTEQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("status",VmVolumeStatusEnum.DELETING , FilterBeanType.NOTEQUAL));
		
		List<VmVolume> vmBackups;
		try {
			vmBackups = (List<VmVolume>)volumeProxy.searchAll(query, false, false, false, false);
		} catch (Exception e) {
			throw new OperationFailedException("search user's backup failed");
		}
		if(vmBackups == null || vmBackups.size() == 0) {
			count = 0;
		} else {
			count = vmBackups.size();
		}
		
		if(count >= max) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkDiskCount(String tenantId) {
		VmUser user = getUser(tenantId);
		VmGroup group = getGroup(user.getGroupId());
		int max = group.getMaxNumberOfDisk();
		
		long count =  0;
		QueryObject<VmVolume> query = new QueryObject<VmVolume>();
		query.addFilterBean(new FilterBean<VmVolume>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("usageType", VmVolume.VmVolumeUsageTypeEnum.NETWORK, FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		query.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.DELETING, FilterBeanType.NOTEQUAL));
		
		try {
			count = volumeProxy.countSearch(query);
		} catch (Exception e) {
			throw new OperationFailedException("search user's network disk failed");
		}
		
		if(count >= max) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean checkSnapshotCount(String tenantId) {
		VmUser user = getUser(tenantId);
		VmGroup group = getGroup(user.getGroupId());
		int max = group.getMaxNumberOfSnapshot();
		
		int count =  0;
		QueryObject<VmSnapshot> query = new QueryObject<VmSnapshot>();
		query.addFilterBean(new FilterBean<VmSnapshot>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmSnapshot>("status", VmSnapshotStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		query.addFilterBean(new FilterBean<VmSnapshot>("status", VmSnapshotStatusEnum.DELETING, FilterBeanType.NOTEQUAL));
		
		List<VmSnapshot> vmSnapshots = null;
		try {
			vmSnapshots = (List<VmSnapshot>)snapshotProxy.searchAll(query, false);
		} catch (Exception e) {
			throw new OperationFailedException("search user's snapshot failed");
		}
		if(vmSnapshots == null || vmSnapshots.size() == 0) {
			count = 0;
		} else {
			count = vmSnapshots.size();
		}
		
		if(count >= max) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean checkInstanceCount(String tenantId) {
		VmUser user = getUser(tenantId);
		VmGroup group = getGroup(user.getGroupId());
		int max = group.getMaxNumberOfInstance();
		
		long count =  0;
		QueryObject<VmInstance> query = new QueryObject<VmInstance>();
		query.addFilterBean(new FilterBean<VmInstance>("userId", Integer.valueOf(tenantId), FilterBeanType.EQUAL));
		query.addFilterBean(new FilterBean<VmInstance>("vmStatus", VmStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		
		try {
			count = instanceProxy.countSearch(query);
		} catch(Exception e) {
			throw new OperationFailedException("search instance count failed");
		}
		
		if(count >= max) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean checkPublishImage(String tenantId) {
		VmUser user = getUser(tenantId);
		VmGroup group = getGroup(user.getGroupId());
		Boolean publish_image = group.getPublishiImage();
		return publish_image;
	}
	
	/**
	 * @param tenantId
	 * @param selectedClusters
	 * @return
	 */
	public static boolean checkSelectedCluster(String tenantId, List<Integer> selectedClusters) {
		VmUser user = getUser(tenantId);
		VmGroup group = getGroup(user.getGroupId());
		String allowedClustersString = group.getAvailableClusters();
		
		String[] allowedClustersArray = allowedClustersString.split(",");
		if(selectedClusters.size() > allowedClustersArray.length) {
			return false;
		}
		
		List<Integer> allowedClusters = new ArrayList<Integer>();
		for(String allowedCluster : allowedClustersArray) {
			allowedClusters.add(new Integer(allowedCluster));
		}
		
		boolean check = true;
		for(Integer selectedCluster : selectedClusters) {
			if(!allowedClusters.contains(selectedCluster)) {
				check = false;
				break;
			}
		}
		
		return check;
	}
	
	public static void main(String[] args) {
		System.out.println(checkDiskCount("745"));
	}

}
