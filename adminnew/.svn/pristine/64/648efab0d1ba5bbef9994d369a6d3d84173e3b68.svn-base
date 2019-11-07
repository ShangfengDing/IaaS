package appcloud.admin.action.vm;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.appcloud.vm.fe.manager.VmHdEndtimeManager;
import com.appcloud.vm.fe.model.VmHdEndtime;
import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.api.beans.AcAggregate;
import appcloud.api.beans.AcHost;
import appcloud.api.beans.AcUser;
import appcloud.api.beans.AvailabilityZone;
import appcloud.api.beans.IP;
import appcloud.api.beans.Network;
import appcloud.api.beans.Server;
import appcloud.api.client.AcAggregateClient;
import appcloud.api.client.AcHostClient;
import appcloud.api.client.AcUserClient;
import appcloud.api.client.AggregateClient;
import appcloud.api.client.ServerClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.users.UsersAPI;
import com.appcloud.vm.fe.util.ClientFactory;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

public class SearchVmAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String tenantId = "admin";
	private Logger logger = Logger.getLogger(SearchVmAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();
	private AcHostClient hostClient = ClientFactory.getHostClient();
	private AcUserClient userClient = ClientFactory.getAcUserClient();
	
	private int page = 1;
    private final static int PAGESIZE = 10;
    private int total = 0;
    private int lastpage = 1;
	
    private String email = "";
    private String name = "";
    private String status = "";
    private String uuid = "";
    private String zoneId;
    private String clusterId;
    private String hostId = "";
    private String ip = "";
    private String starttime = "";
    private String endtime = "";
    
    private int isEmail = 0;
    
	private List<Server> servers = new ArrayList<Server>();
	private List<Server> oldservers = new ArrayList<Server>();
	private Map<String,List<IP>> privateIpMap = new HashMap<String, List<IP>>();
	private Map<String,List<IP>> publicIpMap = new HashMap<String, List<IP>>();
	private HashMap<Integer, String> zoneIdNameMap = new HashMap<Integer, String>();
	private HashMap<Integer, String> clusterIdNameMap = new HashMap<Integer, String>();
	private HashMap<String, String> hostIdNameMap = new HashMap<String, String>();
	private HashMap<String, String> statusMap = new HashMap<String, String>();
	private HashMap<String, String> timeMap = new HashMap<String, String>();
	private HashMap<String, String> emailMap = new HashMap<String, String>();
	private StringBuffer searchCondition = new StringBuffer("");
	private HashMap<String,Integer> vmEndTimeMap = new HashMap<String,Integer>();

	public void countCloudClient(){
		page=0;
		Integer zid=null;
		Integer cid=null;
		Date startDate = null;//构造日期
		Date endDate = null;
		DateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer userIds = new StringBuffer("");
		if(email != null && !email.equals("")) {
			searchCondition.append("用户邮箱:"+email+", ");
			List<AcUser> users = userClient.search(null, email, null, null, null, null);
			if(users != null && users.size() != 0) {
				for(AcUser tmp : users){
					userIds.append(tmp.userId+",");
				}
			} else {
			}
		}

		int activetotal = Integer.valueOf(serverClient.countByProperties(tenantId, userIds.toString(), uuid,
				name,
				"active",
				zid, cid, hostId, ip, startDate, endDate).toString());
		int buildingtotal = Integer.valueOf(serverClient.countByProperties(tenantId, userIds.toString(), uuid,
				name,
				"building",
				zid, cid, hostId, ip, startDate, endDate).toString());
		int stoppedtotal = Integer.valueOf(serverClient.countByProperties(tenantId, userIds.toString(), uuid,
				name,
				"stopped",
				zid, cid, hostId, ip, startDate, endDate).toString());
		int errortotal = Integer.valueOf(serverClient.countByProperties(tenantId, userIds.toString(), uuid,
				name,
				"error",
				zid, cid, hostId, ip, startDate, endDate).toString());
		int suspendedtotal = Integer.valueOf(serverClient.countByProperties(tenantId, userIds.toString(), uuid,
				name,
				"suspended",
				zid, cid, hostId, ip, startDate, endDate).toString());
		//返回
		try{
			Integer total = Integer.valueOf(serverClient.countByProperties(tenantId, userIds.toString(),
					uuid,
					name,
					status,
					zid, cid, hostId, ip, startDate, endDate).toString());
			HttpServletResponse responses = ServletActionContext.getResponse();
			responses.setCharacterEncoding("utf-8");
			PrintWriter writer = responses.getWriter();
			Map<String,Integer> map = new HashMap<>();
			map.put("active",activetotal);
			map.put("building",buildingtotal);
			map.put("stopped",stoppedtotal);
			map.put("error",errortotal);
			map.put("suspended",suspendedtotal);
			String json = JSON.toJSONString(map);
                        writer.write(json);
                        writer.flush();
                        writer.close();
			}catch (Exception e) {
				e.printStackTrace();
			}

		}

	public String execute() {
		// 准备条件
		Date startDate = null;//构造日期
		Date endDate = null;
		DateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer userIds = new StringBuffer("");
		if (email != null && !email.equals("")) {
			searchCondition.append("用户邮箱:" + email + ", ");
			List<AcUser> users = userClient.search(null, email, null, null, null, null);
			if (users != null && users.size() != 0) {
				for (AcUser tmp : users) {
					userIds.append(tmp.userId + ",");
				}
			} else {
				return SUCCESS;
			}
		}
		try {
			if (!starttime.equalsIgnoreCase("")) {
				startDate = time.parse(starttime);
				logger.info(startDate.toString());
			}
			if (!endtime.equalsIgnoreCase("")) {
				endDate = time.parse(endtime);
				logger.info(endDate.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("获取时间失败");
		}

		Integer zid = null;
		Integer cid = null;

		if (!zoneId.equalsIgnoreCase("")) {
			searchCondition.append("数据中心:" + zoneClient.getZoneById(Integer.parseInt(zoneId)).name + ", ");
			zid = Integer.parseInt(zoneId);
		}
		if (!clusterId.equalsIgnoreCase("")) {
			cid = Integer.parseInt(clusterId);
			searchCondition.append("集群:" + zoneClient.get(cid).name + ", ");
		}
		search(searchCondition, name, "主机名称");
		search(searchCondition, uuid, "主机标识");
		search(searchCondition, status, "主机状态");
		if (hostId != null && !hostId.equals("")) {
			searchCondition.append("节点：" + hostClient.get(hostId).ip + ", ");
		}
		search(searchCondition, ip, "主机IP");
		search(searchCondition, starttime, "起始时间");
		search(searchCondition, endtime, "结束时间");
		oldservers = serverClient.searchByProperties(tenantId, userIds.toString(), uuid, name, status,
				zid, cid, hostId, ip, startDate, endDate, page, PAGESIZE);
		if (startDate != null) {
		    for (int i = 0;i < oldservers.size();i++){
                        if (oldservers.get(i).created.getTime() >= startDate.getTime()) {
                            servers.add(oldservers.get(i));
                        }
                    }
                    total = servers.size();
                }else{
		    servers = oldservers;
                    total = Integer.valueOf(serverClient.countByProperties(tenantId, userIds.toString(), uuid, name,
                            status,
                            zid, cid, hostId, ip, startDate, endDate).toString());
                    logger.info(total);
                }

		//返回
		if(total % PAGESIZE == 0){
    		lastpage = total / PAGESIZE;
    	}else{
    		lastpage = total / PAGESIZE + 1;
    	}
		if(servers == null){
			return SUCCESS;
		}
		List<AvailabilityZone> zones = zoneClient.getZones();
		for (AvailabilityZone zone : zones) {
			zoneIdNameMap.put(zone.id, zone.name);
		}
		List<AcAggregate> aggregates = zoneClient.getAggregates();
		for(AcAggregate acAggregate: aggregates){
			clusterIdNameMap.put(acAggregate.id, acAggregate.name);
		}
		List<AcHost> hosts = hostClient.getAcHosts();
		for(AcHost acHost: hosts){
			hostIdNameMap.put(acHost.id, acHost.ip);
		}
		for (Server server : servers) {
			//读取公网和内网IP地址
			String serverId = server.id;
			for(Network address: server.addresses){
				if (address.id.equals("private")) {
					privateIpMap.put(serverId, address.ips);
				} else {
					publicIpMap.put(serverId, address.ips);
				}
			}
			
			//时间改为string
			Date createTime = server.created;
			if(!timeMap.containsKey(serverId)){
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateStr = sdf.format(createTime); 
				timeMap.put(serverId, dateStr);
			}
			
			//状态改为中文
			String status = server.status;
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
					statusMap.put(status, "未知");
				}
			}
			
			//获取用户邮箱
			String userId = server.tenantId;
			if(!emailMap.containsKey(userId)){
				emailMap.put(userId, userClient.get(server.tenantId).getUserEmail());
			}
			VmHdEndtime vmEndtime = new VmHdEndtimeManager().getVmEndtimeByUuid(serverId);
			vmEndTimeMap.put(serverId,vmEndtime.getId());

		}
		if(searchCondition.length() == 0) {
			searchCondition.append("查询全部");
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "查询云主机", "查询条件："+searchCondition, "SearchVmAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
	}
	
	private void search(StringBuffer sb, String name, String des) {
		if (name != null && !name.equals("")) {
			sb.append(des+":"+name+", ");
		}
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public int getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(int isEmail) {
		this.isEmail = isEmail;
	}

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

	public Map<String, List<IP>> getPrivateIpMap() {
		return privateIpMap;
	}

	public void setPrivateIpMap(Map<String, List<IP>> privateIpMap) {
		this.privateIpMap = privateIpMap;
	}

	public Map<String, List<IP>> getPublicIpMap() {
		return publicIpMap;
	}

	public void setPublicIpMap(Map<String, List<IP>> publicIpMap) {
		this.publicIpMap = publicIpMap;
	}

	public HashMap<Integer, String> getZoneIdNameMap() {
		return zoneIdNameMap;
	}

	public void setZoneIdNameMap(HashMap<Integer, String> zoneIdNameMap) {
		this.zoneIdNameMap = zoneIdNameMap;
	}

	public HashMap<Integer, String> getClusterIdNameMap() {
		return clusterIdNameMap;
	}

	public void setClusterIdNameMap(HashMap<Integer, String> clusterIdNameMap) {
		this.clusterIdNameMap = clusterIdNameMap;
	}

	public HashMap<String, String> getHostIdNameMap() {
		return hostIdNameMap;
	}

	public void setHostIdNameMap(HashMap<String, String> hostIdNameMap) {
		this.hostIdNameMap = hostIdNameMap;
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

	public HashMap<String, String> getEmailMap() {
		return emailMap;
	}

	public void setEmailMap(HashMap<String, String> emailMap) {
		this.emailMap = emailMap;
	}

	public HashMap<String, Integer> getVmEndTimeMap() {
		return vmEndTimeMap;
	}

	public void setVmEndTimeMap(HashMap<String, Integer> vmEndTimeMap) {
		this.vmEndTimeMap = vmEndTimeMap;
	}
}
