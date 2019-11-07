package com.appcloud.vm.action.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import appcloud.api.beans.IP;
import appcloud.api.beans.Network;
import appcloud.api.beans.Server;
import appcloud.api.client.ServerClient;

import com.appcloud.vm.action.BaseAction;
import com.appcloud.vm.fe.util.ClientFactory;

public class VmListByUidAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(VmListByUidAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	/*private AcAggregateClient zoneClient = ClientFactory.getAggregateClient();*/
	
	private List<Server> servers = new ArrayList<Server>();
	private HashMap<String, String> statusMap = new HashMap<String, String>();
	private HashMap<String,List<IP>> privateIpMap = new HashMap<String, List<IP>>();
	private HashMap<String,List<IP>> publicIpMap = new HashMap<String, List<IP>>();
	/*private HashMap<Integer, String> zoneIdNameMap = new HashMap<Integer, String>();*/
	
	public String execute() {
		Integer devId = this.getSessionUserID();
		servers = serverClient.getServers(devId.toString(), true);
		logger.info("获得所有server成功");
		/*zoneIdNameMap.put(null, "");*/
		for (Server server : servers) {
			// 读取公网和内网IP地址
			String serverId = server.id;
			for(Network address: server.addresses){
				if (address.id.equals("private")) {
					if(address.ips == null){
						privateIpMap.put(serverId, new ArrayList<IP>());
					}else{
						privateIpMap.put(serverId, address.ips);
					}
				} else {
					if(address.ips == null){
						publicIpMap.put(serverId, new ArrayList<IP>());
					}else{
						publicIpMap.put(serverId, address.ips);
					}
				}
			}
			// 读取server所在的数据中心
			/*Integer zoneId = server.availabilityZone;
			if (!zoneIdNameMap.containsKey(zoneId)) {
				String zoneName = zoneClient.getZoneById(zoneId).name;
				zoneIdNameMap.put(zoneId, zoneName);
				logger.info("读取server所在的zone成功");
			}*/
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
					server.status = "other";
					statusMap.put(server.status, "任务进行中");
				}
			}
		}
		return SUCCESS;
	}

	public List<Server> getServers() {
		return servers;
	}

	public HashMap<String, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(HashMap<String, String> statusMap) {
		this.statusMap = statusMap;
	}

	public Map<String, List<IP>> getPrivateIpMap() {
		return privateIpMap;
	}

	public void setPrivateIpMap(HashMap<String, List<IP>> privateIpMap) {
		this.privateIpMap = privateIpMap;
	}

	public Map<String, List<IP>> getPublicIpMap() {
		return publicIpMap;
	}

	public void setPublicIpMap(HashMap<String, List<IP>> publicIpMap) {
		this.publicIpMap = publicIpMap;
	}

	/*public HashMap<Integer, String> getZoneIdNameMap() {
		return zoneIdNameMap;
	}

	public void setZoneIdNameMap(HashMap<Integer, String> zoneIdNameMap) {
		this.zoneIdNameMap = zoneIdNameMap;
	}*/

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}

}
