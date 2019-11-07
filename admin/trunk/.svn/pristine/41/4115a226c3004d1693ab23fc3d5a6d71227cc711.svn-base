package appcloud.admin.action.hd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.Volume;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.ServerClient;
import appcloud.api.client.VolumeClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import com.appcloud.vm.fe.users.UsersAPI;
import com.appcloud.vm.fe.util.ClientFactory;

public class SearchHdAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(HdListAction.class);
	private VolumeClient volumeClient = ClientFactory.getVolumeClient();
	private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();
	private ServerClient serverClient = ClientFactory.getServerClient();
	
	private static final String tenantId = "admin";
	private static final String usageType = "network";
	private int page = 1;
    private final static int PAGESIZE = 10;
    private int total = 0;
    private int lastpage = 1;
    private int isEmail = 0;
	
    private String email = "";
    private String name = "";
    private String status = "";
    private String attachstatus = "";
    private String uuid = "";
    private String zoneId ="";
    private String vmuuid = "";
    
	private List<Volume> volumes = new ArrayList<Volume>();
	private HashMap<String, String> volumeIdServerNameMap = new HashMap<String, String>();
	private HashMap<String, String> zoneIdNameMap = new HashMap<String, String>();
	private HashMap<String, String> statusMap = new HashMap<String, String>();
	private HashMap<String, Integer> endtimeMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> uidMap = new HashMap<String, Integer>();
	private HashMap<String, String> timeMap = new HashMap<String, String>();
	
	private StringBuffer searchContion = new StringBuffer("");
	
	public String execute() {
		Integer uid = null;
		logger.info("email:"+email);
		if(!email.equalsIgnoreCase("")){//根据email查找用户userid
			uid = UsersAPI.getUidByEmail(email);
			searchContion.append("用户邮箱："+email+", ");
			logger.info(uid);
			if(uid.toString().equalsIgnoreCase("-1")){
				isEmail = 1;
				return SUCCESS;
			}
		}
		
		Integer zid = null;
		String userId = null;
		if(!zoneId.equalsIgnoreCase("")){
			zid = Integer.parseInt(zoneId);
			searchContion.append("数据中心："+zoneClient.getZoneById(zid).name+", ");
		}
		if(uid != null){
			userId = uid.toString();
		}
		
		logger.info("uuid:"+uuid+"; userId:"+userId+"; vmuuid:"+vmuuid+"; stautus:"+status+
				"; attachstatus:"+attachstatus+"; name:"+name+"; zid:"+zid);
		searchContion = search(searchContion, name, "硬盘名称");
		searchContion = search(searchContion, uuid, "硬盘标识");
		searchContion = search(searchContion, status, "硬盘状态");
		searchContion = search(searchContion, attachstatus, "挂载状态");
		searchContion = search(searchContion, vmuuid, "虚拟机标识");
		total = Integer.valueOf(volumeClient.countByProperties(tenantId, uuid, 
				userId, vmuuid, usageType, status, attachstatus, false,
				name, zid, null, null).toString());
		
		logger.info("total"+total);
		//返回
		if(total % PAGESIZE == 0){
    		lastpage = total / PAGESIZE;
    	}else{
    		lastpage = total / PAGESIZE + 1;
    	}
		
		volumes = volumeClient.searchByProperties(tenantId, uuid, 
				userId, vmuuid, usageType, status, attachstatus, false,
				name, zid, null, null, page, PAGESIZE);
		
		if(volumes == null || volumes.size() == 0){
			return SUCCESS;
		}
		
		logger.info("读取用户硬盘列表成功， 硬盘数为："+volumes.size());
		
		HashMap<String, String> serverIdNameMap = new HashMap<String, String>();
		serverIdNameMap.put(null, "无");
		for (Volume volume : volumes) {
			//查询硬盘所属的数据中心名称
			String zoneId = volume.availabilityZone;
			if (!zoneIdNameMap.containsKey(zoneId)) {
				String zoneName = zoneClient.getZoneById(Integer.valueOf(zoneId)).name;
				zoneIdNameMap.put(zoneId, zoneName);
			}

			//查询硬盘挂载的虚拟机
			String device = volume.attachments.device;
			String serverName = "无";
			if (!device.contains("null")) {	//硬盘现在是挂载状态
				String serverId = volume.attachments.serverId;
				serverName = serverIdNameMap.get(serverId);
				if (serverName == null) {
					serverName = serverClient.get(userId, serverId).name;
					serverIdNameMap.put(serverId, serverName);
				}
			}
			volumeIdServerNameMap.put(volume.id, serverName);
			
			String volumeStatus = volume.status;
			if(!statusMap.containsKey(volumeStatus)){
				if(volumeStatus.equalsIgnoreCase("available")){
					statusMap.put(volumeStatus, "正常");
				}else if(volumeStatus.equalsIgnoreCase("creating")){
					statusMap.put(volumeStatus, "创建中");
				}else if(volumeStatus.equalsIgnoreCase("defined")){
					statusMap.put(volumeStatus, "格式化");
				}else if(volumeStatus.equalsIgnoreCase("deleted")){
					statusMap.put(volumeStatus, "已删除");
				}else if(volumeStatus.equalsIgnoreCase("deleting")){
					statusMap.put(volumeStatus, "删除中");
				}else if(volumeStatus.equalsIgnoreCase("error")){
					statusMap.put(volumeStatus, "故障");
				}else if(volumeStatus.equalsIgnoreCase("error_deleting")){
					statusMap.put(volumeStatus, "删除错误");
				}else{
					statusMap.put(volumeStatus, "未知错误");
				}
			}
			
			//时间改为string
			Date createTime = volume.createdAt;
			if(!timeMap.containsKey(volume.id)){
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = sdf.format(createTime); 
				timeMap.put(volume.id, dateStr);
			}
		}
		
		/*读取硬盘截止时间*/
		List<VmHdEndtime> endtimes = new ArrayList<VmHdEndtime>();
		if(uid != null){
			endtimes = new VmHdEndtimeManager().getHdEndtimeByUserId(uid);
		}else{
			endtimes = new VmHdEndtimeManager().getAllHdEndtime();
		}
		for (VmHdEndtime endtime : endtimes) {
			endtimeMap.put(endtime.getUuid(), endtime.getId());
			uidMap.put(endtime.getUuid(), endtime.getUserId());
		}
		if (searchContion.length() == 0) {
			searchContion.append("查询全部");
		}
		logger.info("读取硬盘所属虚拟机名称成功");
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "查询硬盘", "查询条件为："+searchContion, "SearchHdAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		
		return SUCCESS;
	}

	private StringBuffer search(StringBuffer sb, String name, String des) {
		if (name != null && !name.equals("")) {
			sb.append(des+":"+name+", ");
		}
		return sb;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public int getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAttachstatus() {
		return attachstatus;
	}

	public void setAttachstatus(String attachstatus) {
		this.attachstatus = attachstatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	
	public String getVmuuid() {
		return vmuuid;
	}

	public void setVmuuid(String vmuuid) {
		this.vmuuid = vmuuid;
	}
	
	public List<Volume> getVolumes() {
		return volumes;
	}

	public void setVolumes(List<Volume> volumes) {
		this.volumes = volumes;
	}
	
	public HashMap<String, String> getVolumeIdServerNameMap() {
		return volumeIdServerNameMap;
	}

	public void setVolumeIdServerNameMap(
			HashMap<String, String> volumeIdServerNameMap) {
		this.volumeIdServerNameMap = volumeIdServerNameMap;
	}
	
	public HashMap<String, String> getZoneIdNameMap() {
		return zoneIdNameMap;
	}

	public void setZoneIdNameMap(HashMap<String, String> zoneIdNameMap) {
		this.zoneIdNameMap = zoneIdNameMap;
	}
	
	public HashMap<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(HashMap<String, String> statusMap) {
		this.statusMap = statusMap;
	}
	
	public HashMap<String, String> getTimeMap() {
		return timeMap;
	}

	public void setTimeMap(HashMap<String, String> timeMap) {
		this.timeMap = timeMap;
	}
	
	
	public HashMap<String, Integer> getUidMap() {
		return uidMap;
	}

	public void setUidMap(HashMap<String, Integer> uidMap) {
		this.uidMap = uidMap;
	}
	
	public HashMap<String, Integer> getEndtimeMap() {
		return endtimeMap;
	}

	public void setEndtimeMap(HashMap<String, Integer> endtimeMap) {
		this.endtimeMap = endtimeMap;
	}
}
