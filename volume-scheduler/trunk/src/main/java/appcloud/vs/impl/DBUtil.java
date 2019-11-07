package appcloud.vs.impl;

import appcloud.common.model.*;
import appcloud.common.model.ResourceStrategy.StrategyTypeEnum;
import appcloud.common.model.Service.ServiceStatus;
import appcloud.common.model.VmVolume.VmVolumeStatusEnum;
import appcloud.common.proxy.*;
import appcloud.common.util.ConnectionFactory;
import appcloud.common.util.query.FilterBean;
import appcloud.common.util.query.FilterBean.FilterBeanType;
import appcloud.common.util.query.QueryObject;

import java.sql.Timestamp;
import java.util.List;

public class DBUtil {
	private VmImageBackProxy vmImageBackProxy;
	private VmVolumeProxy vmVolumeProxy;
	private VmSnapshotProxy vmSnapshotProxy;
	private VmImageProxy imageProxy;
	private ServiceProxy serviceProxy;
	private HostLoadProxy hostLoadProxy;
	private ClusterProxy clusterProxy;
	private VmGroupProxy vmGroupProxy;
	private ResourceStrategyProxy resourceStrategyProxy;

	static DBUtil instance = null;
	static public DBUtil getInstance(){
		if(instance == null)
			instance = new DBUtil();
		return instance;
	}

	private DBUtil() {
		this.vmVolumeProxy = (VmVolumeProxy) ConnectionFactory.getDBProxy(VmVolumeProxy.class);
		this.vmSnapshotProxy = (VmSnapshotProxy) ConnectionFactory.getDBProxy(VmSnapshotProxy.class);
		this.imageProxy = (VmImageProxy) ConnectionFactory.getDBProxy(VmImageProxy.class);
		this.serviceProxy = (ServiceProxy) ConnectionFactory.getDBProxy(ServiceProxy.class);
		this.hostLoadProxy = (HostLoadProxy) ConnectionFactory.getDBProxy(HostLoadProxy.class);
		this.clusterProxy = (ClusterProxy) ConnectionFactory.getDBProxy(ClusterProxy.class);
		this.vmGroupProxy = (VmGroupProxy) ConnectionFactory.getDBProxy(VmGroupProxy.class);
		this.resourceStrategyProxy = (ResourceStrategyProxy) ConnectionFactory.getDBProxy(ResourceStrategyProxy.class);
		this.vmImageBackProxy = (VmImageBackProxy) ConnectionFactory.getDBProxy(VmImageBackProxy.class);
	}

	void update(VmVolume volume) throws Exception{
		volume.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		vmVolumeProxy.update(volume);
	}

	void update(VmImageBack imageBack) throws Exception{
		imageBack.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		vmImageBackProxy.update(imageBack);
	}

	void update(VmSnapshot snapshot) throws Exception{
		snapshot.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		vmSnapshotProxy.update(snapshot);
	}

	void update(VmImage image) throws Exception{
		image.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
		imageProxy.update(image);
	}

	VmVolume getVolume(String volumeUUID) throws Exception{
		return vmVolumeProxy.getByUUID(volumeUUID, true, true, true, true);
	}

	VmImageBack getImageBack(String volumeUUID) throws Exception{
		return vmImageBackProxy.getByUuid(volumeUUID, true, true, true, true);
	}

	List<? extends VmImageBack> getImageBackByInstanceUuid(String instanceUuid) throws Exception{
		return vmImageBackProxy.getByInstanceUuid(instanceUuid);
	}

	VmImageBack getImageBack(String volumeUUID,boolean withCluster, boolean withZone, boolean withInstance,boolean withHost) throws Exception{
		return vmImageBackProxy.getByUuid(volumeUUID, true, true, true, true);
	}

	public VmImageBack getImageBack(String instanceUuid,boolean isTop) throws Exception {
		return vmImageBackProxy.getByInstanceUuidAndTop(instanceUuid,isTop);
	}

	List<? extends VmSnapshot> getSnapshots(Object volume) throws Exception{
		Integer userId;
		String volumeUuid;
		if ( volume instanceof VmVolume) {
			userId = ((VmVolume) volume).getUserId();
			volumeUuid = ((VmVolume) volume).getVolumeUuid();
		}
		else if (volume instanceof VmImageBack) {
			userId = ((VmImageBack) volume).getUserId();
			volumeUuid = ((VmImageBack) volume).getVolumeUuid();
		}
		else return null;
		QueryObject<VmSnapshot> queryObject = new QueryObject<VmSnapshot>();
		queryObject.addFilterBean(new FilterBean<VmSnapshot>("userId", userId,
				FilterBeanType.EQUAL));
		queryObject.addFilterBean(new FilterBean<VmSnapshot>("volumeUuid", volumeUuid,
				FilterBeanType.EQUAL));
		List<? extends VmSnapshot> vmSnapshots= vmSnapshotProxy.searchAll(queryObject, false);
		return vmSnapshots;
	}

//	List<? extends VmSnapshot> getSnapshots(VmImageBack imageBack) throws Exception{
//		QueryObject<VmSnapshot> queryObject = new QueryObject<VmSnapshot>();
//		queryObject.addFilterBean(new FilterBean<VmSnapshot>("userId", imageBack.getUserId(),
//				FilterBeanType.EQUAL));
//		queryObject.addFilterBean(new FilterBean<VmSnapshot>("volumeUuid", imageBack.getVolumeUuid(),
//				FilterBeanType.EQUAL));
//		List<? extends VmSnapshot> vmSnapshots= vmSnapshotProxy.searchAll(queryObject, false);
//		return vmSnapshots;
//	}

	VmSnapshot getSnapshot(Integer id) throws Exception{
		return vmSnapshotProxy.getById(id, true);
	}

	VmSnapshot getSnapshot(String uuid) throws Exception{
		return vmSnapshotProxy.getByUuid(uuid, true);
	}

	VmImage getImage(String imageUUID) throws Exception {
		return imageProxy.getByUuid(imageUUID);
	}

	// 实际用户申请量，非用户实现使用量
	public Integer getHostUsedDisk(String hostUuid) throws Exception {
		QueryObject<VmVolume> queryObject = new QueryObject<VmVolume>();
		queryObject.addFilterBean(new FilterBean<VmVolume>("hostUuid",
				hostUuid, FilterBeanType.EQUAL));
		queryObject.addFilterBean(new FilterBean<VmVolume>("status",
				VmVolumeStatusEnum.AVAILABLE, FilterBeanType.EQUAL));
		List<? extends VmVolume> vmVolumes = vmVolumeProxy.searchAll(
				queryObject, false, false, false, false);
		Integer volumeNum = 0;
		for (VmVolume vmVolume : vmVolumes) {
			volumeNum += vmVolume.getSize();
		}
		return volumeNum;
	}

	List<? extends VmImage> getImageByMd5sum(String md5sum) throws Exception {
		System.out.println(md5sum + "  length:" + md5sum.length());
		QueryObject<VmImage> queryObject = new QueryObject<VmImage>();
		queryObject.addFilterBean(new FilterBean<VmImage>("md5sum", md5sum, FilterBeanType.EQUAL));
		return imageProxy.searchAll(queryObject);
	}
	public static void main(String[] args)throws Exception {
		String md5sum = "9623563a679fe92eea985fd044e61468";
		System.out.println(md5sum);

		List<? extends VmImage> vmImageList = new DBUtil().getImageByMd5sum(md5sum);
//		 List<Integer> groupIds = new ArrayList<Integer>();
//		 if(vmImageList != null && vmImageList.size()>0){
//			 for(VmImage vmImage : vmImageList) {
//				 System.out.println("groupId:"+vmImage.getGroupId());
//				 groupIds.add(vmImage.getGroupId());
//			 }
//		 }
		 System.out.println("groupIds size:"+vmImageList.size());
	}
	List<? extends VmVolume> getVmvolumeByHostAndImage(String hostUUID, String imageUUID) throws Exception {
		QueryObject<VmVolume> queryObject = new QueryObject<VmVolume>();
		queryObject.addFilterBean(new FilterBean<VmVolume>("hostUuid", hostUUID, FilterBeanType.EQUAL));
		queryObject.addFilterBean(new FilterBean<VmVolume>("imageUuid", imageUUID, FilterBeanType.RIGHT_LIKE));
		queryObject.addFilterBean(new FilterBean<VmVolume>("status", VmVolumeStatusEnum.DELETED, FilterBeanType.NOTEQUAL));
		return vmVolumeProxy.searchAll(queryObject, false, false, false, false);
	}
	void save(VmVolume volume) throws Exception{
		vmVolumeProxy.save(volume);
	}
	void save(VmImageBack imageBack) throws Exception{
		vmImageBackProxy.save(imageBack);
	}
	void save(VmSnapshot s) throws Exception{
		vmSnapshotProxy.save(s);
	}

	void deleteVolume(Integer id) throws Exception {
			vmVolumeProxy.deleteById(id);
	}
	void deleteImageBack(Integer id) throws Exception {
		vmImageBackProxy.deleteById(id);
	}
	void deleteSnapshot(Integer id) throws Exception {
		vmSnapshotProxy.deleteById(id);
	}
	void deleteImage(Integer id) throws Exception {
		imageProxy.deleteById(id);
	}

	public List<Service> getRunningServiceList(int zoneID, int clusterID, Service.ServiceTypeEnum hostType) {
		//获取某种类型的、属于某个zone的host List
		QueryObject<Service> queryObject = new QueryObject<Service>();
		FilterBean<Service> fb1 = new FilterBean<Service>("zoneId", zoneID, FilterBeanType.EQUAL);
		if (clusterID > -1) {
			FilterBean<Service> fb2 = new FilterBean<Service>("clusterId", clusterID, FilterBeanType.EQUAL);
			queryObject.addFilterBean(fb2);
		}
		FilterBean<Service> fb3 = new FilterBean<Service>("type", hostType, FilterBeanType.EQUAL);
		FilterBean<Service> fb4 = new FilterBean<Service>("serviceStatus", ServiceStatus.RUNNING, FilterBeanType.EQUAL);

		queryObject.addFilterBean(fb4);
		queryObject.addFilterBean(fb3);
		queryObject.addFilterBean(fb1);

		List<Service> hostList = (List<Service>) serviceProxy.searchAll(queryObject, true);

		return hostList;
	}

	public HostLoad getFreshHostLoad(String hostUUID) {
		HostLoad hostLoad = hostLoadProxy.getLatestLoad(hostUUID);
		return hostLoad;
	}

	public ResourceStrategy getDiskResourceStrategy(Integer id) throws Exception {
		String resourceStrategyUuid = clusterProxy.getById(id, false, false, false).getResrcStrategyUuid();
		QueryObject<ResourceStrategy> queryObject = new QueryObject<ResourceStrategy>();
		queryObject.addFilterBean(new FilterBean<ResourceStrategy>("uuid", resourceStrategyUuid, FilterBeanType.EQUAL));
		queryObject.addFilterBean(new FilterBean<ResourceStrategy>("type", StrategyTypeEnum.DISK, FilterBeanType.EQUAL));
		List<? extends ResourceStrategy> resourceStrategys = resourceStrategyProxy.searchALL(queryObject);
		if (resourceStrategys != null && resourceStrategys.size() != 0) {
			return resourceStrategys.get(0);
		}
		return null;
	}

	public Cluster getClusterById(Integer id) throws Exception {
		Cluster cluster = clusterProxy.getById(id, false, false, false);
		return cluster;
	}

	public VmGroup getVmGroupById(Integer groupId) throws Exception {
		return vmGroupProxy.getById(groupId);
	}
}