package appcloud.admin.action.vm;

import java.sql.Connection;
import java.util.*;

import appcloud.common.model.VmUser;
import appcloud.common.model.VmVolume;
import appcloud.common.model.VmZone;
import appcloud.common.proxy.VmUserProxy;
import appcloud.common.proxy.VmVolumeProxy;
import appcloud.common.proxy.VmZoneProxy;
import appcloud.common.util.ConnectionFactory;
import appcloud.openapi.client.InstanceClient;
import appcloud.openapi.datatype.DiskDetailItem;
import appcloud.openapi.datatype.InstanceAttributes;
import appcloud.openapi.response.DescribeInstancesResponse;
import appcloud.openapi.response.DisksDetailReponse;
import appcloud.openapi.response.SnapshotsDetailReponse;
import com.appcloud.vm.fe.common.TransformAttribute;
import com.appcloud.vm.fe.entity.DiskInventory;
import com.appcloud.vm.fe.entity.InstanceDetail;
import com.appcloud.vm.fe.manager.AppkeyManager;
import com.appcloud.vm.fe.model.Appkey;
import com.appcloud.vm.fe.util.OpenClientFactory;
import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.Backup;
import appcloud.api.beans.Flavor;
import appcloud.api.beans.IP;
import appcloud.api.beans.Image;
import appcloud.api.beans.Network;
import appcloud.api.beans.Server;
import appcloud.api.beans.Snapshot;
import appcloud.api.beans.Volume;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.FlavorClient;
import appcloud.api.client.ImageClient;
import appcloud.api.client.ServerClient;
import appcloud.api.client.SnapshotClient;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.common.Constants;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.appcloud.vm.fe.util.ClientFactory;


public class VmDetailAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(VmDetailAction.class);
	private ImageClient imageClient = ClientFactory.getImageClient();
	private FlavorClient flavorClient = ClientFactory.getFlavorClient();
	private ServerClient serverClient = ClientFactory.getServerClient();
	private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();
	private SnapshotClient snapshotClient = ClientFactory.getSnapshotClient();
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();


	public static String VOLUME_DETACHED = "快照所属硬盘未挂载，不进行显示";
	public static String VOLUME_ATTACH_STATUS = "ATTACHED";
	
	private String serverId;		//虚拟机uuid
	private String userId;			//虚拟机的用户id
	
	private Server server = null;
	private Image image = null;
	private Flavor flavor = null;
	private String zoneName = "";
	private List<IP> publicIps = new ArrayList<IP>();
	private List<IP> privateIps = new ArrayList<IP>();
	private HashMap<String, String> statusMap = new HashMap<String, String>();
	private VmHdEndtime vmEndtime;
	private List<Snapshot> snapshots = new ArrayList<Snapshot>();//快照列表
	private List<Backup> backups = new ArrayList<Backup>();
	private List<Volume> volumes = new ArrayList<Volume>();
	private StringBuffer networkStr = new StringBuffer(); //为了写日志
	private Integer clusterId;

	private String regionId;
	private InstanceClient instanceClient = OpenClientFactory.getInstanceClient();
	private TransformAttribute transform = new TransformAttribute();
	private appcloud.openapi.client.VolumeClient openVolumeClient=OpenClientFactory.getVolumeClient();
	private List<String> diskSnapshotIds = new ArrayList<String>();
	private List<DiskInventory> diskInventories = new ArrayList<DiskInventory>();
	private List<Integer> instanceType = new ArrayList<Integer>();//主机的规格，Cpu、内存、硬盘、带宽
	private InstanceDetail details;
	private String appname;
	private AppkeyManager appkeyManager = new AppkeyManager();
	private String zoneId;

	private Map<String,Long> snapShotCountMap;

	public String execute() {
		VmUserProxy vmUserProxy=ConnectionFactory.getVmUserProxy();
	//	System.out.println("***********"+vmUserProxy);
		VmZoneProxy vmZoneProxy=ConnectionFactory.getVmZoneProxy();
		//TODO appname link the database

		try {
		//	System.out.println(vmVolumeProxy+"**************");
			List<VmZone> vmZonelist=(List<VmZone>)vmZoneProxy.findAll();
			VmZone vmZone=vmZonelist.get(0);
			zoneId=vmZone.getZoneId();
			regionId=vmZone.getRegionId();
			VmUser vmUser = vmUserProxy.getByUserId(Integer.parseInt(userId));
			String appKey=vmUser.getAppKeyId();
			String appSecretKey=vmUser.getAppKeySecret();
			String userEmail=vmUser.getUserEmail();
			List<Appkey> appkeys=appkeyManager.getAppkeyByUserId(Integer.parseInt(userId));
			for(Appkey appkey:appkeys){
				if(appkey.getAppkeyId().equals(appKey)){
					appname=appkey.getAppname();
					break;
				}
			}
			showYunhaiDetails(appKey,appSecretKey,userEmail,zoneId);
		}catch(Exception e){
			e.printStackTrace();
		}
		userId = this.getUsername();
		//取得server的detail
		server = serverClient.get(userId, serverId);
		logger.info("userId:"+userId+" tenanatId:"+server.tenantId);
		logger.info("serverId:"+serverId+" server.id:"+server.id);
		if(server == null){
			logger.info("server is null");
			return SUCCESS;
		}
		if(!server.metadata.containsKey("priBandwidth")){
			server.metadata.put("priBandwidth", "0");
		}
		if(!server.metadata.containsKey("oldPriBandNum")){
			server.metadata.put("oldPriBandNum", "-1");
		}
		if(!server.metadata.containsKey("oldMaxBandNum")){
			server.metadata.put("oldMaxBandNum", "-1");
		}
		String status = server.status;
		if(status == null){
			status = "error";
		}
		logger.info(status);
		if(!statusMap.containsKey(status)){
			if(status.equalsIgnoreCase("active")){
				statusMap.put(status, "运行中");
			}else if(status.equalsIgnoreCase("stopped")){
				statusMap.put(status, "已关机");
			}else if(status.equalsIgnoreCase("building")){
				statusMap.put(status, "创建中");
			}else if(status.equalsIgnoreCase("deleted")){
				statusMap.put(status, "已删除");
			}else if(status.equalsIgnoreCase("error")){
				statusMap.put(status, "故障");
			}else if(status.equalsIgnoreCase("rebuilding")){
				statusMap.put(status, "ISO装机中");
			}else if(status.equalsIgnoreCase("suspended")){
				statusMap.put(status, "已挂起");
			}else{
				server.status = "other";
				statusMap.put(server.status, "任务进行中");
			}
		}
		//取得publicIP和privateIP
		for(Network address: server.addresses){
			if (address.id.equals("private")) {
				privateIps = address.ips;
				if (privateIps.size() > 0) {
					networkStr.append("私网IP:");
					networkStr.append(privateIps.get(0).addr);
				}
			} else {
				publicIps = address.ips;
				if (publicIps.size() > 0) {
					networkStr.append(", 公网IP:");
					networkStr.append(publicIps.get(0).addr);
				}
			}
		}
		
		//取得image和flavor详情
		String imageId = server.image.id;
		if (imageId != null && !imageId.equals("")) {
			image = imageClient.get(Constants.ADMIN_TENANT_ID, imageId);
		}
		flavor = flavorClient.get(Constants.ADMIN_TENANT_ID, server.flavor.id);
		logger.info("image和flavor详情查询成功");
		
		//取得数据中心名称
		zoneName = zoneClient.getZoneById(server.availabilityZone).name;
		
		//取得虚拟机的截止时间，以及计费方式
		vmEndtime = new VmHdEndtimeManager().getVmEndtimeByUuid(serverId);

		//获取快照信息
		HashMap<String, String> volumeIdServerNameMap = new HashMap<String, String>();
		logger.info("image和flavor详情查询成功"+userId+serverId);
		List<Snapshot> srcSnapshots = snapshotClient.getSnapshotsOfServer(server.tenantId, serverId);
		for (Snapshot snapshot: srcSnapshots){
			String volumeId = snapshot.volumeId;
			logger.info("snapshot.volumeId："+volumeId);
			if (!volumeIdServerNameMap.containsKey(volumeId)){
				String serverName = getServerNameByVolumeId(server.tenantId, volumeId);
				logger.info("server name："+serverName);
				volumeIdServerNameMap.put(volumeId, serverName);
				if (!serverName.equals(VOLUME_DETACHED)) {
					snapshots.add(snapshot);
				}
			} else {
				if (!volumeIdServerNameMap.get(volumeId).equals(VOLUME_DETACHED)) {
					snapshots.add(snapshot);
				}
			}
		}
		logger.info("获取snapshots成功");

		//获取备份信息
		logger.info("image和flavor详情查询成功"+userId+serverId);
		backups = volumeClient.getBackupListOfServer(server.tenantId, serverId);
		logger.info("获取backups成功");
		
		//获取挂载在该虚拟机的硬盘
		volumes = volumeClient.searchByProperties(userId, null, server.tenantId, serverId, "NETWORK", null, 
				VOLUME_ATTACH_STATUS, false, null, null, null, null, null, null);
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				super.getUserId(), serverId, "查询云主机详情", "查询云主机: "+networkStr+"详情", "VmDetailAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}
	
	public String getServerNameByVolumeId(String devId, String volumeId) {
		VolumeClient volumeClient = ClientFactory.getVolumeClient();
		ServerClient serverClient = ClientFactory.getServerClient();
		
		Volume volume = volumeClient.get(devId, volumeId);
		if (volume.attachments.device.contains("null")) {	//硬盘未挂载
			return VOLUME_DETACHED;
		}
		try {
			String serverId = volume.attachments.serverId;
			Server server = serverClient.get(devId, serverId);
			return server.name;
		} catch (Exception e) {
			logger.error("volumeId："+volumeId+",没有找到所属主机！！！");
			return "";
		}
	}

	private void showYunhaiDetails(String appKeyId,String appSecretKey,String userEmail,String zoneId) {

		//用instanceId获得实例列表
		DescribeInstancesResponse instanceList = instanceClient.DescribeInstances(regionId.trim(), zoneId,
				"[" + serverId.trim() + "]", null, null, null, null, null, null, null, null, null,
				appKeyId, appSecretKey, userEmail);
		InstanceAttributes instance = instanceList.getInstances().get(0);
		String systemDiskId = null;
		String systemDiskName = null;
		Integer systemDiskSize = null;
		String systemDiskSnapshotId = null;
		String osType = null;
		String publicIpAddress = null;
		String description = null;
		String securityGroupId = instance.getSecurityGroupId();
		String securityGroupName = instance.getSecurityGroupName();
		List<String> systemDiskSnapshotIds = new ArrayList<String>();
		statusMap.put(instance.getStatus(), transform.transformStatus(instance.getStatus()));
		//获得主机相关的硬盘
		DisksDetailReponse diskList = openVolumeClient.DescribeDisks(regionId.trim(), zoneId, null,
				serverId.trim(), null, null, null, null, null, null, null, null, null, null,
				appKeyId,appSecretKey, userEmail);
		List<DiskDetailItem> disks = diskList.getDisks();
		logger.info("diskList");
		snapShotCountMap=new HashMap<String,Long>();
		for (DiskDetailItem disk : disks) {
			if ("SYSTEM".equals(disk.getDiskType())) {
				systemDiskId = disk.getDiskId();
				systemDiskName = disk.getDiskName();
				systemDiskSize = disk.getSize();
				systemDiskSnapshotId = disk.getSnapshotId();
				systemDiskSnapshotIds.add(systemDiskSnapshotId);
			} else {
				diskSnapshotIds.add(disk.getSnapshotId());
//				TODO
				diskInventories.add(new DiskInventory(disk.getDiskId(), disk.getDiskName(), disk.getDiskType(), disk.getSize(), diskSnapshotIds));
			}
			appcloud.openapi.client.VolumeClient volumeClient = OpenClientFactory.getVolumeClient();
			appcloud.openapi.client.SnapshotClient snapshotClient = OpenClientFactory.getSnapshotClient();
			DisksDetailReponse disksDetail = volumeClient.DescribeDisks(regionId.trim(), null, "["+disk.getDiskId().trim()+"]",
					null, null, null, null, null, null, null, null, null, null, null, appKeyId, appSecretKey, null);
			//查询快照
			SnapshotsDetailReponse snapshotsDetail = snapshotClient.DescribeSnapshot(regionId.trim(), zoneId,
					disk.getDiskId().trim(), null, null, "AVAILABLE", null, null, null, null,null,null,
					appKeyId, appSecretKey, userEmail.trim());
			snapShotCountMap.put(disk.getDiskId(),snapshotsDetail.getTotalCount());
		}
		osType = instance.getOstype();
		publicIpAddress = instance.getPublicIpAddress();
		if (publicIpAddress == null || "".equals(publicIpAddress)) publicIpAddress = "null";
		description = instance.getDescription();
		if (description == null || "".equals(description)) description = "null";
//		TODO

		details = new InstanceDetail(serverId.trim(), instance.getInstanceName(), Constants.YUN_HAI,
				transform.transformProvider(Constants.YUN_HAI), regionId.trim(), transform.transformRegion(regionId.trim()),
				instance.getZoneId(), instance.getStatus(), description, instance.getImageId(), osType, "主机规格", instance.getVcpus(),
				instance.getMemoryMb(), 0, publicIpAddress, instance.getPrivateIpAddress(),
				instance.getInstanceChargeType(), instance.getCreationTime(), instance.getExpiredTime(), systemDiskId, systemDiskName, systemDiskSize, systemDiskSnapshotIds, diskInventories, String.valueOf(securityGroupId), String.valueOf(securityGroupName),userEmail);

		//获得主机规格
		logger.info("Cup:" + instance.getVcpus() + "Memory:" + instance.getMemoryMb() + "Gb:" + instance.getLocalGb());
		//获得主机的规格
		try {
			instanceType.add(instance.getVcpus());
			instanceType.add(instance.getMemoryMb());
			instanceType.add(instance.getLocalGb());

		} catch (Exception e) {
			e.printStackTrace();
		}
		try{
			instanceType.add(Integer.parseInt(instance.getInternetMaxBandwidth()));
		}catch(Exception e){

		}



	}

	public Image getImage() {
		return image;
	}
	public Flavor getFlavor() {
		return flavor;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public Server getServer() {
		return server;
	}
	public String getZoneName() {
		return zoneName;
	}
	public List<IP> getPublicIps() {
		return publicIps;
	}
	public List<IP> getPrivateIps() {
		return privateIps;
	}
	public VmHdEndtime getVmEndtime() {
		return vmEndtime;
	}

	public HashMap<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(HashMap<String, String> statusMap) {
		this.statusMap = statusMap;
	}

	public List<Snapshot> getSnapshots() {
		return snapshots;
	}

	public void setSnapshots(List<Snapshot> snapshots) {
		this.snapshots = snapshots;
	}

	public List<Backup> getBackups() {
		return backups;
	}

	public void setBackups(List<Backup> backups) {
		this.backups = backups;
	}

	public List<Volume> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<Volume> volumes) {
		this.volumes = volumes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getClusterId() {
		return clusterId;
	}

	public void setClusterId(Integer clusterId) {
		this.clusterId = clusterId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public InstanceDetail getDetails() {
		return details;
	}

	public void setDetails(InstanceDetail details) {
		this.details = details;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public Map<String, Long> getSnapShotCountMap() {
		return snapShotCountMap;
	}

	public void setSnapShotCountMap(Map<String, Long> snapShotCountMap) {
		this.snapShotCountMap = snapShotCountMap;
	}
}
