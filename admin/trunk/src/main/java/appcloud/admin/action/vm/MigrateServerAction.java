package appcloud.admin.action.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import appcloud.admin.action.base.BaseAction;
import appcloud.admin.common.Constants;
import appcloud.api.beans.Resource;
import appcloud.api.beans.Server;
import appcloud.api.client.AcHostClient;
import appcloud.api.client.ServerClient;
import appcloud.api.enums.AcLogLevelEnum;
import appcloud.api.enums.AcModuleEnum;

import com.appcloud.vm.fe.util.ClientFactory;
import appcloud.api.beans.AcHost;
public class MigrateServerAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MigrateServerAction.class);
	private ServerClient serverClient = ClientFactory.getServerClient();
	private AcHostClient hostClient = ClientFactory.getHostClient();
	
	private String serverId;		//虚拟机uuid
	private String userId;			//虚拟机的用户id
	
	private Server server = null;
	private List<AcHost> hostlist = new ArrayList<AcHost>();
	private HashMap<String, Resource> hostIdResourceMap = 
			new HashMap<String, Resource>();		//host的资源配置详情
	private Integer hosts_num = 0;    //某个集群上的节点数
	private String hostsId = "";   //获取当前
	private String  hostFlag = "";
	private StringBuffer searchCondition = new StringBuffer("");
	
	private String tenantId="admin";  //目前只给管理员提供迁移虚拟机的接口
	private String hostUuid;
	private boolean status;
	private String type;//云主机迁移的种类 online：在线迁移，offline：离线迁移
	public String execute(){
		userId = this.getUsername();
		logger.info(userId);
		if(userId != null && !userId.equals("")){
			searchCondition.append("用户ID:"+userId+", ");
		}
		if(serverId != null && !serverId.equals("")){
			searchCondition.append("虚拟机ID:"+serverId+", ");
		}
		server = serverClient.get(userId, serverId);
		if(server != null){
			hostlist = hostClient.getAcHostOfAggregate(server.aggregate);
		}
		if(hostlist!=null){
			List<AcHost> temlist = new ArrayList<AcHost>();
			for (AcHost host : hostlist){
				//云主机只能迁移到计算节点上
				if(host.hostType.toString().equals(Constants.HOST_TYPE)){
					temlist.add(host);
				}
			}
			hostlist = temlist;
		}
		
		if(hostlist != null){
			Integer index = 0;
			for (AcHost host : hostlist){
				for(Resource resource : host.resources){
					if (resource.project.equals("(total)")) {
						hostIdResourceMap.put(host.id, resource);
						break;
					}
				}
				hostsId = hostsId + host.id + ";";
				if(server.hostId.equalsIgnoreCase(host.id)){
					hostFlag=index+"";
				}
				index++;
			}
			hosts_num = hostlist.size();
			hostsId = hostsId.substring(0, hostsId.length()-1);
		}
		this.addAcMessageLog(AcModuleEnum.VM_ADMIN, UUID.randomUUID().toString().replace("-", ""),
				this.getUserId(), null, "迁移云主机", "查询条件："+searchCondition, "migrateServerAction.class", 
				null, AcLogLevelEnum.INFO, new Date(System.currentTimeMillis()));
		return SUCCESS;
		
	}
	
	public String migrateServer() {
		Server mig_server;
		try{
			mig_server = serverClient.migrateServer(tenantId, serverId, hostUuid);
			logger.info(mig_server);
			if (mig_server == null){
				setStatus(false);
				return SUCCESS;
			}else{
				setStatus(true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info(status);
		return SUCCESS;
	}
	public String onlineMigrateServer() {
		Server mig_server;
		logger.info("try to onlineMigrateServer");
		try{
			mig_server = serverClient.onlineMigrateServer(tenantId, serverId, hostUuid);
			logger.info(mig_server);
			if (mig_server == null){
				setStatus(false);
				return SUCCESS;
			}else{
				setStatus(true);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info(status);
		return SUCCESS;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public List<AcHost> getHostlist() {
		return hostlist;
	}

	public void setHostlist(List<AcHost> hostlist) {
		this.hostlist = hostlist;
	}

	public HashMap<String, Resource> getHostIdResourceMap() {
		return hostIdResourceMap;
	}

	public void setHostIdResourceMap(HashMap<String, Resource> hostIdResourceMap) {
		this.hostIdResourceMap = hostIdResourceMap;
	}

	public Integer getHosts_num() {
		return hosts_num;
	}

	public void setHosts_num(Integer hosts_num) {
		this.hosts_num = hosts_num;
	}

	public String getHostsId() {
		return hostsId;
	}
	public void setHostsId(String hostsId) {
		this.hostsId = hostsId;
	}
	public String getHostFlag() {
		return hostFlag;
	}
	public void setHostFlag(String hostFlag) {
		this.hostFlag = hostFlag;
	}
	public String getHostUuid() {
		return hostUuid;
	}

	public void setHostsUuid(String hostUuid) {
		this.hostUuid = hostUuid;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
