package appcloud.admin.action.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
	
	public String execute() {
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
	
}
